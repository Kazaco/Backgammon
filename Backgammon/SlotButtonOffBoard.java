//SlotButtonOffBoard Panel Constructor
import javax.swing.JButton;
//SlotButtonOffBoard Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;

public class SlotButtonOffBoard extends SlotButton
{
    private Color background;

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
    }
}
