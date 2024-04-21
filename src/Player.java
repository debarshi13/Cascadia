import java.util.*;

import java.io.*;
import java.awt.*;
import java.io.BufferedReader.*;
public class Player {
	

	private int natureTokens, numTiles;
	private Tiles allTiles = new Tiles();
	private ArrayList<TreeMap<String, Object>> claimedHabitats;

	
	public Player(int playerNum, int startingTileIdx) {
		claimedHabitats = new ArrayList<>();
		
		ArrayList<Tile> startingTiles = allTiles.getStartingTiles();

		TreeMap<String, Object> habitatInfo = new TreeMap<>();
		habitatInfo.put("row_idx", 10);
		habitatInfo.put("col_idx", 10);
		habitatInfo.put("habitats", startingTiles.get(startingTileIdx).getHabitats());
		habitatInfo.put("wildlife", startingTiles.get(startingTileIdx).getHabitats());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles.get(startingTileIdx).getRotation());
		claimedHabitats.add(habitatInfo);
		
		habitatInfo = new TreeMap<>();
		habitatInfo.put("row_idx", 11);
		habitatInfo.put("col_idx", 9);
		habitatInfo.put("habitats", startingTiles.get(startingTileIdx+1).getHabitats());
		habitatInfo.put("wildlife", startingTiles.get(startingTileIdx+1).getHabitats());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles.get(startingTileIdx+1).getRotation());
		claimedHabitats.add(habitatInfo);
	
		habitatInfo = new TreeMap<>();
		habitatInfo.put("row_idx", 11);
		habitatInfo.put("col_idx", 10);
		habitatInfo.put("habitats", startingTiles.get(startingTileIdx+2).getHabitats());
		habitatInfo.put("wildlife", startingTiles.get(startingTileIdx+2).getHabitats());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles.get(startingTileIdx+2).getRotation());
		claimedHabitats.add(habitatInfo);
	}



	
}
