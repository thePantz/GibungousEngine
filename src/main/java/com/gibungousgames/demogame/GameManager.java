package com.gibungousgames.demogame;

import com.gibungousgames.engine.AbstractGame;
import com.gibungousgames.engine.GameContainer;
import com.gibungousgames.engine.Renderer;
import com.gibungousgames.engine.gfx.GibungousImage;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private GibungousImage image;

    public GameManager(){
        image = new GibungousImage("/test.png");
    }

    public void update(GameContainer gameContainer, float dt) {
        if(gameContainer.getInput().isKeyDown(KeyEvent.VK_A))
        {
            System.out.println("A was pressed");
        }
    }

    public void render(GameContainer gameContainer, Renderer renderer) {
        renderer.drawImage(image, gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY());
    }

    public static void main(String[] args){
        GameContainer gameContainer = new GameContainer(new GameManager());
        gameContainer.start();
    }
}
