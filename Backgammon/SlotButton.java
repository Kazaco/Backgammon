//SlotButton
import javax.swing.JButton;
import java.awt.Color;
//Abstract class so we can have many buttons
//in an array but with many different appearances 
public abstract class SlotButton extends JButton
{
	protected Color background;
	protected boolean settingCheckers = false; //Change to FALSE after testing
	protected int checkerColor = 0; //Change to ZERO after testing
	protected int numCheckers = 0; //Change to ZERO after testing

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
}
