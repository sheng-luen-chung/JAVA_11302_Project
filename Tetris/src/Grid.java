package src;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
   
   public class Grid
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private int[][] BGarr;
       private int score = 0;
	    
     // constructors
      public Grid()     //default constructor
      {
         BGarr = new int[10][20];
      }

    // accessor methods
      public int[][] getBGarr(){ return BGarr;}
      
   // modifier methods
      public void setBGarr(int x, int y, int arr){BGarr[x][y] = arr;} 
   // clear line methods
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
        score += switch (linesCleared) {
        case 1 -> 100;
        case 2 -> 400;
        case 3 -> 800;
        case 4 -> 1600;
        case 5 -> 2400;
        case 6 -> 3200;
        case 7 -> 4000;
        case 8 -> 4800;
        case 9 -> 5600;
        default -> 0;
        };
      }
    //	 instance methods
      public void draw(Graphics g) {
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
              		int drawX = i * BLOCK_SIZE;
                      int drawY = j * BLOCK_SIZE;
                      g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
                      g.setColor(Color.BLACK);
                      g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
                      g.setColor(Color.CYAN);
              	}
               g.setColor(Color.WHITE);
               g.setFont(new Font("Arial", Font.BOLD, 20));
               g.drawString("Score:", (GRID_COLS) * BLOCK_SIZE, 20);
               g.drawRect(GRID_COLS * BLOCK_SIZE, 30, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE);
               g.setFont(new Font("Arial", Font.BOLD, 30));
               g.drawString(String.valueOf(score), (GRID_COLS + 1) * BLOCK_SIZE, 70);
              }
          }
      }
   }