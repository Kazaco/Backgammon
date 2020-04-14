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
        return numCheckers;
    }

    //Remove checker from a slot
    public boolean removeChecker()
    {
        //Check that slot selected actually has checkers
        if(numCheckers > 0)
        {
            //Change 'color' to empty
            checkers[numCheckers - 1].setCheckerColor(0);
            numCheckers--;
            return true;
        }
        else
        {
            //Can't remove anything!
            System.out.println("Removed nothing!");
            return false;
        }
    }

    //Add checker to a slot
    public boolean addChecker(int color)
    {
        checkers[numCheckers].setCheckerColor(color);
        numCheckers++;
        return true;
    }

    //Retrieve color of first checker in slot
    public int getCheckerTopColor()
    {
        if(numCheckers > 0)
        {
            return checkers[numCheckers - 1].getCheckerColor();
        }
        else
        {
            //Empty slot
            return 0;
        }
    }
}
