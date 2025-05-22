package game;

   import java.awt.*;
   import java.util.Random;
   
   		/**
    * 控制方塊的物件。
    * <p>
    * 包含方塊的生成（使用 7-bag 隨機機制）、旋轉、預覽方塊、儲存方塊（Hold 機制）、
    * 方塊下落計時、鎖定延遲（Lock Delay）與出現時間（ARE），並支援繪製目前與影子方塊。
    * </p>
    *
    * @author Maple、lilmu
    * @version 3.02
    */
   public class Player
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private int Xoffset, Yoffset;
	    private int Xshock = 0, Yshock = 0;
	    private Point[] currentShape;
	    private Point[] nextShape;
	    private Point[] holdShape;
	    
	    private int currentX, currentY, currentS, currentD;
	    private int[] nextS;
	    private int holdS;
	    private double dropFarmes, dropFarmesSet;
	    private int lockDelay, lockDelaySet;
	    private int ARE, AREset;
	    
	    private boolean hold;
	    private boolean Tspin;
	    private boolean speedUP;
	    private boolean lastKick;
	    
	    private Random rand = new Random();
	    
	    private static final Point[][] SHAPES = {
	            { new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1) }, // I
	            { new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1) }, // O
	            { new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // T
	            { new Point(1,0), new Point(2,0), new Point(0,1), new Point(1,1) }, // S
	            { new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1) }, // Z
	            { new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // J
	            { new Point(2,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // L
	     };
     // constructors
      public Player(int x, int y)     //default constructor
      {
    	  Xoffset = x;
    	  Yoffset = y;
    	  nextS = new int[10];
    	  dropFarmesSet = 48;
    	  lockDelaySet = 30;
    	  AREset = 20;
    	  
    	  dropFarmes = dropFarmesSet;
    	  lockDelay = lockDelaySet;
    	  ARE = AREset;
    	  spawn7bag();
    	  spawnNewShape();
      }

    // accessor methods
      public int getXO(){ return Xoffset;}
      public int getyO(){ return Yoffset;}
      public int getXS(){ return Xshock;}
      public int getYS(){ return Yshock;}
      public int getX(){ return currentX;}
      public int getY(){ return currentY;}
      public int getS(){ return currentS;}
      public int getD(){ return currentD;}
      
      public double getDF(){ return dropFarmes;}
      public double getDFS(){ return dropFarmesSet;}
      
      public int getLD(){ return lockDelay;}
      public int getLDS(){ return lockDelaySet;}
      
      public int getARE(){ return ARE;}
      public int getARES(){ return AREset;}
      
      public Point[] getShape(){ return currentShape;}
      public boolean getTspin(){ return Tspin;}
      public boolean getSpeedUP(){ return speedUP;}
      public boolean getLK(){ return lastKick;}
      
   // modifier methods
      public void setXO(int xo){Xoffset = xo;}
      public void setYO(int yo){Xoffset = yo;}
      public void setXS(int xs){Xshock = xs;}
      public void setYS(int ys){Yshock = ys;}
      public void setX(int x){currentX = x;} 
      public void setY(int y){currentY = y;} 
      public void setS(int s){currentS = s;}
      public void setD(int dir) {
    	  currentD = dir % 4;
    	  if(currentD < 0) currentD += 4;
      }
      
      public void setDF(double d){dropFarmes = d;}
      public void setDFS(double d){dropFarmesSet = d;}
      
      public void setLD(int l){lockDelay = l;}
      public void setLDS(int l){lockDelaySet = l;}
      
      public void setARE(int a){ARE = a;}
      public void setARES(int a){AREset = a;}
      
      public void setShape(Point[] s){currentShape = s;} 
      public void setTspin(boolean Ts){Tspin = Ts;}
      public void setSpeedUP(boolean Sp){speedUP = Sp;}
      public void setLK(boolean Lk){lastKick = Lk;}
      
    //	 instance methods
      	/**
       * 產生一組新的 7-bag 隨機順序，並填入 `nextS` 中作為即將出現的方塊順序。
       * 若 `nextS[0]` 為空（0），則會自動將新順序推入前方(第一次創建才會發生)。
       */
    public void spawn7bag() {
    	  int[] bag = {1, 2, 3, 4, 5, 6, 7};
    	  //bag[] to nextShape[]
    	  for(int i=0; i<bag.length; i++) {
    		  int r = rand.nextInt(bag.length-i);
    		  nextS[i+1] = bag[r];
    		  for(int j=r; j<bag.length-1; j++) {
    			  bag[j] = bag[j+1];
    		  }
    	  }
    	  if(nextS[0] == 0) {
    		  for(int i=0; i<nextS.length-1; i++) {
            	  nextS[i] = nextS[i+1];
              }
    	  }
      }
      
    	/**
     * 根據目前狀態生成新的方塊。
     * <ul>
     *   <li>若使用者剛使用 Hold，則從 Hold 區取出方塊。</li>
     *   <li>否則從 next queue 中取出。</li>
     *   <li>同時處理 ARE（出現延遲）、Lock Delay（鎖定延遲）等變數重設。</li>
     *   <li>若 next Shape 中僅剩一個方塊，則自動生成下一組 7-bag。</li>
     * </ul>
     */
      public void spawnNewShape() {
    	  //current shape set
          currentX = GRID_COLS / 2 - 2;
          currentY = -2;
          currentD = 0;
          lockDelay = lockDelaySet;
          ARE = AREset;
          if(hold) {
        	  currentS = holdS;
        	  currentShape = holdShape;
        	  hold = false;
          }
          else {
        	  currentS = nextS[0];
              currentShape = SHAPES[currentS-1];
              for(int i=0; i<nextS.length-1; i++) {
            	  nextS[i] = nextS[i+1];
              }
          }
          
          //next shape set
          nextShape = SHAPES[nextS[0]-1];
          if(nextS[1] == 0) {
        	  spawn7bag();
          }
      }
      
      	/**
       * 將目前的方塊儲存至 Hold 區。
       * <p>
       * 若之前尚未使用 Hold，則：
       * <ul>
       *   <li>儲存目前方塊至 Hold 區</li>
       *   <li>立刻生成新的方塊以補位</li>
       *   <li>如果Hold區有方塊就不再能使用</li>
       * </ul>
       * 若已使用 Hold，則此方法不執行任何動作。
       */
      public void holdCurrentShape() {
    	  //hold shape set
    	  if(!hold) {
    		  holdS = currentS;
    		  holdShape = SHAPES[holdS-1];
    		  spawnNewShape();
    		  hold = true;
    	  }
      }
      
      	/**
       * 旋轉目前的方塊。
       *
       * @param dir 旋轉方向：0 表示逆時針（左轉），非 0 表示順時針（右轉）
       * <p>
       * 根據方塊種類設定旋轉軸（pivot）後，計算所有方塊的新座標。
       * 支援 I, O 以及其他 5 種標準方塊。
       * <p>
       * 完成後更新方向指標（`currentD`）與當前形狀（`currentShape`）。
       */
      public void rotate(int dir) {
    	  double pivot_x;
    	  double pivot_y;
          Point[] rotated = new Point[getShape().length];
          
          switch (currentS) {	//find pivot
          case 1:	// I
        	  pivot_x = 1.5;
        	  pivot_y = 1.5;
        	  break;
        	  
          case 2:	// O
        	  pivot_x = 0.5;
        	  pivot_y = 0.5;
        	  break;
          default:	//other
        	  pivot_x = 1;
        	  pivot_y = 1;
          }
          
          //rotate
          for (int i = 0; i < getShape().length; i++) {
              double x = -(getShape()[i].x - pivot_x);
              double y = -(getShape()[i].y - pivot_y);
              
              //dir=0 -> L, dir!=0 -> R, R:(x,y)->(y, -x), L:(x,y)->(-y, x)
              if(dir != 0) rotated[i] = new Point((int)(pivot_x + y), (int)(pivot_y - x));
              else rotated[i] = new Point((int)(pivot_x - y), (int)(pivot_y + x));    
          }
          //update direction
          if(dir != 0) setD(getD()+1);
          else setD(getD()-1);
          
          setShape(rotated);
      }
      
      	/**
       * 根據方塊類型設定顏色。
       *
       * @param g 繪圖物件 Graphics
       * @param c 方塊代號（1~7 為標準方塊，8 為垃圾行）
       * @param r 顏色透明度（alpha 值，0~255）
       */
      private void switchColor(Graphics g, int c, int r) {
    	  switch (c) {
	          case 1:
	          	g.setColor(new Color(0, 255, 255, r)); break;	// I, CYAN
	          case 2:
	          	g.setColor(new Color(255, 255, 0, r));	break;	// O, YELLOW
	          case 3:
	          	g.setColor(new Color(255, 0, 255, r));	break;	// T, PURPLE
	          case 4:
	          	g.setColor(new Color(0, 255, 0, r)); break;	// S, GREEB
	          case 5:
	          	g.setColor(new Color(255, 0, 0, r));	break;	// Z, RED
	          case 6:
	          	g.setColor(new Color(0, 0, 255, r)); break;	// J, BLUE
	          case 7:
	          	g.setColor(new Color(255, 200, 0, r)); break;	// L, ORANGE
	          case 8:
		        g.setColor(new Color(128, 128, 128, r)); break;	// GarbageLines
    	  }
      }
      
      	/**
       * 繪製目前方塊、下一個方塊、以及（若有）儲存方塊於畫面。
       *
       * @param g Swing 提供的繪圖元件 Graphics，用於繪製方塊。
       * <p>
       * 顯示目前方塊於遊戲場中央，
       * 下一個方塊於右側提示欄位，
       * 儲存方塊（如已使用 Hold）則也繪製在右側。
       */
      public void draw(Graphics g) {
    	  int drawX, drawY;
    	  Xoffset += Xshock;
    	  Yoffset += Yshock;
          for (Point p : currentShape) {
              drawX = Xoffset + (currentX + p.x) * BLOCK_SIZE;
              drawY = Yoffset + (currentY + p.y) * BLOCK_SIZE;
              switchColor(g, currentS, 105+(int)(150*((double)lockDelay/(double)lockDelaySet)));
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          
          for (Point p : nextShape) {
              drawX = Xoffset + (11 + p.x) * BLOCK_SIZE + 15;
              drawY = Yoffset + (5 + p.y) * BLOCK_SIZE - (BLOCK_SIZE / 2);
        	  switch(nextS[0]) {
    	  	  case 1:
    	  		  drawX -= BLOCK_SIZE / 2;
    	  		  drawY -= BLOCK_SIZE / 2;
    	  		  break;
    	  	  case 2:
    	  		  drawX += BLOCK_SIZE / 2;
    	  		  break;
        	  }
              switchColor(g, nextS[0], 255);
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          
          if(hold) {
          for (Point p : holdShape) {
              drawX = Xoffset + (11 + p.x) * BLOCK_SIZE + 15;
              drawY = Yoffset + (9 + p.y) * BLOCK_SIZE - (BLOCK_SIZE / 2);
        	  switch(holdS) {
        	  	  case 1:
        	  		  drawX -= BLOCK_SIZE / 2;
        	  		  drawY -= BLOCK_SIZE / 2;
        	  		  break;
        	  	  case 2:
        	  		  drawX += BLOCK_SIZE / 2;
        	  		  break;
        	  }
              switchColor(g, holdS, 255);
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          }
          Xoffset -= Xshock;
    	  Yoffset -= Yshock;
      }
      
      	/**
       * 繪製目前方塊的影子（Ghost Piece），用以預覽落地位置。
       *
       * @param g 繪圖元件
       * @param y 與目前 Y 座標的距離，用以將影子方塊繪製於正確落點
       * <p>
       * 使用半透明顏色表示，並保持與目前方塊一致外觀。
       */
      public void drawShadow(Graphics g, int y) {
    	  int drawX, drawY;
    	  Xoffset += Xshock;
    	  Yoffset += Yshock;
          for (Point p : currentShape) {
              drawX = Xoffset + (currentX + p.x) * BLOCK_SIZE;
              drawY = Yoffset + (currentY + p.y + y) * BLOCK_SIZE;
              switchColor(g, currentS, 125);
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          Xoffset -= Xshock;
    	  Yoffset -= Yshock;
      }
   }