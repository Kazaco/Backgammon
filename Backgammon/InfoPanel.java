import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

public class InfoPanel extends JPanel
{
    public void paintComponent(Graphics g)
    {
        //Call superclass's paintcomponent
        super.paintComponent(g);
        
        setBackground(Color.white);
    }
}