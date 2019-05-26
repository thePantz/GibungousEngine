package com.gibungousgames.engine.gfx;

/**
 * Loads a font from an image
 * Expects the font image to have reserved colors to indicate the widths
 * and offsets of each character
 */
public class Font {

    public static final Font STANDARD = new Font("/fonts/standardFont.png");

    private GameImage fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path)
    {
        fontImage = new GameImage(path);

        //ToDo: allow fonts to support a different number of characters
        offsets = new int[59];
        widths = new int[59];
        int unicode = 0;

        for(int i = 0; i < fontImage.getWidth(); i++)
        {
            // Record start of character
            if(fontImage.getPixels()[i] == 0xff0000ff)
            {
                offsets[unicode] = i;
            }

            // Record character width
            if(fontImage.getPixels()[i] == 0xffffff00)
            {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public GameImage getFontImage() {
        return fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }
}
