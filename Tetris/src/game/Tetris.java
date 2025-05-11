package game;

import javax.swing.*;
import page.*;
import java.awt.*;
import java.awt.event.*;


public class Tetris extends JPanel{
	public static final int BLOCK_SIZE = 30;
	public static final int TOTAL_SIZE_X = 34 * BLOCK_SIZE;
	public static final int TOTAL_SIZE_Y = 24 * BLOCK_SIZE;
    public static final int GRID_COLS = 10;
    public static final int GRID_ROWS = 20;
    
    public static final int PAGE_MENU = 0;
    public static final int PAGE_MODE = 1;
    public static final int PAGE_CLASSIC = 2;
    
    public static final int PAGE_GRAVITY = 4;
    public static final int PAGE_GAMEOVER = 5;
    
    public static final int PAGE_INSTRUCTIONS = 7;
    
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel mainPanel = new JPanel(cardLayout);
    
    //page
    public static menu menuPanel;
    public static mode modePanel;
    public static classic classicPanel;
    
    public static gravity gravityPanel;
    public static gameover gameoverPanel;
    
    public static instructions instructionsPanel;

    
    public static int gamePage;
    public static int score;
    public static int overReturn;
    
    //key
    public static boolean Key_A;
    public static boolean Key_D;
    public static boolean Key_S;
    public static boolean Key_SPACE;
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
    
    //Back Ground Music
    public static MusicPlayer bgMusic;
    //Effects
    public static MusicPlayer claerLine_effect;
    
    //Path
    public static final String bgMusic_path = "resources/BGM.wav";
    public static final String claerLine_effect_path = "resources/claerLine_effect.wav";
    
    
    
    public Tetris() {
        setPreferredSize(new Dimension(TOTAL_SIZE_X, TOTAL_SIZE_Y));
        setFocusable(true); 
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
	                case KeyEvent.VK_A:			Key_A = true; 	break;
	                case KeyEvent.VK_D:			Key_D = true;	break;
	                case KeyEvent.VK_S:			Key_S = true;	break;
	                case KeyEvent.VK_SPACE:		Key_SPACE = true;	break;
	                case KeyEvent.VK_B:			Key_B = true;	break;
	                case KeyEvent.VK_V:			Key_V = true;	break;
	                case KeyEvent.VK_C:			Key_C = true;   break;
	                
                    case KeyEvent.VK_LEFT:		Key_LEFT = true; 	break;
                    case KeyEvent.VK_RIGHT:		Key_RIGHT = true;	break;
                    case KeyEvent.VK_DOWN:		Key_DOWN = true;	break;
                    case KeyEvent.VK_NUMPAD3:	Key_NUM_3 = true;	break;
                    case KeyEvent.VK_NUMPAD2:	Key_NUM_2 = true;	break;
                    case KeyEvent.VK_NUMPAD1:	Key_NUM_1 = true;	break;
                    case KeyEvent.VK_NUMPAD0:	Key_NUM_0 = true;   break;
                    
                    case KeyEvent.VK_R:			Key_R = true;	 	break;  
                  }

                repaint();
            }
            public void keyReleased (KeyEvent e) {
            	int code = e.getKeyCode();
            	switch (code) {
	            	case KeyEvent.VK_A:			Key_A = false; 	break;
	                case KeyEvent.VK_D:			Key_D = false;	break;
	                case KeyEvent.VK_S:			Key_S = false;	break;
	                case KeyEvent.VK_SPACE:		Key_SPACE = false;	break;
	                case KeyEvent.VK_B:			Key_B = false;	break;
	                case KeyEvent.VK_V:			Key_V = false;	break;
	                case KeyEvent.VK_C:			Key_C = false;   break;
	                
			        case KeyEvent.VK_LEFT:		Key_LEFT = false; 	break;
			        case KeyEvent.VK_RIGHT:		Key_RIGHT = false;	break;
			        case KeyEvent.VK_DOWN:		Key_DOWN = false;	break;
			        case KeyEvent.VK_NUMPAD3:	Key_NUM_3 = false;	break;
			        case KeyEvent.VK_NUMPAD2:	Key_NUM_2 = false;	break;
			        case KeyEvent.VK_NUMPAD1:	Key_NUM_1 = false;	break;
			        case KeyEvent.VK_NUMPAD0:	Key_NUM_0 = false;   break;
			        
			        case KeyEvent.VK_R:			Key_R = false;	 	break; 
            	}
            }
        });
        createPages();
        setPage(PAGE_MENU);   
    }
    
    private void createPages() {
        menuPanel = new menu();
        mainPanel.add(menuPanel, "MENU");
        
        modePanel = new mode();
		mainPanel.add(modePanel, "MODE");
		
		classicPanel = new classic();
		mainPanel.add(classicPanel, "CLASSIC");
		
		gravityPanel = new gravity();
		mainPanel.add(gravityPanel, "GRAVITY");
		
		gameoverPanel = new gameover();
		mainPanel.add(gameoverPanel, "GAMEOVER");
		
		instructionsPanel = new instructions();
		mainPanel.add(instructionsPanel, "INSTRUCTIONS");

		this.add(mainPanel);
	    setLayout(new CardLayout());
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
  		  //calculating score
	  	  int T_spin = 0;	//0=none, 1=Mini T-Spin, 2=T-Spin
	  	  int linesCleared = 0;
	  	  
	  	  if(p.getS() == 3 && p.getTspin()) T_spin = detect_T_spin(p, g);
	      linesCleared = g.clearFullLines();
	      g.calcuateScore(linesCleared, T_spin);
      
	      //new Shape
	      p.spawnNewShape();
	      if (!isValidPosition(p, g)) {
	        // New piece can't be placed
	        setPage(PAGE_GAMEOVER);
	      }
    }
    
    public static int detect_T_spin(Player p, Grid g) {	//return 0=none, 1=Mini T-Spin, 2=T-Spin
    	int x=0;
    	int y=0;
    	int solid_count = 0;
    	int vertex_side_count=0;
    	
    	x = p.getX() + 0;
    	y = p.getY() + 0;
    	if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS || g.getBGarr()[x][y] != 0) {
    		solid_count++;
    		if(p.getD() == 0 || p.getD() == 3) vertex_side_count++;
        }
    	
    	x = p.getX() + 2;
    	y = p.getY() + 0;
    	if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS || g.getBGarr()[x][y] != 0) {
    		solid_count++;
    		if(p.getD() == 0 || p.getD() == 1) vertex_side_count++;
        }
    	
    	x = p.getX() + 2;
    	y = p.getY() + 2;
    	if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS || g.getBGarr()[x][y] != 0) {
    		solid_count++;
    		if(p.getD() == 1 || p.getD() == 2) vertex_side_count++;
        }
    	
    	x = p.getX() + 0;
    	y = p.getY() + 2;
    	if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS || g.getBGarr()[x][y] != 0) {
    		solid_count++;
    		if(p.getD() == 2 || p.getD() == 3) vertex_side_count++;
        }
    	
    	if(solid_count >= 3) {
    		if(vertex_side_count == 2 || p.getLK()) {
    			return 2;
    		}
    		return 1;
    	}
    	else return 0;
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
        	p.setLK(false);
        	if(dy > 0 && p.getSpeedUP())g.setScore(g.getScore() + 1);
        }
    }

    public static void hard_drop(Player p, Grid g) {
    	int addScore = 0;
        while (true) {
            p.setY(p.getY() + 1);
            addScore += 2;
            if (!isValidPosition(p, g)) {
            	p.setY(p.getY() - 1);
            	addScore -= 2;
            	putShape(p, g);
                break;
            }
        }
        if(addScore > 0) p.setTspin(false);
        g.setScore(g.getScore() + addScore);
    }

    public static void rotate_and_check(Player p, Grid g, int dir) {
        Point[] backupS = p.getShape();  //before rotation
        int backupX = p.getX();
        int backupY = p.getY();
        int kick_count = 0;
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
            	kick_count++;
            	
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
            	if(p.getS() == 3) p.setTspin(true);	//T-spin
            }
        }
        else {							//success
        	p.setLD(p.getLDS());
        	if(p.getS() == 3) p.setTspin(true);	//T-spin
        }
        if(kick_count == 3) p.setLK(true);
    }
    
    public static void gravity_drop(Player p, Grid g) {
    	if(p.getARE() > 0) p.setARE(p.getARE() - 1);
    	else if(p.getDF() >= 1) p.setDF(p.getDF() - 1);
    	
		if((p.getDF() < 1 || p.getSpeedUP()) && p.getARE() == 0){
			if(p.getDF() < 1 && p.getDF() != 0) {
				p.setY(p.getY() + 1);
	            if (isValidPosition(p, g)) {
	            	p.setY(p.getY() - 1);
	            	for(int i=0; i < (int)(1/p.getDF())-1; i++) {
						moveBlock(p, g, 0, 1);
					}
	            }
	            else p.setY(p.getY() - 1);
	            moveBlock(p, g, 0, 1);
			}
			else moveBlock(p, g, 0, 1);
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
    
    public int putGarbageLines(Player p, Grid g, int l) {
    	p.setY(p.getY() - 1);
  	    for(int y = 0; y < GRID_ROWS - 1; y++) {
  	    	for(int x = 0; x < GRID_COLS; x++) {
  	    	    g.setBGarr(x, y, g.getBGarr()[x][y+1]);
  		    }
	    }
  	    for(int x = 0; x < GRID_COLS; x++) {
  		    g.setBGarr(x, 19, 8);
  	    }
  	    return l-1;
    }
    
    public static void setPage(int p) {
    	switch(gamePage) {
    		case PAGE_MENU: menuPanel.stopPanel(); break;
    		case PAGE_MODE: modePanel.stopPanel(); break;
			case PAGE_CLASSIC: classicPanel.stopPanel(); break;
			
			case PAGE_GRAVITY: gravityPanel.stopPanel(); break;
			case PAGE_GAMEOVER: gameoverPanel.stopPanel(); break;
			
			case PAGE_INSTRUCTIONS: instructionsPanel.stopPanel(); break;
    	} 
    	
    	switch(p) {
    		case PAGE_MENU: menuPanel.startPanel(); break;
    		case PAGE_MODE: modePanel.startPanel(); break;
    		case PAGE_CLASSIC: classicPanel.startPanel(); break;
    		
    		case PAGE_GRAVITY: gravityPanel.startPanel(); break;
    		case PAGE_GAMEOVER: gameoverPanel.startPanel(); break;
    		
    		case PAGE_INSTRUCTIONS: instructionsPanel.startPanel(); break;
    	}
    	mainPanel.revalidate();
    	gamePage = p;
    	resetKey();
    }
    
    private static void resetKey() {
    	Key_A = false;
    	Key_D = false;
    	Key_S = false;
    	Key_SPACE = false;
    	Key_B = false;
    	Key_V = false;
    	Key_C = false;
    	Key_LEFT = false;
    	Key_RIGHT = false;
    	Key_DOWN = false;
    	Key_NUM_0 = false;
    	Key_NUM_1 = false;
    	Key_NUM_2 = false;
    	Key_NUM_3 = false;
    	Key_R = false;
    }
    
    // Method to create buttons
    public static JButton setAndPutButton(JPanel panel, String text, ActionListener e, int x, int y) {
    	JButton B;
    	B = Tetris.createButton(text); // Create Start button
        B.addActionListener(e);
        B.setBounds(x, y, 250, 60); // x, y, width, height
        panel.add(B);
		return B;
    }
    
    public static JButton createButton(final String text) {
        final JButton button = new JButton("<html><div style='border:2px solid white; padding:5px; color:white;'>" + text + "</div></html>");
        button.setFont(new Font("Arial", Font.BOLD, 32));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);

        // Mouse listener to change text and border color on hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setText("<html><div style='border:2px solid #00FF00; padding:5px; color:#00FF00;'>" + text + "</div></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setText("<html><div style='border:2px solid white; padding:5px; color:white;'>" + text + "</div></html>");
            }
        });
        return button;
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