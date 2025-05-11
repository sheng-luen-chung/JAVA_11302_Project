package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class instructions extends JPanel{ 
	private JButton ReturnButton;
	private String[] Controls1 = {"",
								  "Move Left",
						   		  "Move Right",
								  "Soft Drop",
								  "Hard Drop",
								  "Rotate Right",
								  "Rotate Left",
								  "Hold"
								};
	
	private String[] Controls2 = {"",//SOLO / P2",
								  "Right Arrow",
						   		  "Left Arrow",
								  "Down Arrow",
								  "Num 0",
								  "Num 3",
								  "Num 2",
								  "Num 1",
								  ""
								};
	
	private String[] Controls3 = {"P1",
								  "D",
						   		  "A",
								  "S",
								  " ",
								  "B",
								  "V",
								  "C",
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
								 "50 x Combo x Level",
								 "1 Grid / Per",
								 "2 Grid / Per",
								 "",
								 "800 x Level",
								 "1200 x Level",
								 "1800 x Level",
								 "2000 x Level",
								 "3200 x Level",
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
    }
    
    public void stopPanel() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		
        //Controls
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Controls", 1 * Tetris.BLOCK_SIZE, 2 * Tetris.BLOCK_SIZE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for(int i=0; i < Controls1.length; i++) {
        	g.drawString(Controls1[i], 2 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        	g.drawString(Controls2[i], 7 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
//        	g.drawString(Controls3[i], 12 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        }
        
        //Scoring
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Scoring", 16 * Tetris.BLOCK_SIZE, 2 * Tetris.BLOCK_SIZE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for(int i=0; i < scoring1.length; i++) {
        	g.drawString(scoring1[i], 17 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        	g.drawString(scoring2[i], 27 * Tetris.BLOCK_SIZE, 3 * Tetris.BLOCK_SIZE + i * 30);
        }
    }
    
    private class BReturn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}