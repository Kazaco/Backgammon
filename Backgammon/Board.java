//Board Frame
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.util.Scanner;

//Menu Event Handling
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private boolean [] hitMoves;				//List of hit moves updated during each turn
	private boolean validMovesExist;			//Keeps track of the existence of valid moves each turn
	private boolean d1Used, d2Used, d3Used;		//Keeps track of rolls used during each turn
	private int move1, move2, move3;			//Moves specific to player updated each turn
	private int moveCounter;					//For dealing with doubles
	private int d1, d2, d3, d4;					//Dice variables
	private boolean doubles, playedDoubles;		//For dealing with doubles
	private boolean loadGame, doneIntro, nLoad;	//Flag times available to save/load
	private int curPlayer;						//Current Player's turn

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
		
		//Put/draw checkers to the right locations on the board
		setUpBoard();
		
		//INTRODUCTION
		infoPanel.changeText("Welcome to Backgammon!");
		infoPanel.changeText("Note 1: You can only save games after the first roll.");
		infoPanel.changeText("Note 2: You can only load games before the first roll.");
		infoPanel.changeText("\n\nRoll the dice to determine who goes first!");
		
		//Determine who goes first
		d1 = -1;				//Die values not rolled
		d2 = -1;
		doneIntro = false;
		loadGame = true;
		playedDoubles = false;	//Did they have doubles prior to saving

		while( (d1 == -1 || d2 == -1) && loadGame == true)
		{
			//If player doesn't need to roll again, and player has clicked the button --- retrieve dice values
			if( controlPanel.needsToRoll() == false && controlPanel.hasPressedButton() == true && loadGame == true)
			{
				d1 = controlPanel.getDiceOne();
				d2 = controlPanel.getDiceTwo();
				
				//Handle rolled same value
				if( d1 == d2)
				{
					infoPanel.changeText("...........");
					infoPanel.changeText("You rolled the same values. Roll again!");

					//Reset Dice Values
					d1 = -1;
					d2 = -1;
					
					//Reset roll before leaving loop. Don't need to reset button in other
					//checks b/c we should use this roll as the first move
					controlPanel.resetRoll();
				}
				//Player 1 rolled the highest value
				else if(d1 > d2)
				{
					infoPanel.changeText("...........");
					infoPanel.changeText("Player 1 rolled the highest value they go first!");
					curPlayer = 1;

				}
				//Player 2 rolled the highest value
				else
				{
					infoPanel.changeText("...........");
					infoPanel.changeText("Player 2 rolled the highest value they go first!");
					curPlayer = 2;
				}
			}
		}

		//END OF INTRODUCTION
		//START OF GAME

		doneIntro = true;
		loadGame = false;
        while( gameOver() == false )
        {	
			boardPanel.resetButton();
			doubles = false;
			d1 = -1;			//Die values not rolled
			d2 = -1;
			controlPanel.setDiceColor( 3 );
			
			do{
				//If player doesn't need to roll again, and player has clicked the button --- retrieve dice values
				if( controlPanel.needsToRoll() == false && controlPanel.hasPressedButton() == true)
				{
					controlPanel.setDiceColor( curPlayer );
					d1 = controlPanel.getDiceOne();
					d2 = controlPanel.getDiceTwo();

					//Display Dice Rolled
					infoPanel.changeText("Dice roll 1 = " + d1);
					infoPanel.changeText("Dice roll 2 = " + d2);
					
					//Handle Doubles
					if( d1 == d2)
					{
						//Handle Normal
						infoPanel.changeText("You rolled doubles! You get to move 4 times!\n");
						
						//Handle Doubles
						doubles = true;
						
						//Save state of when user saved during doubles
						if(playedDoubles == false)
						{
							takeTurn( curPlayer, d1, d2 );
							playedDoubles = true;
						}
						takeTurn( curPlayer, d1, d2 );
						playedDoubles = false;	//Reset played doubles if they loaded, no effect if they were normally playing
					}
					else
					{
						//Handle Normal
						infoPanel.changeText("\n");

						//User takes their turn
						//Note: moved resetButton() to takeTurn() function
						takeTurn( curPlayer, d1, d2 );
					}
				}
			}while( d1 == -1 || d2 == -1);

			//Reset Rolling for next player and move to next player, only print below if player has not won
			controlPanel.resetRoll();
			if( gameOver() == false )
			{
				curPlayer = (curPlayer % 2) + 1;
				infoPanel.changeText("Player " + curPlayer +"'s turn! Please roll the dice.");
			}
		}
		doneIntro = false;
		infoPanel.changeText("==== GAME OVER ====");
		infoPanel.changeText("Player " + curPlayer +" has won!");
    }

	//For now, the color doesn't matter because of how primitive checker movement is
	//Hopefully can write this function generically enough where using "p" inplace of 1 or 2 will eliminate a lot of redundant code
	private void takeTurn(int p, int d1, int d2)
	{	
		//Loaded a game, dont overwrite D1/D2 used values
		if(nLoad == true)
		{
			//Dont change dice used values or moveCounter
			d3Used = false;
			nLoad = false;
		}
		else
		{
			d1Used = false; d2Used = false; d3Used = false;
			moveCounter = 0;
		}

		int barSlot;
		
		if( p == 1 )
			barSlot = 27;
		else
			barSlot = 26;

		//Players turn continues until they've used up each dice roll
		while( d3Used == false )
		{	
			// To prevent getting stuck if game is won using 1 out of 2 dice or on a doubles turn
			if( gameOver() == true )
				break;
			
			boardPanel.resetButton();
			
			//Player must enter
			if( bkBoard[ barSlot ].getCheckerNumInSlot() != 0 )
			{
				firstPressed = barSlot;
			}
			//Player must select a slot of their color
			else
			{
				while( boardPanel.getButtonPressedColor() != p );
				firstPressed = boardPanel.getSlotPressed();
			}
			
			System.out.println("firstPressed = " + firstPressed);
			
			//Find validMoves and highlight them if available
			validMoves( p, d1, d2 );
			boardPanel.highlightMoves( firstPressed, moves, true );
			boardPanel.resetButton();
			
			//Valid moves were found
			if( validMovesExist )
			{
				//Player must select a slot on the board
				while( ( secondPressed = boardPanel.getSlotPressed() ) == -1 );
				
				//Player is canceling their move
				if( moves[ secondPressed ] != true )
				{
					System.out.println("Move has been canceled. Try another slot.");
					boardPanel.highlightMoves( firstPressed, moves, false );
					boardPanel.resetButton();
					continue;
				}
			}
			//No valid moves were found
			else
			{
				//Player is "closed out"
				if( bkBoard[ barSlot ].getCheckerNumInSlot() != 0 )
				{
					noValidMoves( firstPressed );
					infoPanel.changeText("No possible entry moves. Ending turn.\n");
					boardPanel.resetButton();
					return;
				}
				
				//No valid moves exist
				noValidMoves( firstPressed );
				infoPanel.changeText("No valid moves exist. Try another move.\n");
				boardPanel.highlightMoves( firstPressed, moves, false );
				boardPanel.resetButton();
				continue;
			}
			
			//Calculate dice used
			diceUsed( p, d1, d2 );
			
			//Dealing with a checker hit on the way
			if( d3Used == true && d1Used == false && d2Used == false )
			{
				if( ( move1 >= 0 && move1 <= 25 ) && hitMoves[ move1 ] == true )
				{
					hitOnTheWay( p, move1 );
				}
				else if( ( move2 >= 0 && move2 <= 25 ) && hitMoves[ move2 ] == true )
				{
					hitOnTheWay( p, move2 );
				}
			}
			
			updateBoard( p, hitMoves[ secondPressed ] );
			boardPanel.highlightMoves( firstPressed, moves, false );
			boardPanel.resetButton();
		}
	}
	
	private void hitOnTheWay( int p, int slotHit )
	{
		//Updating bar
		if( p == 1 )
		{
			bkBoard[ 26 ].addChecker( 2 );
			boardPanel.setSlot( 26, 2, bkBoard[ 26 ].getCheckerNumInSlot() );
		}
		else
		{
			bkBoard[ 27 ].addChecker( 1 );
			boardPanel.setSlot( 27, 1, bkBoard[ 27 ].getCheckerNumInSlot() );
		}
		
		//Removing hit checker
		bkBoard[ slotHit ].removeChecker();
		//Give feedback that slot was hit
		noValidMoves( slotHit );
		boardPanel.setSlot( slotHit, bkBoard[ slotHit ].getCheckerTopColor(), bkBoard[ slotHit ].getCheckerNumInSlot() );
	}
	
	private void updateBoard( int p, boolean hit )
	{
		//Updating the first slot
		bkBoard[ firstPressed ].removeChecker();
		boardPanel.setSlot( firstPressed, bkBoard[ firstPressed ].getCheckerTopColor(), bkBoard[ firstPressed ].getCheckerNumInSlot() );		
		
		if ( hit )
		{
			//Updating bar
			if( p == 1 )
			{
				bkBoard[ 26 ].addChecker( 2 );
				boardPanel.setSlot( 26, 2, bkBoard[ 26 ].getCheckerNumInSlot() );
			}
			else
			{
				bkBoard[ 27 ].addChecker( 1 );
				boardPanel.setSlot( 27, 1, bkBoard[ 27 ].getCheckerNumInSlot() );
			}
			
			//Removing hit checker
			bkBoard[ secondPressed ].removeChecker();
			//Give feedback that slot was hit
			noValidMoves( secondPressed );
		}
		
		//Updating the second slot
		bkBoard[ secondPressed ].addChecker( p );
		boardPanel.setSlot( secondPressed, bkBoard[ secondPressed ].getCheckerTopColor(), bkBoard[ secondPressed ].getCheckerNumInSlot() );			
	}
	
	private void validMoves(int p, int d1, int d2)
	{
		validMovesExist = false;
		moves = new boolean[28];
		hitMoves = new boolean[28];
		int oppColor = 0;
		int min = 1; int max = 24;
		
		//Change min and max available slot based on player's ability to bear off
		if( canBearOff(p) )
		{
			min = 0;
			max = 25;
		}
		
		//Available moves specific to player 1
		if( p == 1 )
		{
			move1 = firstPressed - d1;
			move2 = firstPressed - d2;
			move3 = firstPressed - d1 - d2;
			
			oppColor = 2;
			
			//Need to enter
			if( bkBoard[ 27 ].getCheckerNumInSlot() != 0 )
			{
				move1 = 25 - d1;
				move2 = 25 - d2;
				move3 = 25 - d1 - d2;
			}
		}
		
		//Available moves specific to player 2
		if( p == 2 )
		{
			move1 = firstPressed + d1;
			move2 = firstPressed + d2;
			move3 = firstPressed + d1 + d2;
			oppColor = 1;
			
			//Need to enter
			if( bkBoard[ 26 ].getCheckerNumInSlot() != 0 )
			{
				move1 = d1;
				move2 = d2;
				move3 = d1 + d2;
			}
		}
		
		//Using dice 1
		if( move1 >= min && move1 <= max && d1Used == false )
		{
			//Slot is empty or same color as player
			if( bkBoard[ move1 ].getCheckerTopColor() != oppColor )
			{
				validMovesExist = true;
				moves[ move1 ] = true;
			}
			//Slot is a blot, can be hit
			if( bkBoard[ move1 ].getCheckerTopColor() == oppColor && bkBoard[ move1 ].getCheckerNumInSlot() <= 1 )
			{
				validMovesExist = true;
				moves[ move1 ] = true;
				hitMoves[ move1 ] = true;
			}
		}
		//Using dice 2
		if( move2 >= min && move2 <= max && d2Used == false )
		{
			//Slot is empty or same color as player
			if( bkBoard[ move2 ].getCheckerTopColor() != oppColor )
			{
				validMovesExist = true;
				moves[ move2 ] = true;
			}
			//Slot is a blot, can be hit
			if( bkBoard[ move2 ].getCheckerTopColor() == oppColor && bkBoard[ move2 ].getCheckerNumInSlot() <= 1 )
			{
				validMovesExist = true;
				moves[ move2 ] = true;
				hitMoves[ move2 ] = true;
			}
		}	
		//Using dice 1 and 2 as one move
		if( move3 >= min && move3 <= max && (moves[move1] == true || moves[move2] == true) && d1Used == false && d2Used == false && d3Used == false )
		{
			//Slot is empty or same color as player
			if( bkBoard[ move3 ].getCheckerTopColor() != oppColor )
			{
				validMovesExist = true;
				moves[ move3 ] = true;
			}
			//Slot is a blot, can be hit
			if( bkBoard[ move3 ].getCheckerTopColor() == oppColor && bkBoard[ move3 ].getCheckerNumInSlot() <= 1 )
			{
				validMovesExist = true;
				moves[ move3 ] = true;
				hitMoves[ move3 ] = true;
			}
		}
		
		if( canBearOff(p) && validMovesExist == false )
		{
			int maxDice;
			
			//Neither dice have been used yet
			if( d1Used == false && d2Used == false )
			{
				if( d1 > d2 )
					maxDice = d1;
				else
					maxDice = d2;	
			}
			//Only d1 has been used
			else if( d1Used == true && d2Used == false )
			{
				maxDice = d2;
			}
			//Only d2 has been used
			else if( d2Used == true && d1Used == false )
			{
				maxDice = d1;
			}
			//Shouldn't be reached
			else
				return;
			
			if( p == 1 )
			{
				//If the player has a checker of their color in any slot before the slotPressed (on the home board) and found "no validMoves" above, they cannot bear off
				//There truly are no valid moves, return early
				for(int i = 6; i > firstPressed; i--)
				{
					if( bkBoard[i].getCheckerTopColor() == 1 )
						return;
				}
				
				for(int i = maxDice; i > 0; i--)
				{
					//We have found the highest point checker available to move using the maxDice, return early
					if( bkBoard[i].getCheckerTopColor() == 1 )
					{
						firstPressed = i;
						//Setting move1 or move2 to be the offBoard slot, depending on which dice is used
						if( maxDice == d1 )
						{
							move1 = 0;
						}
						else
						{
							move2 = 0;
						}
						validMovesExist = true;
						moves[ 0 ] = true;
						return;
					}
				}
			}
			else
			{
				//If the player has a checker of their color in any slot before the slotPressed (on the home board) and found "no validMoves" above, they cannot bear off
				//There truly are no valid moves, return early
				for(int i = 19; i < firstPressed; i++)
				{
					if( bkBoard[i].getCheckerTopColor() == 2 )
						return;
				}
				
				for(int i = 25 - maxDice; i < 25; i++)
				{
					//We have found the highest point checker available to move using the maxDice, return early
					if( bkBoard[i].getCheckerTopColor() == 2 )
					{
						firstPressed = i;
						//Setting move1 to be the offBoard slot
						if( maxDice == d1 )
						{
							move1 = 25;
						}
						else
						{
							move2 = 25;
						}
						validMovesExist = true;
						moves[ 25 ] = true;
						return;
					}
				}
			}
		}
	}
	
	private boolean canBearOff(int p)
	{
		int min, max, bar;
		
		if( p == 1 )
		{
			min = 7;
			max = 24;
			bar = 27;
		}
		else
		{
			min = 1;
			max = 18;
			bar = 26;
		}
		
		for(int i = min; i <= max; i++)
		{
			if( bkBoard[ i ].getCheckerTopColor() == p )
				return false;
		}
		
		if( bkBoard[ bar ].getCheckerNumInSlot() != 0 )
			return false;
		
		return true;
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
		if( doubles == true )
		{
			moveCounter++;
			
			if( secondPressed == move3 && moveCounter == 1 )
			{
				d3Used = true;
			}
			if( secondPressed == move1 && moveCounter == 1 )
			{
				d1Used = true;
			}
			if( secondPressed == move2 && moveCounter == 2 )
			{
				d2Used = true;
				d3Used = true;
			}
			return;
		}
	
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

	//Draw game for the User to See
	private void drawGame()
	{
		//Create frame for Backgammon
		frame = new JFrame("Backgammon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension (800,600));  //Trying to make our game function correctly even if user resized screen

		//Create Functioning Menu located at Top
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		JMenuItem load = new JMenuItem("Load");
		load.setMnemonic(KeyEvent.VK_L);
		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic(KeyEvent.VK_S);

		//Add Listeners
		SaveGame saveGame = new SaveGame();
		save.addActionListener(saveGame);
		LoadGame loadGame = new LoadGame();
		load.addActionListener(loadGame);

		//Add Menu items to frame
		menu.add(load);
		menu.add(save);
		bar.add(menu);
		frame.setJMenuBar(bar);

		//TOP 
		//Display whose turn it is, display if move is valid or not
		infoPanel = new InfoPanel();
		infoPanel.setPreferredSize(new Dimension(800,100));
		frame.add(infoPanel, BorderLayout.NORTH);

		//MIDDLE
		//Game Board, let player push button to move pieces on their turn
		boardPanel = new BoardPanel();
		frame.add(boardPanel, BorderLayout.CENTER);

		//BOTTOM
		//Help Pop-up Panel, Roll Dice Sub-panel, whatever else
		controlPanel = new ControlPanel();
		controlPanel.setPreferredSize(new Dimension(800,150));
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private class SaveGame implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//Can't save unless you got passed initial roll in beginning
			if(doneIntro == true)
			{
				//Create file-saving panel
				JFileChooser chooseFile = new JFileChooser();
				
				//Put choosefile into the game's directory
				chooseFile.setCurrentDirectory( new File(".").getAbsoluteFile());

				int buttonChosen = chooseFile.showSaveDialog(frame);
				if(buttonChosen == chooseFile.APPROVE_OPTION)
				{
					//Get user's input for save file
					File saveFile = chooseFile.getSelectedFile();
					Formatter output = null;

					try
					{
						output = new Formatter(saveFile);

						//String to store all our game's data
						StringBuilder saveString = new StringBuilder();

						//Retrieve all checker locations (same format as setUpSlotCombined)
						for(int i = 0; i < bkBoard.length; i++)
						{
							saveString.append(i + " " + bkBoard[i].getCheckerTopColor() + " " + bkBoard[i].getCheckerNumInSlot() + "\n");
						}

						//If player needs to roll and what values are currently rolled if they dont
						saveString.append( curPlayer + " " + playedDoubles + " " + moveCounter + " " + controlPanel.needsToRoll() + "\n");
						saveString.append( controlPanel.getDiceOne() + " " + controlPanel.getDiceTwo() + " " + d1Used + " " + d2Used);

						//Print to file
						output.format(saveString.toString());
						infoPanel.changeText("\nGame Saved!....");
					}
					catch(FileNotFoundException exception)
					{
						infoPanel.changeText("\nFile not found, please try to save again!");
					}
					finally
					{
						if(output != null)
						{
							output.close();
						}
					}
				}
				else
				{
					//Player didnt save game
				}
			}
		}
	}

	private class LoadGame implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(loadGame == true)
			{
				//Create file-saving panel
				JFileChooser chooseFile = new JFileChooser();

				//We loaded a game (dont need initial dice roll)
				loadGame = false;
				
				//Put choosefile into the game's directory
				chooseFile.setCurrentDirectory( new File(".").getAbsoluteFile());

				int buttonChosen = chooseFile.showOpenDialog(frame);
				if(buttonChosen == chooseFile.APPROVE_OPTION)
				{
					//Get user's input for load file
					File loadFile = chooseFile.getSelectedFile();
					Scanner input = null;

					try
					{
						input = new Scanner(loadFile);
						System.out.println(bkBoard.length);
						for(int setUpBoard = 0; setUpBoard < bkBoard.length; setUpBoard++)
						{
							setUpSlotCombined( input.nextInt(), input.nextInt() , input.nextInt() );
						}

						infoPanel.changeText("===== Game Loaded ====\n\n");

						//Check player's turn
						curPlayer = input.nextInt();

						//Check Played doubles
						if( input.next().equals("false") )
						{
							playedDoubles = false;
						}
						else
						{
							playedDoubles = true;
						}

						//Move counter for doubles
						moveCounter = input.nextInt();

						//Check of player rolled before saving
						if( input.next().equals("false"))
						{
							//Info for User
							infoPanel.changeText("It is currently Player " + curPlayer + "'s turn. You rolled before you saved!\n");
									
							//Rolled
							controlPanel.alreadyRolled();

							//Don't overwrite d1Used/d2Used values
							nLoad = true;

							//Dice values
							d1 = input.nextInt();
							controlPanel.setDiceOne(d1);
							d2 = input.nextInt();
							controlPanel.setDiceTwo(d2);

							//Rolled
							controlPanel.alreadyRolled();
						}
						else
						{
							//Put previous dice values on die, but 
							//reset d1 and d2
							d1 = -1;
							controlPanel.setDiceOne(input.nextInt());
							d2 = -1;
							controlPanel.setDiceTwo(input.nextInt());

							//Info for User
							infoPanel.changeText("\nIt is currently Player " + curPlayer + "'s turn. You need to roll!\n");

							//Did not roll
							controlPanel.resetRoll();
						}

						//Dice Used
						if( input.next().equals("true") )
						{
							d1Used = true;
						}
						else
						{
							d1Used = false;
						}

						if( input.next().equals("true") )
						{
							d2Used = true;
						}
						else
						{
							d2Used = false;
						}

						//Prevent out of bounds, no more game loads
						boardPanel.resetButton();
					}
					catch( FileNotFoundException exception)
					{
						infoPanel.changeText("\nFile not found, please try to load again!");
					}
					finally
					{
						if(input != null)
						{
							input.close();
						}

					}
				}
			}
		}
	}
}