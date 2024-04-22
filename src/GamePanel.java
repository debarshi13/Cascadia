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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GamePanel extends JPanel implements MouseListener{
	BufferedImage background;
	private ArrayList<Player> players;
	private ArrayList<Habitat> pile1, pile2, pile3, pile4;
	private Polygon hexagonTest;
	private Tiles tiles;
	private Wildlife animals;
	private ArrayList<Tile> tilesOnTable;
	private ArrayList<String> animalsOnTable;
	private int gameStatus = 0;
	private Font font = new Font("Arial", Font.BOLD, 24);
	int activePlayerNum = 0;
	FontMetrics metrics;
	BufferedImage forestTileImage = null, lakeTileImage =  null ,swampTileImage = null;
	BufferedImage lakeMountainTileImage = null, mountainDesertTileImage =  null ,mountainForestTileImage = null;
	BufferedImage desertTileImage = null, desertLakeTileImage =  null ,desertSwampTileImage = null;
	BufferedImage forestDesertTileImage = null, forestLakeTileImage =  null ,forestSwampTileImage = null;
	BufferedImage mountainTileImage = null, mountainSwampTileImage =  null ,starterTile1 = null;
	BufferedImage swampLakeTileImage = null;
	BufferedImage elkImage  = null, foxImage = null, hawkImage = null, salmonImage = null, bearImage = null;
	BufferedImage bearScoreImage = null, elkScoreImage = null, foxScoreImage = null, hawkScoreImage = null, salmonScoreImage = null;

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


		try {
			background = ImageIO.read(new File("src/images/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		players = new ArrayList<>();
		for (int i = 1 ; i < 4 ; i++) {
			
			players.add(new Player(i, 0));
			
		}
		tiles = new Tiles();
		//Collections.shuffle(tiles.getStartingTiles());
		Collections.shuffle(tiles.getTiles());
		animals =  new Wildlife();
		Collections.shuffle(animals.getWildlife());

		tilesOnTable = new ArrayList<Tile>();
		animalsOnTable = new ArrayList<String>();
		// for(int i = 0; i < 10; i++){
		// 	System.out.println("animal: " + animals.getWildlife().get(i));
		// }
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
			double ang30 = Math.toRadians(30);
			int radius = 57;
			double xOff = Math.cos(ang30) * (radius +0.3);
			double yOff = Math.sin(ang30) * (radius +0.3);
			Point origin = new Point (133, 136);

			int x = (int) (origin.x + (0%2)*xOff + 2*7*xOff -xOff);
			int y = (int) (origin.y + 3*yOff*0) -radius;
			int x2 = (int) (origin.x + (1%2)*xOff + 2*6*xOff -xOff);
			int y2 = (int) (origin.y + 3*yOff*1) -radius;
			int x3 = (int) (origin.x + (1%2)*xOff + 2*7*xOff -xOff);
			int y3 = (int) (origin.y + 3*yOff*1) -radius;

			g.drawImage(forestTileImage, x, y, null);
			g.drawImage(lakeTileImage, x2, y2, null);
			g.drawImage(swampTileImage, x3, y3, null);
		    g.drawImage(bearScoreImage, 10, 200, 160, 110, null);
			g.drawImage(elkScoreImage, 10, 320, 160, 110, null);
			g.drawImage(foxScoreImage, 10, 440, 160, 110, null);
			g.drawImage(hawkScoreImage, 10, 560, 160, 110, null);
			g.drawImage(salmonScoreImage, 10, 680, 160, 110, null);
			//paingBackgroundGrid(g, radius);

			int x4 = (int) (origin.x + (0%2)*xOff + 2*9*xOff -xOff);
			int y4 = (int) (origin.y + 3*yOff*2) -radius;


			// try rotate image
			g.drawImage(lakeMountainTileImage, x4, y4, null);
			int x5 = (int) (origin.x + (1%2)*xOff + 2*8*xOff -xOff);
			int y5 = (int) (origin.y + 3*yOff*3) -radius;
			double locationX = lakeMountainTileImage.getWidth() / 2;
			double locationY = lakeMountainTileImage.getHeight() / 2;
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform identity = AffineTransform.getRotateInstance(Math.toRadians(120), locationX, locationY);		
			AffineTransformOp op = new AffineTransformOp(identity, AffineTransformOp.TYPE_BILINEAR);
			g2d.drawImage(op.filter(lakeMountainTileImage, null), x5, y5, null);

			for(int i = 0; i <  tilesOnTable.size(); i++){
				Tile t = tilesOnTable.get(i);
				System.out.println(t.getTileNum() + " with habitats size of " + t.getHabitats().size());
				for(int j = 0; j < t.getHabitats().size(); j++)
					System.out.println(t.getHabitats().get(j));
			}
		}


		for(int i = 0; i < tilesOnTable.size(); i++){
			ArrayList<String> habitat = tilesOnTable.get(i).getHabitats();
			String imgName = "";
			
			for(int j = 0; j < habitat.size(); j++){
				if(j == 0)
					imgName += habitat.get(j);
				else
					imgName += ("+" + habitat.get(j));
			}
			System.out.println("Will load this image file: " + imgName);
			BufferedImage img = getImage(imgName);
			g.drawImage(img, 250 + i * 120, getHeight() - 250, 100, 100, null);
		}
		for(int i = 0; i < animalsOnTable.size(); i++){
			System.out.println("Should get an image of " + animalsOnTable.get(i));
			BufferedImage img = getImage(animalsOnTable.get(i));
			g.drawImage(img, 255 + i * 120, getHeight() - 150, 80, 80, null);
		}

		
	}


	public void drawStartingTiles(Graphics g)
	{

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
			if(hexagonTest.contains(e.getPoint()))
			System.out.println("debarshi is a stinky indian");
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
