package com.gibungousgames.engine;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * The Game window
 */
public class Window {
    private GameContainer gameContainer;

    private JFrame frame; // The window
    private BufferedImage image; //image to be rendered on screen (pixel data)
    private Canvas canvas; //where the image is rendered to
    private BufferStrategy bufferStrategy; // used to organize complex memory on canvas
    private Graphics graphics; //used to draw onto components


    public Window(GameContainer gameContainer){
        image = new BufferedImage(gameContainer.getWidth(), gameContainer.getHeight(), BufferedImage.TYPE_INT_RGB);

        //Setup Canvas
        canvas = new Canvas();
        Dimension dimension = new Dimension((int)(gameContainer.getWidth() * gameContainer.getScale()), (int)(gameContainer.getHeight() * gameContainer.getScale()));
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMinimumSize(dimension);

        //Setup Frame
        frame = new JFrame(gameContainer.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack(); //set the frame to the size of the canvas
        frame.setLocationRelativeTo(null); // Start in the middle of the screen
        frame.setResizable(false);
        frame.setVisible(true);

        //Setup Buffer Strategy
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void update(){
        graphics.drawImage(image, 0,0, canvas.getWidth(), canvas.getHeight(), null);
        bufferStrategy.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }
}
