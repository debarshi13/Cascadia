
import java.util.ArrayList;
import java.util.Comparator;

public class HabitatLocations implements Comparator<Location> {

    ArrayList <Location> habiTatLocList = new ArrayList<>();
    @Override
    public int compare(Location loc1, Location loc2) {

        if (loc1.getRow() == loc2.getRow())
        {
            if (loc1.getCol() == loc2.getCol())
                return 0;
            else if (loc1.getCol() > loc2.getCol())
                return 1;
            else 
                return -1;
        }
        
        else if (loc1.getRow() > loc2.getRow())
        {
            return 1;
        }
        else 
        {
            return -1;
        }
    }

    public void addLocation (int i, int j, int n)
    {
        Location loc = new Location(i, j, n);
        habiTatLocList.add(loc);
    }
    
    @Override
    public String toString() 
    {
        String l = "";
        for (Location loc : habiTatLocList) {
            l = l + loc;
        }
        return l;
    }

    public ArrayList<Location> getHabitLocList()
    {
        return habiTatLocList;
    }
}
