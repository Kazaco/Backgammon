import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.Graphics;
import java.awt.Color;

public class BoardPanel extends JPanel
{
    public BoardPanel()
    {
        //Call parent's constructor
        super();

        //Set up GridBag layout
        setLayout( new GridBagLayout() );
        GridBagConstraints constraints = new GridBagConstraints();

        JButton buttons[] = new JButton[ 28 ];

        //Numbered list of buttons
        for ( int count = 0; count < 28; count++ )
            buttons[ count ] = new JButton(Integer.toString(count));

        ////////////////////////////////////////////////////
        //Set up off-the-board area
        // 0
        constraints.anchor = GridBagConstraints.SOUTHEAST;  // location on board to draw
        constraints.fill = GridBagConstraints.BOTH;         // resize both width/height on redraw
        constraints.gridx = 13;                             // x,y grid location
        constraints.gridy = 1;
        constraints.weightx = 1.0;                          // horizontal spacing, force into bottom right
        constraints.weighty = 2.0;                          // vertical spacing
        add(buttons[ 0 ], constraints);

        // 25
        constraints.gridx = 13;
        constraints.gridy = 0;
        add(buttons[ 25 ], constraints);
        ////////////////////////////////////////////////////

        //Set up main board
        //Bottom Right to Bottom Left
        for (int count = 1; count <= 12; count++)
        {
            constraints.gridx = 12 - count;
            constraints.gridy = 1;
            constraints.weighty = 1.0;
            add(buttons[ count ], constraints);
        }

        //Top Left to Top Right
        for (int count = 13; count <= 24; count++)
        {
            constraints.gridx = count - 13;
            constraints.gridy = 0;
            constraints.weighty = 1.0;
            add(buttons[ count ], constraints);
        }
    }
    
    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        
        // setBackground(Color.black);
    }
}