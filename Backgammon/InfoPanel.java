//InfoPanel Constructor
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
//InfoPanel paintComponent
import java.awt.Graphics;
import java.awt.Color;

public class InfoPanel extends JPanel
{
    private JTextField textField;

    public InfoPanel()
    {
        //Set up Border Layout
        setLayout( new BorderLayout() );

        //Text field at top of screen to provide info to user
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        add(textField, BorderLayout.CENTER);
    }

    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
    }
    
    public void changeText(String input)
    {
        //Change what text is displayed to the user on box
        textField.setText(input);
    }
}