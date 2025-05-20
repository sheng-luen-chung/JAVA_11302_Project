package game;

   import java.awt.*;
   import java.util.Random;
   
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
      public void setTimer20(){timer = 20;} 
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