package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class menu extends JPanel{ 
	private JButton startButton, exitButton, InstructionsButton;
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "MENU");
    	setBackground(Color.BLACK);
    	
    	setLayout(null);
    	// Start buttons
    	startButton = Tetris.setAndPutButton(this,
    						   				 "Start",
    						   				 new BStart(),
    						   				 Tetris.TOTAL_SIZE_X / 2 - 150,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 50);
    	// Instructionst buttons
    	InstructionsButton = Tetris.setAndPutButton(this,
    						   				 "Instructions",
    						   				 new BInstructions(),
    						   				 Tetris.TOTAL_SIZE_X / 2 - 150,
    						   				 Tetris.TOTAL_SIZE_Y / 2 + 130);
    	
    	// Exit buttons
    	exitButton = Tetris.setAndPutButton(this,
    						   				"Exit",
    						   				new BExit(),
    						   				Tetris.TOTAL_SIZE_X / 2 - 150,
    						   				Tetris.TOTAL_SIZE_Y / 2 + 210);
    }
    
    public void stopPanel() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Tetris", Tetris.TOTAL_SIZE_X / 2 - 80, Tetris.TOTAL_SIZE_Y / 2);
    }
    
    private class BStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_MODE);
        }
    }
    
    private class BInstructions implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_INSTRUCTIONS);
        }
    }
    
    private class BExit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}