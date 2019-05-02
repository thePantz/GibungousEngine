package com.gibungousgames.engine;

import com.gibungousgames.engine.gfx.Image;

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

    /**
     * Sets the specified pixel to the specified color
     *
     * @param x the x coordinate of the desired pixel
     * @param y the y coordinate of the desired pixel
     * @param value the desired color value
     */
    public void setPixel(int x, int y, int value){
        // do not set pixels that are out of bounds or
        // are using the reserved alpha color (invisible color)
        if((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || value == 0xffff00ff){
            return;
        }

        pixels[x + y * pixelWidth] = value;
    }

    /**
     * Draws the specified image to the screen
     *
     * <p>
     * Iterates one horizontal slice at a time setting
     * copying pixel colors from the image to the canvas at
     * the desired coordinates.
     * </p>
     * @param image the image to be drawn on the canvas
     * @param offX the x coordinate to start drawing the image from
     * @param offY the y coordinate to start drawing the image from
     */
    public void drawImage(Image image, int offX, int offY){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }
}
