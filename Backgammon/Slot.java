//Class for each individual slot ( triangles on board, off-board, and bar)
public class Slot
{
    private Checker [] checkers = new Checker[15];   //Array references of checkers in each slot
    private int numCheckers;
    
    //Create an empty slot
    public Slot()
    {   
        //No Checkers are inserted at beginnning, just creating slots
        numCheckers = 0;

        //Create 'blank' checkers for each slot
        for(int j = 0; j < 15; j++)
        {
            checkers[j] = new Checker();
        }
    }

    public boolean setNumCheckers(int color, int cNum)
    {
        //Reset numCheckers value (if somehow changed before)
        numCheckers = 0;

        //Set number of checkers in each slot
        if(cNum >= 0 && cNum <= 15)
        {
            for(int i = 0; i < cNum; i++)
            {
                checkers[i].setCheckerColor(color);
                numCheckers++;
            }
            return true;
        }
        else
        {
            //Invalid Checker Amount
            System.out.println("Invalid Set Checker Amount");
            return false;
        }
    }

    //Retrieve number of checkers in a slot
    public int getCheckerNumInSlot()
    {
        //Slot is not empty
        if(getFirstCheckerColor() == 1 || getFirstCheckerColor() == 2)
        {
            int counter = 0;
            for(int i = 0; i < 15; i++)
            {
                //No more checkers to count
                if(checkers[i].getCheckerColor() == 0)
                {
                    return counter;
                }
                counter++;
            }
            return counter;
        }
        //Empty
        else
        {
            return 0;
        }
    }

    //Retrieve color of first checker in slot
    public int getFirstCheckerColor()
    {
        return checkers[0].getCheckerColor();
    }
}
