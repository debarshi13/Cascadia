
import java.awt.Point;
import java.util.*;


public class Player {
	private int natureTokenCount, numTiles;
	private Tiles allTiles = new Tiles();
	private ArrayList<TreeMap<String, Object>> claimedHabitats;
	private int turnsLeft;
	private TreeMap<String, HabitatLocations> habitatWithTokens;
	int center_i = 10;
	int center_j = 10;
	Map<Integer, Integer> elkScoring = Map.of(
		1, 2,
		2, 5,
		3, 9,
		4, 13
	);

	

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

	public void addHabitatLocForToken(String token, int i, int j, int n)
	{
		HabitatLocations locList = habitatWithTokens.get(token);
		locList.addLocation(i, j, n);
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
			// ArrayList<String> tokens = null;

			TreeMap<String, Object> claimedHab_left = searchHabitatWithTokenByTileLoc(row_i, col_j-1);
			if (claimedHab_left != null && (boolean)(claimedHab_left.get("tokenPlaced"))) {
				addAdjcTokenCnt(claimedHab_left, adjacentTokens);
			}
			TreeMap<String, Object> claimedHab_right = searchHabitatWithTokenByTileLoc(row_i, col_j+1);
			if (claimedHab_right != null && (boolean)(claimedHab_right.get("tokenPlaced"))) {
				addAdjcTokenCnt(claimedHab_right, adjacentTokens);
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
				addAdjcTokenCnt(claimedHab_above_left, adjacentTokens);
			}
			if (claimedHab_above_right != null && (boolean)(claimedHab_above_right.get("tokenPlaced"))) {
				addAdjcTokenCnt(claimedHab_above_right, adjacentTokens);
			}
			if (claimedHab_down_left != null && (boolean)(claimedHab_down_left.get("tokenPlaced"))) {
				addAdjcTokenCnt(claimedHab_down_left, adjacentTokens);
			}
			if (claimedHab_down_right != null && (boolean)(claimedHab_down_right.get("tokenPlaced"))) {
				addAdjcTokenCnt(claimedHab_down_right, adjacentTokens);
			}

			for (Map.Entry<String, Integer> entry : adjacentTokens.entrySet()) {
				if (entry.getValue() > 0)
					totalUniqueCnt++;
			}

		}
		
		System.out.println("*******************fox**************  " + totalUniqueCnt + "************");
		return totalUniqueCnt;
	}


	public int elkScoreCalculate() 
	{
		ArrayList<ArrayList<Integer>> elkTileCons_all = new ArrayList<>();
		int elkScoreTotal = 0;

		HabitatLocations horizontalList = habitatWithTokens.get("elk");
		createConnectElkList(elkTileCons_all, horizontalList);

		HabitatLocations hexList_dir_120 = transformToHexRowColDir(horizontalList, 120);
		createConnectElkList(elkTileCons_all, hexList_dir_120);

		HabitatLocations hexList_dir_60 = transformToHexRowColDir(horizontalList, 60);
		createConnectElkList(elkTileCons_all, hexList_dir_60);

		System.out.println(elkTileCons_all);

		ArrayList<ArrayList<Integer>> uniqueElkLines =  generateUniqueLines(elkTileCons_all);
		System.out.println("~~~~~~~~~~~~~Elk lines~~~");
		System.out.println(uniqueElkLines);
		for (ArrayList<Integer> lineList : uniqueElkLines)
		{
			elkScoreTotal += elkScoring.get(lineList.size());
		}
		System.out.println("******* Elk total Score*** "+ elkScoreTotal);
		return elkScoreTotal;
	}

	public ArrayList<ArrayList<Integer>> generateUniqueLines(ArrayList<ArrayList<Integer>> tileCons_all)
	{
		ArrayList<ArrayList<Integer>> uniqueLines = new ArrayList<>();
		while (tileCons_all.size() != 0)
		{
			ArrayList<Integer> occupiedLine = findLongestLine(tileCons_all);
			uniqueLines.add(occupiedLine);
			tileCons_all.remove(occupiedLine);
			ArrayList<ArrayList<Integer>> tempLines = new ArrayList<>();
			for (ArrayList<Integer> t: tileCons_all)
				tempLines.add(t);
			for (int tileNum : occupiedLine)
			{
				for (ArrayList<Integer> lineList : tempLines)
				{
					if (lineList.contains(tileNum))
						tileCons_all.remove(lineList);
				}
			}
		}
		return uniqueLines;
	}

	public ArrayList<Integer> findLongestLine (ArrayList<ArrayList<Integer>> lines) 
	{
		int lengthCnt = 0;
		ArrayList<Integer> tempList = null;
		for (ArrayList<Integer> line : lines)
		{
			if (line.size() > lengthCnt)
			{
				lengthCnt = line.size();
				tempList = line;
			}
		}
		return tempList;
	}


	public HabitatLocations transformToHexRowColDir(HabitatLocations hList, int dirAngle)
	{
		HabitatLocations hexRowColList = new HabitatLocations();
		ArrayList<Location> tokenList = hexRowColList.getHabitLocList();
		int ref_j = 0;
		for (Location loc : hList.getHabitLocList()) 
		{
			int row = loc.getRow();
			int col = loc.getCol();
			int t_num = loc.getTileNum();
			if (dirAngle == 120)
				ref_j = center_j - ((center_i - row) + (row%2))/2;
			else if (dirAngle == 60)
				ref_j = center_j + ((center_i - row) - (row%2))/2;
			int row_hex = row - center_i;
			int col_hex = col - ref_j;
			Location hexLoc = new Location(col_hex, row_hex, t_num);
			tokenList.add(hexLoc);
		}
		return hexRowColList;
	}

	
	public void createConnectElkList(ArrayList<ArrayList<Integer>> elkTileCons, HabitatLocations hList)
	{
		Collections.sort(hList.getHabitLocList(), new HabitatLocations());
		TreeMap<Integer, Integer> singleTileNextMap = new TreeMap<>();

		System.out.println(hList);
		for (Location loc : hList.getHabitLocList()) {
			int row = loc.getRow();
			int col = loc.getCol();
			int t_num = loc.getTileNum();
			int next_col = col + 1;
			Location nextLoc = nextTileInClaimedTokens(row, next_col, hList);
			if (nextLoc != null)
				singleTileNextMap.put(t_num, nextLoc.getTileNum());
			else
				singleTileNextMap.put(t_num, -1);
		}

		int totalCnt = singleTileNextMap.size();
		int p_idx = 0;
		while (p_idx < totalCnt)
		{
			ArrayList<Integer> tempConList = new ArrayList<>();
			
			Location loctemp = hList.getHabitLocList().get(p_idx);
			int t_num = loctemp.getTileNum();
			tempConList.add(t_num);
			p_idx++;
			int next_t_num = singleTileNextMap.get(t_num);
			while (next_t_num != -1)
			{
				tempConList.add(next_t_num);
				next_t_num = singleTileNextMap.get(next_t_num);
				p_idx++;
			}
			elkTileCons.add(tempConList);
		}
	}

	public Location nextTileInClaimedTokens(int row, int col, HabitatLocations hList)
	{

		for (Location loc : hList.getHabitLocList())
		{
			int r = loc.getRow();
			int c = loc.getCol();
			if (row == r && col == c)
				return loc;
		}
		return null;
	}

	public void addAdjcTokenCnt(TreeMap<String, Object> claimedHabNeb, TreeMap<String, Integer> adjacentTokens) 
	{
		ArrayList<String> tokens = null;
		if (claimedHabNeb != null && (boolean)(claimedHabNeb.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHabNeb.get("wildlife");
			String w = tokens.get(0);
			System.out.println(tokens.get(0));
			adjacentTokens.put(w, adjacentTokens.get((String)tokens.get(0)) +1);
		}
	}

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