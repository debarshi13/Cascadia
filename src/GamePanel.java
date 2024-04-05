import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel{
	BufferedImage background;
	private ArrayList<Player> players;
	private ArrayList<Habitat> unclaimedHabitats;
	public GamePanel() {
		
		try {
			background = ImageIO.read(new File("src/images/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		players = new ArrayList<>();
		unclaimedHabitats = new ArrayList<>();
		for (int i = 0 ; i < 14 ; i++) {
			
			unclaimedHabitats.add(new Habitat(i));
			
		}
		for (int i = 0 ; i < 3 ; i++) {
			
			players.add(new Player());
			
		}
		System.out.println(unclaimedHabitats.get(1).getBiome());
		System.out.println(unclaimedHabitats.get(4).getBiome()); //to test
	}
	public void paint(Graphics g) {
		
		g.drawImage(background, 0, 0, null);

		
	}
}
