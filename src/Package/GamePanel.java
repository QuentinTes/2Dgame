package Package;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    final int originalTileSize = 16; //16x16 tiles
    final int scale = 3;

    final int tileSize = originalTileSize*scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //4:3
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    //FPS
    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //keeps program running

    //player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this); //this class
        gameThread.start();
    }

    @Override
    public void run() {
        //Game Loop
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
        // Update information character position
        update();

        // Draw the screen with updated information
        repaint(); // call paintComponent method


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if (keyH.upPressed == true){
            playerY -= playerSpeed;
        }
        if (keyH.downPressed == true){
            playerY += playerSpeed;
        }
        if (keyH.leftPressed == true){
            playerX -= playerSpeed;
        }
        if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g){ //Graphics = class to draw objects on screen
        super.paintComponent(g);

        //Graphics2D extends Graphics more control over 2D games
        Graphics2D g2 = (Graphics2D)g; //change graphics to graphics2D

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize,tileSize);
        g2.dispose(); //dispose to delete it after used (save memory)
    }
}