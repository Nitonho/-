import javax.swing.JFrame;

public class SnakeGame {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 450;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameFrame game = new GameFrame(WIDTH,HEIGHT);
		game.setLocation(500, 300);
		game.setSize(520,500);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setTitle("Ì°Ê³Éß");
	}

}
