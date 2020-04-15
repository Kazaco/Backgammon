//Control Panel Constructor
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
//Control Panel paintcomponent
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.BorderFactory;

public class ControlPanel extends JPanel
{

    private DicePanel dicePanel;

    public ControlPanel()
    {
        //Call Parent Constructor
        super();

        setLayout( new GridLayout() );

        JButton button1 = new JButton("1");
        add(button1);

        JPanel button2 = new JPanel();
        add(button2);

        JButton button3 = new JButton("3");
        add(button3);

        dicePanel = new DicePanel();
        dicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dicePanel.setBackground(Color.BLUE);
        add(dicePanel);
    }


    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.BLACK);
    }
}
