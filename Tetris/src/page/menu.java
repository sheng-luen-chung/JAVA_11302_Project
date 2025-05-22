package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

/**
 * 主選單的面板類別。
 * <p>
 * 此面板會在 Tetris 主畫面中被切換至 MENU 時啟動。
 * 
 * @author Maple
 * @version 3.02
 */
public class menu extends JPanel{ 
	private JButton startButton, exitButton, InstructionsButton;
	
    /**
     * 初始化面板。
     */
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
    
    /**
     * 停止面板。
     */
    public void stopPanel() {
    }
    
    /**
     * 顯示Tetris標題。
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 80));
        g.drawString("Tetris", Tetris.TOTAL_SIZE_X / 2 -130, Tetris.TOTAL_SIZE_Y / 2-80);
    }
    
    /**
     * 點擊 Start 按鈕的事件處理類別，進到模式選擇。
     */
    private class BStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_MODE);
        }
    }
    
    /**
     * 點擊 Instructions 按鈕的事件處理類別，進到遊戲指示的第一頁。
     */
    private class BInstructions implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tetris.setPage(Tetris.PAGE_INSTRUCTIONS);
        }
    }
    
    /**
     * 點擊 Exit 按鈕的事件處理類別，關閉視窗並結束程式。
     */
    private class BExit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}