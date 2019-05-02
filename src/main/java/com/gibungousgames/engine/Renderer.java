package com.gibungousgames.engine;

import com.gibungousgames.engine.gfx.GibungousImage;

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
    private void setPixel(int x, int y, int value){
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
     * @param image the image to be drawn on the canvas
     * @param offX the x coordinate to start drawing the image from
     * @param offY the y coordinate to start drawing the image from
     */
    public void drawImage(GibungousImage image, int offX, int offY){

        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        // Don't render anything if the image is completely offscreen
        if(offX < -newWidth) return;
        if(offY < -newHeight) return;
        if(offX >= pixelWidth) return;
        if(offY >= pixelHeight) return;

        // Don't attempt to render pixels that are off screen (Clipping)
        if(offX < 0){ newX -= offX; }
        if(offY < 0){ newY -= offY; }
        if(newWidth + offX > pixelWidth){ newWidth -= newWidth + offX - pixelWidth; }
        if(newHeight + offY > pixelHeight){ newHeight -= newHeight + offY - pixelHeight; }

        //Iterate one horizontal slice at a time
        //copying pixel colors from the image to the canvas at
        //the desired coordinates.
        for(int y = newY; y < newHeight; y++){
            for(int x = newX; x < newWidth; x++){
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }
}
