package com.gibungousgames.demogame;

import com.gibungousgames.engine.AbstractGame;
import com.gibungousgames.engine.GameContainer;
import com.gibungousgames.engine.Renderer;
import com.gibungousgames.engine.gfx.GameImage;
import com.gibungousgames.engine.gfx.GameImageTile;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private GameImageTile image;
    private float temp = 0;
    public GameManager(){
        image = new GameImageTile("/TestImageTile.png", 16, 16);
    }

    public void update(GameContainer gameContainer, float dt) {
        if(gameContainer.getInput().isKeyDown(KeyEvent.VK_A))
        {
            System.out.println("A was pressed");
        }

        //update the tile to draw
        temp += dt * 10;

        //reset to first tile after the third tile
        if (temp > 3) {

            temp = 0;
        }
    }

    public void render(GameContainer gameContainer, Renderer renderer) {
        renderer.drawImageTile(image, gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY(), (int)temp, 0);
    }

    public static void main(String[] args){
        GameContainer gameContainer = new GameContainer(new GameManager());
        gameContainer.start();
    }
}
