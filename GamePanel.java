import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;
import java.awt.geom.Ellipse2D;



public class GamePanel extends JPanel implements MouseListener, MouseMotionListener{
	boolean draw_hex_120 = false;
	boolean draw_hex_60 = false;
	enum PlayerState {
		TILES_ON_TABLE_UPDATED,
		TILE_ON_TABLE_IS_SELECTED,
		CANDIDATE_TILE_CLICKED,
		ROTATE_CW,
		ROTATE_COUNTER_CW,
		HABITAT_PLACE_COMFIRMED,
		CANCEL_HABITAT_PLACE,
		CLAIMED_HABITAT_CLICKED,
		TOKEN_PLACED,
		TURN_IS_DONE
	}

	PlayerState playerState = PlayerState.TILES_ON_TABLE_UPDATED;
	BufferedImage background;
	private ArrayList<Player> players;

	private Tiles tiles;
	private Wildlife animals;
	private ArrayList<Tile> tilesOnTable;
	private ArrayList<String> animalsOnTable;
	String [] tilesAnimalsOnTable = {"", "", "", ""};
	
	private int gameStatus = 0;
	private Font font = new Font("Arial", Font.BOLD, 18);

	private Tile selectedTileOnTable = null;
	private int selectedTileOnTableIndex = -1;
	private int selectedTokenOnTableIndex = -1;
	private boolean useNatureToken = false;
	int counterCWclickedCnt = 0;
	int clockwiseClickedCnt = 0;

	private Font smallfont = new Font("Arial", Font.BOLD, 14);

	int activePlayerIdx = 0;
	String activeAnimalToken = "";
	int previousMouseMovedinHabitatNum = -1;
	ArrayList<Ellipse2D> animalOnTableImgElps = null;
	
	TreeMap<String, Object> previousTokenMatchHabit = null;
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
	BufferedImage selectedTileImage = null, tilePlacementCancelImage = null, tilePlacementConfirmImage = null;
	BufferedImage tilePlacementRotateCWImage = null, tilePlacementRotateCounterCWImage = null;
	BufferedImage natureTokenImage = null;
	BufferedImage fullScoreBoardImage = null;

	TreeMap<Integer, Point> scoreBoardLoc;
	TreeMap<String, BufferedImage> animalImageMap = new TreeMap<>();
	Point origin = new Point (133, 136);
	double ang30 = Math.toRadians(30);
	int radius = 57;
	double xOff = Math.cos(ang30) * (radius +0.3);
	double yOff = Math.sin(ang30) * (radius +0.3);
	int endgameRadius = 38;
	double endgamexOff =  Math.cos(ang30) * (endgameRadius +0.3);
	double endgameyOff =  Math.sin(ang30) * (endgameRadius +0.3);
	int center_i = 10;
	int center_j = 10;
	int replaceDuplicateCnt = 0;

	Rectangle rcCancel, rcConfirm, rcUseNatureToken, rcReplaceDuplicate;
	Rectangle rcPlayerIndicator, rcNextPlay, rcTurnsLeft;

	Hexagon  hexClockwiise, hexCounterClockwise;
	int replacetimes = 0;
	public GamePanel() {
		

		try {

			

			desertTileImage = ImageIO.read(GameFrame.class.getResource("/images/desert.png"));
			desertLakeTileImage = ImageIO.read(GameFrame.class.getResource("/images/desert+lake.png"));
			
			
			desertLakeTileImage = ImageIO.read(GameFrame.class.getResource("/images/desert+lake.png"));
			desertSwampTileImage = ImageIO.read(GameFrame.class.getResource("/images/desert+swamp.png"));
			forestTileImage = ImageIO.read(GameFrame.class.getResource("/images/forest.png"));
			forestDesertTileImage = ImageIO.read(GameFrame.class.getResource("/images/forest+desert.png"));
			forestLakeTileImage = ImageIO.read(GameFrame.class.getResource("/images/forest+lake.png"));
			forestSwampTileImage = ImageIO.read(GameFrame.class.getResource("/images/forest+swamp.png"));
			lakeTileImage = ImageIO.read(GameFrame.class.getResource("/images/lake.png"));
			lakeMountainTileImage = ImageIO.read(GameFrame.class.getResource("/images/lake+mountain.png"));
			mountainTileImage = ImageIO.read(GameFrame.class.getResource("/images/mountain.png"));
			mountainDesertTileImage = ImageIO.read(GameFrame.class.getResource("/images/mountain+desert.png"));
			mountainForestTileImage = ImageIO.read(GameFrame.class.getResource("/images/mountain+forest.png"));
			mountainSwampTileImage = ImageIO.read(GameFrame.class.getResource("/images/mountain+swamp.png"));
			swampTileImage = ImageIO.read(GameFrame.class.getResource("/images/swamp.png"));
			starterTile1 = ImageIO.read(GameFrame.class.getResource("/images/starterTile1.png"));
			swampLakeTileImage = ImageIO.read(GameFrame.class.getResource("/images/swamp+lake.png"));
			
			background = ImageIO.read(GameFrame.class.getResource("/images/savedbackground_a.jpg"));
			fullScoreBoardImage = ImageIO.read(GameFrame.class.getResource("/images/fullsizeScore.jpg"));
			// BufferedImage fullScoreBoardImagem = imageResize(fullScoreBoardImage, fullScoreBoardImage.getWidth()*2/3, fullScoreBoardImage.getHeight()*2/3);

			// try {
				
			// 	File outputfile = GameFrame.class.getResource("fullsizeScore.jpg");
			// 	ImageIO.write(fullScoreBoardImagem, "png", outputfile);
			// } catch (IOException e) {
			// 	// handle exception
			// }

			// BufferedImage backgroundorig = ImageIO.read(GameFrame.class.getResource("src/images/texture-wooden-boards.jpg"));
			// background = imageResize(backgroundorig, backgroundorig.getWidth()*3/5, backgroundorig.getHeight()/2);

			// try {
				
			// 	File outputfile = GameFrame.class.getResource("savedbackground_a.jpg");
			// 	ImageIO.write(background, "png", outputfile);
			// } catch (IOException e) {
			// 	// handle exception
			// }
			
			tilePlacementCancelImage = ImageIO.read(GameFrame.class.getResource("/images/tilePlacementCancel.png"));
			tilePlacementConfirmImage = ImageIO.read(GameFrame.class.getResource("/images/tilePlacementConfirm.png")); 
			tilePlacementRotateCWImage = ImageIO.read(GameFrame.class.getResource("/images/tilePlacementRotateClockwise.png")); 
			tilePlacementRotateCounterCWImage = ImageIO.read(GameFrame.class.getResource("/images/tilePlacementRotateCounterClockwise.png")); 
			natureTokenImage = ImageIO.read(GameFrame.class.getResource("/images/nature-token.png")); 

			selectedTileImage = ImageIO.read(GameFrame.class.getResource("/images/selectedTile.png"));

			animalImageMap.put ("bear", ImageIO.read(GameFrame.class.getResource("/images/bear.png")));
			animalImageMap.put ("bearActive", ImageIO.read(GameFrame.class.getResource("/images/bearActive.png")));
			animalImageMap.put ("bearInactive", ImageIO.read(GameFrame.class.getResource("/images/bearInactive.png")));

			animalImageMap.put("elk", ImageIO.read(GameFrame.class.getResource("/images/elk.png")));
			animalImageMap.put("elkActive", ImageIO.read(GameFrame.class.getResource("/images/elkActive.png")));
			animalImageMap.put("elkInactive", ImageIO.read(GameFrame.class.getResource("/images/elkInactive.png")));

			animalImageMap.put ("salmon",ImageIO.read(GameFrame.class.getResource("/images/salmon.png")));
			animalImageMap.put ("salmonActive",ImageIO.read(GameFrame.class.getResource("/images/salmonActive.png")));
			animalImageMap.put ("salmonInactive",ImageIO.read(GameFrame.class.getResource("/images/salmonInactive.png")));

			animalImageMap.put ("fox", ImageIO.read(GameFrame.class.getResource("/images/fox.png")));
			animalImageMap.put ("foxActive", ImageIO.read(GameFrame.class.getResource("/images/foxActive.png")));
			animalImageMap.put ("foxInactive", ImageIO.read(GameFrame.class.getResource("/images/foxInactive.png")));

			animalImageMap.put ("hawk", ImageIO.read(GameFrame.class.getResource("/images/hawk.png")));
			animalImageMap.put ("hawkActive", ImageIO.read(GameFrame.class.getResource("/images/hawkActive.png")));
			animalImageMap.put ("hawkInactive", ImageIO.read(GameFrame.class.getResource("/images/hawkInactive.png")));

			bearScoreImage = ImageIO.read(GameFrame.class.getResource("/images/bear-large.jpg"));
			elkScoreImage = ImageIO.read(GameFrame.class.getResource("/images/elk-large.jpg"));
			hawkScoreImage = ImageIO.read(GameFrame.class.getResource("/images/hawk-large.jpg"));
			foxScoreImage = ImageIO.read(GameFrame.class.getResource("/images/fox-large.jpg"));
			salmonScoreImage = ImageIO.read(GameFrame.class.getResource("/images/salmon-large.jpg"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		players = new ArrayList<>();
		for (int i = 0 ; i < 3 ; i++) {
			
			players.add(new Player(i, i, 20));
			
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
		rcPlayerIndicator = new Rectangle();
		rcNextPlay = new Rectangle();
		rcTurnsLeft =  new Rectangle();
		rcUseNatureToken = new Rectangle();
		animalOnTableImgElps = new ArrayList<>();
		rcReplaceDuplicate = new Rectangle();
		addMouseListener(this);
		addMouseMotionListener(this);
		scoreBoardLoc = new TreeMap<>();
	}

	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if(gameStatus == 0){
			g.setColor(Color.green);
			g.fillRect(getWidth() / 2 - 100, 200, 200, 60);
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString("Start Game", getWidth() / 2 - 50, 235);

			draw_hex_120 = false;
			draw_hex_60 = false;
			//paintBackgroundGrid(g, radius);
		}
		else if (gameStatus == 1) {
			g.clearRect(0, 0,  getWidth(), getHeight());
			
			g.drawImage(background, 0, 0, null);
			rcCancel.setBounds(getWidth() * 2 / 5 - 40, getHeight() - 60, 70, 40);
			rcConfirm.setBounds(rcCancel.x + rcCancel.width + 10, rcCancel.y, 70, 40);
			int buttonWidth = (int)(tilePlacementCancelImage.getWidth()*0.45);
			int buttonHeight = (int)(tilePlacementCancelImage.getHeight()*0.45);
			hexCounterClockwise = new Hexagon((int )((rcConfirm.x + rcConfirm.width + 10 + buttonWidth/2)), (int)(getHeight() - buttonWidth/2-10), (int)(radius*0.8));
			hexClockwiise = new Hexagon((int )((rcConfirm.x + rcConfirm.width + 15 + buttonWidth*1.5)), (int)(getHeight() - buttonWidth/2-10), (int)(radius*0.8));
			g.drawImage(tilePlacementRotateCounterCWImage, (int)(hexCounterClockwise.getCenter().x-buttonWidth/2), (int)(hexCounterClockwise.getCenter().y- buttonHeight/2), buttonWidth, buttonHeight, null);
			g.drawImage(tilePlacementRotateCWImage, (int)(hexClockwiise.getCenter().x-buttonWidth/2), (int)(hexClockwiise.getCenter().y- buttonHeight/2), buttonWidth, buttonHeight, null);
			
			rcPlayerIndicator.setBounds((int)(hexClockwiise.getCenter().x+buttonWidth/2) + 15,rcCancel.y,90,40); 
			rcTurnsLeft.setBounds(rcPlayerIndicator.x + rcPlayerIndicator.width + 15, rcCancel.y, 110,40);
			rcNextPlay.setBounds(rcTurnsLeft.x + rcTurnsLeft.width + 15, rcCancel.y, 115,40);

			rcUseNatureToken.setBounds(rcCancel.x, getHeight() - 120, 180, 40);
			g.drawImage(bearScoreImage, 10, 200, 140, 100, null);
			g.drawImage(elkScoreImage, 10, 310, 140, 100, null);
			g.drawImage(foxScoreImage, 10, 420, 140, 100, null);
			g.drawImage(hawkScoreImage, 10, 530, 140, 100, null);
			g.drawImage(salmonScoreImage, 10, 640, 140, 100, null);

			//paintBackgroundGrid(g, radius);

			// starting tiles
			drawClaimedHabitats(g);
			highlightNewHabitat(g);

			int cnt = 0;
			for (int i = 0; i < 3; i++)
			{
				if (i != activePlayerIdx)
				{
					int totalWidth = getWidth();
					Point ptOther = new Point ((int)(totalWidth)*2/3-120, 100+ cnt* 400);
					drawOtherPlayerClaimedHabitats(g, ptOther, i);
					cnt++;
				}
			}


			if (playerState == PlayerState.TILE_ON_TABLE_IS_SELECTED || playerState == PlayerState.CANDIDATE_TILE_CLICKED)
			{
				drawCandidateHexTiles(g);
			}
			if (candidateHabitat != null) {
				drawHabitatTile(g, candidateHabitat);
				drawHabitatWildlife(g, candidateHabitat);
				highlightNewHabitat(g);
			}
		

			for(int i = 0; i < tilesOnTable.size(); i++){
				Tile habTile = tilesOnTable.get(i);
				if (habTile != null) {
					ArrayList<String> habitat = tilesOnTable.get(i).getHabitats();
					BufferedImage img = getHabiImageFromName (habitat);
					
					int width = (int)(img.getWidth()*0.8);
					int height = (int)(img.getHeight()*0.8);
					int x0 = 250 + i * 120;
					int y0 = getHeight() - 180;

					g.drawImage(img, x0, y0, width, height, null);

					Hexagon hex = new Hexagon(x0+width/2, y0+height/2, radius);
					tilesOnTable.get(i).setHexagon(hex);
				
					drawTileWildlife(g, tilesOnTable.get(i));				
					drawHighlightedTileOnTable(g);
				}
			}
			if (useNatureToken) {
				animalOnTableImgElps.clear();
			}

			ArrayList<Integer> dupTokens = checkDuplicatedTokensOnTable();
			if (dupTokens.size() == 4 && playerState != PlayerState.TURN_IS_DONE)
			{
				// try {
				// 	Thread.sleep(4000);
				// } catch (InterruptedException e1) {
				// 	e1.printStackTrace();
				// }

				for (int i = 0; i < 4; i++)
				{
					String w = animals.getWildlife().remove(i);
					animalsOnTable.set(i, w);
				}
			}

			for(int i = 0; i < animalsOnTable.size(); i++){
				BufferedImage img = null;
				
				if (animalsOnTable.get(i) != "empty")
				{
					if (i == selectedTokenOnTableIndex)
						img = animalImageMap.get(animalsOnTable.get(i) + "Active");
					else
						img = animalImageMap.get(animalsOnTable.get(i));

					g.drawImage(img, 255 + i * 120, getHeight() - 80, img.getWidth(), img.getHeight(), null);
					if (useNatureToken && playerState == PlayerState.HABITAT_PLACE_COMFIRMED) {
						Ellipse2D elps = new Ellipse2D.Double(255 + i * 120, getHeight() -80, 60, 60);
						animalOnTableImgElps.add(elps);
						//System.out.println("-------->>>>animalOnTableImgElps.add(elps)" + animalsOnTable.get(i) + "total elps---->" + animalOnTableImgElps.size());
					}
				}
			}			

			if (dupTokens.size() == 3 && playerState != PlayerState.TURN_IS_DONE && replaceDuplicateCnt == 0)
			{
				rcReplaceDuplicate.setBounds(25, getHeight() -75,200, 40);
				g.setColor(Color.red);
				g.fillRect(rcReplaceDuplicate.x, rcReplaceDuplicate.y, rcReplaceDuplicate.width, rcReplaceDuplicate.height);
				g.setColor(Color.white);
				g.setFont(smallfont);
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				g.drawString("Replace Duplicate Tokens", rcReplaceDuplicate.x + 10, rcReplaceDuplicate.y + 25);
				
			}

			g.setColor(Color.red);
			g.fillRect(rcCancel.x, rcCancel.y, rcCancel.width, rcCancel.height);
			g.setColor(Color.white);
			g.setFont(smallfont);
			g.drawString("Cancel", rcCancel.x + 9, rcCancel.y + 25);

			g.setColor(Color.green);
			g.fillRect(rcConfirm.x, rcConfirm.y, rcConfirm.width, rcConfirm.height);
			g.setColor(Color.white);
			g.setFont(smallfont);
			g.drawString("Confirm", rcConfirm.x + 5, rcConfirm.y + 25);

			g.setColor(Color.blue);
			g.fillRect(rcPlayerIndicator.x, rcPlayerIndicator.y, rcPlayerIndicator.width, rcPlayerIndicator.height);
			g.setColor(Color.white);
			g.setFont(smallfont);
			g.drawString("Player " + Integer.toString(activePlayerIdx+1), rcPlayerIndicator.x + 10, rcPlayerIndicator.y + 25);
			
			g.setColor(Color.green);
			g.fillRect(rcTurnsLeft.x, rcTurnsLeft.y, rcTurnsLeft.width, rcTurnsLeft.height);
			g.setColor(Color.white);
			g.setFont(smallfont);
			g.drawString("Turns Left: " + Integer.toString(players.get(activePlayerIdx).getTurnsLeft()), rcTurnsLeft.x + 5, rcTurnsLeft.y + 25);

			if (players.get(2).getTurnsLeft() > 0) {
				g.setColor(Color.red);
				g.fillRect(rcNextPlay.x, rcNextPlay.y, rcNextPlay.width, rcNextPlay.height);
				g.setColor(Color.white);
				g.setFont(smallfont);
				g.drawString("Next Player", rcNextPlay.x + 10, rcNextPlay.y + 25);
			}

			g.setColor(Color.blue);
			g.fillRect(rcUseNatureToken.x, rcUseNatureToken.y, rcUseNatureToken.width, rcUseNatureToken.height);
			g.setColor(Color.white);
			g.setFont(smallfont);
			g.drawString("Use Nature Token", rcUseNatureToken.x + 27, rcUseNatureToken.y + 25);

			for (int i = 0; i < players.get(activePlayerIdx).getNumNatureToken(); i++) {
				g.drawImage(natureTokenImage, (int)(rcUseNatureToken.x + rcUseNatureToken.getWidth() + 20 + (natureTokenImage.getWidth() +15)* i ), (int)(rcUseNatureToken.y-5), null);

			}
		}
		else if (gameStatus == 2)
		{
			endGame(g);
		}

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

	public void drawClaimedHabitats(Graphics g)
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
			BufferedImage bImage = null;
			if ((boolean)cTile.get("tokenPlaced") == false) 
				bImage = animalImageMap.get(wildlifeList.get(0));
			else
				bImage = animalImageMap.get(wildlifeList.get(0)+"Active");
			g.drawImage(bImage, x, y, (int)xOff, (int)yOff*2,null);
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
	
	public void highlightNewHabitat(Graphics g) {
		if (candidateHabitat != null)
		{
			Hexagon hex = (Hexagon) candidateHabitat.get("hexagon");
			Point center = hex.getCenter();
			int x = (int)(center.getX() - xOff);
			int y = (int)(center.getY() - radius);
			g.drawImage(selectedTileImage, x, y, (int)(selectedTileImage.getWidth()), (int)( selectedTileImage.getHeight()), null);
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
		int ref_j = 0;
        for (int i = 0; i < 21; i++) {
			if (draw_hex_120 == true)
				ref_j = center_j - ((center_i - i) + (i%2))/2;
			else if (draw_hex_60 == true)
				ref_j = center_j + ((center_i - i) - (i%2))/2;
			//System.out.println("############## ref_j= " + ref_j);

        	for (int j = 0; j<21; j++) {
				int xLbl = i;
        		int yLbl = j;  
				
				if (draw_hex_120 == true) 
				{
					yLbl = i -center_i;
					xLbl = j-ref_j;
				}
				else if (draw_hex_60 == true)
				{
					yLbl = i -center_i;
					xLbl = j- ref_j;
				}

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

	public void drawOtherPlayerClaimedHabitats(Graphics g, Point originPt, int playerIdx)
	{
		Player activePlayer = players.get(playerIdx);
		ArrayList<TreeMap<String, Object>> startingTiles = activePlayer.getClaimedHabitats();
		for (TreeMap<String, Object> cTile : startingTiles) {
			drawOtherPlayerHabitatTile(g, cTile, originPt, 0.5);
			drawOtherPlayerHabitatWildlife(g, cTile, originPt, 0.5);
		}
		g.setColor(Color.blue);
		g.setFont(font);
		if(originPt.y < getHeight() / 3)
			g.drawString("Player " + Integer.toString(playerIdx + 1), getWidth() - 100, getHeight() / 3  );
		else
			g.drawString("Player " + Integer.toString(playerIdx + 1), getWidth() - 100, getHeight() * 3 / 4);
	}


	public void drawOtherPlayerHabitatTile(Graphics g, TreeMap<String, Object> cTile, Point originPt, double scaleValue) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		int pRadius = (int)(radius*scaleValue);
		double pxOff =  Math.cos(ang30) * (pRadius +0.3);
		double pyOff =  Math.sin(ang30) * (pRadius +0.3);
		ArrayList<String> habitatsList = (ArrayList<String>) cTile.get("habitats");			
		BufferedImage bImageorig = getHabiImageFromName(habitatsList);
		BufferedImage bImage = imageResize(bImageorig, (int)(bImageorig.getWidth()*scaleValue), (int)(bImageorig.getHeight()*scaleValue));
		int rot = (int) cTile.get("rotation");
		int x = (int) (originPt.x + (row_i%2)*pxOff + 2*col_j*pxOff -pxOff);
		int y = (int) (originPt.y + 3*pyOff*row_i) -pRadius;
		double locationX = bImage.getWidth() / 2;
		double locationY = bImage.getHeight() / 2;
		Graphics2D g2d = (Graphics2D) g;
		if (rot > 0) {
			
			AffineTransform identity = AffineTransform.getRotateInstance(Math.toRadians(rot), locationX, locationY);				
			AffineTransformOp op = new AffineTransformOp(identity, AffineTransformOp.TYPE_BILINEAR);
			g2d.drawImage(op.filter(bImage, null), x, y, null);
		}
		else {
			g.drawImage(bImage, x, y, bImage.getWidth(), bImage.getHeight(), null);
			
		}
	}

	public void drawOtherPlayerHabitatWildlife(Graphics g, TreeMap<String, Object> cTile, Point originPt, double scaleValue) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		int pRadius = (int)(radius*scaleValue);
		double pxOff =  Math.cos(ang30) * (pRadius +0.3);
		double pyOff =  Math.sin(ang30) * (pRadius +0.3);
		int x = (int) (originPt.x + (row_i%2)*pxOff + 2*col_j*pxOff -pxOff);
		int y = (int) (originPt.y + 3*pyOff*row_i) -pRadius;
		ArrayList<String> wildlifeList = (ArrayList<String>) cTile.get("wildlife");
		int num = wildlifeList.size();
		if(num == 1){

			//y+=(int)(endgameyOff + 6);
			BufferedImage bImage = null;
			if ((boolean)cTile.get("tokenPlaced") == false) 
				bImage = animalImageMap.get(wildlifeList.get(0));
			else
				bImage = animalImageMap.get(wildlifeList.get(0)+"Active");
			
			BufferedImage scaledImage = imageResize(bImage, bImage.getWidth()/2, bImage.getHeight()/2);
			x+=(int)pxOff - scaledImage.getWidth()/2;
			y+=(int)pRadius - scaledImage.getHeight()/2;
			g.drawImage(scaledImage, x, y, null);

			//g.drawImage(bImage, x, y, (int)endgamexOff, (int)endgameyOff*2,null);
		}
		else if(num == 2){
			y+=(int)(pyOff + 6);
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	
					x += (int)(pxOff * 0.2);	
				else
					x += (int)(pxOff * 0.8);
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x, y, (int)(pxOff * 1.5 / 2), (int)pyOff*3/2,null);
			
			}
		}
		else if(num == 3){
			int x1, y1;
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	{
					x1 = x + (int)(pxOff * 0.75);
					y1 = y+(int)(pyOff*0.85);
				}
				else if(i == 1){
					x1 = x + (int)(pxOff * 0.5);	
					y1 = y+(int)(pyOff * 1.7);
				}
				else{
					x1 = x + (int)(pxOff * 1.1);
					y1 = y+(int)(pyOff * 1.6);
				}
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x1, y1, (int)(pxOff * 1.2 / 2), (int)(pyOff*1.2),null);
			
			}
		}

	}


	public void drawFinalClaimedHabitats(Graphics g, Point originPt, int playerIdx)
	{
		Player activePlayer = players.get(playerIdx);
		ArrayList<TreeMap<String, Object>> startingTiles = activePlayer.getClaimedHabitats();
		for (TreeMap<String, Object> cTile : startingTiles) {
			drawFinalHabitatTile(g, cTile, originPt);
			drawFinalHabitatWildlife(g, cTile, originPt);
		}
	}

	public static BufferedImage imageResize(BufferedImage img, int newW, int newH) { 
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	} 



	public void drawFinalHabitatTile(Graphics g, TreeMap<String, Object> cTile, Point originPt) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		ArrayList<String> habitatsList = (ArrayList<String>) cTile.get("habitats");			
		BufferedImage bImageorig = getHabiImageFromName(habitatsList);
		BufferedImage bImage = imageResize(bImageorig, bImageorig.getWidth()*2/3, bImageorig.getHeight()*2/3);
		int rot = (int) cTile.get("rotation");
		int x = (int) (originPt.x + (row_i%2)*endgamexOff + 2*col_j*endgamexOff -endgamexOff);
		int y = (int) (originPt.y + 3*endgameyOff*row_i) -endgameRadius;
		double locationX = bImage.getWidth() / 2;
		double locationY = bImage.getHeight() / 2;
		Graphics2D g2d = (Graphics2D) g;
		if (rot > 0) {
			
			AffineTransform identity = AffineTransform.getRotateInstance(Math.toRadians(rot), locationX, locationY);				
			AffineTransformOp op = new AffineTransformOp(identity, AffineTransformOp.TYPE_BILINEAR);
			g2d.drawImage(op.filter(bImage, null), x, y, null);
		}
		else {
			g.drawImage(bImage, x, y, bImage.getWidth(), bImage.getHeight(), null);
			
		}
	}

	public void drawFinalHabitatWildlife(Graphics g, TreeMap<String, Object> cTile, Point originPt) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		//double xOff = Math.cos(ang30) * (endgameRadius +0.3);
		//double yOff = Math.sin(ang30) * (endgameRadius +0.3);
		int x = (int) (originPt.x + (row_i%2)*endgamexOff + 2*col_j*endgamexOff -endgamexOff);
		int y = (int) (originPt.y + 3*endgameyOff*row_i) -endgameRadius;
		ArrayList<String> wildlifeList = (ArrayList<String>) cTile.get("wildlife");
		int num = wildlifeList.size();
		if(num == 1){

			//y+=(int)(endgameyOff + 6);
			BufferedImage bImage = null;
			if ((boolean)cTile.get("tokenPlaced") == false) 
				bImage = animalImageMap.get(wildlifeList.get(0));
			else
				bImage = animalImageMap.get(wildlifeList.get(0)+"Active");
			
			BufferedImage scaledImage = imageResize(bImage, bImage.getWidth()/2, bImage.getHeight()/2);
			x+=(int)endgamexOff - scaledImage.getWidth()/2;
			y+=(int)endgameRadius - scaledImage.getHeight()/2;
			g.drawImage(scaledImage, x, y, null);

			//g.drawImage(bImage, x, y, (int)endgamexOff, (int)endgameyOff*2,null);
		}
		else if(num == 2){
			y+=(int)(endgameyOff + 6);
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	
					x += (int)(endgamexOff * 0.2);	
				else
					x += (int)(endgamexOff * 0.8);
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x, y, (int)(endgamexOff * 1.5 / 2), (int)endgameyOff*3/2,null);
			
			}
		}
		else if(num == 3){
			int x1, y1;
			for(int i = 0; i < wildlifeList.size(); i++){		
				if(i == 0)	{
					x1 = x + (int)(endgamexOff * 0.75);
					y1 = y+(int)(endgameyOff*0.85);
				}
				else if(i == 1){
					x1 = x + (int)(endgamexOff * 0.5);	
					y1 = y+(int)(endgameyOff * 1.7);
				}
				else{
					x1 = x + (int)(endgamexOff * 1.1);
					y1 = y+(int)(endgameyOff * 1.6);
				}
				BufferedImage bImage = animalImageMap.get(wildlifeList.get(i));
				g.drawImage(bImage, x1, y1, (int)(endgamexOff * 1.2 / 2), (int)(endgameyOff*1.2),null);
			
			}
		}

	}


	private void StartGame(){
		gameStatus = 1;


		for(int i = 0; i < 4; i++){
			Tile t = tiles.getTiles().remove(i);
			String w = animals.getWildlife().remove(i);
			tilesOnTable.add(t);
			animalsOnTable.add(w);
			tilesAnimalsOnTable[i] = Integer.toString(t.getTileNum()) + "-" + w;

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
		int x = e.getX();
		int y = e.getY();
		if(gameStatus == 0){
			if(x >= getWidth() / 2 - 100 && x <= getWidth() / 2 + 100 && y >= 200 && y <= 260){
				//System.out.println("Start Game");
				StartGame();
				playerState = PlayerState.TILES_ON_TABLE_UPDATED;
			}
		}
		else{
			//System.out.println( "" + e.getX() + "  " + e.getY());
			if(rcNextPlay.contains(e.getPoint())){
				players.get(activePlayerIdx).turnUsed();
				activePlayerIdx = (activePlayerIdx + 1) % 3;
				for(int i = 0; i < tilesOnTable.size(); i++){
					if(tilesOnTable.get(i) == null){
						tilesOnTable.set(i, tiles.getTiles().remove(0));
						break;
					}
				}
				for(int i = 0; i < 4; i++){
					if(animalsOnTable.get(i) == "empty")
						animalsOnTable.set(i, animals.getWildlife().remove(0));
				}

				playerState = PlayerState.TILES_ON_TABLE_UPDATED;
				selectedTileOnTableIndex = -1;
				selectedTokenOnTableIndex = -1;
				previousMouseMovedinHabitatNum = -1;
				activeAnimalToken = "";
				candidateHabitat = null;
				if (candidateHabitatHexagon != null)
					candidateHabitatHexagon.clear();
				if (animalOnTableImgElps != null)
					animalOnTableImgElps.clear();
				useNatureToken = false;
				replaceDuplicateCnt = 0;

				if (players.get(2).getTurnsLeft() == 0)
					gameStatus  = 2;
				repaint();
				return;
			}
			
			TreeMap<String, Object> cHabitat = players.get(activePlayerIdx).searchHabitat(e.getPoint());
			if (cHabitat != null && (boolean)(cHabitat.get("tokenPlaced")) == false)
			{
				if (playerState == PlayerState.HABITAT_PLACE_COMFIRMED)
				{
					if ( activeAnimalToken != "") {
						ArrayList<String> wildlifeNames =( ArrayList<String>)cHabitat.get("wildlife");
						if (wildlifeNames.contains(activeAnimalToken)) {			
							ArrayList<String> wildlifeLists = new ArrayList<>();
							wildlifeLists.add(activeAnimalToken);
							cHabitat.put("wildlife", wildlifeLists);
							cHabitat.put("tokenPlaced", true);
							if (wildlifeNames.size() == 1)
								players.get(activePlayerIdx).increaseNatureToken();

							players.get(activePlayerIdx).addHabitatLocForToken(activeAnimalToken, 
								(int)(cHabitat.get("row_idx")), (int)(cHabitat.get("col_idx")), (int)(cHabitat.get("tileNum")));
							Graphics g = getGraphics();
							drawHabitatTile(g, cHabitat);
							drawHabitatWildlife(g, cHabitat);
							playerState = PlayerState.TURN_IS_DONE;
							animalsOnTable.set(selectedTokenOnTableIndex, "empty");
							animalOnTableImgElps.clear();
							useNatureToken = false;
							replaceDuplicateCnt = 0;

							// players.get(activePlayerIdx).foxScoreCalculate_A();
							// players.get(activePlayerIdx).elkScoreCalculate_A();
							// int hawk = players.get(activePlayerIdx).hawkScoreCalculate_A();
							// int bear = players.get(activePlayerIdx).bearScoreCalculate_A();
							// int salmon = players.get(activePlayerIdx).salmonScoreCalculate();
							// System.out.println("~~~~~~~~~~~Scalmon Score: " + salmon);
						}
					}
				}
			}

			if (playerState == PlayerState.HABITAT_PLACE_COMFIRMED && useNatureToken)
			{
				for (int i = 0; i < 4; i++) {
					Ellipse2D elps = animalOnTableImgElps.get(i);
					if (elps.contains(e.getPoint())) {
						selectedTokenOnTableIndex = i;
						activeAnimalToken = animalsOnTable.get(i);
						Graphics g = getGraphics();
						g.drawImage(animalImageMap.get(animalsOnTable.get(i)+"Active"), (int)(elps.getCenterX()-elps.getWidth()/2), (int)(elps.getCenterY()-elps.getHeight()/2), null);
						break;
					}
				}
			}

			if (playerState == PlayerState.TILES_ON_TABLE_UPDATED || playerState == PlayerState.TILE_ON_TABLE_IS_SELECTED) 
			{
				for (int i = 0; i<4; i++) {
					Tile tile = tilesOnTable.get(i);
					Hexagon hex = (Hexagon) tile.getHexagon();
					if(hex != null && hex.contains(e.getPoint()))
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

					//System.out.println("~~~~~~ Check: " + "row==>" + row_i + " col==>" + col_j);
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
					
					for(int i = 0; i <  tilesOnTable.size(); i++){
						Tile t = tilesOnTable.get(i);
						if (t.getTileNum() == selectedTileOnTable.getTileNum())
						{
							tilesOnTable.set(i, null);
							selectedTileOnTableIndex = i;
						}
					}				
				}
				
			}
			repaint();

			// players.get(activePlayerIdx).searchHabitat(e.getPoint());
			if(rcCancel.contains(e.getPoint())) {

				if (playerState == PlayerState.CANDIDATE_TILE_CLICKED) {
					playerState = PlayerState.TILES_ON_TABLE_UPDATED;
					tilesOnTable.set(selectedTileOnTableIndex, selectedTileOnTable);
					candidateHabitat = null;
					selectedTileOnTableIndex = -1;
					selectedTokenOnTableIndex = -1;
					clockwiseClickedCnt = 0;
					counterCWclickedCnt = 0;
				
				}
			}
			if(rcConfirm.contains(e.getPoint())) {
				
				if (playerState == PlayerState.CANDIDATE_TILE_CLICKED && candidateHabitat != null) {
					ArrayList<TreeMap<String, Object>> claimedHab = players.get(activePlayerIdx).getClaimedHabitats();
					TreeMap<String, Object> newHab = new TreeMap<>();
					newHab = candidateHabitat;
					claimedHab.add(newHab);
					candidateHabitat = null;
					playerState = PlayerState.HABITAT_PLACE_COMFIRMED;
					if (!useNatureToken)
					{
						selectedTokenOnTableIndex = selectedTileOnTableIndex;
						activeAnimalToken = animalsOnTable.get(selectedTokenOnTableIndex);
					}
					players.get(activePlayerIdx).addHabitatForHab(
								(int)(newHab.get("row_idx")), (int)(newHab.get("col_idx")), newHab);
					clockwiseClickedCnt = 0;
					counterCWclickedCnt = 0;

					//players.get(activePlayerIdx).printHabitatInfo();
					players.get(activePlayerIdx).habitatCorridorCalculate();
					//System.out.println("Confirm clicked");
				}

			}		
			if(hexCounterClockwise.contains(e.getPoint())) {
				//System.out.println("CounterClockwise clicked");
				if (playerState == PlayerState.CANDIDATE_TILE_CLICKED) {
					counterCWclickedCnt ++;
				}

			}
			if(hexClockwiise.contains(e.getPoint())) {
				//System.out.println("Clockwise clicked");		
				if (playerState == PlayerState.CANDIDATE_TILE_CLICKED) {
					clockwiseClickedCnt ++;
				}
			}

			if (hexCounterClockwise.contains(e.getPoint()) || hexClockwiise.contains(e.getPoint()) )
			{
				int rotatCounterCW= counterCWclickedCnt%6;
				int rotatCW = clockwiseClickedCnt%6;
				int rotationResult = 360 - rotatCounterCW*60 + rotatCW*60;
				//System.out.println("rotatCCW: " + rotatCounterCW + "~~~~~" + "rotatCW: " + rotatCW);
				//System.out.println("rotationResult: " + rotationResult%360);
				candidateHabitat.put("rotation", rotationResult%360);
				drawHabitatTile(getGraphics(), candidateHabitat);
				drawHabitatWildlife(getGraphics(), candidateHabitat);
			}

			if (rcUseNatureToken.contains(e.getPoint()))
			{
				if (playerState != PlayerState.TURN_IS_DONE && playerState != PlayerState.HABITAT_PLACE_COMFIRMED) {
					if (players.get(activePlayerIdx).getNumNatureToken() > 0){
						players.get(activePlayerIdx).decreaseNatureToken();
						useNatureToken = true;
					}
				}
			}
			 
			if (rcReplaceDuplicate.contains(e.getPoint()))
			{
				ArrayList<Integer> dupTokens = checkDuplicatedTokensOnTable();
				if (dupTokens.size() == 3) 
				{
					for (int tokenIdx : dupTokens) 
					{
						String w = animals.getWildlife().remove(tokenIdx);
						animalsOnTable.set(tokenIdx, w);

					}
					replaceDuplicateCnt = 1;
					// for (int i = 0; i < 3; i++) {
					// 	animalsOnTable.set(i,"bear");
					// }
				}	

				dupTokens = checkDuplicatedTokensOnTable();
				if (dupTokens.size() == 4) 
				{
					for (int i = 0; i < 4; i++)
					{
						String w = animals.getWildlife().remove(i);
						animalsOnTable.set(i, w);
					}
				}

			}
			repaint();
			
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
				//System.out.println("@@@@@@" + entry.getKey());
				String s = entry.getKey();
				String[] loc_idx_strings = s.split(":");

				candidateHabitat = new TreeMap<>();
				candidateHabitat.put("tileNum", selectedTileOnTable.getTileNum());
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		//System.out.println( "moust enter " + e.getX() + "  " + e.getY());

		// highlight the animal on habitat based on active token
		if (playerState == PlayerState.HABITAT_PLACE_COMFIRMED) {
			boolean foundTokenMatchHab = false;
			ArrayList<TreeMap<String, Object>> claimedHabitats = players.get(activePlayerIdx).getClaimedHabitats();
			Graphics g= getGraphics();
			int foundMatchHabitatNum = -1;
			TreeMap<String, Object> foundMatchHabitat = null;
			for (TreeMap<String, Object> cHabitat : claimedHabitats) {
					ArrayList<String> wildlifeNames =( ArrayList<String>)cHabitat.get("wildlife");
					Hexagon hex = (Hexagon) cHabitat.get("hexagon");
					if(hex.contains(e.getPoint()))
					{						
						if (wildlifeNames.contains(activeAnimalToken))
						{
							foundTokenMatchHab = true;
							foundMatchHabitatNum = (int)(cHabitat.get("tileNum"));
							break;
						}
					}
			}

			if (foundTokenMatchHab == true )
			{
				if (previousMouseMovedinHabitatNum != foundMatchHabitatNum)
				{
					if (previousMouseMovedinHabitatNum == -1)
					{
						foundMatchHabitat = searchClaimedHabitatByTileNum(foundMatchHabitatNum);
						drawHabitatTile(g, foundMatchHabitat);
						int row_i = (int) foundMatchHabitat.get("row_idx");
						int col_j = (int) foundMatchHabitat.get("col_idx");
						int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff -xOff);
						int y = (int) (origin.y + 3*yOff*row_i) -radius;							
						x+=(int)xOff/2;
						y+=(int)(yOff + 6);
						BufferedImage bImage = animalImageMap.get(activeAnimalToken+"Active");
						g.drawImage(bImage, x, y, (int)xOff, (int)yOff*2,null);
						
						previousMouseMovedinHabitatNum = foundMatchHabitatNum;
					}
					else 
					{
						//clear up old one
						previousTokenMatchHabit = searchClaimedHabitatByTileNum(previousMouseMovedinHabitatNum);
						drawHabitatTile(g, previousTokenMatchHabit);
						drawHabitatWildlife(g, previousTokenMatchHabit);
						previousMouseMovedinHabitatNum = foundMatchHabitatNum;
					
						//draw new match hab with active token
						foundMatchHabitat = searchClaimedHabitatByTileNum(foundMatchHabitatNum);
						drawHabitatTile(g, foundMatchHabitat);
						int row_i = (int) foundMatchHabitat.get("row_idx");
						int col_j = (int) foundMatchHabitat.get("col_idx");
						int x = (int) (origin.x + (row_i%2)*xOff + 2*col_j*xOff -xOff);
						int y = (int) (origin.y + 3*yOff*row_i) -radius;							
						x+=(int)xOff/2;
						y+=(int)(yOff + 6);
						BufferedImage bImage = animalImageMap.get(activeAnimalToken+"Active");
						g.drawImage(bImage, x, y, (int)xOff, (int)yOff*2,null);


					}
				}
			}
			else
			{
				if (previousMouseMovedinHabitatNum != -1)
				{
					previousTokenMatchHabit = searchClaimedHabitatByTileNum(previousMouseMovedinHabitatNum);
					drawHabitatTile(g, previousTokenMatchHabit);
					drawHabitatWildlife(g, previousTokenMatchHabit);
					previousMouseMovedinHabitatNum = -1;
					foundTokenMatchHab = false;
				}
			
			}
		}

	}


	public TreeMap<String, Object> searchClaimedHabitatByTileNum(int tilenumber)
	{
		for (TreeMap<String, Object> cHabitat : players.get(activePlayerIdx).getClaimedHabitats()) 
		{
			if (tilenumber == (int)(cHabitat.get("tileNum")))
			{
				return cHabitat;
			}
		}
		return null;
	}

	public ArrayList<Integer> checkDuplicatedTokensOnTable() {
		TreeMap<String, ArrayList<Integer>> tokenTempMap = new TreeMap<>();
		for (int i = 0; i < 4; i++) {
			String token = animalsOnTable.get(i);
			if (!tokenTempMap.containsKey(token)) {
				ArrayList<Integer> idxList = new ArrayList<>();
				idxList.add(i);
				tokenTempMap.put(token, idxList);
			}
			else {
				tokenTempMap.get(token).add(i);
			}
		}

		String tKey = "";
		int cnt = 0;
		for (Map.Entry<String, ArrayList<Integer>> entry : tokenTempMap.entrySet()) 
		{
			if (entry.getValue().size() > cnt)
			{
				tKey = entry.getKey();
				cnt = entry.getValue().size();
			}
		}
		return tokenTempMap.get(tKey);

	}

	public void endGame(Graphics g)
	{
		// g.setColor(Color.green);
		// g.fillRect(getWidth() / 2 - 100, 200, 200, 60);
		// g.setColor(Color.white);
		// g.setFont(font);
		//g.drawString("Game Ended", getWidth() / 2 - 70, 235);


		
		// int foxScore = players.get(activePlayerIdx).foxScoreCalculate_A();
		// int elkScore = players.get(activePlayerIdx).elkScoreCalculate_A();
		//int hawk = players.get(activePlayerIdx).hawkScoreCalculate_A();
		TreeMap<Integer, TreeMap<String, Integer>> playersFinalHabs = new TreeMap<>();
		TreeMap<Integer, TreeMap<String, Integer>> playersHabsBonus = new TreeMap<>();
		String[] habs = {"mountain","forest", "desert",  "swamp", "lake"};
		for (int i =0; i < 3; i++) {
			// System.out.println("player =====> " +i);
			// int bear = players.get(i).bearScoreCalculate();
			// System.out.println(" ########## total bear score: " + bear);

			players.get(i).habitatCorridorCalculate();

			TreeMap<String, Integer> finalCnt = players.get(i).getFinalHabConnGroupCnt();
			playersFinalHabs.put(i, finalCnt);

			TreeMap<String, Integer> bonusScores = new TreeMap<>();
			for (String habType: habs) 
			{
				bonusScores.put(habType, 0);
			}
			playersHabsBonus.put(i, bonusScores);

		}


		for (String habT: habs) 
		{
			// find the scores of this habitat type for all three players.
			int player_0 = playersFinalHabs.get(0).get(habT);
			int player_1 = playersFinalHabs.get(1).get(habT);
			int player_2 = playersFinalHabs.get(2).get(habT);

			if (player_0 == player_1 && player_1 == player_2)
			{
				playersHabsBonus.get(0).put(habT, 1);
				playersHabsBonus.get(1).put(habT, 1);
				playersHabsBonus.get(2).put(habT, 1);
			}
			else 
			{
				PlayerScore[] playerScoreList = {new PlayerScore(0, player_0), 
												 new PlayerScore(1, player_1),
												 new PlayerScore(2, player_2)};
				
				PlayerScore[] sortedPScores = Arrays.stream(playerScoreList).sorted(Comparator.comparing(PlayerScore::getPlayerScore)).toArray(PlayerScore[]::new);


				//System.out.println(" ~~~~" + habT + " ~~~~~~~~ PlayersScore: ");
				// for (int i = 0; i < 3; i++)
				// {
				// 	System.out.println("score and player idx==> : " + sortedPScores[i]);
				// }

				if (sortedPScores[1].getPlayerScore() == sortedPScores[2].getPlayerScore())
				{
					//2 players tie for the largest, 2 points each
					int p1 = sortedPScores[1].getPlayerIdx();
					int p2 = sortedPScores[2].getPlayerIdx();
					playersHabsBonus.get(p1).put(habT, 2);
					playersHabsBonus.get(p2).put(habT, 2);
				}
				else if (sortedPScores[0].getPlayerScore() == sortedPScores[1].getPlayerScore())
				{
					// one largest and two ties for the second largest, 3 points for the largest
					int p2 = sortedPScores[2].getPlayerIdx();
					playersHabsBonus.get(p2).put(habT, 3);
				}
				else {
					int p2 = sortedPScores[2].getPlayerIdx();
					playersHabsBonus.get(p2).put(habT, 3);
					int p1 = sortedPScores[1].getPlayerIdx();
					playersHabsBonus.get(p1).put(habT, 1);
				}
			}

		}

		for (Map.Entry<Integer, TreeMap<String, Integer>> en : playersHabsBonus.entrySet())
		{
			int playerIdx = en.getKey();
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~bonus scores for player idx: " + playerIdx + " ~~~~~~~~~~~~~~~~~~~~");
			TreeMap<String, Integer> enm = en.getValue();
			for (Map.Entry<String, Integer> sen: enm.entrySet()) 
			{
				String hab = sen.getKey();
				int bs = sen.getValue();
				//System.out.println("bonus for ~~~" + hab + " ~~~ bonus score: "+ bs);
			}
		}

		calculateScorBoardLocations();
		for (int i =0; i < 3; i++) 
		{
			
			int totalWidth = getWidth();
			Point pt = new Point ((int)(totalWidth*i/3)-100, 76);
			drawFinalClaimedHabitats(g, pt, i);
			Player pl = players.get(i);

			int yStart = 0;
			for (Map.Entry<Integer, Point> entry : scoreBoardLoc.entrySet()) 
			{
				int y = (int)entry.getValue().getY();
				if (y > yStart) 
					yStart = y;
			}

			int xStart = (int)scoreBoardLoc.get(i).getX();
			g.drawImage(fullScoreBoardImage, xStart + 10, yStart +150, null);
		
			// display scores to score board
			int foxScore = pl.foxScoreCalculate();
			int elkScore = pl.elkScoreCalculate();
			int hawkScore = pl.hawkScoreCalculate();
			int bearScore = pl.bearScoreCalculate();
			int salmonScore = pl.salmonScoreCalculate();

			g.setColor(Color.white);
			//g.fillRect(getWidth() / 2 - 100, 200, 200, 60);
			g.setColor(Color.blue);
			g.setFont(font);

			int scoreYStart = yStart + 177;
			//nature token score
			int nt = pl.getNumNatureToken();
			int totalTokenScore = foxScore + elkScore + hawkScore + bearScore + salmonScore + nt;

			g.drawString(Integer.toString(nt), xStart + fullScoreBoardImage.getWidth()*2/5-18, scoreYStart);

			// token scores
			g.drawString(Integer.toString(bearScore), xStart + fullScoreBoardImage.getWidth()*2/5-18, scoreYStart+41);
			g.drawString(Integer.toString(elkScore),xStart + fullScoreBoardImage.getWidth()*2/5-18, scoreYStart+41*2);
			g.drawString(Integer.toString(foxScore),xStart + fullScoreBoardImage.getWidth()*2/5-18,scoreYStart+41*3);
			g.drawString(Integer.toString(hawkScore), xStart + fullScoreBoardImage.getWidth()*2/5-18, scoreYStart+41*4);
			g.drawString(Integer.toString(salmonScore), xStart + fullScoreBoardImage.getWidth()*2/5-18, scoreYStart+41*5);
			g.drawString(Integer.toString(totalTokenScore), xStart + fullScoreBoardImage.getWidth()*2/5-24, scoreYStart+41*6);
			


			// habitat score
			TreeMap<String, Integer> habScores = playersFinalHabs.get(i);
			int mountainScore = habScores.get("mountain");
			int forestScore = habScores.get("forest");
			int desertScore = habScores.get("desert");
			int swampScore = habScores.get("swamp");
			int lakeScore = habScores.get("lake");

			int totalhabScore = mountainScore + forestScore + desertScore + swampScore + lakeScore;

			//g.drawString(Integer.toString(1), xStart + fullScoreBoardImage.getWidth()*4/5-18, scoreYStart);
			g.drawString(Integer.toString(mountainScore), xStart + fullScoreBoardImage.getWidth()*4/5-18, scoreYStart+41);
			g.drawString(Integer.toString(forestScore), xStart + fullScoreBoardImage.getWidth()*4/5-18, scoreYStart+41*2);
			g.drawString(Integer.toString(desertScore), xStart + fullScoreBoardImage.getWidth()*4/5-18,scoreYStart+41*3);
			g.drawString(Integer.toString(swampScore), xStart + fullScoreBoardImage.getWidth()*4/5-18, scoreYStart+41*4);
			g.drawString(Integer.toString(lakeScore), xStart + fullScoreBoardImage.getWidth()*4/5-18, scoreYStart+41*5);
			g.drawString(Integer.toString(totalhabScore), xStart + fullScoreBoardImage.getWidth()*4/5-24, scoreYStart+41*6);
			

			// habitat bonus 			
			TreeMap<String, Integer> habBnScores = playersHabsBonus.get(i);
			int mountainBnScore = habBnScores.get("mountain");
			int forestBnScore = habBnScores.get("forest");
			int desertBnScore = habBnScores.get("desert");
			int swampBnScore = habBnScores.get("swamp");
			int lakeBnScore = habBnScores.get("lake");
			int totalBonus = mountainBnScore + forestBnScore + desertBnScore + swampBnScore + lakeBnScore;

			g.drawString(Integer.toString(mountainBnScore), xStart + fullScoreBoardImage.getWidth()-18, scoreYStart+41);
			g.drawString(Integer.toString(forestBnScore), xStart + fullScoreBoardImage.getWidth()-18, scoreYStart+41*2);
			g.drawString(Integer.toString(desertBnScore), xStart + fullScoreBoardImage.getWidth()-18,scoreYStart+41*3);
			g.drawString(Integer.toString(swampBnScore), xStart + fullScoreBoardImage.getWidth()-18, scoreYStart+41*4);
			g.drawString(Integer.toString(lakeBnScore), xStart + fullScoreBoardImage.getWidth()-18, scoreYStart+41*5);
			g.drawString(Integer.toString(totalBonus), xStart + fullScoreBoardImage.getWidth()-24, scoreYStart+41*6);

			int finalTotalScore = totalTokenScore + totalhabScore + totalBonus;
			g.drawString(Integer.toString(finalTotalScore), xStart + fullScoreBoardImage.getWidth()*2/5-24, scoreYStart+41*7);

		}

			 
	}

	public void calculateScorBoardLocations()
	{
		for (int i =0; i < 3; i++) 
		{
			Point minXMaxY = new Point();
			Player activePlayer = players.get(i);
			ArrayList<TreeMap<String, Object>> startingTiles = activePlayer.getClaimedHabitats();
			Point pt = new Point ((int)(getWidth()*i/3)-100, 76);
			int min_j = 20, max_i = 0;
			for (TreeMap<String, Object> cTile : startingTiles) {
				int row = (int)cTile.get("row_idx");
				int col = (int)cTile.get("col_idx");
				if (col < min_j) 
					min_j = col;
				if (row > max_i) 
					max_i = row;
			}

			int x = (int) (pt.x + (max_i%2)*endgamexOff + 2*min_j*endgamexOff -endgamexOff);
			int y = (int) (pt.y + 3*endgameyOff*max_i) -endgameRadius;

			minXMaxY.setLocation(x, y);
			scoreBoardLoc.put(i, minXMaxY);
		}
	}

	

}
