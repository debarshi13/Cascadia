import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;



public class GamePanel extends JPanel implements MouseListener{

	enum PlayerState {
		TILES_ON_TABLE_UPDATED,
		TILE_ON_TABLE_IS_SELECTED,
		CANDIDATE_TILE_CLICKED,
		ROTATE_CW,
		ROTATE_COUNTER_CW,
		COMFIRM_HABITAT_PLACE,
		CANCEL_HABITAT_PLACE,
		CLAIMED_HABITAT_CLICKED,
		TOKEN_PLACED,
	}
	
	PlayerState playerState = PlayerState.TILES_ON_TABLE_UPDATED;

	BufferedImage background;
	private ArrayList<Player> players;

	private Polygon hexagonTest;
	private Tiles tiles;
	private Wildlife animals;
	private ArrayList<Tile> tilesOnTable;
	private ArrayList<String> animalsOnTable;
	private int gameStatus = 0;
	private Font font = new Font("Arial", Font.BOLD, 24);

	private Tile selectedTileOnTable = null;

	int counterCWclickedCnt = 0;

	private Font smallfont = new Font("Arial", Font.BOLD, 20);

	int activePlayerIdx = 0;
	TreeMap<String, Hexagon> candidateHabitatHexagon = null;
	TreeMap<String, Object> candidateHabitat =null;
	FontMetrics metrics;
	BufferedImage forestTileImage = null, lakeTileImage =  null ,swampTileImage = null;
	BufferedImage lakeMountainTileImage = null, mountainDesertTileImage =  null ,mountainForestTileImage = null;
	BufferedImage desertTileImage = null, desertLakeTileImage =  null ,desertSwampTileImage = null;
	BufferedImage forestDesertTileImage = null, forestLakeTileImage =  null ,forestSwampTileImage = null;
	BufferedImage mountainTileImage = null, mountainSwampTileImage =  null ,starterTile1 = null;
	BufferedImage swampLakeTileImage = null;
	BufferedImage bearScoreImage = null, elkScoreImage = null, foxScoreImage = null, hawkScoreImage = null, salmonScoreImage = null;
	BufferedImage selectedTileImage = null;

	TreeMap<String, BufferedImage> animalImageMap = new TreeMap<>();
	Point origin = new Point (133, 136);
	double ang30 = Math.toRadians(30);
	int radius = 57;
	double xOff = Math.cos(ang30) * (radius +0.3);
	double yOff = Math.sin(ang30) * (radius +0.3);

	Rectangle rcCancel, rcConfirm, rcClockwise, rcCounterClockwise;
	public GamePanel() {
		

		try {
			desertTileImage = ImageIO.read(new File("src/images/desert.png"));
			desertLakeTileImage = ImageIO.read(new File("src/images/desert+lake.png"));
			desertSwampTileImage = ImageIO.read(new File("src/images/desert+swamp.png"));
			forestTileImage = ImageIO.read(new File("src/images/forest.png"));
			forestDesertTileImage = ImageIO.read(new File("src/images/forest+desert.png"));
			forestLakeTileImage = ImageIO.read(new File("src/images/forest+lake.png"));
			forestSwampTileImage = ImageIO.read(new File("src/images/forest+swamp.png"));
			lakeTileImage = ImageIO.read(new File("src/images/lake.png"));
			lakeMountainTileImage = ImageIO.read(new File("src/images/lake+mountain.png"));
			mountainTileImage = ImageIO.read(new File("src/images/mountain.png"));
			mountainDesertTileImage = ImageIO.read(new File("src/images/mountain+desert.png"));
			mountainForestTileImage = ImageIO.read(new File("src/images/mountain+forest.png"));
			mountainSwampTileImage = ImageIO.read(new File("src/images/mountain+swamp.png"));
			swampTileImage = ImageIO.read(new File("src/images/swamp.png"));
			starterTile1 = ImageIO.read(new File("src/images/starterTile1.png"));
			swampLakeTileImage = ImageIO.read(new File("src/images/swamp+lake.png"));
			background = ImageIO.read(new File("src/images/background.png"));
			selectedTileImage = ImageIO.read(new File("src/images/selectedTile.png"));

			animalImageMap.put ("bear", ImageIO.read(new File("src/images/bear.png")));
			animalImageMap.put ("bearActive", ImageIO.read(new File("src/images/bearActive.png")));
			animalImageMap.put ("bearInactive", ImageIO.read(new File("src/images/bearInactive.png")));

			animalImageMap.put("elk", ImageIO.read(new File("src/images/elk.png")));
			animalImageMap.put("elkActive", ImageIO.read(new File("src/images/elkActive.png")));
			animalImageMap.put("elkInactive", ImageIO.read(new File("src/images/elkInactive.png")));

			animalImageMap.put ("salmon",ImageIO.read(new File("src/images/salmon.png")));
			animalImageMap.put ("salmonActive",ImageIO.read(new File("src/images/salmonActive.png")));
			animalImageMap.put ("salmonInactive",ImageIO.read(new File("src/images/salmonInactive.png")));

			animalImageMap.put ("fox", ImageIO.read(new File("src/images/fox.png")));
			animalImageMap.put ("foxActive", ImageIO.read(new File("src/images/foxActive.png")));
			animalImageMap.put ("foxInactive", ImageIO.read(new File("src/images/foxInactive.png")));

			animalImageMap.put ("hawk", ImageIO.read(new File("src/images/hawk.png")));
			animalImageMap.put ("hawkActive", ImageIO.read(new File("src/images/hawkActive.png")));
			animalImageMap.put ("hawkInactive", ImageIO.read(new File("src/images/hawkInactive.png")));

			bearScoreImage = ImageIO.read(new File("src/images/bear-large.jpg"));
			elkScoreImage = ImageIO.read(new File("src/images/elk-large.jpg"));
			hawkScoreImage = ImageIO.read(new File("src/images/hawk-large.jpg"));
			foxScoreImage = ImageIO.read(new File("src/images/fox-large.jpg"));
			salmonScoreImage = ImageIO.read(new File("src/images/salmon-large.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		hexagonTest = new Polygon();
		for (int i = 0; i < 6; i++){
			hexagonTest.addPoint((int) (150 + 150 * Math.cos(Math.PI/2 + i * 2 * Math.PI / 6)),
			(int) (150 + 150 * Math.sin(Math.PI/2 + i * 2 * Math.PI / 6)));
		}

		players = new ArrayList<>();
		for (int i = 0 ; i < 3 ; i++) {
			
			players.add(new Player(i, 0));
			
		}
		tiles = new Tiles();
		//Collections.shuffle(tiles.getStartingTiles());
		Collections.shuffle(tiles.getTiles());
		animals =  new Wildlife();
		Collections.shuffle(animals.getWildlife());

		tilesOnTable = new ArrayList<Tile>();
		animalsOnTable = new ArrayList<String>();
		rcCancel = new Rectangle();
		rcConfirm = new Rectangle();
		rcClockwise = new Rectangle();
		rcCounterClockwise =  new Rectangle();
		addMouseListener(this);
	}

	public void paint(Graphics g) {
	
	
		
		g.drawImage(background, 0, 0, null);
		if(gameStatus == 0){
			g.setColor(Color.green);
			g.fillRect(getWidth() / 2 - 100, 200, 200, 60);
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString("Start Game", getWidth() / 2 - 70, 235);
		}
		else{
			rcCancel.setBounds(getWidth() * 2 / 5, getHeight() - 150, 140, 50);
			rcConfirm.setBounds(rcCancel.x + rcCancel.width + 10, getHeight() - 150, 140, 50);
			rcCounterClockwise.setBounds(rcConfirm.x + rcConfirm.width + 10, getHeight() - 150, 300, 50);
			rcClockwise.setBounds(rcCounterClockwise.x + rcCounterClockwise.width + 10, getHeight() - 150, 220, 50);

			g.drawImage(bearScoreImage, 10, 200, 160, 110, null);
			g.drawImage(elkScoreImage, 10, 320, 160, 110, null);
			g.drawImage(foxScoreImage, 10, 440, 160, 110, null);
			g.drawImage(hawkScoreImage, 10, 560, 160, 110, null);
			g.drawImage(salmonScoreImage, 10, 680, 160, 110, null);

			//paintBackgroundGrid(g, radius);

			// starting tiles
			drawStartingTiles(g);

			for(int i = 0; i <  tilesOnTable.size(); i++){
				Tile t = tilesOnTable.get(i);
				//System.out.println(t.getTileNum() + " with habitats size of " + t.getHabitats().size());
				for(int j = 0; j < t.getHabitats().size(); j++)
					System.out.println(t.getHabitats().get(j));
			}

			
			drawCandidateHexTiles(g);
			if (candidateHabitat != null) {
				drawHabitatTile(g, candidateHabitat);
				drawHabitatWildlife(g, candidateHabitat);
			}
		}


		for(int i = 0; i < tilesOnTable.size(); i++){
			ArrayList<String> habitat = tilesOnTable.get(i).getHabitats();
			BufferedImage img = getHabiImageFromName (habitat);
			
			int width = (int)(img.getWidth()*0.8);
			int height = (int)(img.getHeight()*0.8);
			int x0 = 250 + i * 120;
			int y0 = getHeight() - 250;

			g.drawImage(img, x0, y0, width, height, null);
			Hexagon hex = new Hexagon(x0+width/2, y0+height/2, radius);
			tilesOnTable.get(i).setHexagon(hex);
			drawTileWildlife(g, tilesOnTable.get(i));
			drawHighlightedTileOnTable(g);
		}
		for(int i = 0; i < animalsOnTable.size(); i++){
			//System.out.println("Should get an image of " + animalsOnTable.get(i));
			BufferedImage img = animalImageMap.get(animalsOnTable.get(i));
			g.drawImage(img, 255 + i * 120, getHeight() - 150, 80, 80, null);
		}
		g.setColor(Color.red);
		g.fillRect(rcCancel.x, rcCancel.y, rcCancel.width, rcCancel.height);
		g.setColor(Color.white);
		g.setFont(smallfont);
		g.drawString("Cancel", rcCancel.x + 30, rcCancel.y + 30);

		g.setColor(Color.green);
		g.fillRect(rcConfirm.x, rcConfirm.y, rcConfirm.width, rcConfirm.height);
		g.setColor(Color.white);
		g.setFont(smallfont);
		g.drawString("Confirm", rcConfirm.x + 30, rcConfirm.y + 30);

		g.setColor(Color.blue);
		g.fillRect(rcCounterClockwise.x, rcCounterClockwise.y, rcCounterClockwise.width, rcCounterClockwise.height);
		g.setColor(Color.white);
		g.setFont(smallfont);
		g.drawString("Rotate Counterclockwise", rcCounterClockwise.x + 30, rcCounterClockwise.y + 30);

		g.setColor(Color.cyan);
		g.fillRect(rcClockwise.x, rcClockwise.y, rcClockwise.width, rcClockwise.height);
		g.setColor(Color.white);
		g.setFont(smallfont);
		g.drawString("Rotate Clockwise", rcClockwise.x + 30, rcClockwise.y + 30);


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


	public BufferedImage getHabiImageFromName(ArrayList<String> habitat) 
	{
		String imgName = constructNameString(habitat);
		BufferedImage img = getImage(imgName);
		return img;
	}

	public void drawStartingTiles(Graphics g)
	{
		Player activePlayer = players.get(activePlayerIdx);
		ArrayList<TreeMap<String, Object>> startingTiles = activePlayer.getClaimedHabitats();
		for (TreeMap<String, Object> cTile : startingTiles) {
			drawHabitatTile(g, cTile);
			addHexagonToTile(cTile);
			drawHabitatWildlife(g, cTile);
		}
	}

	public void addHexagonToTile(TreeMap<String, Object> cTile)
	{
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff);
		int y = (int) (origin.y + 3*yOff*row_i);
		Hexagon hex = new Hexagon(x, y, radius);
		cTile.put("hexagon", hex);
		
	}

	public void drawTileWildlife(Graphics g, Tile cTile){
		int x = (int) cTile.getHexagon().getBounds().getCenterX();
		int y = (int) cTile.getHexagon().getBounds().getCenterY();
		ArrayList<String> wildlifeList = (ArrayList<String>) cTile.getWildlife();
		int num = wildlifeList.size();
		//System.out.println("Number of wildlife: " + num);
		if(num == 1){
			x-=(int)xOff/4;
			y-=(int)(yOff/2);
			BufferedImage bImage = animalImageMap.get(wildlifeList.get(0));
			g.drawImage(bImage, x, y, (int)(xOff * 0.5), (int)(yOff),null);
			
		}
		else if(num == 2){
			y-=(int)(yOff/2);
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	
					x -= (int)(xOff * 0.6);	
				else
					x += (int)(xOff * 0.6);
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x, y, (int)(xOff * 0.5), (int)(yOff),null);
			
			}
		}
		else if(num == 3){
			int x1, y1;
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	{
					x1 = x-(int)xOff/4;
					y1 = y-(int)(yOff*0.75);
				}
				else if(i == 1){
					x1 = x - (int)(xOff * 0.6);	
					y1 = y-(int)(yOff/3);
				}
				else{
					x1 = x + (int)(xOff * 0.1);
					y1 = y-(int)(yOff/3);
				}
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x1, y1, (int)(xOff * 0.5), (int)(yOff),null);
			
			}
		}
	}
	public void drawHabitatTile(Graphics g, TreeMap<String, Object> cTile) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		ArrayList<String> habitatsList = (ArrayList<String>) cTile.get("habitats");			
		BufferedImage bImage = getHabiImageFromName(habitatsList);
		int rot = (int) cTile.get("rotation");
		int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff -xOff);
		int y = (int) (origin.y + 3*yOff*row_i) -radius;
		double locationX = lakeMountainTileImage.getWidth() / 2;
		double locationY = lakeMountainTileImage.getHeight() / 2;
		Graphics2D g2d = (Graphics2D) g;
		if (rot > 0) {
			AffineTransform identity = AffineTransform.getRotateInstance(Math.toRadians(rot), locationX, locationY);		
			AffineTransformOp op = new AffineTransformOp(identity, AffineTransformOp.TYPE_BILINEAR);
			g2d.drawImage(op.filter(bImage, null), x, y, null);
		}
		else {
			g.drawImage(bImage, x, y, null);
		}

	}
	
	public void drawHabitatWildlife(Graphics g, TreeMap<String, Object> cTile) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff -xOff);
		int y = (int) (origin.y + 3*yOff*row_i) -radius;
		ArrayList<String> wildlifeList = (ArrayList<String>) cTile.get("wildlife");
		int num = wildlifeList.size();
		if(num == 1){
			x+=(int)xOff/2;
			y+=(int)(yOff + 6);
			BufferedImage bImage = animalImageMap.get(wildlifeList.get(0));
			g.drawImage(bImage, x, y, (int)xOff, (int)yOff*2,null);
			System.out.println(x + " -  " + y + " xoff - " + xOff + " yOff - " + yOff);
		}
		else if(num == 2){
			y+=(int)(yOff + 6);
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	
					x += (int)(xOff * 0.2);	
				else
					x += (int)(xOff * 0.8);
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x, y, (int)(xOff * 1.5 / 2), (int)yOff*3/2,null);
			
			}
		}
		else if(num == 3){
			int x1, y1;
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	{
					x1 = x + (int)(xOff * 0.75);
					y1 = y+(int)(yOff*0.85);
				}
				else if(i == 1){
					x1 = x + (int)(xOff * 0.5);	
					y1 = y+(int)(yOff * 1.7);
				}
				else{
					x1 = x + (int)(xOff * 1.1);
					y1 = y+(int)(yOff * 1.6);
				}
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x1, y1, (int)(xOff * 1.2 / 2), (int)(yOff*1.2),null);
			
			}
		}

	}

	public void drawCandidateHexTiles(Graphics g)
	{
		metrics = g.getFontMetrics();
		if (candidateHabitatHexagon != null) {
			for (Map.Entry<String, Hexagon> entry : candidateHabitatHexagon.entrySet()) 
			{
				//Hexagon hex = entry.getValue();
				String s = entry.getKey();
				String[] loc_idx_strings = s.split(":");
				int row_i = Integer.parseInt(loc_idx_strings[0]);
				int col_j = Integer.parseInt(loc_idx_strings[1]);
				int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff);
				int y = (int) (origin.y + 3*yOff*row_i);
				drawHex(g, row_i, col_j, x, y, radius);
			}
		}
	}

	public void drawHighlightedTileOnTable(Graphics g) {
		if (playerState == PlayerState.TILE_ON_TABLE_IS_SELECTED  && selectedTileOnTable !=null) {

			Hexagon hex = (Hexagon) selectedTileOnTable.getHexagon();
			Point center = hex.getCenter();
			int x = (int)(center.getX() - xOff*0.8);
			int y = (int)(center.getY() - radius*0.8);
			g.drawImage(selectedTileImage, x, y, (int)(selectedTileImage.getWidth()*0.8), (int)( selectedTileImage.getHeight()*0.8), null);
			
		}
	}
	

	public void paintBackgroundGrid(Graphics g, int radius) {
		Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        Point origin = new Point (133, 136);
        drawHexGrid(g2d, origin, 21, radius);

	}


    private void drawHexGrid(Graphics g, Point origin, int size, int radius) {
    	double ang30 = Math.toRadians(30);
        double xOff = Math.cos(ang30) * (radius +0.5);
        double yOff = Math.sin(ang30) * (radius +0.5);
        
        for (int i = 0; i < 21; i++) {
        	for (int j = 0; j<21; j++) {
        		int xLbl = i;
        		int yLbl = j;        		
        		int x = (int) (origin.x + (i%2)*xOff + 2*j*xOff);
        		int y = (int) (origin.y + 3*yOff*i);
        		 drawHex(g, xLbl, yLbl, x, y, radius);
        	}
        }
    }


	private void drawHex(Graphics g, int posX, int posY, int x, int y, int r) {
        Graphics2D g2d = (Graphics2D) g;
        Hexagon hex = new Hexagon(x, y, r);
		String text = String.format("%s : %s",Integer.toString(posX), Integer.toString(posY));
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();

        hex.draw(g2d, x, y, 1, 0xFFDD88, false);
        g.setColor(Color.blue);
		g.drawString(text, x - w/2, y + h/2);
    }

	private void StartGame(){
		gameStatus = 1;
		// System.out.println("tilesOnTable has what: " + tilesOnTable.size() + " tiles have how many: " + tiles.getTiles().size());
		for(int i = 0; i < 4; i++){
			tilesOnTable.add(tiles.getTiles().remove(i));
			animalsOnTable.add(animals.getWildlife().remove(i));
		}
		
		repaint();
	}

	private BufferedImage getImage(String name){
		BufferedImage img = null;
		switch(name) {
			case "desert":
			img = desertTileImage;
			  	break;
			case "desert+lake":
			img = desertLakeTileImage;
			  	break;
			case "desert+swamp":
			img = desertSwampTileImage;
				break;
			case "forest":
			img = forestTileImage;
		  		break;
		  	case "forest+desert":
		  	img = forestDesertTileImage;
				break;
			case "forest+lake":
			  img = forestLakeTileImage;
				break;
			case "forest+swamp":
			img = forestSwampTileImage;
		  		break;
		  	case "lake":
		  	img = lakeTileImage;
				break;
			case "lake+mountain":
			  img = lakeMountainTileImage;
				break;
			case "mountain":
			img = mountainTileImage;
		  		break;
		  	case "mountain+desert":
		  	img = mountainDesertTileImage;
				break;

			case "mountain+forest":
			img = mountainForestTileImage;
				break;
			case "mountain+swamp":
			img = mountainSwampTileImage;
				break;
			case "swamp":
			img = swampTileImage;
				break;
			case "swamp+lake":
			img = swampLakeTileImage;
				break;
			case "selectedTile":
			img = selectedTileImage;
				break;
														
			default:
			  // code block
		  }
		return img;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(gameStatus == 0){
			int x = e.getX();
			int y = e.getY();

			if(x >= getWidth() / 2 - 100 && x <= getWidth() / 2 + 100 && y >= 200 && y <= 260){
				System.out.println("Start Game");
				StartGame();
				playerState = PlayerState.TILES_ON_TABLE_UPDATED;
			}
		}
		else{
			System.out.println( "" + e.getX() + "  " + e.getY());

			
			TreeMap<String, Object> habiTile = players.get(activePlayerIdx).searchHabitat(e.getPoint());
			if (habiTile != null)
				playerState = PlayerState.CLAIMED_HABITAT_CLICKED;

			if (playerState == PlayerState.TILES_ON_TABLE_UPDATED || playerState == PlayerState.TILE_ON_TABLE_IS_SELECTED) 
			{
				for (Tile tile : tilesOnTable) {
					Hexagon hex = (Hexagon) tile.getHexagon();
					if(hex.contains(e.getPoint()))
					{
						selectedTileOnTable = new Tile();
						selectedTileOnTable = tile;
						playerState = PlayerState.TILE_ON_TABLE_IS_SELECTED;

					}
				}
			}

			if (playerState == PlayerState.TILE_ON_TABLE_IS_SELECTED)
			{
				//loop through player's claimedhabitats, find candidate tiles, hgihtlight potential hexagons to put new habitats

				Player activePlayer = players.get(activePlayerIdx);
				ArrayList<TreeMap<String, Object>> claimedHab = activePlayer.getClaimedHabitats();
				for (TreeMap<String, Object> cTile : claimedHab) {
					int row_i = (int) cTile.get("row_idx");
					int col_j = (int) cTile.get("col_idx");

					checkAndAddCandidateHexTile(row_i, col_j-1, claimedHab);
					checkAndAddCandidateHexTile(row_i, col_j+1, claimedHab);
					if (row_i %2 == 0) {
						checkAndAddCandidateHexTile(row_i-1, col_j-1, claimedHab);
						checkAndAddCandidateHexTile(row_i-1, col_j, claimedHab);
						checkAndAddCandidateHexTile(row_i+1, col_j-1, claimedHab);
						checkAndAddCandidateHexTile(row_i+1, col_j, claimedHab);
					}
					else {
						checkAndAddCandidateHexTile(row_i-1, col_j, claimedHab);
						checkAndAddCandidateHexTile(row_i-1, col_j+1, claimedHab);
						checkAndAddCandidateHexTile(row_i+1, col_j, claimedHab);
						checkAndAddCandidateHexTile(row_i+1, col_j+1, claimedHab);
					}
				}
				
				boolean newHabitatPlaced = findAndPlaceSelectedHabitat(e.getPoint());
				if (newHabitatPlaced == true) {
					// remove the selected tile from the tilesOnTable
					for(int i = 0; i <  tilesOnTable.size(); i++){
						Tile t = tilesOnTable.get(i);
						if (t.getTileNum() == selectedTileOnTable.getTileNum())
						{
							tilesOnTable.remove(t);
						}
					}
				}
				
			}
			repaint();

			// players.get(activePlayerIdx).searchHabitat(e.getPoint());
			if(rcCancel.contains(e.getPoint()))
				System.out.println("Cancel clicked");
			if(rcConfirm.contains(e.getPoint()))
				System.out.println("Confirm clicked");		
			if(rcCounterClockwise.contains(e.getPoint())) {
				System.out.println("CounterClockwise clicked");
				if (playerState == PlayerState.CANDIDATE_TILE_CLICKED) {
					counterCWclickedCnt ++;
					int rotatAng = counterCWclickedCnt%6;
					candidateHabitat.put("rotation", rotatAng*60);
					drawHabitatTile(getGraphics(), candidateHabitat);
					drawHabitatWildlife(getGraphics(), candidateHabitat);
				}

			}
			if(rcClockwise.contains(e.getPoint()))
				System.out.println("Clockwise clicked");		

		}
	}

	
	public void checkAndAddCandidateHexTile(int row_i, int col_j, ArrayList<TreeMap<String, Object>> claminedHab)
	{
		if (!indexInClaimedHabitats(row_i, col_j, claminedHab))
		{
			String key = Integer.toString(row_i) + ":" + Integer.toString(col_j);
			if (candidateHabitatHexagon == null)
			{
				candidateHabitatHexagon = new TreeMap<>();
				int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff);
				int y = (int) (origin.y + 3*yOff*row_i);
				Hexagon hex = new Hexagon(x, y, radius);
				//System.out.println("--------> " + key);
				candidateHabitatHexagon.put(key, hex);
			}
			else if (!candidateHabitatHexagon.containsKey(row_i + ":" + col_j)) {
				int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff);
				int y = (int) (origin.y + 3*yOff*row_i);
				Hexagon hex = new Hexagon(x, y, radius);
				//System.out.println("--------> " + key);
				candidateHabitatHexagon.put(key, hex);
			}
		}

	}

	public boolean indexInClaimedHabitats(int i_idx, int j_idx, ArrayList<TreeMap<String, Object>> claminedHab)
	{
		for (TreeMap<String, Object> cTile : claminedHab) {
			
			int row_i = (int) cTile.get("row_idx");
			int col_j = (int) cTile.get("col_idx");
			if (i_idx == row_i && j_idx == col_j)
				return true;
		}
		return false;
	}

	public boolean findAndPlaceSelectedHabitat(Point pt)
	{
		boolean newHabitatSelected = false;
		for (Map.Entry<String, Hexagon> entry : candidateHabitatHexagon.entrySet()) 
		{
			Hexagon hex = entry.getValue();
			if(hex.contains(pt))
			{
				System.out.println("@@@@@@" + entry.getKey());
				String s = entry.getKey();
				String[] loc_idx_strings = s.split(":");

				candidateHabitat = new TreeMap<>();
				candidateHabitat.put("row_idx", Integer.parseInt(loc_idx_strings[0]));
				candidateHabitat.put("col_idx", Integer.parseInt(loc_idx_strings[1]));
				candidateHabitat.put("habitats", selectedTileOnTable.getHabitats());
				candidateHabitat.put("wildlife", selectedTileOnTable.getWildlife());
				candidateHabitat.put("tokenPlaced", false);
				candidateHabitat.put("rotation", selectedTileOnTable.getRotation());
				candidateHabitat.put("hexagon", hex);
				//players.get(activePlayerIdx).getClaimedHabitats().add(habitatInfo);
				playerState = PlayerState.CANDIDATE_TILE_CLICKED;

				return true;
			}
		}

		return newHabitatSelected;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
