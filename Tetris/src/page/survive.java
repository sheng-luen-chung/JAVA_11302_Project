package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class survive extends JPanel{
	private JButton pauseButton, continueButton, restartButton, menuButton;
	private Timer timer, blinkTimer;
    private boolean BK;
    private Player p1, p2;
    private Grid g1, g2;
    private boolean pause;
    private int sendGarbageTimer;
    private int sendGarbageTimerSet;
    private int sendGarbageLinesSet;
    private int sendCount;
    private int sendGarbageTo1;
    private int sendGarbageTo2;
    
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "SURVIVE");
    	setBackground(Color.BLACK);
    	
        p1 = new Player(60,90);
        g1 = new Grid(60,90);
        
        p2 = new Player(30 + Tetris.TOTAL_SIZE_X / 2,90);
        g2 = new Grid(30 + Tetris.TOTAL_SIZE_X / 2,90); 
        
        sendGarbageTimerSet = 600;
        sendGarbageTimer = sendGarbageTimerSet;
        sendGarbageLinesSet = 1;
        sendCount = 0;
        sendGarbageTo1 = 0;
        sendGarbageTo2 = 0;
        
        pause = false;
        
        timer = new Timer(15, new gravity_timer());
        timer.start();
        blinkTimer = new Timer(500, new blink());
        Tetris.menu_bgMusic = new MusicPlayer();
        Tetris.menu_bgMusic.play(Tetris.menu_bgMusic_path, true);
        
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
    	Tetris.score2 = g2.getScore();
    	Tetris.lines2 = g2.getLinesC();
    	Tetris.round = sendCount;
    	Tetris.overReturn = Tetris.PAGE_SURVIVE;
    	

    	timer.stop();
    	blinkTimer.stop();
    	Tetris.menu_bgMusic.stop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!pause) {
    		if(Tetris.Key_A) Tetris.moveBlock(p1, g1, -1, 0); Tetris.Key_A = false;
        	if(Tetris.Key_D) Tetris.moveBlock(p1, g1, 1, 0); Tetris.Key_D = false;
        	if(Tetris.Key_S) p1.setSpeedUP(true); else p1.setSpeedUP(false);
        	if(Tetris.Key_B) Tetris.rotate_and_check(p1, g1, 1); Tetris.Key_B = false;
        	if(Tetris.Key_V) Tetris.rotate_and_check(p1, g1, 0); Tetris.Key_V = false;
        	if(Tetris.Key_C) p1.holdCurrentShape(); Tetris.Key_C = false;
        	if(Tetris.Key_SPACE) Tetris.hard_drop(p1, g1); Tetris.Key_SPACE = false;
        	if(Tetris.Key_ESC) pauseGame(); Tetris.Key_ESC = false;
        	
        	if(Tetris.Key_LEFT) Tetris.moveBlock(p2, g2, -1, 0); Tetris.Key_LEFT = false;
        	if(Tetris.Key_RIGHT) Tetris.moveBlock(p2, g2, 1, 0); Tetris.Key_RIGHT = false;
        	if(Tetris.Key_DOWN) p2.setSpeedUP(true); else p2.setSpeedUP(false);
        	if(Tetris.Key_NUM_3) Tetris.rotate_and_check(p2, g2, 1); Tetris.Key_NUM_3 = false;
        	if(Tetris.Key_NUM_2) Tetris.rotate_and_check(p2, g2, 0); Tetris.Key_NUM_2 = false;
        	if(Tetris.Key_NUM_1) p2.holdCurrentShape(); Tetris.Key_NUM_1 = false;
        	if(Tetris.Key_NUM_0) Tetris.hard_drop(p2, g2); Tetris.Key_NUM_0 = false;
        	if(Tetris.Key_NUM_9) pauseGame(); Tetris.Key_NUM_9 = false;
        }
        else {
        	if(Tetris.Key_ESC || Tetris.Key_NUM_9) continueGame(); Tetris.Key_ESC = false; Tetris.Key_NUM_9 = false;
        }
        
        //show current mode
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Mode : Survive", 5, Tetris.TOTAL_SIZE_Y-20);
        
        g1.draw(g);
        p1.draw(g);
        Tetris.findShadowAndDraw(g, p1, g1);
        g2.draw(g);
        p2.draw(g);
        Tetris.findShadowAndDraw(g, p2, g2);
        
        // team mode
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Round " + sendCount , 10, 40);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(String.valueOf((sendGarbageTimer+30) / 60), Tetris.TOTAL_SIZE_X / 2 - 50, 60);
        
        //pause
        if(pause) drawPause(g);
        
        repaint();
    }
    
    private class gravity_timer implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
    		Tetris.gravity_drop(p2, g2);
    		
    		// Clear Garbage
    		Tetris.clearGarbageLines(p1, g1, g1.getA());
    		Tetris.clearGarbageLines(p2, g2, g1.getA() / 2);
    		Tetris.clearGarbageLines(p2, g2, g2.getA());
    		Tetris.clearGarbageLines(p1, g1, g1.getA() / 2);
    		g1.setA(0);
    		g2.setA(0);
    		
    		// Send Garbage
    		if(sendGarbageTo2 > 0) sendGarbageTo2 = Tetris.spawnGarbageLines(p2, g2, sendGarbageTo2, false);
    		if(sendGarbageTo1 > 0) sendGarbageTo1 = Tetris.spawnGarbageLines(p1, g1, sendGarbageTo1, false);
    		
    		// Send System Control
    		if(sendGarbageTimer > 0) {
    			sendGarbageTimer--;
    			if(sendGarbageTimer == 0) {
    				sendGarbageTo2 += sendGarbageLinesSet;
    				sendGarbageTo1 += sendGarbageLinesSet;
    				switch(sendCount) {
        			case 0: sendGarbageTimerSet = 600;	break;
        			case 1: sendGarbageTimerSet = 540;  break;
        			case 3: sendGarbageTimerSet = 480;	break;
        			case 6: sendGarbageTimerSet = 420;	break;
        			case 10: sendGarbageTimerSet = 360;	break;
        			case 15: sendGarbageTimerSet = 300;	break;
        			case 25: sendGarbageLinesSet = 2;	break;
        			case 35: sendGarbageLinesSet = 3;	break;
        			case 50: sendGarbageLinesSet = 4;	break;
        			case 100: sendGarbageLinesSet = 5;	break;
    				}
    				sendGarbageTimer = sendGarbageTimerSet;
    				sendCount++;
    			}
    		}
    		if(sendGarbageTimer == 0) sendGarbageTimer = sendGarbageTimerSet;
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
        	Tetris.setPage(Tetris.PAGE_SURVIVE);
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