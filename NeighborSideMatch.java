
import java.util.TreeMap;

public class NeighborSideMatch {
 
    // class NeighborLocIdx {
    //     int side_idx = -1;
    //     String side_loc = "";

    //     public NeighborLocIdx(int idx, String loc)
    //     {
    //         side_idx = idx;
    //         side_loc = loc;
    //     }
    // }

    // TreeMap<Integer, NeighborLocIdx> neighborSidesMatching = new TreeMap<>();

    // public NeighborSideMatch()
    // {
    //     NeighborLocIdx locidx = new NeighborLocIdx(2, "upleft");
    //     neighborSidesMatching.put(5, locidx);

    //     locidx = new NeighborLocIdx(3, "upright");
    //     neighborSidesMatching.put(0, locidx);

    //     locidx = new NeighborLocIdx(1, "left");
    //     neighborSidesMatching.put(4, locidx);
    
    //     locidx = new NeighborLocIdx(4, "right");
    //     neighborSidesMatching.put(1, locidx);

    //     locidx = new NeighborLocIdx(0, "downleft");
    //     neighborSidesMatching.put(3, locidx);

    //     locidx = new NeighborLocIdx(5, "downright");
    //     neighborSidesMatching.put(2, locidx);
    // }

    TreeMap<Integer, String> neighborSidesMatching = new TreeMap<>();

    public NeighborSideMatch ()
    {
        neighborSidesMatching.put(5, "upleft");
        neighborSidesMatching.put(0, "upright");
        neighborSidesMatching.put(4, "left");
        neighborSidesMatching.put(1, "right");
        neighborSidesMatching.put(3, "downleft");
        neighborSidesMatching.put(2, "downright");

    }

    public TreeMap<Integer, String> getNeighborSidesMatching()
    {
        return neighborSidesMatching;
    }

}
