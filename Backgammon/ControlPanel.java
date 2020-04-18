//Control Panel Constructor
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
//Control Panel paintcomponent
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.BorderFactory;
//Control Panel Event Handling
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
        HelpPage hHandler = new HelpPage();

        //Far Left Panel (Pop-up Rules in HTML?)
        HelpButton button1 = new HelpButton();
        button1.addActionListener( hHandler );
        add(button1);

        //Left Middle (Empty Panel)
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(85,60,42));
        add(panel2);

        //Middle right panel
        DiceButton button3 = new DiceButton();
        add(button3);
        button3.addActionListener( handler );

        //Far right panel
        dicePanel = new DicePanel();
        dicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(dicePanel);

    }

    //Rolling Dice Button Listener
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

    //Rule's Button Listener
    private class HelpPage implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            //Create a new pop-up frame
            JFrame help = new JFrame("Rules");

            //Insert our HTML page to the dialog
            JEditorPane jedit = new JEditorPane();
            jedit.setEditable(false);

            //Need to handle an IO exception
            try
            {
                jedit.setPage(getClass().getResource("help.html"));
            }
            catch( IOException exception)
            {
                //Problem?
                System.out.println("Cant find help.html");
            }

            //Add Scrollable bar on right
            JScrollPane scroll = new JScrollPane(jedit);

            //Make page visible
            help.add(scroll);
            help.setSize(400,400); //Beginning Size
            help.setVisible(true);
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
