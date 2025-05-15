package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class classic extends JPanel{
	private JButton pauseButton, continueButton, restartButton, menuButton;
	private Timer timer, blinkTimer;
    private boolean BK;
    private Player p1;
    private Grid g1;
    private boolean pause;
    
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "CLASSIC");
    	setBackground(Color.BLACK);
    	
        p1 = new Player(300,90);
        g1 = new Grid(300,90);
        pause = false;
        
        timer = new Timer(15, new gravity_timer());
        timer.start();
        blinkTimer = new Timer(500, new blink());
        Tetris.classic_bgMusic = new MusicPlayer();
        Tetris.classic_bgMusic.play(Tetris.classic_bgMusic_path, true);
        
        setLayout(null);
     	// Pause button
        pauseButton = Tetris.setAndPutButton(this,
				   							 "Pause",
				   							 new BPause(),
				   							 Tetris.TOTAL_SIZE_X - 185,
				   							 0);
    }  
    public void stopPanel() {
    	Tetris.score1 = g1.getScore();
    	Tetris.lines1 = g1.getLinesC();
    	Tetris.overReturn = Tetris.PAGE_CLASSIC;
    	
    	timer.stop();
    	blinkTimer.stop();
    	Tetris.classic_bgMusic.stop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!pause) {
    		if(Tetris.Key_A || Tetris.Key_LEFT) Tetris.moveBlock(p1, g1, -1, 0); Tetris.Key_A = false; Tetris.Key_LEFT = false;
        	if(Tetris.Key_D || Tetris.Key_RIGHT) Tetris.moveBlock(p1, g1, 1, 0); Tetris.Key_D = false; Tetris.Key_RIGHT = false;
        	if(Tetris.Key_S || Tetris.Key_DOWN) p1.setSpeedUP(true); else p1.setSpeedUP(false);
        	if(Tetris.Key_B || Tetris.Key_NUM_3) Tetris.rotate_and_check(p1, g1, 1); Tetris.Key_B = false; Tetris.Key_NUM_3 = false;
        	if(Tetris.Key_V || Tetris.Key_NUM_2) Tetris.rotate_and_check(p1, g1, 0); Tetris.Key_V = false; Tetris.Key_NUM_2 = false;
        	if(Tetris.Key_C || Tetris.Key_NUM_1) p1.holdCurrentShape(); Tetris.Key_C = false; Tetris.Key_NUM_1 = false;
        	if(Tetris.Key_SPACE || Tetris.Key_NUM_0) Tetris.hard_drop(p1, g1); Tetris.Key_SPACE = false; Tetris.Key_NUM_0 = false;
        	if(Tetris.Key_ESC || Tetris.Key_NUM_9) pauseGame(); Tetris.Key_ESC = false; Tetris.Key_NUM_9 = false;

        	switch(g1.getLinesC()/10) {		//level up speed
        		case 0: p1.setDFS(48); break;
        		case 1: p1.setDFS(43); break;
        		case 2: p1.setDFS(38); break;
        		case 3: p1.setDFS(33); break;
        		case 4: p1.setDFS(28); break;
        		case 5: p1.setDFS(23); break;
        		case 6: p1.setDFS(18); break;
        		case 7: p1.setDFS(13); break;
        		case 8: p1.setDFS(8); break;
        		case 9: p1.setDFS(6); break;
        		default:p1.setDFS(1);  break;
        	}
        }
        else {
        	if(Tetris.Key_ESC || Tetris.Key_NUM_9) continueGame(); Tetris.Key_ESC = false; Tetris.Key_NUM_9 = false;
        }
        
        //show current mode
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Mode : Classic", 5, Tetris.TOTAL_SIZE_Y-20);
        
        g1.draw(g);
        p1.draw(g);
        Tetris.findShadowAndDraw(g, p1, g1);
        
        //pause
        if(pause) drawPause(g);
        
        repaint();
    }
    
    private class gravity_timer implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
        }  
    }
    
    private class blink implements ActionListener{
    	public void actionPerformed(ActionEvent e){
            BK = !BK;
            repaint();
        } 
    }
    
    private void drawPause(Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 155,
    			   Tetris.TOTAL_SIZE_Y / 2 - 230,
    			   260,
    			   420);
    	g.setColor(Color.ORANGE);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 150,
    			   Tetris.TOTAL_SIZE_Y / 2 - 225,
    			   250,
    			   410);
    	g.setColor(Color.BLACK);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 140,
    			   Tetris.TOTAL_SIZE_Y / 2 - 215,
    			   230,
    			   390);
    	
    	if(!BK) {
    		g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSE", Tetris.TOTAL_SIZE_X / 2 - 95, Tetris.TOTAL_SIZE_Y / 2 - 120);
    	}
    }
    
    private void pauseGame() {
    	pause = true;
    	timer.stop();
        blinkTimer.start();
        BK = false;
    	remove(pauseButton);
    	
    	// Continue button
    	continueButton = Tetris.setAndPutButton(this,
				 								"Continue",
				 								new BContinue(),
				 								Tetris.TOTAL_SIZE_X / 2 - 150,
				 								Tetris.TOTAL_SIZE_Y / 2 -80);
    	// Restart button
    	restartButton = Tetris.setAndPutButton(this,
											   "Restart",
											   new BRestart(),
											   Tetris.TOTAL_SIZE_X / 2 - 150,
											   Tetris.TOTAL_SIZE_Y / 2);
    	
    	// Menu button
    	menuButton = Tetris.setAndPutButton(this,
			    							"Menu",
			    							new BMenu(),
			    							Tetris.TOTAL_SIZE_X / 2 - 150,
			    							Tetris.TOTAL_SIZE_Y / 2 + 80);
    }
    
    private void continueGame() {
    	pause = false;
    	timer.start();
    	blinkTimer.stop();
    	// Pause button
    	pauseButton = Tetris.setAndPutButton(this,
				 							 "Pause",
				 							 new BPause(),
				 							 Tetris.TOTAL_SIZE_X - 185,
				 							 0);
    	
    	remove(continueButton);
    	remove(restartButton);
    	remove(menuButton);
    }
    
    private class BPause implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	pauseGame();
        }
    }
    
    private class BContinue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	continueGame();
        }
    }
    
    private class BRestart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	remove(continueButton);
        	remove(menuButton);
        	remove(restartButton);
        	Tetris.setPage(Tetris.PAGE_CLASSIC);
        }
    }
    
    private class BMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	remove(continueButton);
        	remove(menuButton);
        	remove(restartButton);
        	Tetris.menu_bgMusic = new MusicPlayer();
            Tetris.menu_bgMusic.play(Tetris.menu_bgMusic_path, true);
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}