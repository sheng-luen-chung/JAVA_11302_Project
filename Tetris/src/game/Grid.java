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
	    private int score = 0;
	    
     // constructors
      public Grid(int x, int y)     //default constructor
      {
    	 Xoffset = x;
    	 Yoffset = y;
         BGarr = new int[10][20];
      }

    // accessor methods
      public int getScore(){ return score;}
      public int[][] getBGarr(){ return BGarr;}
      
   // modifier methods
      public void setScore(int s){ score = s;}
      public void setBGarr(int x, int y, int arr){ BGarr[x][y] = arr;} 
      
    //	 instance methods
      public void clearFullLines() {
          int linesCleared = 0;
          for (int y = GRID_ROWS - 1; y >= 0; y--) {
             boolean isFull = true;

         // Check if the row is full
             for (int x = 0; x < GRID_COLS; x++) {
                if (BGarr[x][y] == 0) {
                   isFull = false;
                   break;
                }
             }

             if (isFull) {
             // Shift all rows above down
                linesCleared++;
                for (int yy = y; yy > 0; yy--) {
                   for (int x = 0; x < GRID_COLS; x++) {
                      BGarr[x][yy] = BGarr[x][yy - 1];
                   }
                }
             // Clear top row
                for (int x = 0; x < GRID_COLS; x++) {
                   BGarr[x][0] = 0;
                }
             // Recheck current row since it's now filled with the above row
                y++;
             }
          }
         int s;
         switch (linesCleared) {
         case 1: s=100; break;
         case 2: s=400; break;
         case 3: s=800; break;
         case 4: s=1600; break;
         case 5: s=2400; break;
         case 6: s=3200; break;
         case 7: s=4000; break;
         case 8: s=4800; break;
         case 9: s=5600; break;
         default: s=0; break;
         };
         score += s;
       }
      
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
          
          g.setColor(Color.WHITE);
          //Score:
          drawX = Xoffset + GRID_COLS * BLOCK_SIZE;
          drawY = Yoffset + 0 * BLOCK_SIZE;
          g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Score:", drawX+5, drawY+20);
          g.drawRect(drawX, drawY+BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE);
          //Score
          g.setFont(new Font("Arial", Font.BOLD, 30));
          g.drawString(String.valueOf(score), (GRID_COLS + 1) * BLOCK_SIZE, 70);
          
          //Next:
          drawY = Yoffset + 3*BLOCK_SIZE;
          g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Next:", drawX+5, drawY+20);
          g.drawRect(drawX, drawY+BLOCK_SIZE , 5*BLOCK_SIZE, 3*BLOCK_SIZE);
          
          //Hold:
          drawY = Yoffset + 7*BLOCK_SIZE;
          g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Hold:", drawX+5, drawY+20);
          g.drawRect(drawX, drawY+BLOCK_SIZE, 5*BLOCK_SIZE, 3*BLOCK_SIZE);
      }
   }