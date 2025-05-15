package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class instructions extends JPanel{ 
	private JButton ReturnButton, NextButton;
	private final int page = 0;
	private String[] Controls1 = {"",
								  "Move Left",
						   		  "Move Right",
								  "Soft Drop",
								  "Hard Drop",
								  "Rotate Right",
								  "Rotate Left",
								  "Hold",
								  "Pause"
								};
	
	private String[] Controls2 = {"P1",
								  "D",
						   		  "A",
								  "S",
								  "SPACE",
								  "B",
								  "V",
								  "C",
								  "ESC",
								  ""
								  };
	
	private String[] Controls3 = {"P2",
								  "Right Arrow",
						   		  "Left Arrow",
								  "Down Arrow",
								  "Num 0",
								  "Num 3",
								  "Num 2",
								  "Num 1",
								  "Num 9",
								  ""
								};
		
	private String[] scoring1 = {"Single",
								 "Double",
								 "Triple",
								 "Quadruple",
								
								 "Mini T-Spin",
								 "T-Spin",
								
								 "Mini T-Spin Single",
								 "T-Spin Single",
								
								 "Mini T-Spin Double",
								 "T-Spin Double",
								
								 "T-Spin Triple",
								 
								 "Back To Back",
								 "Combo",
								 "Soft drop",
								 "Hard drop",
								 "",
								 "All Cleared Single",
								 "All Cleared Double",
								 "All Cleared Triple",
								 "All Cleared Quadruple",
								 "All Cleared Quadruple BTB"
								 };
	
	private String[] scoring2 = {"100 x Level",
						   		 "300 x Level",
								 "500 x Level",
								 "800 x Level",
								
								 "100 x Level",
								 "400 x Level",
								
								 "200 x Level",
								 "800 x Level",
								
								 "1200 x Level",
								 "1200 x Level",
								
								 "1600 x Level",
								 
								 "x 1.5",
								 "50 x C x Level",
								 "1 Grid / Per",
								 "2 Grid / Per",
								 "",
								 "800 x Level",
								 "1200 x Level",
								 "1800 x Level",
								 "2000 x Level",
								 "3200 x Level",
								 };
	
	private String[] scoring3 = {"none",
								 "+1",
								 "+2",
								 "+4",
			
								 "none",
								 "none",
			
								 "+1",
								 "+2",
			
								 "+2",
								 "+3",
			
								 "+6",
			 
								 "+1",
								 "+C/2 (+5 max)",
								 "none",
								 "none",
								 "",
								 "+6",
								 "+7",
								 "+8",
								 "+9",
								 "+10",
								 };
	
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "INSTRUCTIONS");
    	setBackground(Color.BLACK);
    	
    	setLayout(null);
    	// Return buttons
    	ReturnButton = Tetris.setAndPutButton(this,
    						   				 "Return",
    						   				 new BReturn(),
    						   				 Tetris.TOTAL_SIZE_X - 190,
    						   				 0);
    	// Next buttons
    	NextButton = Tetris.setAndPutButton(this,
				    						"Next",
				    						new BNext(),
				    						-50,
				    						Tetris.TOTAL_SIZE_Y - 130);
    }
    
    public void stopPanel() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		
        //Controls
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Controls", 1 * Tetris.BLOCK_SIZE, 2 * Tetris.BLOCK_SIZE);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for(int i=0; i < Controls1.length; i++) {
        	g.drawString(Controls1[i], 1 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        	g.drawString(Controls2[i], 6 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        	g.drawString(Controls3[i], 9 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        }
        g.drawLine(6 * Tetris.BLOCK_SIZE - 15, 2 * Tetris.BLOCK_SIZE + 15,
        		   6 * Tetris.BLOCK_SIZE - 15, 11 * Tetris.BLOCK_SIZE + 15);
        g.drawLine(9 * Tetris.BLOCK_SIZE - 10, 2 * Tetris.BLOCK_SIZE + 15,
     		   	   9 * Tetris.BLOCK_SIZE - 10, 11 * Tetris.BLOCK_SIZE + 15);
        
        //Scoring
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Scoring", 14 * Tetris.BLOCK_SIZE, 2 * Tetris.BLOCK_SIZE);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("&", 14 * Tetris.BLOCK_SIZE + 120, 2 * Tetris.BLOCK_SIZE);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Attacks", 14 * Tetris.BLOCK_SIZE + 150, 2 * Tetris.BLOCK_SIZE);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for(int i=0; i < scoring1.length; i++) {
        	g.drawString(scoring1[i], 15 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        	g.drawString(scoring2[i], 25 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        	g.drawString(scoring3[i], 30 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        }
        g.drawLine(25 * Tetris.BLOCK_SIZE - 15, 2 * Tetris.BLOCK_SIZE + 15,
     		   	   25 * Tetris.BLOCK_SIZE - 15, 23 * Tetris.BLOCK_SIZE + 15);
        g.drawLine(30 * Tetris.BLOCK_SIZE - 5, 2 * Tetris.BLOCK_SIZE + 15,
  		   	       30 * Tetris.BLOCK_SIZE - 5, 23 * Tetris.BLOCK_SIZE + 15);
        
        //instructions pages
        for(int p = 0; p < Tetris.INS_TOTAL_PAGES; p++) {
        	g.drawOval(150 + p*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
        }
        g.fillOval(150 + page*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
    }
    
    private class BReturn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        Tetris.menu_bgMusic.stop();
        Tetris.click_effect = new MusicPlayer();
        Tetris.click_effect.play(Tetris.click_effect_path, false);
            Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
    
    private class BNext implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        Tetris.click_effect = new MusicPlayer();
        Tetris.click_effect.play(Tetris.click_effect_path, false);
            Tetris.setPage(Tetris.PAGE_MTS);
        }
    }
}