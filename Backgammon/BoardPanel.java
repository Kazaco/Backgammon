//Board Panel Constructor
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
//Board Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
//Board Panel Event Handling
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BoardPanel extends JPanel
{
    private SlotButton [] buttons;
    private volatile int rSlotPressed = -1;                 //Most Recent Button Pressed Info
    private volatile int rButtonPressedColor = -1;         // -1 - for all, if no button has been pressed / resetting button
    private volatile int rButtonPressedNumCheckers = -1;   //volatile - loop won't recognize a button changing due to a threading problem
                                                            //need to make variable visible on all threads, Ref: http://tutorials.jenkov.com/java-concurrency/volatile.html
    public BoardPanel()
    {
        //Call parent's constructor
        super();

        //Set up GridBag layout
        //Reference: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();
        //Also set up Event Listening
        ButtonHandler handler = new ButtonHandler();

        buttons = new SlotButton[ 28 ];
        //Numbered list of buttons: each with a unique paintComponent
        for (int count = 0; count < 28; count++)
        {
            //Off-Board Area, where we need 15 checkers to win
            if(count == 0 || count == 25)
            {
                buttons[ count ] = new SlotButtonOffBoard(Integer.toString(count));
            }
            //Bottom of board Area, buttons closest to the player
            else if( count > 0 && count <= 12 )
            {
				buttons[ count ] = new SlotButtonPlayerBot(Integer.toString(count));
				// Alternating colors
				if( count % 2 == 0 )
				{
					buttons[ count ].setTriColor(new Color(120,47,64));
				}
            }
            //Top of board Area, buttons farthest away from the player
            else if(count >= 13 && count < 26 )
            {
				buttons[ count ] = new SlotButtonPlayerTop(Integer.toString(count));
				// Alternating colors
				if( count % 2 == 0 )
				{
					buttons[ count ].setTriColor(new Color(120,47,64));
				}
            }
            //Bar, middle of board where checkers captured are placed
            else
            {
                buttons[ count ] = new SlotButtonBar(Integer.toString(count));
            }

            //Add Listener to newly created button
            buttons[ count ].addActionListener( handler );
        }

        ////////////////////////////////////////////////////
        //Set up off-the-board area
        // 0
        constraints.anchor = GridBagConstraints.SOUTHEAST;  // location on board to draw
        constraints.fill = GridBagConstraints.BOTH;         // resize both width/height on redraw
        constraints.gridx = 14;                             // x,y grid location
        constraints.gridy = 1;
        constraints.weightx = 10.0;                          // horizontal spacing, force into bottom right
        constraints.weighty = 2.0;                          // vertical spacing
        add(buttons[ 0 ], constraints);

        // 25
        constraints.gridx = 14;
        constraints.gridy = 0;
        add(buttons[ 25 ], constraints);
        ////////////////////////////////////////////////////

        //Bottom Right to Bottom Left
        for (int count = 1; count <= 13; count++)
        {
            constraints.gridx = 13 - count;
            constraints.gridy = 1;
            constraints.weighty = 1.0;

            //Board in front of user (normal size)
            if(count < 7)
            {
                constraints.weightx = 100.0;
                add(buttons[ count ], constraints);
            }
            else if (count > 7 )
            {
                constraints.weightx = 100.0;
                //Offset back onto button we going to put before bar
                add(buttons[ count - 1 ], constraints);
            }
            //Make bar slightly smaller and use button 26!
            else
            {
                constraints.weightx = 50.0;
                add(buttons[ 26 ], constraints);
            }
        }
        ////////////////////////////////////////////////////

        //Top Left to Top Right
        for (int count = 13; count <= 25; count++)
        {
            constraints.gridx = count - 13;
            constraints.gridy = 0;
            constraints.weighty = 1.0;

            //Board in front of opponent (normal size)
            if(count < 19)
            {
                constraints.weightx = 100.0;
                add(buttons[ count ], constraints);
            }
            else if(count > 19)
            {
                constraints.weightx = 100.0;
                //Offset back onto button we going to put before bar
                add(buttons[ count - 1 ], constraints);
            }
            //Make bar slightly smaller and use button 27!
            else
            {
                constraints.weightx = 50.0;
                add(buttons[ 27 ], constraints);
            }
        }
    }
    
    //Draw border for game
    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        
        //Create border for game board (could be in constructor)
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(85,60,42));
    }

	public void highlightMoves( int press, boolean[] moves, boolean flag )
	{
		if( press < 0 || press > 27 )
			return;
		
		boolean shouldHighlightPress = false;
		
		for(int i = 0; i < moves.length; i++)
		{
			if( moves[i] == true )
			{
				buttons[i].setHighlight( flag );
				shouldHighlightPress = true;
			}
		}
		
		if( shouldHighlightPress )
		{
			buttons[ press ].setPressHighlight( flag );
		}
	}

	public void noValidMoves( int numSlot, boolean flag )
	{
		if( numSlot >= 0 && numSlot < 28 )
			buttons[ numSlot ].setNoValidMoves( flag );
		else
			return;
	}

    //Change a specific slots checker color/number
    public void setSlot(int numSlot, int colorCheckers, int numCheckers)
    {
		if( numCheckers == 0 )
			buttons[numSlot].setCheckers(0, 0);
		else
			buttons[numSlot].setCheckers(colorCheckers, numCheckers);
    }

    //Accessors: for button presses
    public int getSlotPressed()
    {
        return rSlotPressed;
    }

    public int getButtonPressedColor()
    {
        return rButtonPressedColor;
    }

    public int getButtonPressedNumCheckers()
    {
        return rButtonPressedNumCheckers;
    }

	public boolean barWasPressed()
	{
		if( rSlotPressed == 26 || rSlotPressed == 27 )
			return true;
		else
			return false;
	}
	
	public boolean offBoardWasPressed()
	{
		if( rSlotPressed == 0 || rSlotPressed == 25 )
			return true;
		else
			return false;
	}
	
	public boolean boardWasPressed()
	{
		if( barWasPressed() == false && offBoardWasPressed() == false )
			return true;
		else
			return false;
	}

    //Assign everything to -1 to reset the button
    public void resetButton()
    {
        rSlotPressed = -1;
        rButtonPressedColor = -1;
        rButtonPressedNumCheckers = -1;
    }

    //Retrieve NumCheckers and CheckeColor when a button is pressed
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed( ActionEvent event)
        {
            for(int i = 0; i < 28; i++)
            {
                if(event.getSource() == buttons[i])
                {
                    rSlotPressed = i;
                    rButtonPressedColor = buttons[i].getCheckerColor();
                    rButtonPressedNumCheckers = buttons[i].getNumCheckers();
                }
            }
        }
    }
}
