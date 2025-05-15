package game;

import javax.swing.*;
import page.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Tetris extends JPanel{
	public static final int BLOCK_SIZE = 30;
	public static final int TOTAL_SIZE_X = 36 * BLOCK_SIZE;
	public static final int TOTAL_SIZE_Y = 25 * BLOCK_SIZE;
    public static final int GRID_COLS = 10;
    public static final int GRID_ROWS = 20;
    
    public static final int PAGE_MENU = 0;
    public static final int PAGE_MODE = 1;
    
    public static final int PAGE_CLASSIC = 2;
    public static final int PAGE_GRAVITY = 3;
    public static final int PAGE_GAP = 4;
    public static final int PAGE_PURGE = 5; 
    public static final int PAGE_SURVIVE = 6;
    public static final int PAGE_GAMEOVER = 7;
    
    public static final int PAGE_INSTRUCTIONS = 8;
    public static final int PAGE_MTS = 9;
    public static final int PAGE_MTST1 = 10;
    public static final int PAGE_MTST2 = 11;
    public static final int PAGE_TS = 12;
    public static final int PAGE_TST1 = 13;
    public static final int PAGE_TST2 = 14;
    public static final int PAGE_TST3 = 15;
    public static final int INS_TOTAL_PAGES = 8;
    
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel mainPanel = new JPanel(cardLayout);
    
    //page
    public static menu menuPanel;
    public static mode modePanel;
    
    public static classic classicPanel;
    public static gravity gravityPanel;
    public static gap gapPanel;
    public static purge purgePanel;
    public static survive survivePanel;
    public static gameover gameoverPanel;
    
    public static instructions instructionsPanel;
    public static MTS MTSPanel;
    public static MTST1 MTST1Panel;
    public static MTST2 MTST2Panel;
    public static TS TSPanel;
    public static TST1 TST1Panel;
    public static TST2 TST2Panel;
    public static TST3 TST3Panel;

    // state
    public static int gamePage;
    public static int score1, score2;
    public static int lines1, lines2;
    public static boolean winner1, winner2;	// in battle mode
    public static int round;	// in team mode
    public static int overReturn;
    
    // key
    public static boolean Key_A;
    public static boolean Key_D;
    public static boolean Key_S;
    public static boolean Key_SPACE;
    public static boolean Key_C;
    public static boolean Key_V;
    public static boolean Key_B;
    public static boolean Key_ESC;
    
    public static boolean Key_RIGHT;
	public static boolean Key_LEFT;
	public static boolean Key_DOWN;
	public static boolean Key_NUM_0;
	public static boolean Key_NUM_1;
	public static boolean Key_NUM_2;
	public static boolean Key_NUM_3;
	public static boolean Key_NUM_9;
    
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
    
    public static final int[] putShock = {20, -20, 15 -15, 10, 0}; 
    public static final int[] clearLineShock = {20, -20, 15 -15, 10, 0};
    
    //Back Ground Music
    public static MusicPlayer menu_bgMusic;
    public static MusicPlayer classic_bgMusic;
    public static MusicPlayer TWG_bgMusic;
    public static MusicPlayer battle_bgMusic;
    //Sound Effect
    public static MusicPlayer clearLine_effect;
    public static MusicPlayer gameOver_effect;
    public static MusicPlayer putShape_effect;
    public static MusicPlayer click_effect;
    public static MusicPlayer rotate_effect;
    
    //Music Path
    public static final String menu_bgMusic_path = "resources/menu_BGM.wav";
    public static final String classic_bgMusic_path = "resources/classic_BGM.wav";
    public static final String TWG_bgMusic_path = "resources/20G_BGM.wav";
    public static final String battle_bgMusic_path = "resources/battle_BGM.wav";
    public static final String clearLine_effect_path = "resources/clearLine_effect.wav";
    public static final String gameOver_effect_path = "resources/gameOver_effect.wav";
    public static final String putShape_effect_path = "resources/putShape_effect.wav";
    public static final String click_effect_path = "resources/click_effect.wav";
    public static final String rotate_effect_path = "resources/rotate_effect.wav";
    
//---------------------------------------------------------------------------------------//
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
	                case KeyEvent.VK_ESCAPE:	Key_ESC = true;	break;
	                
                    case KeyEvent.VK_LEFT:		Key_LEFT = true; 	break;
                    case KeyEvent.VK_RIGHT:		Key_RIGHT = true;	break;
                    case KeyEvent.VK_DOWN:		Key_DOWN = true;	break;
                    case KeyEvent.VK_NUMPAD3:	Key_NUM_3 = true;	break;
                    case KeyEvent.VK_NUMPAD2:	Key_NUM_2 = true;	break;
                    case KeyEvent.VK_NUMPAD1:	Key_NUM_1 = true;	break;
                    case KeyEvent.VK_NUMPAD0:	Key_NUM_0 = true;   break;
                    case KeyEvent.VK_NUMPAD9:	Key_NUM_9 = true;	break;
                  }
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
	                case KeyEvent.VK_ESCAPE:	Key_ESC = false;	break;
	                
			        case KeyEvent.VK_LEFT:		Key_LEFT = false; 	break;
			        case KeyEvent.VK_RIGHT:		Key_RIGHT = false;	break;
			        case KeyEvent.VK_DOWN:		Key_DOWN = false;	break;
			        case KeyEvent.VK_NUMPAD3:	Key_NUM_3 = false;	break;
			        case KeyEvent.VK_NUMPAD2:	Key_NUM_2 = false;	break;
			        case KeyEvent.VK_NUMPAD1:	Key_NUM_1 = false;	break;
			        case KeyEvent.VK_NUMPAD0:	Key_NUM_0 = false;   break;
			        case KeyEvent.VK_NUMPAD9:	Key_NUM_9 = false;	break;
            	}
            }
        });
        createPages();
        
        menu_bgMusic = new MusicPlayer();
        menu_bgMusic.play(Tetris.menu_bgMusic_path, true);
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
		gapPanel = new gap();
		mainPanel.add(gapPanel, "GAP");
		purgePanel = new purge();
		mainPanel.add(purgePanel, "PURGE");
		survivePanel = new survive();
		mainPanel.add(survivePanel, "SURVIVE");
		gameoverPanel = new gameover();
		mainPanel.add(gameoverPanel, "GAMEOVER");
		
		instructionsPanel = new instructions();
		mainPanel.add(instructionsPanel, "INSTRUCTIONS");
		MTSPanel = new MTS();
		mainPanel.add(MTSPanel, "MTS");
		MTST1Panel = new MTST1();
		mainPanel.add(MTST1Panel, "MTST1");
		MTST2Panel = new MTST2();
		mainPanel.add(MTST2Panel, "MTST2");
		TSPanel = new TS();
		mainPanel.add(TSPanel, "TS");
		TST1Panel = new TST1();
		mainPanel.add(TST1Panel, "TST1");
		TST2Panel = new TST2();
		mainPanel.add(TST2Panel, "TST2");
		TST3Panel = new TST3();
		mainPanel.add(TST3Panel, "TST3");
		
		this.add(mainPanel);
	    setLayout(new CardLayout());
    }
    
    public static void putShape(Player p, Grid g) {
         putShape_effect = new MusicPlayer();
         putShape_effect.play(putShape_effect_path, false);
  		 for (int i = 0; i < p.getShape().length; i++) {
	         int x = p.getX() + p.getShape()[i].x;
	         int y = p.getY() + p.getShape()[i].y;
	         if (y < 0) {
	            // Game over condition: shape locks above the visible grid
	        	g.setWin(false);
	            setPage(PAGE_GAMEOVER);
	            return;
	         }
	         g.setBGarr(x,y,p.getS());
  		  }
  		  setPutShock(p,g); // shock
  		 
  		  //calculating score
	  	  int T_spin = 0;	//0=none, 1=Mini T-Spin, 2=T-Spin
	  	  int linesCleared = 0;
	  	  if(p.getS() == 3 && p.getTspin()) T_spin = detect_T_spin(p, g);
	      linesCleared = g.clearFullLines();
	      g.calcuateScore(linesCleared, T_spin);
	      if(linesCleared > 0) setClearLineShock(p,g); //shock
	      
	      //new Shape
	      p.spawnNewShape();
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
            else {						//success rotate
            	rotate_effect = new MusicPlayer();
                rotate_effect.play(Tetris.rotate_effect_path, false);
            	p.setLD(p.getLDS());
            	if(p.getS() == 3) p.setTspin(true);	//T-spin
            }
        }
        else {							//success rotate
        	rotate_effect = new MusicPlayer();
            rotate_effect.play(Tetris.rotate_effect_path, false);
        	p.setLD(p.getLDS());
        	if(p.getS() == 3) p.setTspin(true);	//T-spin
        }
        if(kick_count == 4) p.setLK(true);
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
    	
		// test for touchdown
		p.setY(p.getY()+1);
		if(p.getLD() > 0 && !isValidPosition(p, g)) p.setLD(p.getLD() - 1);
		p.setY(p.getY()-1);
		
		//shock control
	  	if(p.getXS() != 0) {
			for(int i = 0; i<Tetris.clearLineShock.length-1; i++) {
				if(p.getXS() == Tetris.clearLineShock[i]) {
					p.setXS(Tetris.clearLineShock[i+1]);
					break;
		}}}
		if(p.getYS() != 0) {
			for(int i = 0; i<Tetris.putShock.length-1; i++) {
				if(p.getYS() == Tetris.putShock[i]) {
					p.setYS(Tetris.putShock[i+1]);
					break;
		}}}
		if(g.getXS() != 0) {
			for(int i = 0; i<Tetris.clearLineShock.length-1; i++) {
				if(g.getXS() == Tetris.clearLineShock[i]) {
					g.setXS(Tetris.clearLineShock[i+1]);
					break;
		}}}
		if(g.getYS() != 0) {
			for(int i = 0; i<Tetris.putShock.length-1; i++) {
				if(g.getYS() == Tetris.putShock[i]) {
					g.setYS(Tetris.putShock[i+1]);
					break;
		}}}
		
		// overlap pieces in any way
		if (!isValidPosition(p, g)) {
			g.setWin(false);
	        setPage(PAGE_GAMEOVER);	
	    }
		
		// clear effects timer decrease
		g.clearEffectssTimerDecrease();
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
            if (x < 0 || x >= GRID_COLS || y >= GRID_ROWS) {
                return false;
            }
            if(y>=0) {
            	if (g.getBGarr()[x][y] != 0) {
                	return false;
                }
            }
        }
        return true;
    }
    
    public static int spawnGarbageLines(Player p, Grid g, int l, boolean empty) {
    	Random rand = new Random();
    	// if top not empty
    	for(int x = 0; x < GRID_COLS; x++) {
    	    if(g.getBGarr()[x][0] != 0) {
    	    	g.setWin(false);
    	    	setPage(PAGE_GAMEOVER);
    	    	break;
    	    }
    	}
    	
    	// stack up
    	if(p.getY() > -3) p.setY(p.getY() - 1);
  	    for(int y = 0; y < GRID_ROWS - 1; y++) {
  	    	for(int x = 0; x < GRID_COLS; x++) {
  	    	    g.setBGarr(x, y, g.getBGarr()[x][y+1]);
  		    }
	    }
  	    
  	    // spawn garbage
  	    for(int x = 0; x < GRID_COLS; x++) {
  		    g.setBGarr(x, 19, 8);
  	    }
  	    if(empty) g.setBGarr(rand.nextInt(GRID_COLS), 19, 0);
  	    return l-1;
    }
    
    public static int clearGarbageLines(Player p, Grid g, int l) {
    	int clearedLine=0;
    	boolean isGarbageLine = false;
    	
    	//clear garbage lines
    	for(int y = (GRID_ROWS - 1); y > (GRID_ROWS - 1 - l); y--) {
    		for(int x = 0; x < GRID_COLS; x++) {
      		    if(g.getBGarr()[x][y] == 8) {
      		    	g.setBGarr(x, y, 0);
      		    	isGarbageLine = true;
      		    }
      	    }
    		if(isGarbageLine) clearedLine++;
    		isGarbageLine = false;
    	}
    	
    	//stack down
    	p.setY(p.getY() + clearedLine);
    	for(int times = 0; times < clearedLine; times++) {
    		for(int y = (GRID_ROWS - 1); y > 0; y--) {
      	    	for(int x = 0; x < GRID_COLS; x++) {
      	    	    g.setBGarr(x, y, g.getBGarr()[x][y-1]);
      		    }
    	    }
    	}
  	    
  	    // clear top
  	    for(int x = 0; x < GRID_COLS; x++) {
    	    g.setBGarr(x, 0, 0);
	    }
  	    
  	    return clearedLine;
    }
    
    public static void setPage(int p) {
    	switch(gamePage) {
    		case PAGE_MENU: menuPanel.stopPanel(); break;
    		case PAGE_MODE: modePanel.stopPanel(); break;
			case PAGE_CLASSIC: classicPanel.stopPanel(); break;			
			case PAGE_GRAVITY: gravityPanel.stopPanel(); break;
			
			case PAGE_GAP: gapPanel.stopPanel(); break;
			case PAGE_PURGE: purgePanel.stopPanel(); break;
			case PAGE_SURVIVE: survivePanel.stopPanel(); break;
			
			case PAGE_GAMEOVER: gameoverPanel.stopPanel(); break;
			
			case PAGE_INSTRUCTIONS: instructionsPanel.stopPanel(); break;
			case PAGE_MTS: MTSPanel.stopPanel(); break;
			case PAGE_MTST1: MTST1Panel.stopPanel(); break;
			case PAGE_MTST2: MTST2Panel.stopPanel(); break;
			case PAGE_TS: TSPanel.stopPanel(); break;
			case PAGE_TST1: TST1Panel.stopPanel(); break;
			case PAGE_TST2: TST2Panel.stopPanel(); break;
			case PAGE_TST3: TST3Panel.stopPanel(); break;
    	} 
    	
    	switch(p) {
    		case PAGE_MENU: menuPanel.startPanel(); break;
    		case PAGE_MODE: modePanel.startPanel(); break;
    		case PAGE_CLASSIC: classicPanel.startPanel(); break;
    		case PAGE_GRAVITY: gravityPanel.startPanel(); break;
    		
    		case PAGE_GAP: gapPanel.startPanel(); break;
			case PAGE_PURGE: purgePanel.startPanel(); break;
			case PAGE_SURVIVE: survivePanel.startPanel(); break;
    		
    		case PAGE_GAMEOVER: gameoverPanel.startPanel(); break;
    		
    		case PAGE_INSTRUCTIONS: instructionsPanel.startPanel(); break;
    		case PAGE_MTS: MTSPanel.startPanel(); break;
    		case PAGE_MTST1: MTST1Panel.startPanel(); break;
    		case PAGE_MTST2: MTST2Panel.startPanel(); break;
    		case PAGE_TS: TSPanel.startPanel(); break;
    		case PAGE_TST1: TST1Panel.startPanel(); break;
    		case PAGE_TST2: TST2Panel.startPanel(); break;
    		case PAGE_TST3: TST3Panel.startPanel(); break;
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
    	Key_ESC = false;
    	Key_LEFT = false;
    	Key_RIGHT = false;
    	Key_DOWN = false;
    	Key_NUM_0 = false;
    	Key_NUM_1 = false;
    	Key_NUM_2 = false;
    	Key_NUM_3 = false;
    	Key_NUM_9 = false;
    }
    
    public static void setClearLineShock(Player p, Grid g) {
    	p.setXS(clearLineShock[0]);
    	g.setXS(clearLineShock[0]);
    }
    public static void setPutShock(Player p, Grid g) {
    	p.setYS(putShock[0]);
    	g.setYS(putShock[0]);
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
            
            @Override
            public void mousePressed(MouseEvent e) {
            	click_effect = new MusicPlayer();
                click_effect.play(Tetris.click_effect_path, false);
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