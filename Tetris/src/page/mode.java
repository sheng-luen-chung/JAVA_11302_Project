package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class mode extends JPanel{ 
	private JButton classicButton, gravityButton, gapButton;
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "MODE");
    	setBackground(Color.BLACK);
    	
    	setLayout(null);
    	// Classic buttons
    	classicButton = Tetris.setAndPutButton(this,
    						   				 "Classic",
    						   				 new BClassic(),
    						   				 Tetris.TOTAL_SIZE_X / 2 - 140,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 50);
    	// GravityButton buttons
    	gravityButton = Tetris.setAndPutButton(this,
    						   				 "20G Mode",
    						   				 new BGravity(),
    						   				 Tetris.TOTAL_SIZE_X / 2 - 140,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 130);
    	
    	// Gap buttons
    	gapButton = Tetris.setAndPutButton(this,
    									   "Battle",
    									   new BGap(),
    									   Tetris.TOTAL_SIZE_X / 2 - 140,
    									   Tetris.TOTAL_SIZE_Y / 2 + 210);
    }
    
    public void stopPanel() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Selection Mode", Tetris.TOTAL_SIZE_X / 2 - 160
        		, Tetris.TOTAL_SIZE_Y / 2 - 100);
    }
    
    private class BClassic implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.menu_bgMusic.stop();
            Tetris.click_effect = new MusicPlayer();
            Tetris.click_effect.play(Tetris.click_effect_path, false);
            Tetris.setPage(Tetris.PAGE_CLASSIC);
        }
    }
    
    private class BGravity implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.menu_bgMusic.stop();
            Tetris.click_effect = new MusicPlayer();
            Tetris.click_effect.play(Tetris.click_effect_path, false);
            Tetris.setPage(Tetris.PAGE_GRAVITY);
        }
    }
    
    private class BGap implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.menu_bgMusic.stop();
            Tetris.click_effect = new MusicPlayer();
            Tetris.click_effect.play(Tetris.click_effect_path, false);
            Tetris.setPage(Tetris.PAGE_GAP);
        }
    }
}