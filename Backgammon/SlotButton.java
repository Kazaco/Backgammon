//SlotButton
import javax.swing.JButton;
import java.awt.Color;
//Abstract class so we can have many buttons
//in an array but with many different appearances 
public abstract class SlotButton extends JButton
{
	protected Color background;
	protected boolean settingCheckers = false;
	protected int checkerColor = 0;
	protected int numCheckers = 0;
	protected boolean highlightingMoves = false;

	//Sets background color
	public abstract void setBkgdColor(Color b);
  	//Sets triangle color (if applicable)
	public abstract void setTriColor(Color t);

	//Sets checker attributes
	public void setCheckers(int c, int n)
	{
		//Error checking for invalid checker colors
		if( c > 2 || c < 0)
		{
			System.out.println("Invalid checker color.....");
			return;
		}
		
		checkerColor = c;
		numCheckers = n;
		
		//Reset the space
		settingCheckers = false;
		repaint();
		
		//Setting Blue or white checkers
		if( c == 1 || c == 2 )
		{
			settingCheckers = true;
			repaint();
		}
		//Space is cleared
		else
			return;
	}

	//Flags a SlotButton for highlighting valid moves
	public void setHighlight(boolean flag)
	{
		highlightingMoves = flag;
		setCheckers( checkerColor, numCheckers );
	}
	
	//Flags a SlotButton for having no valid moves
	public void setNoValidMoves(boolean flag)
	{
		if ( flag == true )
		{
			background = Color.RED;
		}
		else
		{
			background = new Color(32, 32, 32);
		}
		
		setCheckers( checkerColor, numCheckers );
	}

	//Accessors: Used for button logic
	public int getNumCheckers()
	{
		return numCheckers;
	}

	public int getCheckerColor()
	{
		return checkerColor;
	}
}
