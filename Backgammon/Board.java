import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.util.Random;

//Class to put the entire board together
public class Board
{
    //GUI
    private JFrame frame;
    private InfoPanel infoPanel;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
	private int firstPressed, secondPressed;
    //LOGIC
    private Slot [] bkBoard = new Slot[28];		//Array references of Slots on the board
	private boolean [] moves;					//List of valid moves updated during each turn
	private boolean validMovesExist;			//Keeps track of the existence of valid moves each turn
	private boolean d1Used, d2Used, d3Used;		//Keeps track of rolls used during each turn
	private int move1, move2, move3;			//Moves specific to player updated each turn

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

        setUpBoard();
        System.out.println("New game begins!\n");
        infoPanel.changeText("Welcome to Backgammon!");

        while( gameOver() == false )
        {	
			Random random = new Random();
			int d1 = -1, d2 = -1;

			System.out.println("\nPlayer 1's turn");
			//Doubles not yet supported
			do{
				//If player doesn't need to roll again, and player has clicked the button --- retrieve dice values
				if( controlPanel.needsToRoll() == false && controlPanel.hasPressedButton() == true)
				{
					d1 = controlPanel.getDiceOne();
					d2 = controlPanel.getDiceTwo();
					
					//Handle Doubles
					if( d1 == d2)
					{
						controlPanel.resetRoll();
					}
				}
			}while( d1 == -1 || d2 == -1 );
			
			System.out.println("Dice roll 1 = " + d1);
			System.out.println("Dice roll 2 = " + d2 + "\n");

			//User takes their turn
			//Note: Reset button to prevent user from moving pieces from another person's first selected
			boardPanel.resetButton();
			takeTurn( 1, d1, d2 );
			
			//Reset Rolling
			controlPanel.resetRoll();
			d1 = -1;
			d2 = -1;

			System.out.println("\nPlayer 2's turn");
			//Doubles not yet supported
			do{
				//If player doesn't need to roll again, and player has clicked the button --- retrieve dice values
				if( (controlPanel.needsToRoll() == false) && (controlPanel.hasPressedButton() == true) )
				{
					d1 = controlPanel.getDiceOne();
					d2 = controlPanel.getDiceTwo();

					if( d1 == d2)
					{
						controlPanel.resetRoll();
					}
				}
			}while( d1 == -1 || d2 == -1 );

			//Printing
			System.out.println("Dice roll 1 = " + d1);
			System.out.println("Dice roll 2 = " + d2 + "\n");

			//User takes their turn
			//Note: Reset button to prevent user from moving pieces from another person's first selected
			boardPanel.resetButton();
			takeTurn( 2, d1, d2 );

			//Reset Rolling for next player
			controlPanel.resetRoll();
			d1 = -1;
			d2 = -1;
		}
		
		System.out.println("Game over!");
    }

	//For now, the color doesn't matter because of how primitive checker movement is
	//Hopefully can write this function generically enough where using "p" inplace of 1 or 2 will eliminate a lot of redundant code
	private void takeTurn(int p, int d1, int d2)
	{	
		d1Used = false; d2Used = false; d3Used = false;

		//Players turn continues until they've used up each dice roll
		while( d3Used == false )
		{	
			//Listening until player selects a checker of their color on the board
			while ( boardPanel.getSlotPressed() == -1 )
			{
				while( boardPanel.getButtonPressedColor() != p )
				{
					firstPressed = boardPanel.getSlotPressed();
				}
			}
			
			System.out.println("firstPressed = " + firstPressed);
			
			//Changing the moves array (member data) based on what moves are valid
			validMoves( p, d1, d2 );
			boardPanel.highlightMoves( moves, true);
			boardPanel.resetButton();
			
			//Valid moves exist, waiting for a secondPressed that is valid
			//NOTE: player can select their firstPressed to cancel their move if they don't like their options
			if( validMovesExist )
			{	
				do
				{
					//Listening until a slot is selected
					//NOTE: some extra code was needed here due to rPresseds being -1 and out of bounds of moves[]
					while( ( secondPressed = boardPanel.getSlotPressed() ) < 0 );

					//Player wants to cancel their current move
					if( secondPressed == firstPressed )
						break;
					
				} while( moves[ secondPressed ] != true ); //Listening until a valid slot is selected
				
				System.out.println("secondPressed = " + secondPressed);
				
				//If player has canceled their move, start the move over
				if( secondPressed == firstPressed )
				{
					System.out.println("Move has been canceled. Try another slot.");
					boardPanel.highlightMoves( moves, false );
					boardPanel.resetButton();
					continue;
				}
			}
			else
			//If the player has selected a slot with no valid moves, start the move over
			{
				noValidMoves( firstPressed );
				System.out.println("No valid moves exist. Resetting.");
				boardPanel.resetButton();
				continue;
			}
			
			//Function keeps track of how many dice have been used
			diceUsed( p, d1, d2 );
			
			//Updating the first slot
			bkBoard[ firstPressed ].removeChecker();
			boardPanel.setSlot( firstPressed, bkBoard[ firstPressed ].getCheckerTopColor(), bkBoard[ firstPressed ].getCheckerNumInSlot() );
			
			//Updating the second slot
			bkBoard[ secondPressed ].addChecker( p );
			boardPanel.setSlot( secondPressed, bkBoard[ secondPressed ].getCheckerTopColor(), bkBoard[ secondPressed ].getCheckerNumInSlot() );
			
			boardPanel.highlightMoves( moves, false );
			boardPanel.resetButton();
		}
	}
	
	private void validMoves(int p, int d1, int d2)
	{
		validMovesExist = false;
		moves = new boolean[28];
		int oppColor = 0;
		
		//Available moves specific to player 1
		if( p == 1 )
		{
			move1 = firstPressed - d1;
			move2 = firstPressed - d2;
			move3 = firstPressed - d1 - d2;
			oppColor = 2;
		}
		
		//Available moves specific to player 2
		if( p == 2 )
		{
			move1 = firstPressed + d1;
			move2 = firstPressed + d2;
			move3 = firstPressed + d1 + d2;
			oppColor = 1;
		}
		
		//Using dice 1
		if( move1 >= 0 && move1 <= 25 && d1Used == false )
		{
			//Slot is empty or same color as player
			if( bkBoard[ move1 ].getCheckerTopColor() != oppColor )
			{
				validMovesExist = true;
				moves[ move1 ] = true;
			}
		}
		//Using dice 2
		if( move2 >= 0 && move2 <= 25 && d2Used == false )
		{
			//Slot is empty or same color as player
			if( bkBoard[ move2 ].getCheckerTopColor() != oppColor )
			{
				validMovesExist = true;
				moves[ move2 ] = true;
			}
		}	
		//Using dice 1 and 2 as one move
		if( move3 >= 0 && move3 <= 25 && d1Used == false && d2Used == false && d3Used == false )
		{
			//Slot is empty or same color as player
			if( bkBoard[ move3 ].getCheckerTopColor() != oppColor )
			{
				validMovesExist = true;
				moves[ move3 ] = true;
			}
		}
	}
	
	//Slot blinks red
	private void noValidMoves(int numSlot)
	{
		boardPanel.noValidMoves( numSlot, true );
		
		try
		{
			Thread.sleep(250);
		}
		catch( InterruptedException e )
		{
			System.out.println("Sleep interrupted!");
		}
		
		boardPanel.noValidMoves( numSlot, false );		
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
        if( bkBoard[numSlot].setNumCheckers(color, numCheckers) )    //LOGIC
        {
            //Draw to Screen
            boardPanel.setSlot(numSlot, color, numCheckers);        //GUI
        }
        else
        {
            //Error
        }
    }
	
	private void diceUsed(int p, int d1, int d2)
	{	
		if( secondPressed == move1 )
		{	
			d1Used = true;
		}	
		if( secondPressed == move2 )
		{
			d2Used = true;
		}
		if( secondPressed == move3 )
		{
			d3Used = true;
		}
		if( d1Used == true && d2Used == true )
		{
			d3Used = true;
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
	
	private boolean gameOver()
	{
		if( bkBoard[0].getCheckerNumInSlot() == 15 || bkBoard[25].getCheckerNumInSlot() == 15)
			return true;
		else
			return false;
	}
}