
import java.awt.Point;
import java.util.*;
import java.util.Map.Entry;


public class Player {
	private int natureTokenCount, numTiles;
	private Tiles allTiles = new Tiles();
	private ArrayList<TreeMap<String, Object>> claimedHabitats;
	private int turnsLeft;
	private TreeMap<String, HabitatLocations> habitatWithTokens;
	private TreeMap<String, HabitatLocations> habitatsByHabType;
	private TreeMap<String, Integer> finalHabCounts;

	private TreeMap<String, ArrayList<ArrayList<Integer>>> habitatsConnected;

	int center_i = 10;
	int center_j = 10;
	Map<Integer, Integer> elkScoring_A = Map.of(
		0, 0,
		1, 2,
		2, 5,
		3, 9,
		4, 13
	);

	Map<Integer, Integer> hawkScoring_A = Map.of(
		0, 0,
		1, 2,
		2, 5,
		3, 8,
		4, 11,
		5, 14, 
		6, 18, 
		7, 22, 
		8, 26
	);

	Map<Integer, Integer> bearScoring_A = Map.of(
		0, 0,
		1, 4,
		2, 11,
		3, 19,
		4, 27
	);



	public Player(int playerNum, int startingTileIdx, int turns) {
		turnsLeft = turns;
		natureTokenCount = 0;
		claimedHabitats = new ArrayList<>();
		habitatWithTokens = new TreeMap<>();
		habitatsByHabType = new TreeMap<>();
		habitatsConnected = new TreeMap<>();

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

		HabitatLocations lakeLocations = new HabitatLocations();
		HabitatLocations forestLocations = new HabitatLocations();
		HabitatLocations mountainLocations = new HabitatLocations();
		HabitatLocations desertLocations = new HabitatLocations();
		HabitatLocations swampLocations = new HabitatLocations();

		habitatsByHabType.put("lake", lakeLocations);
		habitatsByHabType.put("forest", forestLocations);
		habitatsByHabType.put("mountain", mountainLocations);
		habitatsByHabType.put("desert", desertLocations);
		habitatsByHabType.put("swamp", swampLocations);

		ArrayList<ArrayList<Integer>> lakeConnLocations = new ArrayList<>();
		ArrayList<ArrayList<Integer>> forestConnLocations = new ArrayList<>();
		ArrayList<ArrayList<Integer>> mountainConnLocations = new ArrayList<>();
		ArrayList<ArrayList<Integer>> desertConnLocatioans = new ArrayList<>();
		ArrayList<ArrayList<Integer>> swampConnLocatioans = new ArrayList<>();

		habitatsConnected.put("lake", lakeConnLocations);
		habitatsConnected.put("forest", forestConnLocations);
		habitatsConnected.put("mountain", mountainConnLocations);
		habitatsConnected.put("desert", desertConnLocatioans);
		habitatsConnected.put("swamp", swampConnLocatioans);

		finalHabCounts = new TreeMap<>();

		Tile[][] startingTiles = allTiles.getStartingTiles();

		Hexagon hex = null;
		TreeMap<String, Object> habitatInfo = new TreeMap<>();
		habitatInfo.put("tileNum",startingTiles[startingTileIdx][0].getTileNum());
		habitatInfo.put("row_idx", 4);
		habitatInfo.put("col_idx", 6);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][0].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][0].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][0].getRotation());
		habitatInfo.put("hexagon", hex);
		claimedHabitats.add(habitatInfo);
		addHabitatForHab((int)(habitatInfo.get("row_idx")), (int)(habitatInfo.get("col_idx")), habitatInfo);
		
		habitatInfo = new TreeMap<>();
		habitatInfo.put("tileNum",startingTiles[startingTileIdx][1].getTileNum());
		habitatInfo.put("row_idx", 5);
		habitatInfo.put("col_idx", 5);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][1].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][1].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][1].getRotation());
		habitatInfo.put("hexagon", hex);
		claimedHabitats.add(habitatInfo);
		addHabitatForHab((int)(habitatInfo.get("row_idx")), (int)(habitatInfo.get("col_idx")), habitatInfo);

		habitatInfo = new TreeMap<>();
		habitatInfo.put("tileNum",startingTiles[startingTileIdx][2].getTileNum());
		habitatInfo.put("row_idx", 5);
		habitatInfo.put("col_idx", 6);
		habitatInfo.put("habitats", startingTiles[startingTileIdx][2].getHabitats());
		habitatInfo.put("wildlife", startingTiles[startingTileIdx][2].getWildlife());
		habitatInfo.put("tokenPlaced", false);
		habitatInfo.put("rotation", startingTiles[startingTileIdx][2].getRotation());
		habitatInfo.put("hexagon", hex);
		claimedHabitats.add(habitatInfo);
		addHabitatForHab((int)(habitatInfo.get("row_idx")), (int)(habitatInfo.get("col_idx")), habitatInfo);

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
				//System.out.println("@@@@@@" + cTile.get("habitats"));
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

	public void addHabitatForHab(int i, int j, TreeMap<String, Object> cHabitat)
	{
		//System.out.println("cHabitat tile num: " + cHabitat.get("tileNum"));
		ArrayList<String> hList = (ArrayList<String>)(cHabitat.get("habitats"));
		for (String h : hList)
		{
			HabitatLocations locList = habitatsByHabType.get(h);
			locList.addLocation(i, j, (int)(cHabitat.get("tileNum")));

		}
	}
	

	public void printHabitatInfo()
	{
		for (Map.Entry<String, HabitatLocations> entry : habitatsByHabType.entrySet())
		{
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}

	// public void printClaimedHabInfo()
	// {
	// 	for (Map.Entry<String, HabitatLocations> entry : habitatWithTokens.entrySet()) 
	// 	{
	// 		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	// 		System.out.println(entry.getKey());
	// 		System.out.println(entry.getValue());
	// 		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	// 	}
	// }

	public ArrayList<Location> getNeighbourTilesForWildlife(Location loc, String wildlife)
	{
		ArrayList<Location> NeighboringTiles = new ArrayList<Location>();
		ArrayList<String> tokens = null;
		int row_i = loc.getRow();
		int col_j = loc.getCol();
		TreeMap<String, Object> claimedHab_above_left = null;
		TreeMap<String, Object> claimedHab_above_right = null;
		TreeMap<String, Object> claimedHab_down_left = null;
		TreeMap<String, Object> claimedHab_down_right = null;
		TreeMap<String, Object> claimedHab_left = searchHabitatWithTokenByTileLoc(row_i, col_j-1);
		TreeMap<String, Object> claimedHab_right = searchHabitatWithTokenByTileLoc(row_i, col_j+1);
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
		if (claimedHab_left != null && (boolean)(claimedHab_left.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHab_left.get("wildlife");
			if(tokens.get(0) == wildlife)
				NeighboringTiles.add(new Location(row_i, col_j-1));
		}
		if (claimedHab_right != null && (boolean)(claimedHab_right.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHab_right.get("wildlife");
			if(tokens.get(0) == wildlife)
				NeighboringTiles.add(new Location(row_i, col_j+1));
		}
		if (claimedHab_above_left != null && (boolean)(claimedHab_above_left.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHab_above_left.get("wildlife");
			if(tokens.get(0) == wildlife){
				if (row_i %2 == 0) {
					NeighboringTiles.add(new Location(row_i-1, col_j-1));
				}else{
					NeighboringTiles.add(new Location(row_i-1, col_j));
				}
			}
		}
		if (claimedHab_above_right != null && (boolean)(claimedHab_above_right.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHab_above_right.get("wildlife");
			if(tokens.get(0) == wildlife){
				if (row_i %2 == 0) {
					NeighboringTiles.add(new Location(row_i-1, col_j));
				}else{
					NeighboringTiles.add(new Location(row_i-1, col_j+1));
				}
			}
		}
		if (claimedHab_down_left != null && (boolean)(claimedHab_down_left.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHab_down_left.get("wildlife");
			if(tokens.get(0) == wildlife){
				if (row_i %2 == 0) {
					NeighboringTiles.add(new Location(row_i+1, col_j-1));
				}else{
					NeighboringTiles.add(new Location(row_i+1, col_j));
				}
			}
		}
		if (claimedHab_down_right != null && (boolean)(claimedHab_down_right.get("tokenPlaced"))) {
			tokens = (ArrayList<String>)claimedHab_down_right.get("wildlife");
			if(tokens.get(0) == wildlife){
				if (row_i %2 == 0) {
					NeighboringTiles.add(new Location(row_i+1, col_j));
				}else{
					NeighboringTiles.add(new Location(row_i+1, col_j+1));
				}
			}
		}	
		return NeighboringTiles;	
	}


	private boolean SalmonNotFound(ArrayList<Location> lst, Location loc){
		for(int i = 0; i < lst.size(); i++)
		{
			if(lst.get(i).getCol() == loc.getCol() && lst.get(i).getRow() == loc.getRow())
				return false;
		}
		return true;
	}
	public boolean SalmonAlreadyUsed(Location loc, ArrayList<String> lst){
		for(int i = 0; i < lst.size(); i++)
		{
			if(lst.get(i).contains(loc.toString()))
				return true;
		}
		return false;
	}
	public Location getNotCrowdedNeighbor(ArrayList<Location> l1, ArrayList<Location> l2){
		for(int i = 0; i < l1.size(); i++){
			boolean crowded = false;
			for(int j = 0; j < l2.size(); j++){
				if(l1.get(i).getRow() == l2.get(j).getRow() && l1.get(i).getCol() == l2.get(j).getCol())
					crowded = true;
			}
			if(crowded == false)
				return l1.get(i);
		}
		return l1.get(0);//to eliminate compile warning
	}
	public int salmonScoreCalculate(){
		int salmonScore = 0;
		HabitatLocations hList = habitatWithTokens.get("salmon");
		int neighboringSalmons = 0;
		ArrayList<Location> salmonLoc = new ArrayList<Location>();
		ArrayList<Location> salmonOnSchoolSide = new ArrayList<Location>();
		ArrayList<Location> salmonOnSchoolSideTemp = new ArrayList<Location>();
		ArrayList<Location> salmonOvercrowded = new ArrayList<Location>();
		ArrayList<String> salmonSchools = new ArrayList<String>();
		Location prevLoc, nextLoc;
		for (Location loc : hList.getHabitLocList()) {
			neighboringSalmons = getNeighbourTilesForWildlife(loc, "salmon").size();
			if(neighboringSalmons > 2){
				salmonOvercrowded.add(loc);
			}	
		}
		for (Location loc : hList.getHabitLocList()) {
			boolean locOverCrowded = false;
			for(int i = 0; i < salmonOvercrowded.size(); i++)
			{
				if(salmonOvercrowded.get(i).getRow() == loc.getRow() && salmonOvercrowded.get(i).getCol() == loc.getCol())
				{
					locOverCrowded = true;
					break;
				}
			}
			if(locOverCrowded)
				continue;
			ArrayList<Location> neighbors = getNeighbourTilesForWildlife(loc, "salmon");
			int disqualifiedNeighbors = 0;
			for(int n = neighbors.size() - 1; n >= 0; n--){
				for(int k = 0; k < salmonOvercrowded.size(); k++)
				{
					if(neighbors.size() > n){
						if(neighbors.get(n).getCol() == salmonOvercrowded.get(k).getCol() && neighbors.get(n).getRow() == salmonOvercrowded.get(k).getRow()){
							// System.out.println("removing " + neighbors.get(n).getRow() + " - " + neighbors.get(n).getCol() + " since it connects to an overcrowded salmon");
							// if(n < neighbors.size() - 1)
							// 	neighbors.remove(n);		
							disqualifiedNeighbors++;				
						}
					}
				}
			}
			neighboringSalmons = neighbors.size() - disqualifiedNeighbors;
			if(neighboringSalmons == 0){
				salmonScore += 2; //isolated salmon worth 2 points
			}else if(neighboringSalmons == 2){
				salmonLoc.add(loc);
			}
			else if(neighboringSalmons == 1){
				salmonLoc.add(loc);
				salmonOnSchoolSide.add(loc);
			}	
		}
		for(int i = 0; i < salmonLoc.size(); i++){
			salmonLoc.get(i).setUsed(false);
		}
		for(Location loc : salmonOnSchoolSide){
			//if a location has been used by traversing, mark it as used for the side of school list
			for(int k = 0; k < salmonOnSchoolSideTemp.size(); k++)
			{
				if(loc.getRow() == salmonOnSchoolSideTemp.get(k).getRow() && loc.getCol() == salmonOnSchoolSideTemp.get(k).getCol())
					loc.setUsed(true);
			}
			if(SalmonAlreadyUsed(loc, salmonSchools)){
				loc.setUsed(true);
			}
			if(loc.getUsed() == false){
				loc.setUsed(true);

				String salmonOneSchool = loc.toString();
				ArrayList<Location> neighbors = getNeighbourTilesForWildlife(loc, "salmon");
				neighboringSalmons = neighbors.size();
				int disqualifiedNeighbors = 0;
				for(int i = neighbors.size() - 1; i >= 0; i--){
					for(int k = 0; k < salmonOvercrowded.size(); k++)
					{
						if(neighbors.get(i).getRow() == salmonOvercrowded.get(k).getRow() && neighbors.get(i).getCol() == salmonOvercrowded.get(k).getCol()){
							// System.out.println("removing " + neighbors.get(i).getRow() + " - " + neighbors.get(i).getCol() + " since it connects to an overcrowded salmon");
							// if(i < neighbors.size() - 1)
							// 	neighbors.remove(i);
							disqualifiedNeighbors++;
						}
					}
				}
				neighboringSalmons = neighboringSalmons - disqualifiedNeighbors;
				if(neighboringSalmons == 0){
					salmonScore += 2;
				}
				else
				{
					prevLoc = loc;
					nextLoc = getNotCrowdedNeighbor(neighbors, salmonOvercrowded);
					salmonOneSchool += ("&" + nextLoc.toString());	
					//System.out.println("starting of the salmon hunt: " + prevLoc.toString() + " to " + nextLoc.toString());
					
					int neighborNum = 1; //it has to be 1 neighbour
					while(neighborNum > 0){
						
						neighbors = getNeighbourTilesForWildlife(nextLoc, "salmon");
						neighborNum = neighbors.size();
						int validNeihborNum = 0;
						for(int i = neighborNum - 1; i >= 0; i--){
							//don't count the salmon that starts the search
							if(SalmonNotFound(salmonLoc, neighbors.get(i))){
								continue;
							}
							if(salmonOneSchool.contains(neighbors.get(i).toString()))
								continue;
							if(neighbors.get(i).getRow() == prevLoc.getRow() && neighbors.get(i).getCol() == prevLoc.getCol())
							{
								continue;
							}
							
							for(int k = 0; k < salmonOvercrowded.size(); k++)
							{
								if(neighbors.get(i).getRow() == salmonOvercrowded.get(k).getRow() && neighbors.get(i).getCol() == salmonOvercrowded.get(k).getCol()){
									continue;
								}								
							}	
							nextLoc = neighbors.get(i);
							validNeihborNum++;					
						}
						neighborNum = validNeihborNum;
						if(salmonOneSchool.contains(nextLoc.toString()))
							neighborNum = 0;
						if(neighborNum == 1)
						{
							if(!salmonOneSchool.contains(nextLoc.toString()))
							{
								nextLoc.setUsed(true);
								salmonOneSchool += ("&" + nextLoc.toString());		
								prevLoc = nextLoc;
//								nextLoc = neighbors.get(0);	
								System.out.println("middle of the salmon hunt: " + salmonOneSchool);
							}
								
						}
						else{ //end of the school
							if(!salmonOneSchool.contains(nextLoc.toString()))
							{
								salmonOneSchool += ("&" + nextLoc.toString());
								salmonSchools.add(salmonOneSchool);
														
							}
							salmonSchools.add(salmonOneSchool);
							//System.out.println("end of the salmon hunt: " + salmonOneSchool);
							salmonOnSchoolSideTemp.add(nextLoc);
						}
					}
				}
				
			}
		}
		for(int i = 0; i < salmonSchools.size(); i++){
			//System.out.println(salmonSchools.get(i));
			String[] sTmps = salmonSchools.get(i).split("&");
			if(sTmps.length == 2)
				salmonScore += 4;
			else if(sTmps.length == 3)
				salmonScore += 7;
			else if(sTmps.length == 4)
				salmonScore += 11;
			else if(sTmps.length == 5)
				salmonScore += 15;
			else if(sTmps.length == 6)
				salmonScore += 20;
			else if(sTmps.length >= 7)
				salmonScore += 28;
		}

		//System.out.println("*************************SALMON SCORE: "+ salmonScore);
		return salmonScore;
		
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
		
		//System.out.println("*******************fox**************  " + totalUniqueCnt + "************");
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
			elkScoreTotal += elkScoring_A.get(lineList.size());
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

	
	public int hawkScoreCalculate() 
	{
		HabitatLocations hList = habitatWithTokens.get("hawk");
		int totalIsoHawkCnt = 0;
		int totalHawkScore = 0;

		for (Location loc : hList.getHabitLocList()) {
			boolean foundAdjHawk = false;
			int row_i = loc.getRow();
			int col_j = loc.getCol();
			TreeMap<String, Object> claimedHab_above_left = null;
			TreeMap<String, Object> claimedHab_above_right = null;
			TreeMap<String, Object> claimedHab_down_left = null;
			TreeMap<String, Object> claimedHab_down_right = null;

			TreeMap<String, Object> claimedHab_left = searchHabitatWithTokenByTileLoc(row_i, col_j-1);
			TreeMap<String, Object> claimedHab_right = searchHabitatWithTokenByTileLoc(row_i, col_j+1);
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

			if (checkTokenExistInHab(claimedHab_left, "hawk") == true ||
				checkTokenExistInHab(claimedHab_right, "hawk") == true ||
				checkTokenExistInHab(claimedHab_above_left, "hawk") == true || 
				checkTokenExistInHab(claimedHab_above_right, "hawk") == true ||
				checkTokenExistInHab(claimedHab_down_left, "hawk") == true || 
				checkTokenExistInHab(claimedHab_down_right, "hawk") == true)
				{
					foundAdjHawk = true;
				}

			if (foundAdjHawk == false)
			{
				totalIsoHawkCnt++;
			}
		}

		if (totalIsoHawkCnt > 8)
		{
			totalIsoHawkCnt = 8;
		}

		totalHawkScore += hawkScoring_A.get(totalIsoHawkCnt);
		//System.out.println ("@@@@@@@@@@@@@@@@@ hawk score @@@@@@@@@@@@@@@@@@ " + totalHawkScore);
		return totalHawkScore;
	}


	public int bearScoreCalculate() 
	{
		HabitatLocations hList = habitatWithTokens.get("bear");
		TreeMap<Integer, ArrayList<Integer>> neighborsList = new TreeMap<>();
		int totalBearPairCnt = 0;
		int totalBearScore = 0;
		TreeMap<Integer, Integer> bearPairs = new TreeMap<>();
		ArrayList<Integer> tNumsAdded = new ArrayList<>();

		for (Location loc : hList.getHabitLocList()) {
			int row = loc.getRow();
			int col = loc.getCol();
			int tNum = loc.getTileNum();
			ArrayList<Integer> nbNums = searchNeighborByToken(row, col, "bear");
			neighborsList.put(tNum, nbNums);
			//System.out.println("Bear at: " + tNum + "====>" + nbNums);
		}

		for (Map.Entry<Integer, ArrayList<Integer>> entry : neighborsList.entrySet()) 
		{
			int tNum = entry.getKey();
			ArrayList<Integer> nbNums = entry.getValue();
			if (nbNums.size() == 0)
				continue;
			else if (nbNums.size() >1)
				continue;
			else if (nbNums.size() == 1)
			{
				int neighborTokenNum = nbNums.get(0);
				ArrayList<Integer> nbtokenNums = neighborsList.get(neighborTokenNum);
				if (nbtokenNums.size() == 1 && nbtokenNums.get(0) == tNum) 
				{
					if (!tNumsAdded.contains(tNum))
					{
						bearPairs.put(tNum, nbNums.get(0));
						tNumsAdded.add(tNum);
						tNumsAdded.add(nbNums.get(0));
					}
				}
			}
		}

		// for (Map.Entry<Integer,Integer> entry : bearPairs.entrySet()) 
		// {
		// 	System.out.println("~~~~~~~~~~Bear: " + entry.getKey() + "====> " + entry.getValue());
		// }

		totalBearPairCnt = bearPairs.size();
		if (bearPairs.size() > 4)
			totalBearPairCnt = 4;
		totalBearScore = bearScoring_A.get(totalBearPairCnt);
		return totalBearScore;
	}


	public void habitatCorridorCalculate()
	{
		NeighborSideMatch sidesMatch = new NeighborSideMatch();
		TreeMap<Integer, String> sidesMatchPairs = sidesMatch.getNeighborSidesMatching();
		HabitatSides habitatSides = new HabitatSides();

		TreeMap<String, String[]> habitatSideList = habitatSides.getHabitatSidesInfo();
		ArrayList<Integer> unprocessedHabs = new ArrayList<>();


		for (Map.Entry<String, HabitatLocations> entry : habitatsByHabType.entrySet())
		{
			System.out.println("****************************************************************************************");
			String habNameToConnect = entry.getKey();
			System.out.println("habNameToConnect ==> " + habNameToConnect);
			ArrayList<ArrayList<Integer>> connectedHabsForEachHabType = habitatsConnected.get(habNameToConnect);
			connectedHabsForEachHabType.clear();
			HabitatLocations habList = entry.getValue();
			//ArrayList<Integer> nbHabFound = new ArrayList<>();
			TreeMap<Integer, ArrayList<Integer>> tempHabiMaps = new TreeMap<>();

			//copy all the potential hab under same hab name to unprocessed list
			for (Location h : habList.getHabitLocList()) {
				unprocessedHabs.add(h.getTileNum());
			}
			ArrayList<Integer> processedHabs = new ArrayList<>();

			// process each potential hab, 1) find the hab with rotation and hab type, 2) based on rotation, calculate the lookup ref_idx
			// from habitatSideList, 3) use ref_idx to look up the side hab info (e.g. "lake", "forest", etc.) 4) save all sides matching 
			// entry.key(). these are the sides need to be searched for neighbors. 
			// 4) find it's upper neighbor  hahaha
			// 5) Calculate the corresponding
			// neighbor side index (nb_side_idx) which is sharing the same side as of side_idx. (for example, side_idx = 0 shares with side_idx 
			// of 3 of its upperright neighbor tile). 6) use neighbor's rotation info to calculate the lookup nb_ref_idx to find out the
			for (Location habLoc: habList.getHabitLocList())
			{
				
				ArrayList<Integer> nbHabFound = new ArrayList<>();
				int tNum = habLoc.getTileNum();
				int row = habLoc.getRow();
				int col = habLoc.getCol();
				tempHabiMaps.put(tNum, nbHabFound);
				TreeMap<String, Object> cHabitat = searchHabitatByTileNum(tNum);
				if (cHabitat != null)
				{
					int rotation = (int)(cHabitat.get("rotation"));
					ArrayList<String> habsName = (ArrayList<String>)(cHabitat.get("habitats"));
					ArrayList<Integer> idx_to_search = new ArrayList<>();

					if (habsName.size() == 1) // single hab type
					{
						for (int i = 0; i <6; i++)
							idx_to_search.add(i);
					}
					else {
						String habname = constructNameString (habsName);
						//System.out.println("!!!!!!!!!! habtat name: " + habsName);
						String[] habLookup = habitatSideList.get(habname);
						
						for (int side_idx = 0; side_idx < 6; side_idx++)
						{
							int ref_idx = ((int)(side_idx+(360-rotation)/60))%6;  //rotated index used for looking up table
							//System.out.println("!!!!!!!!!!! side_idx: " + side_idx + "  rotation: " + rotation + "  ref_idx: " + ref_idx);

							String habitatSideName = habLookup[ref_idx];
							if (habNameToConnect == habitatSideName)
							{
								idx_to_search.add(side_idx);
								
							}
						}
					}

					////System.out.println("idx_to_search::: " + idx_to_search + "rotation: " + rotation + "row: " + row + " col: " + col);
					for (int c_idx: idx_to_search)
					{
						String nbHabDir = sidesMatchPairs.get(c_idx);
						Point nbPt = getIJIndexforNeighbor(row, col, nbHabDir);
						int nb_i = (int) nbPt.getX();
						int nb_j = (int) nbPt.getY();
					
						for (TreeMap<String, Object> cHabNb : getClaimedHabitats()) 
						{
							if (nb_i == (int)(cHabNb.get("row_idx")) && nb_j == (int)(cHabNb.get("col_idx")))
							{
								//System.out.println("c_idx: " + c_idx );
								//System.out.println("nbPt: (" + nb_i + ", " + nb_j + ")" );
								int nbRotation = (int)cHabNb.get("rotation");
								ArrayList<String> nbHabsName = (ArrayList<String>)(cHabNb.get("habitats"));
								if (nbHabsName.contains(habNameToConnect))
								 {
									if (nbHabsName.size() == 1) // single hab type
									{
										//single hab, only need to compare two names.
										String nbName = nbHabsName.get(0);
										if (nbName == habNameToConnect)
										{
											//found matching
											nbHabFound.add((int)cHabNb.get("tileNum"));
											////System.out.println("Con: " + habNameToConnect + " tileNum: " + (int)cHabNb.get("tileNum") + "loc: " + (int)cHabNb.get("row_idx") + ", " + (int)cHabNb.get("col_idx"));
										}
									}
									else{
										int nbs_idx = (c_idx+3)%6;
										int nb_ref_idx = ((int)(nbs_idx+(360-nbRotation)/60))%6; 
										//System.out.println("nbs_idx ==> "+ nbs_idx + " nb_ref_idx ==> " + nb_ref_idx + "nbRotation ==>" + nbRotation);
										String nbHabname = constructNameString (nbHabsName);
										//System.out.println("constructNameString return ===> " + nbHabname);
										String[] habNbLookup = habitatSideList.get(nbHabname);
										//System.out.println ("lookup nb_ref_idx ==>" + nb_ref_idx);
										String habitatSideName = habNbLookup[nb_ref_idx];
										if (habNameToConnect == habitatSideName)
										{
											nbHabFound.add((int)cHabNb.get("tileNum"));
											////System.out.println("Con: " + habNameToConnect + " tileNum: " + (int)cHabNb.get("tileNum") + "loc: " + (int)cHabNb.get("row_idx") + ", " + (int)cHabNb.get("col_idx"));
										}

									}
								}
							}
						}
					}				
				}
				////System.out.println("for " + habNameToConnect + " tileNum: " + cHabitat.get("tileNum") + ":" + "nbHabFound==> " + nbHabFound);
			}
			
			Set<Integer> allHabitatForOneType = tempHabiMaps.keySet();
			for (Map.Entry<Integer, ArrayList<Integer>> a : tempHabiMaps.entrySet())
			{
				System.out.println("habNum: " + a.getKey() + "  connected habNums: " + a.getValue());
			}

			//System.out.println("allHabitatForOneType nums ===> " + allHabitatForOneType);
			for( int habNum : allHabitatForOneType) {
				ArrayList<Integer> habsInGroup = new ArrayList<>();
				addToConnectedHabsGroup(habNum, habsInGroup, processedHabs, tempHabiMaps);
				if (habsInGroup.size()>0)
					connectedHabsForEachHabType.add(habsInGroup);
			}

			int largestHabConnectedCnt = 0;
			for (ArrayList<Integer> groups: connectedHabsForEachHabType)
			{
				System.out.println("habitat: " + habNameToConnect + " ==> groups: " + groups);
				if (groups.size() > largestHabConnectedCnt)
					largestHabConnectedCnt = groups.size();
			}
			
			finalHabCounts.put(habNameToConnect, largestHabConnectedCnt);
			System.out.println("habitat: " + habNameToConnect + " ==> largest conn hab count: " + largestHabConnectedCnt);
		}
	}


	public void addToConnectedHabsGroup(int hNum, ArrayList<Integer> habsGp, ArrayList<Integer> processedHabs, TreeMap<Integer, ArrayList<Integer>> tempHabiMaps)
	{	
		if (!processedHabs.contains(hNum))
		{
			habsGp.add(hNum);
			processedHabs.add(hNum);
			ArrayList<Integer> conns = tempHabiMaps.get(hNum);
			if (conns.size() == 0)
				return;
			else{
				for (int subHabNum: conns)
				{
					addToConnectedHabsGroup(subHabNum, habsGp, processedHabs, tempHabiMaps);
				}
			}
		}
		else
			return;
	}


	public Point getIJIndexforNeighbor(int row_i, int col_j, String nbDir) 
	{
		Point loc = new Point();
		switch (nbDir) 
		{
			case "upleft":
			{
				if (row_i %2 == 0) {
					loc.setLocation(new Point(row_i-1, col_j-1));
				}
				else {
					loc.setLocation(new Point(row_i-1, col_j));
				}
				break;
			}
			case "upright":
			{
				if (row_i %2 == 0) {
					loc.setLocation(new Point(row_i-1, col_j));
				}
				else {
					loc.setLocation(new Point(row_i-1, col_j+1));
				}
				break;
			}
			case "left":
			{
				loc.setLocation(new Point(row_i, col_j-1));
				break;
			}
			case "right":
			{
				loc.setLocation(new Point(row_i, col_j+1));
				break;
			}
			case "downleft":
			{
				if (row_i %2 == 0) {
					loc.setLocation(new Point(row_i+1, col_j-1));
				}
				else {
					loc.setLocation(new Point(row_i+1, col_j));
				}
				break;
			}
			case "downright":
			{
				if (row_i %2 == 0) {
					loc.setLocation(new Point(row_i+1, col_j));
				}
				else {
					loc.setLocation(new Point(row_i+1, col_j+1));
				}
				break;
			}
		}

		return loc;

	}

	public TreeMap<String, Object> searchHabitatByTileNum(int tilenumber)
	{
		for (TreeMap<String, Object> cHabitat : getClaimedHabitats()) 
		{
			if (tilenumber == (int)(cHabitat.get("tileNum")))
			{
				return cHabitat;
			}
		}
		return null;
	}


	public ArrayList<Integer> searchNeighborByToken(int row_i, int col_j, String token)
	{
		ArrayList<Integer> neighborTileNums = new ArrayList<>();
		ArrayList<Point> neighborLocs = new ArrayList<>();
		neighborLocs.add(new Point(row_i, col_j-1));
		neighborLocs.add(new Point(row_i, col_j+1));
		if (row_i %2 == 0) {
			neighborLocs.add(new Point(row_i-1, col_j-1));
			neighborLocs.add(new Point(row_i-1, col_j));
			neighborLocs.add(new Point(row_i+1, col_j-1));
			neighborLocs.add(new Point(row_i+1, col_j));
		}
		else {
			neighborLocs.add(new Point(row_i-1, col_j));
			neighborLocs.add(new Point(row_i-1, col_j+1));
			neighborLocs.add(new Point(row_i+1, col_j));
			neighborLocs.add(new Point(row_i+1, col_j+1));
		}

		for (TreeMap<String, Object> cHabitat : getClaimedHabitats()) 
		{
			if (cHabitat != null && (boolean)(cHabitat.get("tokenPlaced")) == true) {
				ArrayList<String> tokens = (ArrayList<String>)cHabitat.get("wildlife");
				String w = tokens.get(0);
				if (w == token) {
					Point tLoc = new Point((int)(cHabitat.get("row_idx")), (int)(cHabitat.get("col_idx")));
					if (neighborLocs.contains(tLoc))
						neighborTileNums.add((int)(cHabitat.get("tileNum")));
				}
			}
		}
		return neighborTileNums;
	}


	public boolean checkTokenExistInHab(TreeMap<String, Object> hab, String token)
	{
		if (hab != null && (boolean)(hab.get("tokenPlaced"))) {
			ArrayList<String> tokens = null;
			tokens = (ArrayList<String>)hab.get("wildlife");
			String w = tokens.get(0);
			if (w == token)
			{
				return true;
			}
		}
		return false;

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
			//System.out.println(tokens.get(0));
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


	public String constructNameString (ArrayList<String> names)
	{
		String imgName = "";
		for(int j = 0; j < names.size(); j++){
			if(j == 0)
				imgName += names.get(j);
			else
				imgName += ("+" + names.get(j));
		}
		return imgName;
	}


	public TreeMap<String, Integer> getFinalHabConnGroupCnt()
	{
		return finalHabCounts;
	}
}