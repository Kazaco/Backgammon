//SlotButtonPlayerTop Panel Constructor
import javax.swing.JButton;
//SlotButtonPlayerTop Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class SlotButtonPlayerTop extends SlotButton
{
    private Color triangle;
	
    public SlotButtonPlayerTop(String text)
    {
        //Call Parent Constructor
        super();
		//Default colors
		background = new Color(32,32,32);
		triangle = new Color(206,184,136);
    }

    public void setBkgdColor(Color b)
    {
		background = b;
		repaint();
    }
	
    public void setTriColor(Color t)
    {
		triangle = t;
		repaint();
    }
		
    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);

        //Background for board on player slot on bottom of board
        setBackground(background);

		Graphics2D g2d = (Graphics2D)g;
		
		//Makes edges smoother
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        //Points to draw triangle on each triangle away from player
        int xPoints[] = {this.getWidth()/16, this.getWidth()/2, this.getWidth() - this.getWidth()/16};
        int yPoints[] = {0, this.getHeight() - this.getHeight()/4, 0};
		g2d.setColor(triangle);
		g2d.fillPolygon(xPoints, yPoints, 3);
		
		//Only if changing checkers
		if( settingCheckers == true )
		{	
			int radius = getWidth()/6;
			int x = getWidth()/2 - radius;
			int y = radius;
			
			//White checker
			if( checkerColor == 1 ) 
			{	
				//Using slightly lighter color for outline for appearance of depth
				g2d.setColor(new Color(250,250,250));
				g2d.setStroke(new BasicStroke(3));
				g2d.drawOval(x, y, radius * 2, radius * 2);
				//Filling checker and adjusting font
				g2d.setColor(new Color(220,220,220));
				g2d.fillOval(x, y, radius * 2, radius * 2);
				g2d.setColor(new Color(32,32,32));
				g2d.setFont(new Font("Calibri", Font.BOLD, radius));
				
				//Adjusting for rare case of double digit checkers on space
				if( numCheckers > 9 )
				{
					g2d.drawString(Integer.toString(numCheckers), x + radius/2, y + radius*4/3);
				}
				else
				{
					g2d.drawString(Integer.toString(numCheckers), x + radius*13/16, y + radius*4/3);
				}
			}
			//Blue checker
			else if(checkerColor == 2)
			{
				//Using slightly lighter color for outline for appearance of depth
				g2d.setColor(new Color(32,132,176));
				g2d.setStroke(new BasicStroke(3));
				g2d.drawOval(x, y, radius * 2, radius * 2);
				//Filling checker and adjusting font
				g2d.setColor(new Color(7,107,151));
				g2d.fillOval(x, y, radius * 2, radius * 2);
				g2d.setColor(new Color(32,32,32));
				g2d.setFont(new Font("Calibri", Font.BOLD, radius));
				
				//Adjusting for rare case of double digit checkers on space
				if( numCheckers > 9 )
				{
					g2d.drawString(Integer.toString(numCheckers), x + radius/2, y + radius*4/3);
				}
				else
				{
					g2d.drawString(Integer.toString(numCheckers), x + radius*13/16, y + radius*4/3);
				}
			}
			else
				return; //Should never be needed
		}
    }
}
