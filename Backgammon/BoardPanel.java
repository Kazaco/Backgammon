import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

public class BoardPanel extends JPanel
{
    public BoardPanel()
    {

    }
    
    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        
        setBackground(Color.black);
    }
}