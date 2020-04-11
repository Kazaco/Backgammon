//Board Panel Constructor
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
//Board Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class BoardPanel extends JPanel
{
    private SlotButton [] buttons;

    public BoardPanel()
    {
        //Call parent's constructor
        super();

        //Set up GridBag layout
        //Reference: https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();

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
    
    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        
        //Create border for game board (could be in constructor)
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(85,60,42));
    }

    public void setSlot(int numSlot, int colorCheckers, int numCheckers)
    {
        //Change a specific slots checker color oe number
        buttons[numSlot].setCheckers(colorCheckers, numCheckers);
    }
}
