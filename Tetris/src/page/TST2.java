package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

/**
 * 顯示 T轉消兩行 示範畫面，並且可以切換到其他指示畫面。
 * 為整個指示和消行示範的第6頁。
 * <p>
 * 此面板會在 Tetris 主畫面中被切換至 TST2 時啟動。
 * 
 * @author Maple
 * @version 3.02
 */
public class TST2 extends JPanel{
	private JButton ReturnButton, NextButton, BackButton;
	private Timer reTimer, timer;
    private Player p1;
    private Grid g1;
    private final int page = 6;
    private int controlTimer;
    
    /**
     * 初始化面板。
     */
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "TST2");
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
    
    /**
     * 停止面板。
     */
    public void stopPanel() {
    	reTimer.stop();
    	timer.stop();
    }
    
    /**
     * 初始化示範遊戲的數值。
     */
    private void initial() {
    	p1 = new Player(300,90);
        g1 = new Grid(300,90);
        p1.setDFS(15);
        Point[] pp = {new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1)};
        p1.setShape(pp);
        p1.setS(3);
        p1.setX(p1.getX() + 1);
        p1.setY(p1.getY() + 8);
        
        controlTimer = 240;
        
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
				  	      {0,0,0,0,0,0,0,0,0,0},
				  	      {0,0,0,0,0,0,0,0,0,0},
				  	      {0,0,0,0,0,0,4,0,0,0},
				  	      {0,7,7,7,0,0,4,4,2,2},
				  	      {6,7,5,5,0,0,0,4,2,2},
				  	      {6,6,6,5,5,0,1,1,1,1},
        				};
        
        for(int x = 0; x < Tetris.GRID_COLS; x++) {
        	for(int y = 0; y < Tetris.GRID_ROWS; y++) {
            	g1.setBGarr(x,  y, resetG[y][x]);
            }
        }
        p1.rotate(0);
    }
    
    /**
     * 在這個頁面的程式循環，但不支援玩家控制。
     *
     * @param g 畫圖用的 Graphics 物件
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g1.draw(g);
        p1.draw(g);
        
		g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("T-Spin Double", Tetris.TOTAL_SIZE_X / 2 - 160, 40);
        
        //instructions pages
        for(int p = 0; p < Tetris.INS_TOTAL_PAGES; p++) {
        	g.drawOval(150 + p*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
        }
        g.fillOval(150 + page*15, Tetris.TOTAL_SIZE_Y - 30, 10, 10);
        
        repaint();
    }
    
    /**
     * 定時呼叫的動作監聽器，用來處理方塊重力下落的邏輯。
     * 也包含這個示範中的自動控制器
     */
    private class gravity_timer implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
    		
    		// auto control
    		if(controlTimer > 0) {
    			controlTimer--;
    			if(controlTimer == 0) {
    				Tetris.rotate_and_check(p1, g1, 0);
    		}}
        }  
    }
    
    /**
     * 重新執行和初始化這個示範的畫面。
     */
    private class reShow implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		initial();
        } 
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
        	Tetris.setPage(Tetris.PAGE_TST3);
        }
    }
    
    /**
     * 點擊 Back 按鈕的事件處理類別，切到上一頁。
     * 如果沒有上一頁則到最後一頁。
     */
    private class BBack implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Tetris.setPage(Tetris.PAGE_TST1);
        }
    }
}