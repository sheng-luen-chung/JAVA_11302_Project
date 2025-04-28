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
	    private int currentX, currentY, currentS;
	    private Random rand = new Random();

	    private static final Point[][] SHAPES = {
	            { new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0) }, // I
	            { new Point(1,0), new Point(2,0), new Point(1,1), new Point(2,1) }, // O
	            { new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // T
	            { new Point(1,1), new Point(2,1), new Point(0,2), new Point(1,2) }, // S
	            { new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,2) }, // Z
	            { new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // J
	            { new Point(2,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // L
	     };
	    
     // constructors
      public Player()     //default constructor
      {
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
      public void spawnNewShape() {
          currentX = GRID_COLS / 2 - 2;
          currentY = 0;
          currentS = rand.nextInt(SHAPES.length) + 1;
          currentShape = SHAPES[currentS-1];
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
              	g.setColor(new Color(128, 0, 128));	break;	// T
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