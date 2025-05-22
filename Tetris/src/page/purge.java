package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.*;

/**
 * purge的遊戲面板類別。
 * 此模式中，兩名玩家由完全實心的垃圾行規則進行對戰，垃圾行需要獲得攻擊點後立即消除。
 * 當一名玩家擁有攻擊點且當前不是冷卻階段時進入攻擊階段，在階段計時歸零後互抵結算垃圾行。
 * <p>
 * 此面板會在 Tetris 主畫面中被切換至 PURGE 時啟動。
 * 
 * @author Maple
 * @version 3.02
 */
public class purge extends JPanel{
	private JButton pauseButton, continueButton, restartButton, menuButton;
	private Timer timer, blinkTimer;
    private boolean BK;
    private Player p1, p2;
    private Grid g1, g2;
    private boolean pause;
    private int attackTimer;
    private int cooldownTimer;
    private int sendGarbageTo1;
    private int sendGarbageTo2;
    
    /**
     * 初始化面板、玩家、網格、計時器與背景音樂。
     */
    public void startPanel() {
    	Tetris.cardLayout.show(Tetris.mainPanel, "PURGE");
    	setBackground(Color.BLACK);
    	
        p1 = new Player(60,90);
        g1 = new Grid(60,90);
        
        p2 = new Player(30 + Tetris.TOTAL_SIZE_X / 2,90);
        g2 = new Grid(30 + Tetris.TOTAL_SIZE_X / 2,90); 
        
        attackTimer = 0;
        cooldownTimer = 0;
        sendGarbageTo1 = 0;
        sendGarbageTo2 = 0;
        
        pause = false;
        
        timer = new Timer(15, new gravity_timer());
        timer.start();
        blinkTimer = new Timer(500, new blink());
        Tetris.battle_bgMusic = new MusicPlayer();
        Tetris.battle_bgMusic.play(Tetris.battle_bgMusic_path, true);
        
        setLayout(null);
     	// Pause button
        pauseButton = Tetris.setAndPutButton(this,
				   							 "Pause",
				   							 new BPause(),
				   							 Tetris.TOTAL_SIZE_X - 185,
				   							 0);
    }  
    
    /**
     * 停止面板，儲存分數與結束計時器與背景音樂。
     */
    public void stopPanel() {
    	Tetris.score1 = g1.getScore();
    	Tetris.lines1 = g1.getLinesC();
    	Tetris.score2 = g2.getScore();
    	Tetris.lines2 = g2.getLinesC();
    	if(g1.getWin()) Tetris.winner1 = true;
    	else Tetris.winner1 = false;
    	if(g2.getWin()) Tetris.winner2 = true;
    	else Tetris.winner2 = false;
    	Tetris.overReturn = Tetris.PAGE_PURGE;
    	

    	timer.stop();
    	blinkTimer.stop();
    	Tetris.battle_bgMusic.stop();
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
    		if(Tetris.Key_A) Tetris.moveBlock(p1, g1, -1, 0); Tetris.Key_A = false;
        	if(Tetris.Key_D) Tetris.moveBlock(p1, g1, 1, 0); Tetris.Key_D = false;
        	if(Tetris.Key_S) p1.setSpeedUP(true); else p1.setSpeedUP(false);
        	if(Tetris.Key_B) Tetris.rotate_and_check(p1, g1, 1); Tetris.Key_B = false;
        	if(Tetris.Key_V) Tetris.rotate_and_check(p1, g1, 0); Tetris.Key_V = false;
        	if(Tetris.Key_C) p1.holdCurrentShape(); Tetris.Key_C = false;
        	if(Tetris.Key_SPACE) Tetris.hard_drop(p1, g1); Tetris.Key_SPACE = false;
        	if(Tetris.Key_ESC) pauseGame(); Tetris.Key_ESC = false;
        	
        	if(Tetris.Key_LEFT) Tetris.moveBlock(p2, g2, -1, 0); Tetris.Key_LEFT = false;
        	if(Tetris.Key_RIGHT) Tetris.moveBlock(p2, g2, 1, 0); Tetris.Key_RIGHT = false;
        	if(Tetris.Key_DOWN) p2.setSpeedUP(true); else p2.setSpeedUP(false);
        	if(Tetris.Key_NUM_3) Tetris.rotate_and_check(p2, g2, 1); Tetris.Key_NUM_3 = false;
        	if(Tetris.Key_NUM_2) Tetris.rotate_and_check(p2, g2, 0); Tetris.Key_NUM_2 = false;
        	if(Tetris.Key_NUM_1) p2.holdCurrentShape(); Tetris.Key_NUM_1 = false;
        	if(Tetris.Key_NUM_0) Tetris.hard_drop(p2, g2); Tetris.Key_NUM_0 = false;
        	if(Tetris.Key_NUM_9) pauseGame(); Tetris.Key_NUM_9 = false;
        }
        else {
        	if(Tetris.Key_ESC || Tetris.Key_NUM_9) continueGame(); Tetris.Key_ESC = false; Tetris.Key_NUM_9 = false;
        }
        
        //show current mode
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Mode : Purge", 5, Tetris.TOTAL_SIZE_Y-20);
        
        g1.draw(g);
        p1.draw(g);
        Tetris.findShadowAndDraw(g, p1, g1);
        g2.draw(g);
        p2.draw(g);
        Tetris.findShadowAndDraw(g, p2, g2);
        
        //battle mode
        if(attackTimer > 0) {
        	g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Attack Phase", 10, 40);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(String.valueOf((attackTimer+30) / 60), Tetris.TOTAL_SIZE_X / 2 - 50, 60);
        }
        if(cooldownTimer > 0) {
        	g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Cooldown Phase", 10, 40);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(String.valueOf((cooldownTimer+30) / 60), Tetris.TOTAL_SIZE_X / 2 - 50, 60);
        }
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(String.valueOf(g1.getA()), Tetris.TOTAL_SIZE_X / 2 - 150, 60);
        
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(String.valueOf(g2.getA()), Tetris.TOTAL_SIZE_X / 2 + 50, 60);
        
        //pause
        if(pause) drawPause(g);
        
        repaint();
    }
    
    /**
     * 定時呼叫的動作監聽器，用來處理方塊重力下落的邏輯。
     * 也處理對戰模式中回合、發送垃圾行的系統。
     */
    private class gravity_timer implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tetris.gravity_drop(p1, g1);
    		Tetris.gravity_drop(p2, g2);
    		
    		// Clear Garbage
    		g1.setA(g1.getA() - Tetris.clearGarbageLines(p1, g1, g1.getA()));
    		g2.setA(g2.getA() - Tetris.clearGarbageLines(p2, g2, g2.getA()));
    		
    		// Send Garbage
    		if(sendGarbageTo2 > 0) sendGarbageTo2 = Tetris.spawnGarbageLines(p2, g2, sendGarbageTo2, false);
    		if(sendGarbageTo1 > 0) sendGarbageTo1 = Tetris.spawnGarbageLines(p1, g1, sendGarbageTo1, false);
    		
    		// Phase Control
    		if(cooldownTimer > 0) {
    			cooldownTimer--;
    		}
    		else if(attackTimer > 0) {
    			attackTimer--;
    			if(attackTimer == 0) {
    				if(g1.getA() >= g2.getA()) sendGarbageTo2 = g1.getA() - g2.getA();
    				else if(g1.getA() < g2.getA()) sendGarbageTo1 = g2.getA() - g1.getA();
    				g1.setA(0);
    				g2.setA(0);
    				cooldownTimer = 300;
    			}
    		}
    		else if((g1.getA() >0 || g2.getA() >0) && cooldownTimer == 0) attackTimer = 600;
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
        	Tetris.setPage(Tetris.PAGE_PURGE);
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