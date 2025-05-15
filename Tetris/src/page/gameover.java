package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class gameover extends JPanel{
	private JButton restartButton, menuButton;
    public void startPanel() {
      Tetris.gameOver_effect = new MusicPlayer();
      Tetris.gameOver_effect.play(Tetris.gameOver_effect_path, false);
    	Tetris.cardLayout.show(Tetris.mainPanel, "GAMEOVER");
    	setBackground(Color.BLACK);
        
        setLayout(null);
        // ReStart button
        restartButton = Tetris.setAndPutButton(this,
    						   				   "Restart",
    						   				   new BReStart(),
    						   				   Tetris.TOTAL_SIZE_X / 2 - 150,
    						   				   Tetris.TOTAL_SIZE_Y / 2 + 80);
    	// Menu button
    	menuButton = Tetris.setAndPutButton(this,
    					 	  			    "Menu",
    					 	  			    new BMenu(),
    					 	  			    Tetris.TOTAL_SIZE_X / 2 - 150,
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
        g.drawString("GAME  OVER", Tetris.TOTAL_SIZE_X / 2 - 160, Tetris.TOTAL_SIZE_Y / 2-50);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Your Score: "+String.valueOf(Tetris.score), Tetris.TOTAL_SIZE_X / 2 - 100, Tetris.TOTAL_SIZE_Y / 2 -20);
    }
    
    private class BReStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        Tetris.click_effect = new MusicPlayer();
        Tetris.click_effect.play(Tetris.click_effect_path, false);
            Tetris.setPage(Tetris.overReturn);
        }
    }
    private class BMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        Tetris.click_effect = new MusicPlayer();
        Tetris.click_effect.play(Tetris.click_effect_path, false);
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}