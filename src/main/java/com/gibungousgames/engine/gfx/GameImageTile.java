package com.gibungousgames.engine.gfx;

public class GameImageTile extends GameImage
{
    private final int tileWidth;
    private final int tileHeight;

    public GameImageTile(String path, int tileWidth, int tileHeight) {
        super(path);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
