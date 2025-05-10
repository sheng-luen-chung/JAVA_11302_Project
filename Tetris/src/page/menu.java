package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class menu extends JPanel{
    public void startPanel() {
    	setBackground(Color.BLACK);
    }
    public void stopPanel() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		if(Tetris.Key_R) {
			Tetris.setPage(Tetris.PAGE_INGAME);
            Tetris.Key_R = false;
    	}
		if(Tetris.Key_S) {
			System.exit(0);
    	}
    	
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Tetris", 14 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("press \"R\" to start", 13 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2 + 120);
        g.drawString("press \"S\" to exit", 13 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2 + 140);
    }
}