import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GamePanel extends JPanel implements MouseListener{
	BufferedImage background;
	private ArrayList<Player> players;
	private ArrayList<Habitat> unclaimedHabitats;
	private Stack<Habitat> pile1, pile2, pile3, pile4;
	public GamePanel() {
		
		try {
			background = ImageIO.read(new File("src/images/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		players = new ArrayList<>();
		unclaimedHabitats = new ArrayList<>();
		pile1 = new Stack<>();
		pile2 = new Stack<>();
		pile3 = new Stack<>();
		pile4 = new Stack<>();
		
		for (int i = 0 ; i < 14 ; i++) {
			
			for (int j = 0 ; j < new Habitat(i).getAmount() ; j++ ) {
				
				unclaimedHabitats.add(new Habitat(i));
				
			}
			
		}
		System.out.println(unclaimedHabitats);
		for (int i = 1 ; i < 4 ; i++) {
			
			players.add(new Player(i));
			
		}
		makePiles();
		addMouseListener(this);
	}
	public void makePiles() {
		
		for (Habitat h : unclaimedHabitats) {
			
			int rand = (int) (Math.floor(Math.random() * 4) + 1);
			System.out.println("AWYFSIH" + rand);
			switch (rand) {
			
				case 1:
					pile1.push(h);
					break;
				case 2:
					pile1.push(h);
					break;
				case 3:
					pile1.push(h);
					break;
				case 4:
					pile1.push(h);
					break;
				
				
			}
	
			
		}
	
	}
	public void paint(Graphics g) {
		
		
		g.drawImage(background, 0, 0, null);
		System.out.println((players.get(0).getHabitats().get(0).getBiome()));
		g.drawImage(players.get(0).getHabitats().get(0).getImg() , 250, 500, null);
		System.out.println(pile1);
		g.drawImage(pile1.peek().getImg(), 800, 80, null);

		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println( "" + e.getX() + "  " + e.getY());
		
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
