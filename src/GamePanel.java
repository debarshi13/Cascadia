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
	private ArrayList<Habitat> unclaimedHabitats;
	private ArrayList<Habitat> pile1, pile2, pile3, pile4;
	private Polygon hexagonTest;
	private Font font = new Font("Arial", Font.BOLD, 18);
	FontMetrics metrics;
	public GamePanel() {
		
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
		unclaimedHabitats = new ArrayList<>();
		pile1 = new ArrayList<>();
		pile2 = new ArrayList<>();
		pile3 = new ArrayList<>();
		pile4 = new ArrayList<>();
		
		for (int i = 1 ; i < 13 ; i++) {
			
			for (int j = 0 ; j < new Habitat(i).getAmount() ; j++ ) {
				
				unclaimedHabitats.add(new Habitat(i));
				
			}
			
		}
		//System.out.println(unclaimedHabitats);
		for (int i = 1 ; i < 4 ; i++) {
			
			players.add(new Player(i));
			
		}
		makePiles();
		addMouseListener(this);
	}
	public void makePiles() {
		
		for (Habitat h : unclaimedHabitats) {
			
			int rand = (int) (Math.floor(Math.random() * 4) + 1); //change floor to round after done testing
			//System.out.println("AWYFSIH" + rand);
			switch (rand) {
			
				case 1:
					pile1.add(h);
					System.out.println(h);
					break;
				case 2:
					pile2.add(h);
					break;
				case 3:
					pile3.add(h);
					break;
				case 4:
					pile4.add(h);
					break;
				
				
			}
					
		}
		// Collections.shuffle(pile1, new Random(1253));
		// Collections.shuffle(pile2, new Random(1232));
		// Collections.shuffle(pile3, new Random(551252135));
		// Collections.shuffle(pile4, new Random(512341255));
		
	
	}
	public void paint(Graphics g) {
		
		
		g.drawImage(background, 0, 0, null);
		//System.out.println((players.get(0).getHabitats().get(0).getBiome()));
		g.drawImage(players.get(0).getHabitats().get(0).getImg() , 250, 500, null);

		//System.out.println(pile1);
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
	
		BufferedImage forestTileImage = null, lakeTileImage =  null ,swampTileImage = null;
		BufferedImage lakeMountainTileImage = null, mountainDesertTileImage =  null ,mountainForestTileImage = null;

		try {
			forestTileImage = ImageIO.read(new File("src/images/forest.png"));
			lakeTileImage = ImageIO.read(new File("src/images/lake.png"));
			swampTileImage = ImageIO.read(new File("src/images/swamp.png"));
			lakeMountainTileImage = ImageIO.read(new File("src/images/lake+mountain.png"));
			mountainDesertTileImage = ImageIO.read(new File("src/images/mountain+desert.png"));
			mountainForestTileImage = ImageIO.read(new File("src/images/mountain+forest.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(forestTileImage, x, y, null);
		g.drawImage(lakeTileImage, x2, y2, null);
		g.drawImage(swampTileImage, x3, y3, null);

		
		//g.drawPolygon(hexagonTest);
		// for(int i = 0; i < pile1.size(); i++) {
		// 	g.drawImage(pile1.get(pile1.size() - 1).getImg(), 800, 80, null);
		// }
		
		paingBackgroundGrid(g, radius);

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


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println( "" + e.getX() + "  " + e.getY());
		if(hexagonTest.contains(e.getPoint()))
		System.out.println("debarshi is a stinky indian");
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
