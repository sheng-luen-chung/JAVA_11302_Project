package game;

   import java.awt.*;
   
   public class Grid
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private int Xoffset, Yoffset;
	    private int Xshock = 0, Yshock = 0;
	    private int[][] BGarr;
	    private int action = 0;
	    private int score = 0;
	    private int SS=0, BS=0, AS=0, CS=0;
	    // SS = Regular Score
	    // BS = BTB Score
	    // AS = All Cleared Score
	    // CS = Combo Score
	    
	    private int lines_cleared=0;
	    private int combo = 0;
	    private int level = 0;
	    private int attack = 0;
	    private boolean B2B;
	    
     // constructors
      public Grid(int x, int y)     //default constructor
      {
    	 Xoffset = x;
    	 Yoffset = y;
         BGarr = new int[10][20];
      }

    // accessor methods
      public int getXO(){ return Xoffset;}
      public int getYO(){ return Yoffset;}
      public int getXS(){ return Xshock;}
      public int getYS(){ return Yshock;}
      public int getA(){ return attack;}
      public int getLinesC(){ return lines_cleared;}
      public int getScore(){ return score;}
      public int[][] getBGarr(){ return BGarr;}
      public boolean getB2B(){ return B2B;}
      
   // modifier methods
      public void setXO(int xo){Xoffset = xo;}
      public void setYO(int yo){Xoffset = yo;}
      public void setXS(int xs){Xshock = xs;}
      public void setYS(int ys){Yshock = ys;}
      public void setA(int a){attack = a;}
      public void setLinesC(int lc){lines_cleared = lc;}
      public void setScore(int s){score = s;}
      public void setBGarr(int x, int y, int arr){BGarr[x][y] = arr;} 
      public void setB2B(boolean b){ B2B = b;}
      
    //	 instance methods
      public int clearFullLines() {
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
            	 //start playing music
                 Tetris.clearLine_effect = new MusicPlayer();
                 Tetris.clearLine_effect.play(Tetris.clearLine_effect_path, false);
                 
                 // Shift all rows above down
                linesCleared++;
                for (int yy = y; yy > 0; yy--) {
                   for (int x = 0; x < GRID_COLS; x++) {
                      BGarr[x][yy] = BGarr[x][yy - 1];
                   }
                }
                // Clear top row
                for (int x = 0; x < GRID_COLS; x++) BGarr[x][0] = 0;
                // Recheck current row since it's now filled with the above row
                y++;
             }
          }
          lines_cleared += linesCleared;
          return linesCleared;
       }
       
      public void calcuateScore(int linesCleared, int T_spin) {
    	  String addScoreCase = linesCleared + "," + T_spin;
    	  boolean B2B_buffer = B2B;
    	  BS = 0;
	      switch(addScoreCase) {	//(linesCleared, T_spin)
	      	  case "1,0":				//Single
	      		  action = 1;
	      		  SS = 100;
	      		  B2B_buffer = false; 
	      		  break;
	      	  case "2,0":				//Double
	      		  action = 2;
	      		  attack += 1;
	      		  SS = 300;
	      		  B2B_buffer = false;
	      		  break;
	      	  case "3,0":				//Triple
	      		  action = 3;
	      		  attack += 2;
	      		  SS = 500;
	      		  B2B_buffer = false;
	      		  break;
	      	  case "4,0":				//Quadruple
	      		  action = 4;
	      		  attack += 4;
	      		  SS = 800;
	      		  if(B2B){ BS = 800/2; attack++;}	//Back to Back *1.5
	      		  B2B_buffer = true;
	      		  //clearAll();//for test all cleared
	      		  break;
	      		  
	      	  case "0,1":				//Mini T-Spin
	      		  action = 5;
	      		  SS = 100;
	      		  break;
	      	  case "0,2":				//T-Spin
	      		  action = 6;
	      		  SS = 400;
	      		  break;
	      		  
	      	  case "1,1":				//Mini T-Spin Single
	      		  action = 7;
	      		  attack += 1;
	      		  SS = 200;
	      		  if(B2B){ BS = 200/2; attack++;}	//Back to Back *1.5
	      		  B2B_buffer = true;
	      		  break;
	      	  case "1,2":				//T-Spin Single
	      		  action = 8;
	      		  attack += 2;
	      		  SS = 800;
	      		  if(B2B){ BS = 800/2; attack++;}	//Back to Back *1.5
	      		  B2B_buffer = true;
	      		  break;
	      		  
	      	  case "2,1":				//Mini T-Spin Double
	      		  action = 9;
	      		  attack += 2;
	      		  SS = 1200;
	      		  if(B2B){ BS = 1200/2; attack++;}	//Back to Back *1.5
	      		  B2B_buffer = true;
	      		  break;
	      	  case "2,2":				//T-Spin Double
	      		  action = 10;
	      		  attack += 3;
	      		  SS = 1200;
	      		  if(B2B){ BS = 1200/2; attack++;}	//Back to Back *1.5
	      		  B2B_buffer = true;
	      		  break;
	      		  
	      	  case "3,2":				//T-Spin Triple
	      		  action = 11;
	      		  attack += 6;
	      		  SS = 1600;
	      		  if(B2B){ BS = 1600/2; attack++;}	//Back to Back *1.5
	      		  B2B_buffer = true;
	      		  break;
	      	  default:
	      		  action = 0;
	      		  SS = 0;
	      }
	      
	      if(checkAllEmpty()) {		//All Cleared Score
	    	  switch(linesCleared) {
	    	    case 1:
	    	    	AS = 800;			//All Cleared Single
	    	    	break;
	    	    case 2:
	    	    	AS = 1200;			//All Cleared Double
	    	    	break;
	    	    case 3:
	    	    	AS = 1800;			//All Cleared Triple
	    	    	break;
	    	    case 4:
	    	    	if(B2B && B2B_buffer) AS = 3200;	//All Cleared Quadruple + Back to Back
	    	    	else AS = 2000;		//All Cleared Quadruple
	    	    	break;
	    	    default:
	    	    	AS = 0;
	    	  }
	      }
	      else AS = 0;
	      
	      if(linesCleared > 0) {	//Combo Score
	    	  CS = 50 * combo;
	    	  combo++;
	      }
	      else combo = 0;
	      
	      B2B = B2B_buffer;
	      score += (SS+AS+CS) * (level + 1);
	      level = lines_cleared / 10;
      }
      
      public boolean checkAllEmpty() {
          for (int y = GRID_ROWS - 1; y >= 0; y--) {
              for (int x = 0; x < GRID_COLS; x++) {
                 if (BGarr[x][y] != 0)  return false;
              }
           }
          return true;
      }
      
      public void clearAll() {
          for (int y = GRID_ROWS - 1; y >= 0; y--) {
              for (int x = 0; x < GRID_COLS; x++) {
                 BGarr[x][y]  = 0;
              }
           }
      }
      
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
			    g.setColor(new Color(64, 64, 64, r)); break;	// GarbageLines
    	  }
      }
      
      public void draw(Graphics g) {
    	  int drawX, drawY;
    	  Xoffset += Xshock;
    	  Yoffset += Yshock;
    	  //edge
    	  g.setColor(Color.LIGHT_GRAY);
	      g.fillRect(Xoffset-10,
	    		  	 Yoffset-10,
	    		  	 GRID_COLS * BLOCK_SIZE + 20,
	    		  	 GRID_ROWS * BLOCK_SIZE + 20);
	      g.setColor(Color.BLACK);
	      g.fillRect(Xoffset,
	    		  	 Yoffset,
	    		  	 GRID_COLS * BLOCK_SIZE,
	    		  	 GRID_ROWS * BLOCK_SIZE);
	      
      	  //line
          g.setColor(Color.DARK_GRAY);
          drawX = Xoffset;
          drawY = Yoffset;
          for (int x = 0; x <= GRID_COLS; x++)
             g.drawLine(drawX + x * BLOCK_SIZE, drawY,
            		 	drawX + x * BLOCK_SIZE, drawY + GRID_ROWS * BLOCK_SIZE);
          for (int y = 0; y <= GRID_ROWS; y++)
             g.drawLine(drawX + 0, drawY + y * BLOCK_SIZE,
            		 	drawX + GRID_COLS * BLOCK_SIZE, drawY + y * BLOCK_SIZE);
          
          //shape
          for(int i=0; i < getBGarr().length; i++) {
          	for(int j=0; j < getBGarr()[i].length; j++) {
                switchColor(g, BGarr[i][j], 255);

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
          drawX = Xoffset + 15 + GRID_COLS * BLOCK_SIZE;
          //Score:
          drawY = Yoffset + 0 * BLOCK_SIZE;
          g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Score:", drawX+5, drawY+20);
          g.drawRect(drawX, drawY+BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE);
          //Score
          g.setFont(new Font("Arial", Font.BOLD, 30));
          g.drawString(String.valueOf(score), drawX + 30, drawY + 70);
          
          //Next:
          drawY = Yoffset + 3 * BLOCK_SIZE;
          g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Next:", drawX+5, drawY+20);
          g.drawRect(drawX, drawY+BLOCK_SIZE , 5*BLOCK_SIZE, 3*BLOCK_SIZE);
          
          //Hold:
          drawY = Yoffset + 7 * BLOCK_SIZE;
          g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Hold:", drawX+5, drawY+20);
          g.drawRect(drawX, drawY+BLOCK_SIZE, 5*BLOCK_SIZE, 3*BLOCK_SIZE);
          
          //level
	      drawY = Yoffset + 11 * BLOCK_SIZE;
    	  g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Level: " + level, drawX+5, drawY+20);
          
          //lines cleared
          drawY = Yoffset + 12 * BLOCK_SIZE;
    	  g.setFont(new Font("Arial", Font.BOLD, 20));
          g.drawString("Lines: " + lines_cleared, drawX+5, drawY+20);
          
          //action:
          if(SS > 0) {
        	  String str_action = "";
	          drawY = Yoffset + 13 * BLOCK_SIZE;
	          switch(action) {
	          	  case 1: str_action = "Single"; break;
	          	  case 2: str_action = "Double"; break;
	          	  case 3: str_action = "Triple"; break;
	          	  case 4: str_action = "Quadruple"; break;
	          	  
	          	  case 5: str_action = "Mini T-Spin"; break;
	          	  case 6: str_action = "T-Spin"; break;
	          	  
	          	  case 7: str_action = "Mini T-Spin Single"; break;
	          	  case 8: str_action = "T-Spin Single"; break;
	          	  
	          	  case 9: str_action = "Mini T-Spin Double"; break;
	          	  case 10: str_action = "T-Spin Double"; break;
	          	  
	          	  case 11: str_action = "T-Spin Triple"; break;
	          	  default: str_action = ""; break;
	          }
	        	  g.setFont(new Font("Arial", Font.BOLD, 20));
	              g.drawString(str_action, drawX+5, drawY+20);
	        	  g.setFont(new Font("Arial", Font.BOLD, 15));
	              g.drawString("+" + SS * (level+1), drawX+5, drawY+40);
	              if(BS > 0){
        	  	  	  g.drawString("+" + BS * (level+1) + "(BTB)", drawX+5, drawY+55);
	              }
          }
          
          //all cleared
          drawY = Yoffset + 15 * BLOCK_SIZE;
          if(AS > 0) {
        	  g.setFont(new Font("Arial", Font.BOLD, 20));
              g.drawString("All Cleared", drawX+5, drawY+20);
        	  g.setFont(new Font("Arial", Font.BOLD, 15));
              g.drawString("+" + AS * (level+1), drawX+5, drawY+40);
          }
          
       	  //back to back
          if(B2B) {
    	      drawY = Yoffset + 17 * BLOCK_SIZE;
        	  g.setFont(new Font("Arial", Font.BOLD, 20));
              g.drawString("Back To Back", drawX+5, drawY+20);
        	  g.setFont(new Font("Arial", Font.BOLD, 20));
              g.drawString("ON", drawX+5, drawY+40);
          }
          
          //Combo
          if(combo > 0) {
    	      drawY = Yoffset + 19 * BLOCK_SIZE;
        	  g.setFont(new Font("Arial", Font.BOLD, 20));
              g.drawString("Combo", drawX+5, drawY+20);
              g.drawString(String.valueOf(combo), drawX+80, drawY+20);
              if(CS > 0) {
            	  g.setFont(new Font("Arial", Font.BOLD, 15));
                  g.drawString("+" + CS * (level+1), drawX+5, drawY+40);
              }
          }
          Xoffset -= Xshock;
    	  Yoffset -= Yshock;
      }
   }