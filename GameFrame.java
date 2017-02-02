import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;

public class GameFrame extends JFrame implements KeyListener{
	public final int WIDTH;//游戏区域大小
	public final int HEIGHT;
	int score = 0;
	JPanel body;
	JMenuBar bar;
	JMenu game;
	JMenuItem start;
	JLabel scoreLabel;
	SnakeModel snake;
	
	public GameFrame(int w, int h){
		WIDTH = w;
		HEIGHT = h - 50;
		
		bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		game = new JMenu("game");
		bar.add(game);
		start = new JMenuItem("开始游戏");
		game.add(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				begin();
				
			}
		});
		
		JPanel message = new JPanel();
		scoreLabel = new JLabel("Score: "+score);
		JLabel tipsLabel = new JLabel("press 'R' to restart");
		message.add(scoreLabel,BorderLayout.CENTER);
		message.add(tipsLabel,BorderLayout.SOUTH);
		add(message,BorderLayout.SOUTH);
		
		body = new JPanel();
		add(body,BorderLayout.CENTER);
		body.setFocusable(true);
		body.addKeyListener(this);
		
//		repaint();
	}
	
	private void begin(){
		score = 0;
		snake = new SnakeModel(this);
		new Thread(snake).start();
		game.setEnabled(false);
	}
	
	public void repaint(LinkedList<Node> nodes){
		Graphics g = body.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.RED);
		g.fillRect(snake.food.x * snake.d, snake.food.y* snake.d, snake.d, snake.d);
		
		g.setColor(Color.BLACK);
		Iterator<Node> it = nodes.iterator();
		while(it.hasNext()){
			Node node = it.next();
			int x = node.x * snake.d;
			int y = node.y * snake.d;
			g.fillRect(x, y, snake.d-1, snake.d-1);
		}
		
		modifyScore();
	}

	private void modifyScore() {
		scoreLabel.setText("Score: "+score);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(snake.running){
			switch(keyCode){
			case KeyEvent.VK_UP: 
				if(snake.dir!=Direction.UP && snake.dir!=Direction.DOWN){
					snake.dir = Direction.UP;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(snake.dir!=Direction.UP && snake.dir!=Direction.DOWN){
					snake.dir = Direction.DOWN;
				}
				break;
			case KeyEvent.VK_LEFT:
				if(snake.dir!=Direction.LEFT && snake.dir!=Direction.RIGHT){
					snake.dir = Direction.LEFT;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(snake.dir!=Direction.LEFT && snake.dir!=Direction.RIGHT){
					snake.dir = Direction.RIGHT;
				}
				break;
			case KeyEvent.VK_R:
				snake.running = false;
				begin();
				break;
			case KeyEvent.VK_SPACE:
				snake.pause = !snake.pause;
				break;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}
