package com.gibungousgames.engine;

/**
 * This is the Where the game starts, stop, etc.
 *
 * Various engine components are managed through this object
 */
public class GameContainer implements Runnable {

    private Thread thread;
    private boolean isRunning = false;
    private final double UPDATE_CAP = 1.0/60.0; //60fps
    private int width = 320;
    private int height = 240;
    private float scale = 4f;
    private String title = "Gibungous Engine";

    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    public GameContainer(AbstractGame game){
        this.game = game;
    }

    public void start(){
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);

        thread = new Thread(this);
        thread.run(); //calls this.run();
    }

    public void stop(){

    }

    public void dispose(){

    }

    public void run() {
        boolean render;
        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps;

        isRunning = true;


        while (isRunning){
            render = false;
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP){
                unprocessedTime -= UPDATE_CAP;
                render = true;

                game.update(this, (float)UPDATE_CAP);

                input.update();
                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;

                    System.out.println(fps);

                }
            }

            // only render a new screen if there was an update
            if (render){
                renderer.clear();
                game.render(this, renderer);
                window.update();
                frames++;
            }
            else
            {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        dispose();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }
}
