package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

public class MTST2 extends JPanel{
	private JButton ReturnButton, NextButton, BackButton;
	private Timer reTimer, timer;
    private Player p1;
    private Grid g1;
    private final int page = 3;
    private int controlTimer;
    private int controlTimer2;
    private int controlTimer3;
    
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "MTST2");
    	setBackground(Color.BLACK);
        
    	initial();
    	
    	reTimer = new Timer(6000, new reShow());
    	reTimer.start();
        timer = new Timer(15, new gravity_timer());
        timer.start(); 
        
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
    	// Back button
    	BackButton = Tetris.setAndPutButton(this,
				    					    "Back",
				    					    new BBack(),
				    					    -50,
				    					    Tetris.TOTAL_SIZE_Y - 70);
    }  
    public void stopPanel() {
    	reTimer.stop();
    	timer.stop();
    }
    
    private void initial() {
    	p1 = new Player(300,90);
        g1 = new Grid(300,90);
        p1.setDFS(15);
        Point[] pp = {new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1)};
        p1.setShape(pp);
        p1.setS(3);
        p1.setX(p1.getX() + 2);
        p1.setY(p1.getY() + 7);
        
        controlTimer = 210;
        controlTimer2 = 215;
        controlTimer3 = 230;
        
        int[][] resetG = {{0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,0,0},
				  		  {0,0,0,0,0,0,0,0,4,4},
				  		  {0,0,0,0,0,0,0,4,4,1},
				  		  {0,0,0,0,0,0,0,0,0,1},
				  		  {0,0,3,3,3,0,0,0,7,1},
				  		  {2,2,6,3,5,5,0,0,7,1},
				  		  {2,2,6,6,6,5,5,0,7,7},
						};
        
        for(int x = 0; x < Tetris.GRID_COLS; x++) {
        	for(int y = 0; y < Tetris.GRID_ROWS; y++) {
            	g1.setBGarr(x,  y, resetG[y][x]);
            }
        }
        p1.rotate(0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g1.draw(g);
        p1.draw(g);
        
		g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Mini T-Spin Double", Tetris.TOTAL_SIZE_X / 2 - 200, 40);
        
        //instructions pages
        for(int p = 0; p < Tetris.INS_TOTAL_PAGES; p++) {
        	g.drawOval(150 + p*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
        }
        g.fillOval(150 + page*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
        
        repaint();
    }
    
    private class gravity_timer implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
    		
    		// auto control
    		if(controlTimer > 0) {
    			controlTimer--;
    			if(controlTimer == 0) {
    				Tetris.rotate_and_check(p1, g1, 0);
    		}}
    		if(controlTimer2 > 0) {
    			controlTimer2--;
    			if(controlTimer2 == 0) {
    				Tetris.moveBlock(p1, g1, 1, 0);
    		}}
    		if(controlTimer3 > 0) {
    			controlTimer3--;
    			if(controlTimer3 == 0) {
    				Tetris.rotate_and_check(p1, g1, 1);
    		}}
        }  
    }
    
    private class reShow implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		initial();
        } 
    }
    
    private class control implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.rotate_and_check(p1, g1, 0);
        } 
    }

    private class BReturn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
    
    private class BNext implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.setPage(Tetris.PAGE_TS);
        }
    }
    
    private class BBack implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.setPage(Tetris.PAGE_MTST1);
        }
    }
}