//SlotButtonPlayerBot Panel Constructor
import javax.swing.JButton;
//SlotButtonPlayerBot Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;

public class SlotButtonPlayerTop extends SlotButton{

    public SlotButtonPlayerTop(String text)
    {
        //Call Parent Constructor
        super();
    }

    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);

        //Background for board on player slot on top of board
        setBackground(Color.BLACK);

        //Points to draw triangle for each panel closest to player
        int xPoints[] = {this.getWidth()/16, this.getWidth()/2, this.getWidth() - this.getWidth()/16};
        int yPoints[] = {0, this.getHeight() - this.getHeight()/4, 0};
        g.fillPolygon(xPoints, yPoints, 3);
    }
}