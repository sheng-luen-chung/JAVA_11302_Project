package game;

   import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
   
   public class Grid
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private int Xoffset, Yoffset;
	    private int[][] BGarr;
	    
     // constructors
      public Grid(int x, int y)     //default constructor
      {
    	 Xoffset = x;
    	 Yoffset = y;
         BGarr = new int[10][20];
      }

    // accessor methods
      public int[][] getBGarr(){ return BGarr;}
      
   // modifier methods
      public void setBGarr(int x, int y, int arr){BGarr[x][y] = arr;} 
      
    //	 instance methods
      public void draw(Graphics g) {
    	  int drawX, drawY;
          for(int i=0; i < getBGarr().length; i++) {
          	for(int j=0; j < getBGarr()[i].length; j++) {
          		switch (BGarr[i][j]) {
                  case 1:
                  	g.setColor(Color.CYAN); break;	// I
                  case 2:
                  	g.setColor(Color.YELLOW);	break;	// O
                  case 3:
                  	g.setColor(new Color(192, 0, 192));	break;	// T
                  case 4:
                  	g.setColor(Color.GREEN); break;	// S
                  case 5:
                  	g.setColor(Color.RED);	break;	// Z
                  case 6:
                  	g.setColor(Color.BLUE); break;	// J
                  case 7:
                  	g.setColor(Color.ORANGE); break;	// L
          		}

              	if(BGarr[i][j] > 0) {
              		  drawX = Xoffset + i * BLOCK_SIZE;
              		  drawY = Yoffset + j * BLOCK_SIZE;
                      g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
                      g.setColor(Color.BLACK);
                      g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              	}
              }
          }
          //預覽塊區
          drawX = Xoffset + 10 * BLOCK_SIZE;
          drawY = Yoffset + 0 * BLOCK_SIZE;
          g.setColor(new Color(64, 64, 64));
          g.fillRect(drawX, drawY, BLOCK_SIZE*5, BLOCK_SIZE*4);
          g.setColor(Color.BLACK);
          g.fillRect(drawX+10, drawY+10, BLOCK_SIZE*5-20, BLOCK_SIZE*4-20);
          
          //暫存區塊
          drawX = Xoffset + 10 * BLOCK_SIZE;
          drawY = Yoffset + 4 * BLOCK_SIZE;
          g.setColor(new Color(64, 64, 64));
          g.fillRect(drawX, drawY, BLOCK_SIZE*5, BLOCK_SIZE*4);
          g.setColor(Color.BLACK);
          g.fillRect(drawX+10, drawY+10, BLOCK_SIZE*5-20, BLOCK_SIZE*4-20);
      }
   }