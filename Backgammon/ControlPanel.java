//Control Panel Constructor
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.Random;
//Control Panel paintcomponent
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.BorderFactory;
//Control Panel Event Handling
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel
{
    private DicePanel dicePanel;
    private volatile boolean hasPressedButton = false;
    private volatile boolean needsToRoll = true;

    public ControlPanel()
    {
        //Call Parent Constructor
        super();

        setLayout( new GridLayout() );

        //Set-up event listening
        RollDice handler = new RollDice();

        JButton button1 = new JButton("1");
        add(button1);

        JPanel button2 = new JPanel();
        add(button2);

        JButton button3 = new JButton("3");
        add(button3);
        button3.addActionListener( handler );

        //Far right panel
        dicePanel = new DicePanel();
        dicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(dicePanel);

    }

    private class RollDice implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Random rand = new Random();

            //Flag that the user has rolled once
            hasPressedButton = true;

            //Only allow player to roll once
            if( needsToRoll == true & hasPressedButton == true)
            {
                int d1 = rand.nextInt(6) + 1;
                int d2 = rand.nextInt(6) + 1;

                if(d1 == d2)
                {
                    //Rolled Doubles
                    dicePanel.setDiceOne(d1);
                    dicePanel.setDiceTwo(d2);
                }
                else
                {
                    //Normal Roll
                    dicePanel.setDiceOne(d1);
                    dicePanel.setDiceTwo(d2);
                }

                //Make player unable to change dice chosen
                needsToRoll = false;
            }
        }
    }

    //Accessors:
    public int getDiceOne()
    {
        return dicePanel.getDiceOne();
    }

    public int getDiceTwo()
    {
        return dicePanel.getDiceTwo();
    }

    public boolean needsToRoll()
    {
        return needsToRoll;
    }
    
    public boolean hasPressedButton()
    {
        return hasPressedButton;
    }

    //Mutators:
    public void resetRoll()
    {
        //Start a new roll
        needsToRoll = true;
        hasPressedButton = false;
    }

    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.BLACK);
    }
}
