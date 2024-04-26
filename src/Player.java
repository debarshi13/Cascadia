
import java.awt.Point;
import java.util.*;


public class Player {
	private int natureTokenCount, numTiles;
	private Tiles allTiles = new Tiles();
	private ArrayList<TreeMap<String, Object>> claimedHabitats;
	private int turnsLeft;
	private TreeMap<String, HabitatLocations> habitatWithTokens;

	public Player(int playerNum, int startingTileIdx, int turns) {
		turnsLeft = turns;
		natureTokenCount = 0;
		claimedHabitats = new ArrayList<>();
		habitatWithTokens = new TreeMap<>();

		HabitatLocations bearLocations = new HabitatLocations();
		HabitatLocations elkLocations = new HabitatLocations();
		HabitatLocations hawkLocations = new HabitatLocations();
		HabitatLocations salmonLocations = new HabitatLocations();
		HabitatLocations foxLocations = new HabitatLocations();

		habitatWithTokens.put("bear", bearLocations);
		habitatWithTokens.put("elk", elkLocations);
		habitatWithTokens.put("hawk", hawkLocations);
		habitatWithTokens.put("salmon", salmonLocations);
		habitatWithTokens.put("fox", foxLocations);

		
		Tile[][] startingTiles = allTiles.getStartingTiles();

		Hexagon hex = null;
		TreeMap<String, Object> habitatInfo = new TreeMap<>();
		habitatInfo.put("tileNum",startingTiles[startingTileIdx][0].getTileNum());
		habitatInfo.put("row_idx", 4);
		habitatInfo.put("col_idx", 10);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][0].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][0].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][0].getRotation());
		habitatInfo.put("hexagon", hex);
		claimedHabitats.add(habitatInfo);

		
		habitatInfo = new TreeMap<>();
		habitatInfo.put("tileNum",startingTiles[startingTileIdx][1].getTileNum());
		habitatInfo.put("row_idx", 5);
		habitatInfo.put("col_idx", 9);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][1].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][1].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][1].getRotation());
		habitatInfo.put("hexagon", hex);
		claimedHabitats.add(habitatInfo);


		habitatInfo = new TreeMap<>();
		habitatInfo.put("tileNum",startingTiles[startingTileIdx][2].getTileNum());
		habitatInfo.put("row_idx", 5);
		habitatInfo.put("col_idx", 10);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][2].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][2].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][2].getRotation());
		habitatInfo.put("hexagon", hex);
		claimedHabitats.add(habitatInfo);
	}

	public ArrayList<TreeMap<String, Object>> getClaimedHabitats() {
		return claimedHabitats;
	}

	public TreeMap<String, Object> searchHabitat(Point pt)
	{
		
		for (TreeMap<String, Object> cTile : claimedHabitats) {
			Hexagon hex = (Hexagon) cTile.get("hexagon");
			if(hex.contains(pt))
			{
				System.out.println("@@@@@@" + cTile.get("habitats"));
				return cTile;
			}
			
		}
		return null;
	}

	public int getTurnsLeft(){
		return turnsLeft;
	}
	public void turnUsed(){
		turnsLeft--;
	}

	public int getNumNatureToken() 
	{
		return natureTokenCount;
	}

	public void setNumNatureToken(int numNt)
	{
		natureTokenCount = numNt;
	}

	public void increaseNatureToken()
	{
		natureTokenCount++;
	}
	
	public void decreaseNatureToken()
	{
		natureTokenCount--;
	}

	public void addHabitatLocForToken(String token, int i, int j)
	{
		HabitatLocations locList = habitatWithTokens.get(token);
		locList.addLocation(i, j);
	}

	public void printClaimedHabInfo()
	{
		for (Map.Entry<String, HabitatLocations> entry : habitatWithTokens.entrySet()) 
		{
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}