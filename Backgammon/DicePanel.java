//Dice Panel paintcomponent
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.BorderFactory;
//Dice Panel Constructor
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Font;

public class DicePanel extends JPanel
{
    private int dice1 = 1;
    private int dice2 = 2;
	private Color background = Color.WHITE;

    public DicePanel()
    {
        super();
    }

    //Mutators:
    public void setDiceOne(int num)
    {
        dice1 = num;
    }

    public void setDiceTwo(int num)
    {
        dice2 = num;
    }
	
	public void setDiceColor(int c)
	{
		if( c == 1 )
		{
			background = new Color(220, 220, 200);
		}
		else if( c == 2 )
		{
			background = new Color(7, 107, 151);
		}
		else
		{
			background = Color.RED;
		}
		repaint();
	}

    //Accessors:
    public int getDiceOne()
    {
        return dice1;
    }

    public int getDiceTwo()
    {
        return dice2;
    }

    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);

        //Background for Dice Panel
        setBackground(new Color(85,60,42));

        //Cast-down
        Graphics2D g2d = (Graphics2D)g;

        //Makes edges smoother
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        //Draw inner circle for dice to be on
        g2d.setColor( new Color(32,32,32));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth(), getHeight() );

        //Draw outer outline for oval that dice are on
        g2d.setColor(new Color(206,184,136));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(0, 0, getWidth() - 3, getHeight() - 3, getWidth(), getHeight() );

        //Math to get things in right spot
        int radius = getWidth()/8;
        int x = getWidth()/4 - radius;
        int y = radius;
        int middleX = getWidth() / 2;
        int middleY = getHeight() / 2;

        //Dice 1
        g2d.setColor(background);
        g2d.fillRoundRect(x + (radius * 5/8) * 2, middleY - radius * 1/2, x, y, 10, 10);    //Ugly calculations to make things works
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.BOLD, radius));
        g2d.drawString(Integer.toString(dice1), x + ((radius * 7/8) * 2) - (radius * 2/8), middleY + radius * 1/3);

        //Dice 2
        g2d.setColor(background);
        g2d.fillRoundRect(x + (radius * 7/8) * 4, middleY - radius * 1/2, x, y, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.BOLD, radius));
        g2d.drawString(Integer.toString(dice2), x + (radius * 7/8) * 5 - (radius * 5/8), middleY + radius * 1/3);
    }
}