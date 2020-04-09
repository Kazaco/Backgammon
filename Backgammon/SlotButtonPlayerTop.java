//SlotButtonPlayerBot Panel Constructor
import javax.swing.JButton;
//SlotButtonPlayerBot Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;

public class SlotButtonPlayerTop extends SlotButton
{
	private Color background;
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

        //Background for board on player slot on top of board
        setBackground(background);

        //Points to draw triangle for each panel closest to player
        int xPoints[] = {this.getWidth()/16, this.getWidth()/2, this.getWidth() - this.getWidth()/16};
        int yPoints[] = {0, this.getHeight() - this.getHeight()/4, 0};
		g.setColor(triangle);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
