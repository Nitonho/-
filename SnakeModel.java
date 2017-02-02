import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;


public class SnakeModel implements Runnable{
	private final int M_WIDTH,M_HEIGHT;//矩阵长宽
	LinkedList<Node> nodes = new LinkedList<Node>();
	Node head;//蛇的头部
	final int d = 10;//一格的长度
	Direction dir = Direction.LEFT;
	Node food;
	boolean matrix[][];
	boolean pause = false;
	boolean running = false;
	boolean gameover = false;
	int length = 5;//蛇的长度
	GameFrame gf;
	
	public SnakeModel(GameFrame gframe){
		gf = gframe;
		M_WIDTH = gf.WIDTH / d;
		M_HEIGHT = gf.HEIGHT / d;
		head = new Node(M_WIDTH / 2,M_HEIGHT / 2);
		
		matrix = new boolean[M_HEIGHT][M_WIDTH];
		for(int i=0;i<M_HEIGHT;i++){//初始化整个矩阵
			for(int j=0;j<M_WIDTH;j++){
				matrix[i][j] = false;
			}
		}
		
		int x = head.x;
		int y = head.y;
		for(int i=0;i<length;i++){//初始化贪食蛇
			nodes.add(new Node(x,y));
			matrix[y][x] = true;
			x += 1;
		}
		
		generateFood();
	}
	
	public void move(){
		Node tmp = nodes.getFirst();
		head.x = tmp.x;
		head.y = tmp.y;
		
		switch(dir){
		case UP:
			head.y -= 1;
			break;
		case DOWN:
			head.y += 1;
			break;
		case LEFT:
			head.x -= 1;
			break;
		case RIGHT:
			head.x += 1;
			break;
		}
		
		if(head.x>=0 && head.x<M_WIDTH && head.y>=0 && head.y<M_HEIGHT){
			if(matrix[head.y][head.x]){//遇到食物或者撞到自己身体
				if(head.x==food.x && head.y==food.y){//吃了食物，增加蛇的长度
					nodes.addFirst(new Node(food.x,food.y));
					generateFood();
					length += 1;
					gf.score += 10;
				}
				else{//撞到身体
					running = false;
					gameover = true;
				}
			}
			else{//没吃到食物且没有撞到自己身体，向前移动一格
				nodes.addFirst(new Node(head.x,head.y));
				matrix[head.y][head.x] = true;
				Node last = nodes.removeLast();
				matrix[last.y][last.x] = false;
			}
		}
		else{//越界
			running = false;
			gameover = true;
		}
	}
	
	public void generateFood(){
		Random r = new Random();
		do{
			food = new Node(r.nextInt(M_WIDTH), r.nextInt(M_HEIGHT));
		}while(matrix[food.y][food.x]);
		matrix[food.y][food.x] = true;
	}
	

	@Override
	public void run() {
		running = true;
		while(running){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!pause){
				move();
				gf.repaint(nodes);
			}
		}
		if(gameover){
			JOptionPane.showMessageDialog(null, "gameover！", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
			gf.game.setEnabled(true);
		}
		
	}
	
}
