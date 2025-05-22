package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

/**
 * 選擇模式的面板類別。
 * <p>
 * 此面板會在 Tetris 主畫面中被切換至 MODE 時啟動。
 * 
 * @author Maple
 * @version 3.02
 */
public class mode extends JPanel{ 
	private JButton classicButton, gravityButton, gapButton, purgeButton, surviveButton;
	
    /**
     * 初始化面板。
     */
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
    
    /**
     * 停止面板。
     */
    public void stopPanel() {
    	Tetris.menu_bgMusic.stop();
    }
    
    /**
     * 顯示字樣。
     */
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
    
    /**
     * 點擊 Classic 按鈕的事件處理類別，進到classic模式。
     */
    private class BClassic implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_CLASSIC);
        }
    }
    
    /**
     * 點擊 Gravity 按鈕的事件處理類別，進到20G模式。
     */
    private class BGravity implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_GRAVITY);
        }
    }
    
    /**
     * 點擊 Gap 按鈕的事件處理類別，進到Gap模式。
     */
    private class BGap implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_GAP);
        }
    }
    
    /**
     * 點擊 Purge 按鈕的事件處理類別，進到Purge模式。
     */
    private class BPurge implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_PURGE);
        }
    }
    
    /**
     * 點擊 Survive 按鈕的事件處理類別，進到Survive模式。
     */
    private class BSurvive implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_SURVIVE);
        }
    }
}