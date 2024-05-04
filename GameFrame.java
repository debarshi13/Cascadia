















import java.awt.*;
import javax.swing.*;
public class GameFrame extends JFrame{
	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	public GameFrame(String framename) {
		
		super(framename);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		add(new GamePanel());
		// GamePanel gPanel = new GamePanel();
		// gPanel.setPreferredSize(new Dimension(2160, 1200));
		// gPanel.setLayout(new BorderLayout());

		// JScrollPane scrollerPane = new JScrollPane( gPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scrollerPane.setPreferredSize(new Dimension(1920, 1080));

		// add(scrollerPane);
		// pack();
		// setLocationRelativeTo(null);
		setVisible(true);
		
	}
}
