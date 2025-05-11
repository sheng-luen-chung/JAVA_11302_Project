package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class gameover extends JPanel{
	private JButton restartButton, menuButton;
    public void startPanel() {
    	setBackground(Color.BLACK);
        
        setLayout(null);
        // ReStart button
        restartButton = Tetris.setAndPutButton(this,
    						   				   "ReStart",
    						   				   new BReStart(),
    						   				   Tetris.TOTAL_SIZE_X / 2 - 130,
    						   				   Tetris.TOTAL_SIZE_Y / 2 + 80);
    	// Menu button
    	menuButton = Tetris.setAndPutButton(this,
    					 	  			    "Menu",
    					 	  			    new BMenu(),
    					 	  			    Tetris.TOTAL_SIZE_X / 2 - 130,
    					 	  			    Tetris.TOTAL_SIZE_Y / 2 + 160);
    }
    public void stopPanel() {
    	setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME  OVER", 12 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Your Score: "+String.valueOf(Tetris.score), 13 * Tetris.BLOCK_SIZE, Tetris.TOTAL_SIZE_Y / 2 + 30);
    }
    
    private class BReStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_INGAME);
        }
    }
    private class BMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}