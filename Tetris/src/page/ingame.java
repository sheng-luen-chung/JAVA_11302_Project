package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class ingame extends JPanel{
    private Timer timer;
    private Player p1;
    private Grid g1;
    
    public void startPanel() {
    	setBackground(Color.BLACK);
        p1 = new Player(300,60);
        g1 = new Grid(300,60,0);
        
        timer = new Timer(15, new gravity());
        timer.start();
        Tetris.bgMusic = new MusicPlayer();
        Tetris.bgMusic.play(Tetris.bgMusic_path, true);
    }  
    public void stopPanel() {
    	Tetris.score = g1.getScore();
    	timer.stop();
    	Tetris.bgMusic.stop();
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