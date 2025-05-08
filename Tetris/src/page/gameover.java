package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class gameover extends JPanel{
    private Timer timer;
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
    	
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME  OVER", 3 * Tetris.BLOCK_SIZE + 5, Tetris.GRID_ROWS * Tetris.BLOCK_SIZE / 2);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Your Score: "+String.valueOf(Tetris.score), 4 * Tetris.BLOCK_SIZE + 15, Tetris.GRID_ROWS * Tetris.BLOCK_SIZE / 2 + 30);
        
        if(BK){
           g.setFont(new Font("Arial", Font.BOLD, 20));
           g.drawString("press \"R\" to restart", 4 * Tetris.BLOCK_SIZE + 15, (Tetris.GRID_ROWS+2) * Tetris.BLOCK_SIZE / 2 + 50);
        }
    }
    
    private class blink implements ActionListener{
    	public void actionPerformed(ActionEvent e){
            BK = !BK;
            repaint();
        }  
    }
}