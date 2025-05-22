package game;

   import java.awt.*;
   import java.util.Random;
   
   /**
 * 用於在俄羅斯方塊遊戲中呈現「行消除」動畫的效果。
 * 每個 {@code ClearEffects} 物件代表畫面上某一位置的消除動畫效果。
 *
 * 動畫根據計時器 (timer) 值進行不同階段的顯示，隨時間變化逐漸淡出。
 * @author Maple
 * @version 3.02
 */
public class ClearEffects
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private int X, Y;
	    private int timer;
	    
     // constructors
    public ClearEffects(int x, int y)     //default constructor
      {
    	  X = x;
    	  Y = y;
    	  timer = 0;
      }

    // accessor methods
      public int getX(){ return X;}
      public int getY(){ return Y;}
      public int getT(){ return timer;}
      
   // modifier methods
      public void setX(int x){X = x;} 
      public void setY(int y){Y = y;} 
      public void setT(int t){timer = t;} 
      
    //	 instance methods
      /**
     * 預設消行動畫計時器為20幀
     */
    public void setTimer20(){timer = 20;} 
    
      /**
     * 根據當前 {@code timer} 狀態繪製動畫效果：
     * <ul>
     *   <li>當 {@code timer > 15} 時，繪製一個寬度逐漸增加的紅色矩形。</li>
     *   <li>當 {@code timer <= 15} 且 {@code timer > 0} 時，繪製一個半透明紅色的固定寬度矩形，透明度與 {@code timer} 成正比。</li>
     * </ul>
     * @param g
     */
    public void draw(Graphics g) {
    	  if(timer > 15) {
    		  g.setColor(new Color(255, 0, 0));
              g.fillRect(X-5, Y-10, (20 - timer) * 2 * BLOCK_SIZE+10, 20);
    	  }
    	  else if(timer > 0) {
    		  g.setColor(new Color(255, 0, 0, timer*17));
              g.fillRect(X-5, Y-10, 9 * BLOCK_SIZE+10, 20);
    	  }
      }
   }