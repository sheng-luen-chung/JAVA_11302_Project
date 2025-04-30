package game;

   import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
   
   public class Player
   {
	    private static final int GRID_COLS = 10;
	    private static final int GRID_ROWS = 20;
	    private static final int BLOCK_SIZE = 30;
	    private Point[] currentShape;
	    private Point[] nextShape;
	    private Point[] holdShape;
	    
	    private int currentX, currentY, currentS;
	    private int[] nextS;
	    private int holdS;
	    
	    private boolean hold;
	    private boolean Tspin;
	    
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
      public Player()     //default constructor
      {
    	  nextS = new int[10];
    	  spawn7bag();
    	  spawnNewShape();
      }

    // accessor methods
      public int getX(){ return currentX;}
      public int getY(){ return currentY;}
      public int getS(){ return currentS;}
      public Point[] getShape(){ return currentShape;}
      
   // modifier methods
      public void setX(int x){currentX = x;} 
      public void setY(int y){currentY = y;} 
      public void setS(int s){currentS = s;} 
      public void setShape(Point[] s){currentShape = s;} 
      
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
    	  //當前塊設定
          currentX = GRID_COLS / 2 - 2;
          currentY = 0;
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
          
          //預覽塊設定
          nextShape = SHAPES[nextS[0]-1];
          if(nextS[1] == 0) {
        	  spawn7bag();
          }
      }
      
      public void holdCurrentShape() {
    	  //暫存塊
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
          
          switch (currentS) {	//尋找基準點
          case 1:	// I
        	  pivot_x = 1.5;
        	  pivot_y = 1.5;
        	  break;
        	  
          case 2:	// O
        	  pivot_x = 0.5;
        	  pivot_y = 0.5;
        	  break;
          default:	//除了IO的其他形狀
        	  pivot_x = 1;
        	  pivot_y = 1;
          }
          
          //旋轉
          for (int i = 0; i < getShape().length; i++) {
              double x = -(getShape()[i].x - pivot_x);
              double y = -(getShape()[i].y - pivot_y);
              
              //dir=0逆轉 !=0順轉, 順:(x,y)->(y, -x), 逆:(x,y)->(-y, x)
              if(dir != 0) rotated[i] = new Point((int)(pivot_x + y), (int)(pivot_y - x));
              else rotated[i] = new Point((int)(pivot_x - y), (int)(pivot_y + x));    
          }
          setShape(rotated);
      }
      
      public void draw(Graphics g) {
          for (Point p : currentShape) {
              int drawX = (currentX + p.x) * BLOCK_SIZE;
              int drawY = (currentY + p.y) * BLOCK_SIZE;
              switch (currentS) {
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
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          
          for (Point p : nextShape) {
              int drawX = (11 + p.x) * BLOCK_SIZE;
              int drawY = (1 + p.y) * BLOCK_SIZE;
              switch (nextS[0]) {
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
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          
          if(hold) {
          for (Point p : holdShape) {
              int drawX = (11 + p.x) * BLOCK_SIZE;
              int drawY = (6 + p.y) * BLOCK_SIZE;
              switch (holdS) {
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
              g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
              g.setColor(Color.BLACK);
              g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
          }
          }
      }
   }