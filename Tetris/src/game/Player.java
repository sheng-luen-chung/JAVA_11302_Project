package game;

   import java.awt.*;
   import java.util.Random;
   
   public class Player
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private int Xoffset, Yoffset;
	    private Point[] currentShape;
	    private Point[] nextShape;
	    private Point[] holdShape;
	    
	    private int currentX, currentY, currentS, currentD;
	    private int[] nextS;
	    private int holdS;
	    private int dropFarmes, dropFarmesSet;
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
    	  AREset = 8;
    	  dropFarmes = dropFarmesSet;
    	  lockDelay = lockDelaySet;
    	  ARE = AREset;
    	  spawn7bag();
    	  spawnNewShape();
      }

    // accessor methods
      public int getX(){ return currentX;}
      public int getY(){ return currentY;}
      public int getS(){ return currentS;}
      public int getD(){ return currentD;}
      
      public int getDF(){ return dropFarmes;}
      public int getDFS(){ return dropFarmesSet;}
      
      public int getLD(){ return lockDelay;}
      public int getLDS(){ return lockDelaySet;}
      
      public int getARE(){ return ARE;}
      public int getARES(){ return AREset;}
      
      public Point[] getShape(){ return currentShape;}
      public boolean getTspin(){ return Tspin;}
      public boolean getSpeedUP(){ return speedUP;}
      public boolean getLK(){ return lastKick;}
      
   // modifier methods
      public void setX(int x){currentX = x;} 
      public void setY(int y){currentY = y;} 
      public void setS(int s){currentS = s;}
      public void setD(int dir) {
    	  currentD = dir % 4;
    	  if(currentD < 0) currentD += 4;
      }
      
      public void setDF(int d){dropFarmes = d;}
      public void setDFS(int d){dropFarmesSet = d;}
      
      public void setLD(int l){lockDelay = l;}
      public void setLDS(int l){lockDelaySet = l;}
      
      public void setARE(int a){ARE = a;}
      public void setARES(int a){AREset = a;}
      
      public void setShape(Point[] s){currentShape = s;} 
      public void setTspin(boolean Ts){Tspin = Ts;}
      public void setSpeedUP(boolean Sp){speedUP = Sp;}
      public void setLK(boolean Lk){lastKick = Lk;}
      
    //	 instance methods
      public void spawn7bag() {
    	  int[] bag = {1, 2, 3, 4, 5, 6, 7};
    	  for(int i=bag.length-1; i>0; i--) {
    		  int j = rand.nextInt(i);
    		  int temp = bag[i];
    		  bag[i] = bag[j];
    		  bag[j] = temp;
    	  }
    	  //bag[] to nextShape[]
    	  for(int i=0; i<bag.length; i++) {
    		  nextS[i+1] = bag[i];
    	  }
    	  if(nextS[0] == 0) {
    		  for(int i=0; i<nextS.length-1; i++) {
            	  nextS[i] = nextS[i+1];
              }
    	  }
      }
      
      public void spawnNewShape() {
    	  //current shape set
          currentX = GRID_COLS / 2 - 2;
          currentY = 0;
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
      
      public void holdCurrentShape() {
    	  //hold shape set
    	  if(!hold) {
    		  holdS = currentS;
    		  holdShape = SHAPES[holdS-1];
    		  spawnNewShape();
    		  hold = true;
    	  }
      }
      
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
    	  }
      }
      
      public void draw(Graphics g) {
    	  int drawX, drawY;
          for (Point p : currentShape) {
              drawX = Xoffset + (currentX + p.x) * BLOCK_SIZE;
              drawY = Yoffset + (currentY + p.y) * BLOCK_SIZE;
              switchColor(g, currentS, 105+(int)(150*((double)lockDelay/(double)lockDelaySet)));
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          
          for (Point p : nextShape) {
              drawX = Xoffset + (11 + p.x) * BLOCK_SIZE;
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
              drawX = Xoffset + (11 + p.x) * BLOCK_SIZE;
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
      }
      
      public void drawShadow(Graphics g, int y) {
    	  int drawX, drawY;
          for (Point p : currentShape) {
              drawX = Xoffset + (currentX + p.x) * BLOCK_SIZE;
              drawY = Yoffset + (currentY + p.y + y) * BLOCK_SIZE;
              switchColor(g, currentS, 125);
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
      }
   }