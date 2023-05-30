import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextArea citationField;
    private JFormattedTextField codeField;
    private JLabel directionsLabel;

    private Citation citation;

    public MainGUI() {
        start();
    }

    public void start(){
        setContentPane(mainPanel);
        setTitle("Citation Generator");
        setSize(1024,600);
        setLocation(0,0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addListeners();

        setVisible(true);
    }

    private void addListeners(){
        codeField.addActionListener(this);
    }

    private void createCitation(String text) {
        citation = new Citation(text);
        citationField.setText(Characters.INDENT + citation.createCitation());
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object sourceObj = e.getSource();
        if (sourceObj==codeField) {
            JFormattedTextField source = (JFormattedTextField) sourceObj;
            createCitation(source.getText());
        }
    }
}
