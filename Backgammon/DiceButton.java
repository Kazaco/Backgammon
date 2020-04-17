import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
//paintComponent
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;

public class DiceButton extends JButton
{
    public DiceButton()
    {
        //Call Parent
        super();
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void paintComponent(Graphics g)
    {
        //Call Parent
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
		
		//Makes edges smoother
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        //Background for Dice Panel
        setBackground(new Color(85,60,42));

        int x = getWidth() / 20;
        int y = getHeight() / 20;

        //Grey
        g2d.setColor(new Color(32,32,32));
        g2d.fillRect(x/2, y/2, getWidth() - x, getHeight() - y);

        //Yellow
        g2d.setColor(new Color(206,184,136));
        // g2d.fillRect(x, y, getWidth() - 2 * x, getHeight() - 2 * y);
        g2d.fill3DRect(x, y, getWidth() - 2 * x, getHeight() - 2 * y, true);

        //Yellow Inner
        g2d.setColor(new Color(206,184,136));
        g2d.fill3DRect(x + x, y + y, getWidth() - 4 * x, getHeight() - 4 * y, true);

        int radius = getWidth()/5;
        int xx = getWidth()/4 - radius;
        int yy = radius;
        int middleX = getWidth() / 2;
        int middleY = getHeight() / 2;

        //Draw Text
        g2d.setFont(new Font("Calibri", Font.BOLD, radius));
        g2d.setColor(new Color(0,0,0));
        g2d.drawString("Roll Dice", x + x + x, (y + y) + getHeight() - 10 * y);
    }

}