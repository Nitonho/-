import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;


public class SnakeModel implements Runnable{
	private final int M_WIDTH,M_HEIGHT;//���󳤿�
	LinkedList<Node> nodes = new LinkedList<Node>();
	Node head;//�ߵ�ͷ��
	final int d = 10;//һ��ĳ���
	Direction dir = Direction.LEFT;
	Node food;
	boolean matrix[][];
	boolean pause = false;
	boolean running = false;
	boolean gameover = false;
	int length = 5;//�ߵĳ���
	GameFrame gf;
	
	public SnakeModel(GameFrame gframe){
		gf = gframe;
		M_WIDTH = gf.WIDTH / d;
		M_HEIGHT = gf.HEIGHT / d;
		head = new Node(M_WIDTH / 2,M_HEIGHT / 2);
		
		matrix = new boolean[M_HEIGHT][M_WIDTH];
		for(int i=0;i<M_HEIGHT;i++){//��ʼ����������
			for(int j=0;j<M_WIDTH;j++){
				matrix[i][j] = false;
			}
		}
		
		int x = head.x;
		int y = head.y;
		for(int i=0;i<length;i++){//��ʼ��̰ʳ��
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
			if(matrix[head.y][head.x]){//����ʳ�����ײ���Լ�����
				if(head.x==food.x && head.y==food.y){//����ʳ������ߵĳ���
					nodes.addFirst(new Node(food.x,food.y));
					generateFood();
					length += 1;
					gf.score += 10;
				}
				else{//ײ������
					running = false;
					gameover = true;
				}
			}
			else{//û�Ե�ʳ����û��ײ���Լ����壬��ǰ�ƶ�һ��
				nodes.addFirst(new Node(head.x,head.y));
				matrix[head.y][head.x] = true;
				Node last = nodes.removeLast();
				matrix[last.y][last.x] = false;
			}
		}
		else{//Խ��
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
			JOptionPane.showMessageDialog(null, "gameover��", "��Ϸ����", JOptionPane.INFORMATION_MESSAGE);
			gf.game.setEnabled(true);
		}
		
	}
	
}
