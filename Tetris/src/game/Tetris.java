package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Tetris extends JPanel{
    private static final int GRID_COLS = 10;
    private static final int GRID_ROWS = 20;
    private static final int BLOCK_SIZE = 30;
    private Timer timer;
    private Player p1;
    private Grid g1;
    
    //踢牆表(I與O分開處理，其他形狀共用)
    private static final Point[][] KICK = {
    		//逆
            { new Point(1,0), new Point(1,1), new Point(0,-2), new Point(1,-2) }, 	// 1 to 0
            { new Point(-1,0), new Point(-1,-1), new Point(0,2), new Point(-1,2) },// 2 to 1
            { new Point(-1,0), new Point(-1,1), new Point(0,-2), new Point(-1,-2) }, // 3 to 2
            { new Point(1,0), new Point(1,-1), new Point(0,2), new Point(1,2) },	// 0 to 3
            //順
            { new Point(-1,0), new Point(-1,1), new Point(0,-2), new Point(-1,-2) }, // 3 to 0
            { new Point(-1,0), new Point(-1,-1), new Point(0,2), new Point(-1,2) }, // 0 to 1
            { new Point(1,0), new Point(1,1), new Point(0,-2), new Point(1,-2) },	// 1 to 2
            { new Point(1,0), new Point(1,-1), new Point(0,2), new Point(1,2) },	// 2 to 3
     };
    private static final Point[][] KICK_I = {
    		//逆
            { new Point(2,0), new Point(-1,0), new Point(2,-1), new Point(-1,2) }, // 1 to 0
            { new Point(1,0), new Point(-2,0), new Point(1,2), new Point(-2,-1) }, // 2 to 1
            { new Point(-2,0), new Point(1,0), new Point(-2,1), new Point(1,-2) }, // 3 to 2
            { new Point(-1,0), new Point(2,0), new Point(-1,-2), new Point(2,1) }, // 0 to 3
            //順
            { new Point(1,0), new Point(-2,0), new Point(1,2), new Point(-2,-1) }, // 3 to 0
            { new Point(-2,0), new Point(1,0), new Point(-2,1), new Point(1,-2) }, // 0 to 1
            { new Point(-1,0), new Point(2,0), new Point(-1,-2), new Point(2,1) }, // 1 to 2
            { new Point(2,0), new Point(-1,0), new Point(2,-1), new Point(-1,2) },	// 2 to 3
     };
    private static final Point[][] KICK_O = {
            {}
     };
    
    public Tetris() {
        setPreferredSize(new Dimension((GRID_COLS+5) * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_LEFT:
                        moveBlock(p1, g1, -1, 0); 	break;
                    case KeyEvent.VK_RIGHT:
                        moveBlock(p1, g1, 1, 0);	break;
                    case KeyEvent.VK_DOWN:
                        moveBlock(p1, g1, 0, 1);	break;
                    case KeyEvent.VK_NUMPAD3:
                    	rotate_and_check(p1, g1, 1);	break;
                    case KeyEvent.VK_NUMPAD2:
                    	rotate_and_check(p1, g1, 0);	break;
                    case KeyEvent.VK_NUMPAD1:
                    	p1.holdCurrentShape();		break;
                    case KeyEvent.VK_NUMPAD0:
                    	hard_drop(p1, g1);      	break;
                    
                }
                repaint();
            }
        });
        
        p1 = new Player(0,0);
        g1 = new Grid(0,0);
        
        timer = new Timer(500, new gravity());
        timer.start();
    }
    private void putShape(Player p, Grid g) {
  		for (int i = 0; i < p.getShape().length; i++) {
              g.setBGarr(p.getX() + p.getShape()[i].x,
            		  	 p.getY() + p.getShape()[i].y,
            		  	 p.getS());
         }
        p.spawnNewShape();
    }
    
    private void moveBlock(Player p, Grid g, int dx, int dy) {
        p.setX(p.getX() + dx);
        if (!isValidPosition(p, g)) {
        	p.setX(p.getX() - dx);
        }
        
        p.setY(p.getY() + dy);
        if (!isValidPosition(p, g)) {
        	p.setY(p.getY() - dy);
            putShape(p1, g1);
        }
        else p.setTspin(false);
    }

    private void hard_drop(Player p, Grid g) {
        while (true) {
            p.setY(p.getY() + 1);
            if (!isValidPosition(p, g)) {
            	p.setY(p.getY() - 1);
            	putShape(p, g);
                break;
            }
        }
    }

    private void rotate_and_check(Player p, Grid g, int dir) {
        Point[] backupS = p.getShape();  //旋轉前備份
        int backupX = p.getX();
        int backupY = p.getY();
        p.rotate(dir);					//旋轉
        
        /*踢牆偵測，持續測試踢牆表中的偏移量直到成功
        或無法成功踢牆時復原*/
        if (!isValidPosition(p, g)) {			//第一次無偏移，成功則略過踢牆判定
        	
            //判斷並套用哪個踢牆表
            Point[][] kick_table;
            switch(p.getS()) {
            	case 1: kick_table = KICK_I; break;
            	case 2: kick_table = KICK_O; break;
            	default:kick_table = KICK;	 break;
            }
            
            //開始測試踢牆表中的偏移量
        	for(Point k : kick_table[p.getD()+dir*4]) {
            	p.setX(p.getX() + k.x);
            	p.setY(p.getY() + k.y);
            	
            	if (!isValidPosition(p, g)) {	//踢牆失敗，還原
                	p.setX(backupX);
                	p.setY(backupY);
                }
            	else break;						//踢牆成功，跳出迴圈
            }
        	
        	//如果測試完結果是無效位置，踢牆失敗:還原到旋轉前
            if (!isValidPosition(p, g)) {
            	p.setShape(backupS);
            	if(dir != 0) p.setD(p.getD()+1);
            	else p.setD(p.getD()-1);
            }
        }
    }

    private boolean isValidPosition(Player p, Grid g) {
        for (Point po : p.getShape()) {
            int x = p.getX() + po.x;
            int y = p.getY() + po.y;
            if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS) {
                return false;
            }
            if (g.getBGarr()[x][y] != 0) {
            	return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //劃格線
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= GRID_COLS; x++)
            g.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE);
        for (int y = 0; y <= GRID_ROWS; y++)
            g.drawLine(0, y * BLOCK_SIZE, GRID_COLS * BLOCK_SIZE, y * BLOCK_SIZE);

        //畫玩家/背景方塊
        g1.draw(g);
        p1.draw(g);
    }

    private class gravity implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		moveBlock(p1, g1, 0, 1);
    		
            repaint();
        }  
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        Tetris tetrisPanel = new Tetris();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tetrisPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tetrisPanel.requestFocus();  
    }

}