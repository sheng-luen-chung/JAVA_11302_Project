package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Tetris extends JPanel{
    private static final int GRID_COLS = 10;
    private static final int GRID_ROWS = 20;
    private static final int BLOCK_SIZE = 30;
    private Timer timer;
    private Player p1;
    private Grid g1;
    
    public Tetris() {
        setPreferredSize(new Dimension((GRID_COLS+5) * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_LEFT:
                        moveBlock(p1, g1, -1, 0); 	break;
                    case KeyEvent.VK_RIGHT:
                        moveBlock(p1, g1, 1, 0);	break;
                    case KeyEvent.VK_DOWN:
                        moveBlock(p1, g1, 0, 1);	break;
                    case KeyEvent.VK_UP:
                    	rotate_and_check(p1, g1, 1);	break;
                    case KeyEvent.VK_NUMPAD2:
                    	rotate_and_check(p1, g1, 0);	break;
                    case KeyEvent.VK_NUMPAD1:
                    	p1.holdCurrentShape();		break;
                    case KeyEvent.VK_NUMPAD0:
                    	hard_drop(p1, g1);      	break;
                    
                }
                repaint();
            }
        });
        
        p1 = new Player();
        g1 = new Grid();
        
        timer = new Timer(500, new gravity());
        timer.start();
    }
    private void putShape(Player p, Grid g) {
  		for (int i = 0; i < p.getShape().length; i++) {
              g.setBGarr(p.getX() + p.getShape()[i].x,
            		  	 p.getY() + p.getShape()[i].y,
            		  	 p.getS());
         }
      g.clearFullLines();
      p.spawnNewShape();
    }
    
    private void moveBlock(Player p, Grid g, int dx, int dy) {
        p.setX(p.getX() + dx);
        if (!isValidPosition(p, g)) {
        	p.setX(p.getX() - dx);
        }
        
        p.setY(p.getY() + dy);
        if (!isValidPosition(p, g)) {
        	p.setY(p.getY() - dy);
            putShape(p1, g1);
        } 
    }

    private void hard_drop(Player p, Grid g) {
        while (true) {
            p.setY(p.getY() + 1);
            if (!isValidPosition(p, g)) {
            	p.setY(p.getY() - 1);
            	putShape(p, g);
               break;
            }
        }
    }

    private void rotate_and_check(Player p, Grid g, int dir) {
        Point[] backup = p.getShape();
        
        p.rotate(dir);
        
        for (Point po : p.getShape()) {
            int x = p.getX() + po.x;
            int y = p.getY() + po.y;
            
            if (x < 0) p.setX( p.getX() - x );
            if (x >= GRID_COLS) p.setX(p.getX() - (x - GRID_COLS) -1);
            if (y >= GRID_ROWS) p.setY(p.getY() - (y - GRID_ROWS) -1);
        }
        if (!isValidPosition(p, g)) {
        	p.setShape(backup);
        }
    }

    private boolean isValidPosition(Player p, Grid g) {
        for (Point po : p.getShape()) {
            int x = p.getX() + po.x;
            int y = p.getY() + po.y;
            if (x < 0 || x >= GRID_COLS || y < 0 || y >= GRID_ROWS) {
                return false;
            }
            if (g.getBGarr()[x][y] != 0) {
            	return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= GRID_COLS; x++)
            g.drawLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, GRID_ROWS * BLOCK_SIZE);
        for (int y = 0; y <= GRID_ROWS; y++)
            g.drawLine(0, y * BLOCK_SIZE, GRID_COLS * BLOCK_SIZE, y * BLOCK_SIZE);

        p1.draw(g);
        g1.draw(g);
        
    }

    private class gravity implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		moveBlock(p1, g1, 0, 1);
    		
            repaint();
        }  
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        Tetris tetrisPanel = new Tetris();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tetrisPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tetrisPanel.requestFocus();  
    }

}