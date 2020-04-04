//SlotButtonOffBoard Panel Constructor
import javax.swing.JButton;
//SlotButtonOffBoard Panel paintComponent
import java.awt.Graphics;
import java.awt.Color;

public class SlotButtonOffBoard extends SlotButton{

    public SlotButtonOffBoard(String text)
    {
        //Call Parent Constructor
        super();
    }

    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);

        //Make bar the same color as the border
        setBackground(Color.BLACK);
    }
}