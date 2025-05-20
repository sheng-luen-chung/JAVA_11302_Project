package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class mode extends JPanel{ 
	private JButton classicButton, gravityButton, gapButton, purgeButton, surviveButton;
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "MODE");
    	setBackground(Color.BLACK);
    	
    	setLayout(null);
    	// Classic buttons
    	classicButton = Tetris.setAndPutButton(this,
    						   				 "Classic",
    						   				 new BClassic(),
    						   				 Tetris.TOTAL_SIZE_X / 4 * 1 - 140,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 50);
    	// GravityButton buttons
    	gravityButton = Tetris.setAndPutButton(this,
    						   				 "20 Gravity",
    						   				 new BGravity(),
    						   				 Tetris.TOTAL_SIZE_X / 4 * 1 - 140,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 130);
    	// Gap buttons
    	gapButton = Tetris.setAndPutButton(this,
    									   "Gap",
    									   new BGap(),
    									   Tetris.TOTAL_SIZE_X / 4 * 2 - 140,
    									   Tetris.TOTAL_SIZE_Y / 2 + 50);
    	// Purge buttons
    	purgeButton = Tetris.setAndPutButton(this,
    									     "Purge",
    									     new BPurge(),
    									     Tetris.TOTAL_SIZE_X / 4 * 2 - 140,
    									     Tetris.TOTAL_SIZE_Y / 2 + 130);
    	// Survive buttons
    	surviveButton = Tetris.setAndPutButton(this,
    									       "Survive",
    									       new BSurvive(),
    									       Tetris.TOTAL_SIZE_X / 4 * 3 - 140,
    									       Tetris.TOTAL_SIZE_Y / 2 + 50);
    }
    
    public void stopPanel() {
    	Tetris.menu_bgMusic.stop();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        
        g.setColor(Color.ORANGE);
        g.drawString("Selection Mode", Tetris.TOTAL_SIZE_X / 2 - 160, Tetris.TOTAL_SIZE_Y / 2 - 80);
        
        g.setColor(Color.GREEN);
        g.drawString("SOLO", Tetris.TOTAL_SIZE_X / 4 * 1 - 70, Tetris.TOTAL_SIZE_Y / 2 + 20);
        
        g.setColor(Color.RED);
        g.drawString("Battle", Tetris.TOTAL_SIZE_X / 4 * 2 - 70, Tetris.TOTAL_SIZE_Y / 2 + 20);
        
        g.setColor(Color.YELLOW);
        g.drawString("Team", Tetris.TOTAL_SIZE_X / 4 * 3 - 70, Tetris.TOTAL_SIZE_Y / 2 + 20);
    }
    
    private class BClassic implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_CLASSIC);
        }
    }
    
    private class BGravity implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_GRAVITY);
        }
    }
    
    private class BGap implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_GAP);
        }
    }
    
    private class BPurge implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_PURGE);
        }
    }
    
    private class BSurvive implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_SURVIVE);
        }
    }
}