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

import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;



public class GamePanel extends JPanel implements MouseListener{
	BufferedImage background;
	private ArrayList<Player> players;

	private Polygon hexagonTest;
	private Tiles tiles;
	private Wildlife animals;
	private ArrayList<Tile> tilesOnTable;
	private ArrayList<String> animalsOnTable;
	private int gameStatus = 0;
	private Font font = new Font("Arial", Font.BOLD, 24);
	private Font smallfont = new Font("Arial", Font.BOLD, 20);
	int activePlayerIdx = 0;
	FontMetrics metrics;
	BufferedImage forestTileImage = null, lakeTileImage =  null ,swampTileImage = null;
	BufferedImage lakeMountainTileImage = null, mountainDesertTileImage =  null ,mountainForestTileImage = null;
	BufferedImage desertTileImage = null, desertLakeTileImage =  null ,desertSwampTileImage = null;
	BufferedImage forestDesertTileImage = null, forestLakeTileImage =  null ,forestSwampTileImage = null;
	BufferedImage mountainTileImage = null, mountainSwampTileImage =  null ,starterTile1 = null;
	BufferedImage swampLakeTileImage = null;
	BufferedImage elkImage  = null, foxImage = null, hawkImage = null, salmonImage = null, bearImage = null;
	BufferedImage bearScoreImage = null, elkScoreImage = null, foxScoreImage = null, hawkScoreImage = null, salmonScoreImage = null;
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

			bearImage = ImageIO.read(new File("src/images/bear.png"));
			elkImage = ImageIO.read(new File("src/images/elk.png"));
			salmonImage = ImageIO.read(new File("src/images/salmon.png"));
			foxImage = ImageIO.read(new File("src/images/fox.png"));
			hawkImage = ImageIO.read(new File("src/images/hawk.png"));

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

			//paingBackgroundGrid(g, radius);

			// starting tiles
			drawStartingTiles(g);

			for(int i = 0; i <  tilesOnTable.size(); i++){
				Tile t = tilesOnTable.get(i);
				System.out.println(t.getTileNum() + " with habitats size of " + t.getHabitats().size());
				for(int j = 0; j < t.getHabitats().size(); j++)
					System.out.println(t.getHabitats().get(j));
			}
		}


		for(int i = 0; i < tilesOnTable.size(); i++){
			ArrayList<String> habitat = tilesOnTable.get(i).getHabitats();
			BufferedImage img = getHabiImageFromName (habitat);
			g.drawImage(img, 250 + i * 120, getHeight() - 250, 100, 100, null);

		}
		for(int i = 0; i < animalsOnTable.size(); i++){
			System.out.println("Should get an image of " + animalsOnTable.get(i));
			BufferedImage img = getImage(animalsOnTable.get(i));
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

	public BufferedImage getHabiImageFromName(ArrayList<String> habitat) 
	{
		String imgName = "";
		for(int j = 0; j < habitat.size(); j++){
			if(j == 0)
				imgName += habitat.get(j);
			else
				imgName += ("+" + habitat.get(j));
		}
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

	public void drawHabitatTile(Graphics g, TreeMap<String, Object> cTile) 
	{		
		int row_i = (int) cTile.get("row_idx");
		int col_j = (int) cTile.get("col_idx");
		ArrayList<String> habitatsList = (ArrayList<String>) cTile.get("habitats");			
		ArrayList<String> WidelifeList = (ArrayList<String>) cTile.get("wildlife");
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

	public void paingBackgroundGrid(Graphics g, int radius) {
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
        hex.draw(g2d, x, y, 1, 0xFFDD88, false);
        //g.setColor(Color.blue);
       
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
			case "hawk":
			img = hawkImage;
				break;
			case "elk":
			img = elkImage;
				break;
			case "fox":
			img = foxImage;
				break;
			case "salmon":
			img = salmonImage;
				break;
			case "bear":
			img = bearImage;
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
			}
		}
		else{
			System.out.println( "" + e.getX() + "  " + e.getY());
			players.get(activePlayerIdx).searchHabitat(e.getPoint());
			if(rcCancel.contains(e.getPoint()))
				System.out.println("Cancel clicked");
			if(rcConfirm.contains(e.getPoint()))
				System.out.println("Confirm clicked");		
			if(rcCounterClockwise.contains(e.getPoint()))
				System.out.println("CounterClockwise clicked");
			if(rcClockwise.contains(e.getPoint()))
				System.out.println("Clockwise clicked");		
		}
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
