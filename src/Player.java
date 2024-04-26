
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

	public int foxScoreCalculate() 
	{
		HabitatLocations hList = habitatWithTokens.get("fox");
		int totalUniqueCnt = 0;
		
		for (Location loc : hList.getHabitLocList()) {
			TreeMap<String, Integer> adjacentTokens = new TreeMap<>();
			adjacentTokens.put("bear", 0);
			adjacentTokens.put("elk", 0);
			adjacentTokens.put("hawk", 0);
			adjacentTokens.put("fox", 0);
			adjacentTokens.put("salmon", 0);

			int row_i = loc.getRow();
			int col_j = loc.getCol();
			TreeMap<String, Object> claimedHab_above_left = null;
			TreeMap<String, Object> claimedHab_above_right = null;
			TreeMap<String, Object> claimedHab_down_left = null;
			TreeMap<String, Object> claimedHab_down_right = null;
			ArrayList<String> tokens = null;

			TreeMap<String, Object> claimedHab_left = searchHabitatWithTokenByTileLoc(row_i, col_j-1);
			if (claimedHab_left != null && (boolean)(claimedHab_left.get("tokenPlaced"))) {
				tokens = (ArrayList<String>)claimedHab_left.get("wildlife");
				String w = tokens.get(0);
				System.out.println(tokens.get(0));
				adjacentTokens.put(w, adjacentTokens.get(w) +1);
			}
			TreeMap<String, Object> claimedHab_right = searchHabitatWithTokenByTileLoc(row_i, col_j+1);
			if (claimedHab_right != null && (boolean)(claimedHab_right.get("tokenPlaced"))) {
				tokens = (ArrayList<String>)claimedHab_right.get("wildlife");
				String w = tokens.get(0);
				System.out.println(tokens.get(0));
				adjacentTokens.put(w, adjacentTokens.get(w) +1);
			}
			if (row_i %2 == 0) {
				claimedHab_above_left = searchHabitatWithTokenByTileLoc(row_i-1, col_j-1);
				claimedHab_above_right = searchHabitatWithTokenByTileLoc(row_i-1, col_j);
				claimedHab_down_left = searchHabitatWithTokenByTileLoc(row_i+1, col_j-1);
				claimedHab_down_right = searchHabitatWithTokenByTileLoc(row_i+1, col_j);
			}
			else {
				claimedHab_above_left = searchHabitatWithTokenByTileLoc(row_i-1, col_j);
				claimedHab_above_right = searchHabitatWithTokenByTileLoc(row_i-1, col_j+1);
				claimedHab_down_left = searchHabitatWithTokenByTileLoc(row_i+1, col_j);
				claimedHab_down_right = searchHabitatWithTokenByTileLoc(row_i+1, col_j+1);

			}

			if (claimedHab_above_left != null && (boolean)(claimedHab_above_left.get("tokenPlaced"))) {
				tokens = (ArrayList<String>)claimedHab_above_left.get("wildlife");
				String w = tokens.get(0);
				System.out.println(tokens.get(0));
				adjacentTokens.put(w, adjacentTokens.get(w) +1);
			}
			if (claimedHab_above_right != null && (boolean)(claimedHab_above_right.get("tokenPlaced"))) {
				tokens = (ArrayList<String>)claimedHab_above_right.get("wildlife");
				String w = tokens.get(0);
				System.out.println(tokens.get(0));
				adjacentTokens.put(w, adjacentTokens.get(w) +1);
			}
			if (claimedHab_down_left != null && (boolean)(claimedHab_down_left.get("tokenPlaced"))) {
				tokens = (ArrayList<String>)claimedHab_down_left.get("wildlife");
				String w = tokens.get(0);
				System.out.println(tokens.get(0));
				adjacentTokens.put(w, adjacentTokens.get(w) +1);
			}
			if (claimedHab_down_right != null && (boolean)(claimedHab_down_right.get("tokenPlaced"))) {
				tokens = (ArrayList<String>)claimedHab_down_right.get("wildlife");
				String w = tokens.get(0);
				System.out.println(tokens.get(0));
				adjacentTokens.put(w, adjacentTokens.get((String)tokens.get(0)) +1);
			}

			for (Map.Entry<String, Integer> entry : adjacentTokens.entrySet()) {
				if (entry.getValue() > 0)
					totalUniqueCnt++;
			}

		}
		
		System.out.println("*******************fox**************  " + totalUniqueCnt + "************");
		return totalUniqueCnt;
	}


	// public void addAdjcTokenCnt(TreeMap<String, Object> claimedHabNeb) 
	// {
	// 	if (claimedHabNeb != null && (boolean)(claimedHabNeb.get("tokenPlaced"))) {
	// 		tokens = (ArrayList<String>)claimedHabNeb.get("wildlife");
	// 		String w = tokens.get(0);
	// 		System.out.println(tokens.get(0));
	// 		adjacentTokens.put(w, adjacentTokens.get((String)tokens.get(0)) +1);
	// 	}
	// }

	public TreeMap<String, Object> searchHabitatWithTokenByTileLoc(int row, int col)
	{
		for (TreeMap<String, Object> cHabitat : getClaimedHabitats()) 
		{
			if (row == (int)(cHabitat.get("row_idx")) && col == (int)(cHabitat.get("col_idx")))
			{
				if ((boolean)(cHabitat.get("tokenPlaced")) == true)
					return cHabitat;
				else
					return null;
			}
		}
		return null;
	}

}