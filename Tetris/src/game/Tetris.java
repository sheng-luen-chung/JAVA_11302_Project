package game;

import javax.swing.*;
import page.*;
import java.awt.*;
import java.awt.event.*;


public class Tetris extends JPanel{
    public static final int GRID_COLS = 10;
    public static final int GRID_ROWS = 20;
    public static final int BLOCK_SIZE = 30;
    
    public static final int PAGE_MENU = 0;
    public static final int PAGE_INGAME = 1;
    public static final int PAGE_GAMEOVER = 2;
    
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel mainPanel = new JPanel(cardLayout);
    
    //page
    private static menu menuPanel;
    private static ingame ingamePanel;
    private static gameover gameoverPanel;
    
    public static int gamePage;
    public static int score;
    
    public static boolean Key_A;
    public static boolean Key_D;
    public static boolean Key_S;
    public static boolean Key_BLANK;
    public static boolean Key_C;
    public static boolean Key_V;
    public static boolean Key_B;
    
    public static boolean Key_RIGHT;
	public static boolean Key_LEFT;
	public static boolean Key_DOWN;
	public static boolean Key_NUM_0;
	public static boolean Key_NUM_1;
	public static boolean Key_NUM_2;
	public static boolean Key_NUM_3;
	
    public static boolean Key_R;
    
    //kick wall table
    public static final Point[][] KICK = {
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
    public static final Point[][] KICK_I = {
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
    public static final Point[][] KICK_O = {
            {}
     };
    
    public Tetris() {
        setPreferredSize(new Dimension((GRID_COLS+5) * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE));
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
	        menuPanel = new menu();
	        mainPanel.add(menuPanel, "MENU");
	        
			ingamePanel = new ingame();
			mainPanel.add(ingamePanel, "INGAME");
			
			gameoverPanel = new gameover();
			mainPanel.add(gameoverPanel, "GAMEOVER");
		
    	add(mainPanel);
        setLayout(new CardLayout());
        setPage(PAGE_MENU);   
    }
    
    public static void putShape(Player p, Grid g) {
  		for (int i = 0; i < p.getShape().length; i++) {
         int x = p.getX() + p.getShape()[i].x;
         int y = p.getY() + p.getShape()[i].y;
         if (y < 0) {
            // Game over condition: shape locks above the visible grid
            setPage(PAGE_GAMEOVER);
            return;
         }
         g.setBGarr(x,y,p.getS());
      }
      g.clearFullLines();
      p.spawnNewShape();
      if (!isValidPosition(p, g)) {
        // New piece can't be placed
        setPage(PAGE_GAMEOVER);
      }
    }
    
    public static void moveBlock(Player p, Grid g, int dx, int dy) {
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

    public static void hard_drop(Player p, Grid g) {
        while (true) {
            p.setY(p.getY() + 1);
            if (!isValidPosition(p, g)) {
            	p.setY(p.getY() - 1);
            	putShape(p, g);
                break;
            }
        }
    }

    public static void rotate_and_check(Player p, Grid g, int dir) {
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
    
    public static void gravity_drop(Player p, Grid g) {
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
    
    public static void findShadowAndDraw(Graphics g, Player p, Grid gr) {	
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
    
    public static boolean isValidPosition(Player p, Grid g) {
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
    
    public static void setPage(int p) {
    	switch(gamePage) {
    		case PAGE_MENU:
    			menuPanel.stopPanel();
    			break;
			case PAGE_INGAME:
				ingamePanel.stopPanel();
				break;
			case PAGE_GAMEOVER:
				gameoverPanel.stopPanel();
				break;
    	} 
    	
    	switch(p) {
    		case PAGE_MENU:
    			cardLayout.show(mainPanel, "MENU");
				menuPanel.startPanel();
				break;
    		case PAGE_INGAME:
    			cardLayout.show(mainPanel, "INGAME");
    			ingamePanel.startPanel();
    			break;
    		case PAGE_GAMEOVER:
    			cardLayout.show(mainPanel, "GAMEOVER");
    			gameoverPanel.startPanel();
    			break;
    	}
    	mainPanel.revalidate();
    	gamePage = p;
    	resetKey();
    }
    
    private static void resetKey() {
    	Key_LEFT = false;
    	Key_RIGHT = false;
    	Key_DOWN = false;
    	Key_NUM_0 = false;
    	Key_NUM_1 = false;
    	Key_NUM_2 = false;
    	Key_NUM_3 = false;
    	Key_R = false;
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