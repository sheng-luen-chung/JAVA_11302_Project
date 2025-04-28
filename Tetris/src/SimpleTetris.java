package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Main extends JPanel implements ActionListener {
    // 格線設定：10 列 × 20 行
    private static final int GRID_COLS = 10;
    private static final int GRID_ROWS = 20;
    private static final int BLOCK_SIZE = 30;
    private Timer timer;
    private Point[] currentShape;    // Tetromino 內部座標
    private int currentX, currentY, currentS;  // Tetromino 在格線上的左上原點
    private Random rand = new Random();
    private int[][] BGarr = new int[10][20];
    
    // 七種 Tetromino（單一旋轉狀態）
    private static final Point[][] SHAPES = {
        { new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1) }, // I
        { new Point(1,0), new Point(2,0), new Point(1,1), new Point(2,1) }, // O
        { new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // T
        { new Point(1,1), new Point(2,1), new Point(0,2), new Point(1,2) }, // S
        { new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,2) }, // Z
        { new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // J
        { new Point(2,0), new Point(0,1), new Point(1,1), new Point(2,1) }, // L
    };

    public Main() {
        setPreferredSize(new Dimension(GRID_COLS * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE));
        setBackground(Color.BLACK);

        // 監聽鍵盤
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_LEFT:
                        moveBlock(-1, 0); 	break;
                    case KeyEvent.VK_RIGHT:
                        moveBlock(1, 0);	break;
                    case KeyEvent.VK_DOWN:
                        moveBlock(0, 1);	break;
                    case KeyEvent.VK_NUMPAD3:
                    	if(currentS != 2) rotate();	break;
                    case KeyEvent.VK_NUMPAD2:
                    	if(currentS != 2) rotate_reverse();	break;
                    case KeyEvent.VK_NUMPAD0:
                    	instant_drop();      		break;
                }
                repaint();
            }
        });

        spawnNewShape();
        timer = new Timer(500, new soft_drop());
        timer.start();
    }

    private void spawnNewShape() {
    	if(currentShape != null) {
    		for (int i = 0; i < currentShape.length; i++) {
                BGarr[currentX + currentShape[i].x]
                     [currentY + currentShape[i].y] = currentS;
            }
    	}
        
        currentX = GRID_COLS / 2 - 2;
        currentY = 0;
        currentS = rand.nextInt(SHAPES.length) + 1;
        currentShape = SHAPES[currentS-1];
    }

    private void moveBlock(int dx, int dy) {
        currentX += dx;
        if (!isValidPosition()) {
            currentX -= dx;
        }
        
        currentY += dy;
        if (!isValidPosition()) {
            currentY -= dy;
            spawnNewShape();
        } 
    }

    private void instant_drop() {
        while (true) {
            currentY++;
            if (!isValidPosition()) {
                currentY--;
                spawnNewShape();
                break;
            }
        }
    }

    private void rotate() {
        // 以第一個方塊為 pivot，順時針旋轉
        Point pivot = currentShape[0];
        Point[] rotated = new Point[currentShape.length];
        for (int i = 0; i < currentShape.length; i++) {
            int x = -(currentShape[i].x - pivot.x);
            int y = -(currentShape[i].y - pivot.y);
            // 旋轉公式 (x,y)->(y, -x)
            rotated[i] = new Point(pivot.x + y, pivot.y - x);
        }
        Point[] backup = currentShape;
        currentShape = rotated;		
        for (Point p : currentShape) {	//踢牆
            int x = currentX + p.x;
            int y = currentY + p.y;
            
            if (x < 0) currentX = currentX - x;
            if (x >= GRID_COLS) currentX = currentX - (x - GRID_COLS) -1;
            if (y >= GRID_ROWS) currentX = currentX - (x - GRID_ROWS) -1;
        }
        if (!isValidPosition()) {
            currentShape = backup; // 還原
        }
    }
    
    private void rotate_reverse() {
        // 以第一個方塊為 pivot，逆時針旋轉
        Point pivot = currentShape[0];
        Point[] rotated = new Point[currentShape.length];
        for (int i = 0; i < currentShape.length; i++) {
            int x = -(currentShape[i].x - pivot.x);
            int y = -(currentShape[i].y - pivot.y);
            // 旋轉公式 (x,y)->(-y, x)
            rotated[i] = new Point(pivot.x - y, pivot.y + x);
        }
        Point[] backup = currentShape;
        currentShape = rotated;
        for (Point p : currentShape) {	//踢牆
            int x = currentX + p.x;
            int y = currentY + p.y;
            
            if (x < 0) currentX = currentX - x;
            if (x >= GRID_COLS) currentX = currentX - (x - GRID_COLS) -1;
            if (y >= GRID_ROWS) currentX = currentX - (x - GRID_ROWS) -1;
        }
        if (!isValidPosition()) {
            currentShape = backup; // 還原
        }
    }

    private boolean isValidPosition() {
        for (Point p : currentShape) {
            int x = currentX + p.x;
            int y = currentY + p.y;
            if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS) {
                return false;
            }
            if (BGarr[x][y] != 0) {
            	return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 畫格線
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= GRID_COLS; x++)
            g.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE);
        for (int y = 0; y <= GRID_ROWS; y++)
            g.drawLine(0, y * BLOCK_SIZE, GRID_COLS * BLOCK_SIZE, y * BLOCK_SIZE);

        // 畫當前方塊
        

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
        for(int i=0; i < BGarr.length; i++) {
        	for(int j=0; j < BGarr[i].length; j++) {
        		switch (BGarr[i][j]) {
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

            	if(BGarr[i][j] > 0) {
            		int drawX = i * BLOCK_SIZE;
                    int drawY = j * BLOCK_SIZE;
                    g.fillRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.CYAN);
            	}
            }
        }
    }

    private class soft_drop implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		moveBlock(0, 1);
    		
            repaint();
        }  
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        Main tetrisPanel = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tetrisPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tetrisPanel.requestFocus();
       
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}