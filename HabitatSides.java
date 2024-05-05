
import java.util.ArrayList;
import java.util.TreeMap;



public class HabitatSides {
	//private TreeMap<String, ArrayList<String>> habitatSidesInfo = new TreeMap<>();
	private TreeMap<String, String[]> habitatSidesInfo = new TreeMap<>();

	public HabitatSides ()
	{
		String[] sides = new String[] {"lake", "lake", "lake", "lake", "lake", "lake"};
		habitatSidesInfo.put("lake", sides);

		sides = new String[] {"lake", "lake", "lake", "forest", "forest", "forest"};
		habitatSidesInfo.put("forest+lake", sides);

		sides = new String[] {"lake", "lake", "lake", "desert", "desert", "desert"};
		habitatSidesInfo.put("desert+lake", sides);

		sides = new String[] {"mountain", "mountain", "mountain", "lake", "lake", "lake"};
		habitatSidesInfo.put("lake+mountain", sides);

		sides = new String[] {"lake", "lake", "lake", "swamp", "swamp", "swamp"};
		habitatSidesInfo.put("swamp+lake", sides);

		sides = new String[] {"forest", "forest", "forest", "forest", "forest", "forest"};
		habitatSidesInfo.put("forest", sides);

		sides = new String[] {"desert", "desert", "desert", "forest", "forest", "forest"};
		habitatSidesInfo.put("forest+desert", sides);

		sides = new String[] {"swamp", "swamp", "swamp", "forest", "forest", "forest"};
		habitatSidesInfo.put("forest+swamp", sides);

		sides = new String[] {"forest", "forest", "forest", "mountain", "mountain", "mountain"};
		habitatSidesInfo.put("mountain+forest", sides);

		sides = new String[] {"mountain", "mountain", "mountain", "mountain", "mountain", "mountain"};
		habitatSidesInfo.put("mountain", sides);

		sides = new String[] {"desert", "desert", "desert", "mountain", "mountain", "mountain"};
		habitatSidesInfo.put("mountain+desert", sides);

		sides = new String[] {"swamp", "swamp", "swamp", "mountain", "mountain", "mountain"};
		habitatSidesInfo.put("mountain+swamp", sides);

		sides = new String[] {"swamp", "swamp", "swamp", "swamp", "swamp", "swamp"};
		habitatSidesInfo.put("swamp", sides);

		sides = new String[] {"swamp", "swamp", "swamp", "desert", "desert", "desert"};
		habitatSidesInfo.put("desert+swamp", sides);

		sides = new String[] {"desert", "desert", "desert", "desert", "desert", "desert"};
		habitatSidesInfo.put("desert", sides);

	}

	public TreeMap<String, String[]> getHabitatSidesInfo()
	{
		return habitatSidesInfo;
	}
	
}
