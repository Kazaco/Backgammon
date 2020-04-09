//SlotButton Panel Constructor
import javax.swing.JButton;
import java.awt.Color;
//Abstract class so we can have many buttons
//in an array but with many different appearances
public abstract class SlotButton extends JButton
{
	//Sets background color
	public abstract void setBkgdColor(Color b);
  	//Sets triangle color (if applicable)
	public abstract void setTriColor(Color t);
}
