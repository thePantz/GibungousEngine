package com.gibungousgames.engine;

import java.awt.image.DataBufferInt;

/**
 * Manages what is drawn on screen
 */
public class Renderer {
    private int pixelWidth, pixelHeight;
    private int[] pixels;

    public Renderer(GameContainer gameContainer){
        pixelWidth = gameContainer.getWidth();
        pixelHeight = gameContainer.getHeight();

        //Get the pixel data from Window
        //Modifying these pixels will display when update is called in window.
        pixels = ((DataBufferInt)gameContainer.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    /**
     * Clears to a black screen;
     */
    public void clear(){
        for (int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }
}
