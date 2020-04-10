//Class for each individual checker 
public class Checker
{
    int color;  //0 = empty, 1 = white, 2 = blue

    //Create a checker location
    public Checker()
    {
        // System.out.println("New Checker!");
        color = 0;
    }

    //Change a checker's color
    public void setCheckerColor(int newColor)
    {
        color = newColor;
    }

    //Retrieve checker's color
    public int getCheckerColor()
    {
        return color;
    }
}