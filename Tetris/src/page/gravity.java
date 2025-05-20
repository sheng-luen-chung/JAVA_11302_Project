package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class gravity extends JPanel{
	private JButton pauseButton, continueButton, menuButton;
	private Timer timer, blinkTimer;
    private boolean BK;
    private Player p1;
    private Grid g1;
    private boolean pause;
    
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "GRAVITY");
    	setBackground(Color.BLACK);
    	
        p1 = new Player(300,60);
        g1 = new Grid(300,60);
        pause = false;
        
        timer = new Timer(15, new gravity_timer());
        timer.start();
        blinkTimer = new Timer(500, new blink());
        Tetris.bgMusic = new MusicPlayer();
        Tetris.bgMusic.play(Tetris.bgMusic_path, true);
        
        setLayout(null);
     	// Pause button
        pauseButton = Tetris.setAndPutButton(this,
				   							 "Pause",
				   							 new BPause(),
				   							 Tetris.TOTAL_SIZE_X - 185,
				   							 0);
        p1.setDFS(0.05);
    }  
    public void stopPanel() {
    	Tetris.score = g1.getScore();
    	Tetris.overReturn = Tetris.PAGE_GRAVITY;
    	timer.stop();
    	blinkTimer.stop();
    	Tetris.bgMusic.stop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!pause) {
    		if(Tetris.Key_LEFT) Tetris.moveBlock(p1, g1, -1, 0); Tetris.Key_LEFT = false;
        	if(Tetris.Key_RIGHT) Tetris.moveBlock(p1, g1, 1, 0); Tetris.Key_RIGHT = false;
        	if(Tetris.Key_DOWN) p1.setSpeedUP(true); else p1.setSpeedUP(false);
        	if(Tetris.Key_NUM_3) Tetris.rotate_and_check(p1, g1, 1); Tetris.Key_NUM_3 = false;
        	if(Tetris.Key_NUM_2) Tetris.rotate_and_check(p1, g1, 0); Tetris.Key_NUM_2 = false;
        	if(Tetris.Key_NUM_1) p1.holdCurrentShape(); Tetris.Key_NUM_1 = false;
        	if(Tetris.Key_NUM_0) Tetris.hard_drop(p1, g1); Tetris.Key_NUM_0 = false;
        }
        
        
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
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 135,
    			   Tetris.TOTAL_SIZE_Y / 2 - 180,
    			   210,
    			   310);
    	g.setColor(Color.ORANGE);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 130,
    			   Tetris.TOTAL_SIZE_Y / 2 - 175,
    			   200,
    			   300);
    	g.setColor(Color.BLACK);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 120,
    			   Tetris.TOTAL_SIZE_Y / 2 - 165,
    			   180,
    			   280);
    	
    	if(!BK) {
    		g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSE", Tetris.TOTAL_SIZE_X / 2 - 95, Tetris.TOTAL_SIZE_Y / 2 - 90);
    	}
    }
    
    private void pauseGame() {
    	pause = true;
    	timer.stop();
        blinkTimer.start();
    	remove(pauseButton);
    	
    	// Continue button
    	continueButton = Tetris.setAndPutButton(this,
				 								"Continue",
				 								new BContinue(),
				 								Tetris.TOTAL_SIZE_X / 2 - 150,
				 								Tetris.TOTAL_SIZE_Y / 2 -50);
    	// Menu button
    	menuButton = Tetris.setAndPutButton(this,
			    							"Menu",
			    							new BMenu(),
			    							Tetris.TOTAL_SIZE_X / 2 - 150,
			    							Tetris.TOTAL_SIZE_Y / 2 + 30);
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
    
    private class BMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	remove(continueButton);
        	remove(menuButton);
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}