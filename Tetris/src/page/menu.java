package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class menu extends JPanel{ 
	private JButton startButton, exitButton;
    public void startPanel() {
    	setBackground(Color.BLACK);
    	
    	setLayout(null);
    	// Start buttons
    	startButton = Tetris.setAndPutButton(this,
    						   				 "Start",
    						   				 new BStart(),
    						   				 Tetris.TOTAL_SIZE_X / 2 - 130,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 50);
    	// Exit buttons
    	exitButton = Tetris.setAndPutButton(this,
    						   				"Exit",
    						   				new BExit(),
    						   				Tetris.TOTAL_SIZE_X / 2 - 130,
    						   				Tetris.TOTAL_SIZE_Y / 2 + 130);
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
    }
    
    private class BStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_INGAME);
        }
    }
    private class BExit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}