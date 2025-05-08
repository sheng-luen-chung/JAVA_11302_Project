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
    	
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("MENU", 5 * Tetris.BLOCK_SIZE, Tetris.GRID_ROWS * Tetris.BLOCK_SIZE / 2);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("press \"R\" to start", 4 * Tetris.BLOCK_SIZE + 15, (Tetris.GRID_ROWS+2) * Tetris.BLOCK_SIZE / 2 + 30);
    }
}