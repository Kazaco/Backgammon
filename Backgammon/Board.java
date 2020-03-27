import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

//Class to put the entire board together
public class Board
{
    //GUI
    private JFrame frame;
    private InfoPanel infoPanel;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    //LOGIC
    private Slot [] bkBoard = new Slot[28];   //Array references of Slots on the board

    //Create an empty board
    public Board()
    {
        for(int i = 0; i < 28; i++)
        {
            bkBoard[i] = new Slot();
        }
    }

    //Game-play loop
    public void play()
    {
        System.out.println("New game begins!\n");
        System.out.println("What color chips do you want?: 1-White, 2-Black");

        System.out.println("Test Functions");

        //Empty List - length 0
        System.out.println(bkBoard[0].getCheckerNumInSlot());
        //Empty List - color 0
        System.out.println(bkBoard[0].getFirstCheckerInSlot());

        drawGame();

    }

    private void drawGame()
    {
        //Create frame for Backgammon
        frame = new JFrame("Backgammon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,800);
        //TOP 
        //Display whose turn it is, display if move is valid or not
        infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension(800,100));
        frame.add(infoPanel, BorderLayout.NORTH);

        //MIDDLE
        //Game Board, let player drag and drop pieces on their turn
        boardPanel = new BoardPanel();
        frame.add(boardPanel, BorderLayout.CENTER);

        //BOTTOM
        //Help Pop-up Panel, Roll Dice Sub-panel, whatever else
        controlPanel = new ControlPanel();
        controlPanel.setPreferredSize(new Dimension(800,100));
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

}