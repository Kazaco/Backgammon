//InfoPanel Constructor
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

//InfoPanel paintComponent
import java.awt.Graphics;
import java.awt.Color;

public class InfoPanel extends JPanel
{
    private JTextArea textArea;
    private String display;

    public InfoPanel()
    {
        //Set up Border Layout
        setLayout( new BorderLayout() );

        //Text Area at top of screen to provide info to user
        textArea = new JTextArea();
        textArea.setEditable(false);

        //Source: https://tips4java.wordpress.com/2008/10/22/text-area-scrolling/
        //Require Scroll Pane to move downward with input
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //Add Scrollable bar on right
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.CENTER);
    }
    
    public void changeText(String input)
    {
        //Change what text is displayed to the user on box
        textArea.append("\n" + input);
    }
}