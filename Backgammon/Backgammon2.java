import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Backgammon2
{
    private JFrame frame2;
    private InfoPanel infoPanel;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    public static void main(String [] args)
    {
        boolean playing = true;
        boolean startPanel = true;

        //Create frame for Backgammon
        frame = new JFrame("Backgammon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,800);

        //Launch Game
        while (playing == true)
        {
            //Draw Title Screen
            if(startPanel == true)
            {
                // drawTitle(frame);
            }
            //Game Screen
            else
            {
                // drawGame(frame);
            }
            playing = false;
        }
    }

    // private void drawGame(JFrame frame)
    // {
    //     //TOP 
    //     //Display whose turn it is, display if move is valid or not
    //     infoPanel = new infoPanel("Backgammon");
    //     infoPanel.setPreferredSize(new Dimension(800,100));
    //     frame.add(infoPanel, BorderLayout.NORTH);

    //     //MIDDLE
    //     //Game Board, let player drag and drop pieces on their turn
    //     boardPanel = new BoardPanel();
    //     frame.add(boardPanel, BorderLayout.CENTER);

    //     //BOTTOM
    //     //Help Pop-up Panel, Roll Dice Sub-panel, whatever else
    //     controlPanel = new ControlPanel();
    //     controlPanel.setPreferredSize(new Dimension(800,100));
    //     frame.add(controlPanel, BorderLayout.SOUTH);
    //     frame.setVisible(true);
    // }
}