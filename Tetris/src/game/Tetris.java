package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Tetris extends JPanel{
    private static final int GRID_COLS = 10;
    private static final int GRID_ROWS = 20;
    private static final int BLOCK_SIZE = 30;
    
    private static final int PAGE_MENU = 0;
    private static final int PAGE_GAME = 1;
    private static final int PAGE_GAMEOVER = 2;
    
    private int gamePage;
    private Timer timer;
    private Timer blinkTimer;
    private Player p1;
    private Grid g1;
    private boolean gameOver = false;
    private boolean blink = true;
    
    private boolean Key_A, Key_D, Key_S, Key_BLANK, Key_C, Key_V, Key_B;
    private boolean Key_RIGHT, Key_LEFT, Key_DOWN, Key_NUM_0, Key_NUM_1, Key_NUM_2, Key_NUM_3;
    private boolean Key_R;
    
    //kick wall table
    private static final Point[][] KICK = {
    		//L
            { new Point(1,0), new Point(1,1), new Point(0,-2), new Point(1,-2) }, 	// 1 to 0
            { new Point(-1,0), new Point(-1,-1), new Point(0,2), new Point(-1,2) },// 2 to 1
            { new Point(-1,0), new Point(-1,1), new Point(0,-2), new Point(-1,-2) }, // 3 to 2
            { new Point(1,0), new Point(1,-1), new Point(0,2), new Point(1,2) },	// 0 to 3
            //R
            { new Point(-1,0), new Point(-1,1), new Point(0,-2), new Point(-1,-2) }, // 3 to 0
            { new Point(-1,0), new Point(-1,-1), new Point(0,2), new Point(-1,2) }, // 0 to 1
            { new Point(1,0), new Point(1,1), new Point(0,-2), new Point(1,-2) },	// 1 to 2
            { new Point(1,0), new Point(1,-1), new Point(0,2), new Point(1,2) },	// 2 to 3
     };
    private static final Point[][] KICK_I = {
    		//L
            { new Point(2,0), new Point(-1,0), new Point(2,-1), new Point(-1,2) }, // 1 to 0
            { new Point(1,0), new Point(-2,0), new Point(1,2), new Point(-2,-1) }, // 2 to 1
            { new Point(-2,0), new Point(1,0), new Point(-2,1), new Point(1,-2) }, // 3 to 2
            { new Point(-1,0), new Point(2,0), new Point(-1,-2), new Point(2,1) }, // 0 to 3
            //R
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
                        Key_LEFT = true; 	break;
                    case KeyEvent.VK_RIGHT:
                    	Key_RIGHT = true;	break;
                    case KeyEvent.VK_DOWN:
                    	Key_DOWN = true;	break;
                    case KeyEvent.VK_NUMPAD3:
                    	Key_NUM_3 = true;	break;
                    case KeyEvent.VK_NUMPAD2:
                    	Key_NUM_2 = true;	break;
                    case KeyEvent.VK_NUMPAD1:
                    	Key_NUM_1 = true;	break;
                    case KeyEvent.VK_NUMPAD0:
                    	Key_NUM_0 = true;   break;
                    case KeyEvent.VK_R:
                        Key_R = true;	 	break;  
                  }

                repaint();
            }
            public void keyReleased (KeyEvent e) {
            	int code = e.getKeyCode();
            	switch (code) {
			        case KeyEvent.VK_LEFT:
			            Key_LEFT = false; 	break;
			        case KeyEvent.VK_RIGHT:
			        	Key_RIGHT = false;	break;
			        case KeyEvent.VK_DOWN:
			        	Key_DOWN = false;	break;
			        case KeyEvent.VK_NUMPAD3:
			        	Key_NUM_3 = false;	break;
			        case KeyEvent.VK_NUMPAD2:
			        	Key_NUM_2 = false;	break;
			        case KeyEvent.VK_NUMPAD1:
			        	Key_NUM_1 = false;	break;
			        case KeyEvent.VK_NUMPAD0:
			        	Key_NUM_0 = false;   break;
			        case KeyEvent.VK_R:
			            Key_R = false;	 	break; 
            	}
            }
        });
        
        p1 = new Player(0,0);
        g1 = new Grid(0,0);
        gamePage = 1;
        
        timer = new Timer(15, new gravity());
        timer.start();
        blinkTimer = new Timer(500, new blink());
        blinkTimer.start();
    }
    
    private void putShape(Player p, Grid g) {
  		for (int i = 0; i < p.getShape().length; i++) {
         int x = p.getX() + p.getShape()[i].x;
         int y = p.getY() + p.getShape()[i].y;
         if (y < 0) {
            // Game over condition: shape locks above the visible grid
            timer.stop();
            setPage(PAGE_GAMEOVER);
            repaint();
            return;
         }
         g.setBGarr(x,y,p.getS());
      }
      g.clearFullLines();
      p.spawnNewShape();
      if (!isValidPosition(p, g)) {
        // New piece can't be placed
        timer.stop();
        setPage(PAGE_GAMEOVER);
        repaint();
      }
    }
    
    private void moveBlock(Player p, Grid g, int dx, int dy) {
        p.setX(p.getX() + dx);
        p.setY(p.getY() + dy);
        if (!isValidPosition(p, g)) {
        	p.setX(p.getX() - dx);
        	p.setY(p.getY() - dy);
        	if(dy > 0 && p.getLD() == 0) putShape(p, g);
        }
        else {
        	p.setLD(p.getLDS());
        	p.setTspin(false);
        }
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
        Point[] backupS = p.getShape();  //before rotation
        int backupX = p.getX();
        int backupY = p.getY();
        p.rotate(dir);					//rotate
        
        //kick wall ckeck
        if (!isValidPosition(p, g)) {			//success, not needed kick wall
        	
            //select kick wall table
            Point[][] kick_table;
            switch(p.getS()) {
            	case 1: kick_table = KICK_I; break;
            	case 2: kick_table = KICK_O; break;
            	default:kick_table = KICK;	 break;
            }
            
            //start test
        	for(Point k : kick_table[p.getD()+dir*4]) {
            	p.setX(p.getX() + k.x);
            	p.setY(p.getY() + k.y);
            	
            	if (!isValidPosition(p, g)) {	//fail, restore
                	p.setX(backupX);
                	p.setY(backupY);
                }
            	else break;						//success, break the loop
            }
        	
        	//if all kick wall fail, restore
            if (!isValidPosition(p, g)) {
            	p.setShape(backupS);
            	if(dir != 0) p.setD(p.getD()+1);
            	else p.setD(p.getD()-1);
            }
            else {						//success
            	p.setLD(p.getLDS());
            }
        }
        else {							//success
        	p.setLD(p.getLDS());
        }
    }
    
    private void gravity_drop(Player p, Grid g) {
    	if(p.getDF() > 0) {
			p.setDF(p.getDF() - 1);
		}
		if(p.getDF() == 0 || p.getSpeedUP()){
			moveBlock(p, g, 0, 1);
			p.setDF(p.getDFS());
		}
		p.setY(p.getY()+1);
		if(p.getLD() > 0 && !isValidPosition(p, g)) p.setLD(p.getLD() - 1);
		p.setY(p.getY()-1);
    }
    
    private void findShadowAndDraw(Graphics g, Player p, Grid gr) {
    	
    	int testY = 0;
    	while (true) {
            p.setY(p.getY() + 1);
            if (!isValidPosition(p, gr)) {
            	p.setY(p.getY() - 1);
                break;
            }
            else testY++;
        }
    	p.setY(p.getY() - testY);
    	p.drawShadow(g, testY);
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
    
    private void setPage(int p) {
    	gamePage = p;
    	resetKey();
    }
    
    private void resetKey() {
    	Key_LEFT = false;
    	Key_RIGHT = false;
    	Key_DOWN = false;
    	Key_NUM_0 = false;
    	Key_NUM_1 = false;
    	Key_NUM_2 = false;
    	Key_NUM_3 = false;
    	Key_R = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(gamePage) {
        
        	case PAGE_GAME:	
        		if(Key_LEFT) moveBlock(p1, g1, -1, 0); Key_LEFT = false;
            	if(Key_RIGHT) moveBlock(p1, g1, 1, 0); Key_RIGHT = false;
            	if(Key_DOWN) p1.setSpeedUP(true); else p1.setSpeedUP(false);
            	if(Key_NUM_3) rotate_and_check(p1, g1, 1); Key_NUM_3 = false;
            	if(Key_NUM_2) rotate_and_check(p1, g1, 0); Key_NUM_2 = false;
            	if(Key_NUM_1) p1.holdCurrentShape(); Key_NUM_1 = false;
            	if(Key_NUM_0) hard_drop(p1, g1); Key_NUM_0 = false;

                g1.draw(g);
                p1.draw(g);
                findShadowAndDraw(g, p1, g1);
                break;
                
        	case PAGE_GAMEOVER:
            	if(Key_R) {
                    p1 = new Player(0,0);
                    g1 = new Grid(0,0);
                    setPage(PAGE_GAME);
                    timer.start();
                    Key_R = false;
            	}
            	
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("GAME   OVER", 3 * BLOCK_SIZE + 5, GRID_ROWS * BLOCK_SIZE / 2);
                if(blink){
                   g.setFont(new Font("Arial", Font.BOLD, 20));
                   g.drawString("press \"R\" to restart", 4 * BLOCK_SIZE + 15, (GRID_ROWS+2) * BLOCK_SIZE / 2 + 30);
                }
                break;
        }
    }

    private class gravity implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		gravity_drop(p1, g1);
    		
            repaint();
        }  
    }
    private class blink implements ActionListener{
    	public void actionPerformed(ActionEvent e){
            if (gamePage == PAGE_GAMEOVER) blink = !blink;
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
        tetrisPanel.enableInputMethods(false);
    }

}