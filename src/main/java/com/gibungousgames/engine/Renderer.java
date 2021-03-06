package com.gibungousgames.engine;

import com.gibungousgames.engine.gfx.Font;
import com.gibungousgames.engine.gfx.GameImage;
import com.gibungousgames.engine.gfx.GameImageTile;

import java.awt.image.DataBufferInt;

/**
 * Manages what is drawn on screen
 */
public class Renderer {
    private int pixelWidth, pixelHeight;
    private int[] pixels;

    private Font font = Font.STANDARD;

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
        // do not set pixels that are out of bounds or invisible
        if((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || ((value >> 24) & 0xff) == 0){
            return;
        }

        pixels[x + y * pixelWidth] = value;
    }

    /**
     * Draws text at the specified coordinates, in the specified color
     * @param text the text to be drawn on screen
     * @param offX x coordinate to start drawing
     * @param offY y coordinate to start drawing
     * @param color hex color to render the text in
     */
    public void drawText(String text, int offX, int offY, int color)
    {
        int offset = 0;
        GameImage fontImage = font.getFontImage();

        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++)
        {
            int unicode = text.codePointAt(i) - 32; //Make space 0

            for(int y = 0; y < fontImage.getHeight(); y++)
            {
                for(int x = 0; x < font.getWidths()[unicode]; x++)
                {
                    if(fontImage.getPixels()[(x + font.getOffsets()[unicode]) + y * fontImage.getWidth()] == 0xffffffff)
                    {
                        setPixel(x + offX + offset,y + offY, color);
                    }
                }
            }

            offset += font.getWidths()[unicode];
        }
    }

    /**
     * Draws the specified image to the screen
     *
     * @param image the image to be drawn on the canvas
     * @param offX the x coordinate to start drawing the image from
     * @param offY the y coordinate to start drawing the image from
     */
    public void drawImage(GameImage image, int offX, int offY){

        // Don't render anything if the image is completely offscreen
        if(offX < -image.getWidth()) return;
        if(offY < -image.getHeight()) return;
        if(offX >= pixelWidth) return;
        if(offY >= pixelHeight) return;

        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

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

    public void drawImageTile(GameImageTile image, int offX, int offY, int tileX, int tileY){
        // Don't render anything if the image is completely offscreen
        if(offX < -image.getTileWidth()) return;
        if(offY < -image.getTileHeight()) return;
        if(offX >= pixelWidth) return;
        if(offY >= pixelHeight) return;

        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileWidth();
        int newHeight = image.getTileHeight();

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
                setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
            }
        }
    }

    /**
     * Draws a rectangle onscreen
     * @param offX x coordinate to start drawing at
     * @param offY y coordinate to start drawing at
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param color hex color of the rectangle
     */
    public void drawRect(int offX, int offY, int width, int height, int color)
    {
        for(int y = 0; y <= height; y++)
        {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }

        for (int x = 0; x <= width; x++)
        {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    /**
     * Draws a filled rectangle on screen
     * @param offX x coordinate to start drawing at
     * @param offY y coordinate to start drawing at
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param color hex color of the rectangle
     */
    public void drawFillRect(int offX, int offY, int width, int height, int color)
    {
        // Don't render anything if the image is completely offscreen
        if(offX < -width) return;
        if(offY < -height) return;
        if(offX >= pixelWidth) return;
        if(offY >= pixelHeight) return;

        int newX = 0;
        int newY = 0;
        int newWidth = width;
        int newHeight = height;

        // Don't attempt to render pixels that are off screen (Clipping)
        if(offX < 0){ newX -= offX; }
        if(offY < 0){ newY -= offY; }
        if(newWidth + offX > pixelWidth){ newWidth -= newWidth + offX - pixelWidth; }
        if(newHeight + offY > pixelHeight){ newHeight -= newHeight + offY - pixelHeight; }

        for(int y = newY; y <= newHeight; y++)
        {
            for (int x = newX; x <= newWidth; x++)
            {
                setPixel(x + offX, y + offY, color);
            }
        }


    }
}
