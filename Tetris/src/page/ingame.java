package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class ingame extends JPanel{
    private Timer timer;
    private Timer blinkTimer;
    private Player p1;
    private Grid g1;
    
    public void startPanel() {
    	setBackground(Color.BLACK);
        p1 = new Player(0,0);
        g1 = new Grid(0,0);
        
        timer = new Timer(15, new gravity());
        timer.start();
    }  
    public void stopPanel() {
    	Tetris.score = g1.getScore();
    	timer.stop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		if(Tetris.Key_LEFT) Tetris.moveBlock(p1, g1, -1, 0); Tetris.Key_LEFT = false;
    	if(Tetris.Key_RIGHT) Tetris.moveBlock(p1, g1, 1, 0); Tetris.Key_RIGHT = false;
    	if(Tetris.Key_DOWN) p1.setSpeedUP(true); else p1.setSpeedUP(false);
    	if(Tetris.Key_NUM_3) Tetris.rotate_and_check(p1, g1, 1); Tetris.Key_NUM_3 = false;
    	if(Tetris.Key_NUM_2) Tetris.rotate_and_check(p1, g1, 0); Tetris.Key_NUM_2 = false;
    	if(Tetris.Key_NUM_1) p1.holdCurrentShape(); Tetris.Key_NUM_1 = false;
    	if(Tetris.Key_NUM_0) Tetris.hard_drop(p1, g1); Tetris.Key_NUM_0 = false;

        g1.draw(g);
        p1.draw(g);
        Tetris.findShadowAndDraw(g, p1, g1);
    }
    
    private class gravity implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
    		
            repaint();
        }  
    }
}