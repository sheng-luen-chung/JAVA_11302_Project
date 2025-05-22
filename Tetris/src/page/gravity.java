package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

/**
 * gravity的遊戲面板類別。
 * 此模式中，單一玩家會隨著Level上升而被增加掉落速度。
 * <p>
 * 此面板會在 Tetris 主畫面中被切換至 GRAVITY 時啟動。
 * @author Maple
 * @version 3.02
 */
public class gravity extends JPanel{
	private JButton pauseButton, continueButton, restartButton, menuButton;
	private Timer timer, blinkTimer;
    private boolean BK;
    private Player p1;
    private Grid g1;
    private boolean pause;
    
    /**
     * 初始化面板、玩家、網格、計時器與背景音樂。
     */
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "GRAVITY");
    	setBackground(Color.BLACK);
    	
        p1 = new Player(300,90);
        g1 = new Grid(300,90);
        pause = false;
        
        timer = new Timer(15, new gravity_timer());
        timer.start();
        blinkTimer = new Timer(500, new blink());
        Tetris.TWG_bgMusic = new MusicPlayer();
        Tetris.TWG_bgMusic.play(Tetris.TWG_bgMusic_path, true);
        
        setLayout(null);
     	// Pause button
        pauseButton = Tetris.setAndPutButton(this,
				   							 "Pause",
				   							 new BPause(),
				   							 Tetris.TOTAL_SIZE_X - 185,
				   							 0);
        p1.setDFS(0.05);
    }  
    
    /**
     * 停止面板，儲存分數與結束計時器與背景音樂。
     */
    public void stopPanel() {
    	Tetris.score1 = g1.getScore();
    	Tetris.lines1 = g1.getLinesC();
    	Tetris.overReturn = Tetris.PAGE_GRAVITY;
    	
    	timer.stop();
    	blinkTimer.stop();
    	Tetris.TWG_bgMusic.stop();
    }
    
    /**
     * 在這個頁面的程式循環，並依據按鍵更新遊戲邏輯與控制。
     *
     * @param g 畫圖用的 Graphics 物件
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!pause) {
        	if(Tetris.Key_A || Tetris.Key_LEFT) Tetris.moveBlock(p1, g1, -1, 0); Tetris.Key_A = false; Tetris.Key_LEFT = false;
        	if(Tetris.Key_D || Tetris.Key_RIGHT) Tetris.moveBlock(p1, g1, 1, 0); Tetris.Key_D = false; Tetris.Key_RIGHT = false;
        	if(Tetris.Key_S || Tetris.Key_DOWN) p1.setSpeedUP(true); else p1.setSpeedUP(false);
        	if(Tetris.Key_B || Tetris.Key_NUM_3) Tetris.rotate_and_check(p1, g1, 1); Tetris.Key_B = false; Tetris.Key_NUM_3 = false;
        	if(Tetris.Key_V || Tetris.Key_NUM_2) Tetris.rotate_and_check(p1, g1, 0); Tetris.Key_V = false; Tetris.Key_NUM_2 = false;
        	if(Tetris.Key_C || Tetris.Key_NUM_1) p1.holdCurrentShape(); Tetris.Key_C = false; Tetris.Key_NUM_1 = false;
        	if(Tetris.Key_SPACE || Tetris.Key_NUM_0) Tetris.hard_drop(p1, g1); Tetris.Key_SPACE = false; Tetris.Key_NUM_0 = false;
        	if(Tetris.Key_ESC || Tetris.Key_NUM_9) pauseGame(); Tetris.Key_ESC = false; Tetris.Key_NUM_9 = false;
        }
        else {
        	if(Tetris.Key_ESC || Tetris.Key_NUM_9) continueGame(); Tetris.Key_ESC = false; Tetris.Key_NUM_9 = false;
        }
        
        //show current mode
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Mode : 20 Gravity", 5, Tetris.TOTAL_SIZE_Y-20);
        
        g1.draw(g);
        p1.draw(g);
        Tetris.findShadowAndDraw(g, p1, g1);
        
        //pause
        if(pause) drawPause(g);
        
        repaint();
    }
    
    /**
     * 定時呼叫的動作監聽器，用來處理方塊重力下落的邏輯。
     */
    private class gravity_timer implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
        }  
    }
    
    /**
     * 閃爍效果的計時器，用於顯示 PAUSE 文字閃爍。
     */
    private class blink implements ActionListener{
    	public void actionPerformed(ActionEvent e){
            BK = !BK;
            repaint();
        } 
    }
    
    /**
     * 繪製暫停時的畫面。
     *
     * @param g 畫圖用的 Graphics 物件
     */
    private void drawPause(Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 155,
    			   Tetris.TOTAL_SIZE_Y / 2 - 230,
    			   260,
    			   420);
    	g.setColor(Color.ORANGE);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 150,
    			   Tetris.TOTAL_SIZE_Y / 2 - 225,
    			   250,
    			   410);
    	g.setColor(Color.BLACK);
    	g.fillRect(Tetris.TOTAL_SIZE_X / 2 - 140,
    			   Tetris.TOTAL_SIZE_Y / 2 - 215,
    			   230,
    			   390);
    	
    	if(!BK) {
    		g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSE", Tetris.TOTAL_SIZE_X / 2 - 95, Tetris.TOTAL_SIZE_Y / 2 - 120);
    	}
    }
    
    /**
     * 進入暫停狀態，停止遊戲計時器與顯示選項按鈕。
     */
    private void pauseGame() {
    	pause = true;
    	timer.stop();
        blinkTimer.start();
        BK = false;
    	remove(pauseButton);
    	
    	// Continue button
    	continueButton = Tetris.setAndPutButton(this,
				 								"Continue",
				 								new BContinue(),
				 								Tetris.TOTAL_SIZE_X / 2 - 150,
				 								Tetris.TOTAL_SIZE_Y / 2 -80);
    	// Restart button
    	restartButton = Tetris.setAndPutButton(this,
											   "Restart",
											   new BRestart(),
											   Tetris.TOTAL_SIZE_X / 2 - 150,
											   Tetris.TOTAL_SIZE_Y / 2);
    	
    	// Menu button
    	menuButton = Tetris.setAndPutButton(this,
			    							"Menu",
			    							new BMenu(),
			    							Tetris.TOTAL_SIZE_X / 2 - 150,
			    							Tetris.TOTAL_SIZE_Y / 2 + 80);
    }
    
    /**
     * 結束暫停狀態，恢復遊戲與隱藏選項按鈕。
     */
    private void continueGame() {
    	pause = false;
    	timer.start();
    	blinkTimer.stop();
    	// Pause button
    	pauseButton = Tetris.setAndPutButton(this,
				 							 "Pause",
				 							 new BPause(),
				 							 Tetris.TOTAL_SIZE_X - 185,
				 							 0);
    	
    	remove(continueButton);
    	remove(restartButton);
    	remove(menuButton);
    }
    
    /**
     * 點擊 Pause 按鈕的事件處理類別，暫停遊戲。
     */
    private class BPause implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	pauseGame();
        }
    }
    
    /**
     * 點擊 Continue 按鈕的事件處理類別，在暫停時繼續遊戲。
     */
    private class BContinue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	continueGame();
        }
    }
    
    /**
     * 點擊 Restart 按鈕的事件處理類別，重新啟動該頁面。
     */
    private class BRestart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	remove(continueButton);
        	remove(menuButton);
        	remove(restartButton);
        	Tetris.setPage(Tetris.PAGE_GRAVITY);
        }
    }
    
    /**
     * 點擊 Menu 按鈕的事件處理類別，返回主選單畫面。
     */
    private class BMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	remove(continueButton);
        	remove(menuButton);
        	remove(restartButton);
        	Tetris.menu_bgMusic = new MusicPlayer();
            Tetris.menu_bgMusic.play(Tetris.menu_bgMusic_path, true);
        	Tetris.setPage(Tetris.PAGE_MENU);
        }
    }
}