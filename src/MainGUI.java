import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextArea citationField;
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
        citation = new Citation(text, includeDateOfAccessCheckBox.isSelected());
        citationField.setText(Characters.INDENT + citation.createCitation());
    }

    private void updateCitation(){
        citationField.setText(Characters.INDENT + citation.getCitation());
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
