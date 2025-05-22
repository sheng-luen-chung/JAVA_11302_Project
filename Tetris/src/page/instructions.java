package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

/**
 * 顯示遊戲指示的畫面，包含如何控制、分數與攻擊點如何計算，並且可以切換到消行示範畫面。
 * 為整個指示和消行示範的第0頁。
 * <p>
 * 此面板會在 Tetris 主畫面中被切換至 INSTRUCTIONS 時啟動。
 * 
 * @author Maple
 * @version 3.02
 */
public class instructions extends JPanel{ 
	private JButton ReturnButton, NextButton, BackButton;
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
								 "Combo (C)",
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
								 "50 x C xLevel",
								 "1 Grid / Per",
								 "2 Grid / Per",
								 "",
								 "800 x Level",
								 "1200 x Level",
								 "1800 x Level",
								 "2000 x Level",
								 "3200 x Level",
								 };
	
	private String[] scoring3 = {"None",
								 "+1",
								 "+2",
								 "+4",
			
								 "None",
								 "None",
			
								 "+1",
								 "+2",
			
								 "+2",
								 "+3",
			
								 "+6",
			 
								 "+1",
								 "+C/2 (+5 max)",
								 "None",
								 "None",
								 "",
								 "+6",
								 "+7",
								 "+8",
								 "+9",
								 "+10",
								 };
	
    /**
     * 初始化面板。
     */
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
    	// Back button
    	BackButton = Tetris.setAndPutButton(this,
				    					    "Back",
				    					    new BBack(),
				    					    -50,
				    					    Tetris.TOTAL_SIZE_Y - 70);
    }
    
    /**
     * 停止面板。
     */
    public void stopPanel() {
    }
    
    /**
     * 顯示玩家控制、分數與攻擊點計算的資訊。
     *
     * @param g 畫圖用的 Graphics 物件
     */
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
        g.drawLine(15 * Tetris.BLOCK_SIZE - 15, 14 * Tetris.BLOCK_SIZE - 22,
		   	       34 * Tetris.BLOCK_SIZE + 15, 14 * Tetris.BLOCK_SIZE - 22);
        
        //instructions pages
        for(int p = 0; p < Tetris.INS_TOTAL_PAGES; p++) {
        	g.drawOval(150 + p*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
        }
        g.fillOval(150 + page*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
    }
    
    /**
     * 點擊 Return 按鈕的事件處理類別，回到主選單。
     */
    private class BReturn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
    
    /**
     * 點擊 Next 按鈕的事件處理類別，切到下一頁。
     * 如果沒有下一頁則到第一頁。
     */
    private class BNext implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_MTS);
        }
    }
    
    /**
     * 點擊 Back 按鈕的事件處理類別，切到上一頁。
     * 如果沒有上一頁則到最後一頁。
     */
    private class BBack implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.setPage(Tetris.PAGE_TST3);
        }
    }
}