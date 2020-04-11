//SlotButtonOffBoard Panel Constructor
import javax.swing.JButton;
//SlotButtonOffBoard Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class SlotButtonOffBoard extends SlotButton
{
    public SlotButtonOffBoard(String text)
    {
        //Call Parent Constructor
        super();
		background = new Color(32,32,32);
    }

    public void setBkgdColor(Color b)
    {
		background = b;
		repaint();
    }
	
    //No definition needed
    public void setTriColor(Color t)
    {
    }

    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);

        //Make bar the same color as the border
        setBackground(background);
		
		Graphics2D g2d = (Graphics2D)g;
		
		//Makes edges smoother
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		//Only if changing checkers
		if(settingCheckers == true)
		{
			//White checker
			if(checkerColor == 1)
			{
				g2d.setColor(new Color(220,220,220));
			}
			//Blue checker
			else if(checkerColor == 2)
			{
				g2d.setColor(new Color(7,107,151));
			}
			else
				return; //Should never be needed
			
			//Initial y value for home checkers
			int y = 0;
			
			//Drawing appropriate checkers
			for(int i = 1; i <= numCheckers; i++)
			{
				//Error check for too many checkers
				if(numCheckers > 15)
					break;
				
				g2d.draw3DRect(0, y, getWidth(), getHeight()/22, true);
				g2d.fill3DRect(0, y, getWidth(), getHeight()/22, true);
				y += getHeight()/22;
			}
		}
    }
}
