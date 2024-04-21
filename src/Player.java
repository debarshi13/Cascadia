import java.util.*;


public class Player {
	

	private int natureTokens, numTiles;
	private Tiles allTiles = new Tiles();
	private ArrayList<TreeMap<String, Object>> claimedHabitats;

	
	public Player(int playerNum, int startingTileIdx) {
		claimedHabitats = new ArrayList<>();
		
		Tile[][] startingTiles = allTiles.getStartingTiles();

		TreeMap<String, Object> habitatInfo = new TreeMap<>();
		habitatInfo.put("row_idx", 10);
		habitatInfo.put("col_idx", 10);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][0].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][0].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][0].getRotation());
		claimedHabitats.add(habitatInfo);
		
		habitatInfo = new TreeMap<>();
		habitatInfo.put("row_idx", 11);
		habitatInfo.put("col_idx", 9);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][1].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][1].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][1].getRotation());
		claimedHabitats.add(habitatInfo);
	
		habitatInfo = new TreeMap<>();
		habitatInfo.put("row_idx", 11);
		habitatInfo.put("col_idx", 10);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][2].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][2].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][2].getRotation());
		claimedHabitats.add(habitatInfo);
	}



	
}
