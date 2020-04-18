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

public class HelpButton extends JButton
{
    public HelpButton()
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

        //Background for Help Button
        setBackground(new Color(85,60,42));

        int x = getWidth() / 20;
        int y = getHeight() / 20;
        int radius = getWidth()/5;

        //Grey
        g2d.setColor(new Color(32,32,32));
        g2d.fillRect(x/2, y/2, getWidth() - x, getHeight() - y);

        //Red
        g2d.setColor(new Color(120,47,64));
        g2d.fill3DRect(x, y, getWidth() - 2 * x, getHeight() - 2 * y, true);

        //Red Inner
        g2d.setColor(new Color(120,47,64));
        g2d.fill3DRect(x + x, y + y, getWidth() - 4 * x, getHeight() - 4 * y, true);

        //Draw Text
        g2d.setFont(new Font("Calibri", Font.BOLD, radius));
        g2d.setColor(new Color(255,255,255));
        g2d.drawString("Rules", x + 5 * x, (y + y) + getHeight() - 10 * y);
    }

}