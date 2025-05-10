package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class gameover extends JPanel{
    private Timer blinkTimer;
    private boolean BK;
    
    public void startPanel() {
    	setBackground(Color.BLACK);
        blinkTimer = new Timer(500, new blink());
        blinkTimer.start();
    }
    public void stopPanel() {
    	setBackground(Color.BLACK);
        blinkTimer.stop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		if(Tetris.Key_R) {
			Tetris.setPage(Tetris.PAGE_INGAME);
            Tetris.Key_R = false;
    	}
		if(Tetris.Key_S) {
			Tetris.setPage(Tetris.PAGE_MENU);
    	}
    	
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME  OVER", 12 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Your Score: "+String.valueOf(Tetris.score), 13 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2 + 60);
        
        if(BK){
           g.setFont(new Font("Arial", Font.BOLD, 20));
           g.drawString("press \"R\" to restart", 13 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2 + 80);
           g.drawString("press \"S\" to menu", 13 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2 + 100);
        }
    }
    
    private class blink implements ActionListener{
    	public void actionPerformed(ActionEvent e){
            BK = !BK;
            repaint();
        }  
    }
}