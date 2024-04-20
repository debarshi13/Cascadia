import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class GamePanel extends JPanel implements MouseListener{
	BufferedImage background;
	private ArrayList<Player> players;
	private ArrayList<Habitat> unclaimedHabitats;
	private ArrayList<Habitat> pile1, pile2, pile3, pile4;
	private Polygon hexagonTest;
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
		Collections.shuffle(pile1, new Random(1253));
		Collections.shuffle(pile2, new Random(1232));
		Collections.shuffle(pile3, new Random(551252135));
		Collections.shuffle(pile4, new Random(512341255));
		
	
	}
	public void paint(Graphics g) {
		
		
		g.drawImage(background, 0, 0, null);
		//System.out.println((players.get(0).getHabitats().get(0).getBiome()));
		g.drawImage(players.get(0).getHabitats().get(0).getImg() , 250, 500, null);

		//System.out.println(pile1);
		g.drawImage(pile1.get(pile1.size() - 1).getImg(), 800, 80, null);
		
		
		g.drawPolygon(hexagonTest);
		// for(int i = 0; i < pile1.size(); i++) {
		// 	g.drawImage(pile1.get(pile1.size() - 1).getImg(), 800, 80, null);
		// }
		
		
		
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
