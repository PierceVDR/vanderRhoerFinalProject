import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextPane citationField;
    // Where I found you can format the text with HTML (which I needed to do in order to italicize the title in a citation):
    // http://www.java2s.com/Tutorials/Java/Swing_How_to/JTextPane/Style_JTextPane_with_HTML_and_CSS.htm
    private JFormattedTextField codeField;
    private JLabel directionsLabel;
    private JCheckBox includeDateOfAccessCheckBox;
    private JLabel legalClaimer;

    private Citation citation;

    public MainGUI() {
        start();
    }

    public void start(){
        setContentPane(mainPanel);
        setTitle("Citationator™");
        setSize(1024,600);
        setLocation(0,0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addListeners();

        setVisible(true);
    }

    private void addListeners(){
        codeField.addActionListener(this);
        includeDateOfAccessCheckBox.addActionListener(this);
    }

    private void createCitation(String text) {
        if (text.equals("")) {
            citationField.setText("ENTER ISBN");
            return;
        }
        citation = new Citation(text, includeDateOfAccessCheckBox.isSelected());
        String citationTxt = citation.createCitation();
        if (citationTxt==null) {
            citationField.setText("INVALID ISBN");
            return;
        }
        citationField.setText(citationTxt);
    }

    private void updateCitation(){
        citationField.setText(citation.getCitation());
    }

    private String processCode(String code) {
        code=code.replaceAll("-","");
        code=code.replaceAll(" ","");
        return code;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object sourceObj = e.getSource();
        if (sourceObj==codeField) {
            createCitation( processCode(codeField.getText()) );
        } else if (sourceObj==includeDateOfAccessCheckBox) {
            if (citation!=null) {
                citation.setIncludeDateOfAccess(includeDateOfAccessCheckBox.isSelected());
                updateCitation();
            }
        }
    }
}
