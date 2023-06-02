import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextPane citationField;
    private JFormattedTextField codeField;
    private JLabel directionsLabel;
    private JCheckBox includeDateOfAccessCheckBox;

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
            includeDateOfAccessCheckBox.setEnabled(false);
            citationField.setText("INVALID ISBN");
            return;
        }
        includeDateOfAccessCheckBox.setEnabled(true);
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
            citation.setIncludeDateOfAccess(includeDateOfAccessCheckBox.isSelected());
            updateCitation();
        }
    }
}
