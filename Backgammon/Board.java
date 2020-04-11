import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
        //Initialize all Panels and Frame
        drawGame();

        //Template Board (Just show where buttons are located)
        // buttonTemplateBoard();

        setUpBoard();
        System.out.println("New game begins!\n");
        infoPanel.changeText("Welcome to Backgammon!");

        //Empty List - length 0
        System.out.println(bkBoard[1].getCheckerNumInSlot());
        //Empty List - color 0
        System.out.println(bkBoard[1].getFirstCheckerColor());
        System.out.println(boardPanel.getSlotPressed());

        while(true)
        {
            if( boardPanel.getSlotPressed() >= 0 && boardPanel.getButtonPressedColor() >= 0 && boardPanel.getButtonPressedNumCheckers() >= 0)
            {
                System.out.println("Slot:" + boardPanel.getSlotPressed() + "\nColor:" + boardPanel.getButtonPressedColor() + "\nNum Checkers:" + boardPanel.getButtonPressedNumCheckers() + "\n\n");
                boardPanel.resetButton();
            }
        }
    }

    //Draw game for the User to See
    private void drawGame()
    {
        //Create frame for Backgammon
        frame = new JFrame("Backgammon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setResizable(false);
        frame.setMinimumSize(new Dimension (800,600));  //Trying to make our game function correctly even if user resized screen

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
        controlPanel.setPreferredSize(new Dimension(800,150));
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    //Initial board set-up
    private void setUpBoard()
    {
        //Player 1
        //Slot #, Color #, numCheckers, 
        setUpSlotCombined(6, 1, 5);
        setUpSlotCombined(8, 1, 3);
        setUpSlotCombined(13, 1, 5);
        setUpSlotCombined(24, 1, 2);

        //Player 2
        setUpSlotCombined(1, 2, 2);
        setUpSlotCombined(12, 2, 5);
        setUpSlotCombined(17, 2, 3);
        setUpSlotCombined(19, 2, 5);
    }

    //Helper function for combining GUI/Logic in one step for building the board
    private void setUpSlotCombined(int numSlot, int color, int numCheckers)
    {   
        //Valid input values for a slot
        if( bkBoard[numSlot].setNumCheckers(color,numCheckers) )    //LOGIC
        {
            //Draw to Screen
            boardPanel.setSlot(numSlot, color, numCheckers);        //GUI
        }
        else
        {
            //Error
        }
    }

    //Just a function to show where each array/button slot is located ()
    private void buttonTemplateBoard()
    {
        for(int i = 0; i < bkBoard.length; i++)
        {
            boardPanel.setSlot(i, 1, i); 
        }
    }
}