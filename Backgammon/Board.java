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
        //System.out.println(bkBoard[1].getCheckerNumInSlot());
        //Empty List - color 0
        //System.out.println(bkBoard[1].getFirstCheckerColor());
        //System.out.println(boardPanel.getSlotPressed());

		int count = 0;
		int numSlot = 0;
		int color = 0;
		int numCheckers = 0;
		boolean boardPressed = false;
		boolean barPressed = false;
		
		//Starting to play with very basic checker movement
		//Logic currently ignores (not limited to):
			// player turns
			// dice rolls
			// partial checker movement
			// bearing off
		//Will probably write some validTurn() functions for each color
        while( gameOver() == false )
        {	
			//A slot has been pressed
            if( boardPanel.getSlotPressed() >= 0 && boardPanel.getButtonPressedColor() >= 0 && boardPanel.getButtonPressedNumCheckers() >= 0 )
            {
				//Initial slot pressed is not empty
				if( boardPanel.getButtonPressedColor() != 0 && count == 0 && boardPanel.offBoardWasPressed() == false )
				{	
					System.out.println("Slot:" + boardPanel.getSlotPressed() + "\nColor:" + boardPanel.getButtonPressedColor() + "\nNum Checkers:" + boardPanel.getButtonPressedNumCheckers() + "\n\n");
				
					numSlot = boardPanel.getSlotPressed();
					color = boardPanel.getButtonPressedColor();
					numCheckers = boardPanel.getButtonPressedNumCheckers();
					
					boardPressed = boardPanel.boardWasPressed();
					barPressed = boardPanel.barWasPressed();
					
					boardPanel.resetButton();
					count++;
				}
				//Initial slot pressed is on board & blue, snd a second button has been pressed
				else if( color == 2 && count == 1 && boardPressed == true )
				{
					int newNumSlot = boardPanel.getSlotPressed();
					int newColor = boardPanel.getButtonPressedColor();
					int newNumCheckers = boardPanel.getButtonPressedNumCheckers();
					
					//Second slot pressed is blue and moving in correct direction
					if( newColor == 2 && count == 1 && newNumSlot > numSlot && newNumSlot < 26 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers + newNumCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					//Second slot is white and playable
					else if( newColor == 1 && count == 1 && newNumSlot > numSlot && newNumSlot < 26 && newNumCheckers <= 1 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 1 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					//Second slot is empty and playable
					else if( newColor == 0 && count == 1 && newNumSlot > numSlot && newNumSlot < 26 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					else
					{
						boardPanel.resetButton();
						count = 0;
					}
				}
				//Initial slot pressed is on board & white, and a second button has been pressed
				else if( color == 1 && count == 1 && boardPressed == true )
				{
					int newNumSlot = boardPanel.getSlotPressed();
					int newColor = boardPanel.getButtonPressedColor();
					int newNumCheckers = boardPanel.getButtonPressedNumCheckers();
					
					//Second slot pressed is white and moving in correct direction
					if( newColor == 1 && count == 1 && newNumSlot < numSlot )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers + newNumCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					//Second slot is blue and playable
					else if( newColor == 2 && count == 1 && newNumSlot < numSlot && newNumCheckers <= 1 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 2 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					
					//Second slot is empty and playable
					else if( newColor == 0 && count == 1 && newNumSlot < numSlot )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					else
					{
						boardPanel.resetButton();
						count = 0;
					}
				}
				//Blue player is trying to re-enter
				else if( color == 2 && count == 1 && barPressed == true )
				{
					int newNumSlot = boardPanel.getSlotPressed();
					int newColor = boardPanel.getButtonPressedColor();
					int newNumCheckers = boardPanel.getButtonPressedNumCheckers();
					
					//Second slot pressed is blue and moving in correct direction
					if( newColor == 2 && count == 1 && newNumSlot > 0 && newNumSlot < 26 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers + newNumCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					//Second slot is white and playable
					else if( newColor == 1 && count == 1 && newNumSlot > 0 && newNumSlot < 26 && newNumCheckers <= 1 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 1 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					//Second slot is empty and playable
					else if( newColor == 0 && count == 1 && newNumSlot > 0 && newNumSlot < 26 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					else
					{
						boardPanel.resetButton();
						count = 0;
					}
				}
				//White player is trying to re-enter
				else if( color == 1 && count == 1 && barPressed == true )
				{
					int newNumSlot = boardPanel.getSlotPressed();
					int newColor = boardPanel.getButtonPressedColor();
					int newNumCheckers = boardPanel.getButtonPressedNumCheckers();
					
					//Second slot pressed is white and moving in correct direction
					if( newColor == 1 && count == 1 && newNumSlot < 25 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers + newNumCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					//Second slot is blue and playable
					else if( newColor == 2 && count == 1 && newNumSlot < 25 && newNumCheckers <= 1 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 2 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					
					//Second slot is empty and playable
					else if( newColor == 0 && count == 1 && newNumSlot < 25 )
					{
						//First pressed
						updateSlotCombined( numSlot, 0, 0, 0);
						//Second pressed
						updateSlotCombined( newNumSlot, color, numCheckers, 0 );
						//Reset button and count
						boardPanel.resetButton();
						count = 0;
					}
					else
					{
						boardPanel.resetButton();
						count = 0;
					}
				}
				else
				{
					boardPanel.resetButton();
					count = 0;
				}
			}
		}
		
		System.out.println("Game over!");
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
	
	//Updates GUI/Logic of a slot
	private void updateSlotCombined(int numSlot, int color, int numCheckers, int colorHit)
	{
		//LOGIC
		bkBoard[ numSlot ].setNumCheckers( color, numCheckers);
		//GUI
		boardPanel.setSlot( numSlot, color, numCheckers );
		
		if( colorHit == 1 )
		{
			int hitCheckers = bkBoard[ 27 ].getCheckerNumInSlot();
			//LOGIC
			bkBoard[ 27 ].setNumCheckers( 1, hitCheckers + 1 );
			//GUI
			boardPanel.setSlot( 27, 1, hitCheckers + 1 );
		}
		else if( colorHit == 2 )
		{
			int hitCheckers = bkBoard[ 26 ].getCheckerNumInSlot();
			//LOGIC
			bkBoard[ 26 ].setNumCheckers( 1, hitCheckers + 1 );
			//GUI
			boardPanel.setSlot( 26, 2, hitCheckers + 1 );
		}
		else
			return;
	}
	
    //Just a function to show where each array/button slot is located ()
    private void buttonTemplateBoard()
    {
        for(int i = 0; i < bkBoard.length; i++)
        {
            boardPanel.setSlot(i, 1, i); 
        }
    }
	
	private boolean gameOver()
	{
		if( bkBoard[0].getCheckerNumInSlot() == 15 || bkBoard[25].getCheckerNumInSlot() == 15)
			return true;
		else
			return false;
	}
}