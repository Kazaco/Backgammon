//Class for each individual slot ( triangles on board, off-board, and bar)
public class Slot
{
    private Checker [] checkers = new Checker[15];   //Array references of checkers in each slot
    
    //Create an empty slot
    public Slot()
    {
        // System.out.println("New Slot!");
        for(int j = 0; j < 15; j++)
        {
            checkers[j] = new Checker();
        }
    }

    public boolean insertChecker()
    {
        return true;
    }

    //Retrieve number of checkers in a slot
    public int getCheckerNumInSlot()
    {
        //Slot is not empty
        if(getFirstCheckerInSlot() == 1 || getFirstCheckerInSlot() == 2)
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
    public int getFirstCheckerInSlot()
    {
        return checkers[0].getCheckerColor();
    }
}
