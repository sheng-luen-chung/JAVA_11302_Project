package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class gameover extends JPanel{
	private JButton restartButton, menuButton;
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "GAMEOVER");
    	setBackground(Color.BLACK);
        
    	Tetris.gameOver_effect = new MusicPlayer();
        Tetris.gameOver_effect.play(Tetris.gameOver_effect_path, false);
        
        setLayout(null);
        // Restart button
        restartButton = Tetris.setAndPutButton(this,
    						   				   "Restart",
    						   				   new BRestart(),
    						   				   Tetris.TOTAL_SIZE_X / 2 - 150,
    						   				   Tetris.TOTAL_SIZE_Y / 2 + 100);
    	// Menu button
    	menuButton = Tetris.setAndPutButton(this,
    					 	  			    "Menu",
    					 	  			    new BMenu(),
    					 	  			    Tetris.TOTAL_SIZE_X / 2 - 150,
    					 	  			    Tetris.TOTAL_SIZE_Y / 2 + 180);
    }
    public void stopPanel() {
    	setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME  OVER", Tetris.TOTAL_SIZE_X / 2 - 150, Tetris.TOTAL_SIZE_Y / 2 - 100);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        switch(Tetris.overReturn) {
        	case Tetris.PAGE_CLASSIC:
        		g.setColor(Color.YELLOW);
                g.drawString("Your Score: "+String.valueOf(Tetris.score1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 70);
                g.drawString("Your Lines: "+String.valueOf(Tetris.lines1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 50);
                break;
        	case Tetris.PAGE_GRAVITY:
        		g.setColor(Color.YELLOW);
        		g.drawString("Your Score: "+String.valueOf(Tetris.score1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 70);
                g.drawString("Your Lines: "+String.valueOf(Tetris.lines1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 50);
                break;
                
        	case Tetris.PAGE_GAP:
        		g.setColor(Color.ORANGE);
                g.drawString("P1 Score: "+String.valueOf(Tetris.score1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 70);
                g.drawString("P1 Lines: "+String.valueOf(Tetris.lines1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 50);
                g.setColor(Color.CYAN);
                g.drawString("P2 Score: "+String.valueOf(Tetris.score2), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 20);
                g.drawString("P2 Lines: "+String.valueOf(Tetris.lines2), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 0);
                
                g.setFont(new Font("Arial", Font.BOLD, 30));
                if(Tetris.winner1) {g.setColor(Color.ORANGE); g.drawString("Winner P1", Tetris.TOTAL_SIZE_X / 2 - 100, Tetris.TOTAL_SIZE_Y / 2 + 60);}
                if(Tetris.winner2) {g.setColor(Color.CYAN); g.drawString("Winner P2", Tetris.TOTAL_SIZE_X / 2 - 100, Tetris.TOTAL_SIZE_Y / 2 + 60);}
                break;
                
        	case Tetris.PAGE_PURGE:
        		g.setColor(Color.ORANGE);
                g.drawString("P1 Score: "+String.valueOf(Tetris.score1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 70);
                g.drawString("P1 Lines: "+String.valueOf(Tetris.lines1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 50);
                g.setColor(Color.CYAN);
                g.drawString("P2 Score: "+String.valueOf(Tetris.score2), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 20);
                g.drawString("P2 Lines: "+String.valueOf(Tetris.lines2), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 0);
                
                g.setFont(new Font("Arial", Font.BOLD, 30));
                if(Tetris.winner1) {g.setColor(Color.ORANGE); g.drawString("Winner P1", Tetris.TOTAL_SIZE_X / 2 - 100, Tetris.TOTAL_SIZE_Y / 2 + 60);}
                if(Tetris.winner2) {g.setColor(Color.CYAN); g.drawString("Winner P2", Tetris.TOTAL_SIZE_X / 2 - 100, Tetris.TOTAL_SIZE_Y / 2 + 60);}
                break;
        		
        	case Tetris.PAGE_SURVIVE:
        		g.setColor(Color.ORANGE);
                g.drawString("P1 Score: "+String.valueOf(Tetris.score1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 70);
                g.drawString("P1 Lines: "+String.valueOf(Tetris.lines1), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 50);
                g.setColor(Color.CYAN);
                g.drawString("P2 Score: "+String.valueOf(Tetris.score2), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 20);
                g.drawString("P2 Lines: "+String.valueOf(Tetris.lines2), Tetris.TOTAL_SIZE_X / 2 - 110, Tetris.TOTAL_SIZE_Y / 2 - 0);
                
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.setColor(Color.WHITE);
                g.drawString("Round " + Tetris.round, Tetris.TOTAL_SIZE_X / 2 - 100, Tetris.TOTAL_SIZE_Y / 2 + 60);
                break;
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        switch(Tetris.overReturn) {
        	case Tetris.PAGE_CLASSIC:	g.drawString("Mode : Classic", 5, Tetris.TOTAL_SIZE_Y-20); break;
        	case Tetris.PAGE_GRAVITY:	g.drawString("Mode : 20 Gravity", 5, Tetris.TOTAL_SIZE_Y-20); break;
        	case Tetris.PAGE_GAP:		g.drawString("Mode : Gap", 5, Tetris.TOTAL_SIZE_Y-20); break;
        	case Tetris.PAGE_PURGE:		g.drawString("Mode : Purge", 5, Tetris.TOTAL_SIZE_Y-20); break;
        	case Tetris.PAGE_SURVIVE:	g.drawString("Mode : Survive", 5, Tetris.TOTAL_SIZE_Y-20); break;
        }
    }
    
    private class BRestart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.overReturn);
        }
    }
    private class BMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.menu_bgMusic = new MusicPlayer();
            Tetris.menu_bgMusic.play(Tetris.menu_bgMusic_path, true);
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}