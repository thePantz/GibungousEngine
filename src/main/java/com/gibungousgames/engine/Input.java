package com.gibungousgames.engine;

import java.awt.event.*;

/**
 * Captures Mouse and Keyboard input
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private GameContainer gameContainer;

    //Keys
    private final int NUM_KEYS = 256; //number of keys on standard keyboard
    private boolean[] keys = new boolean[NUM_KEYS]; //True = pressed, False = released
    private boolean[] keysLast = new boolean[NUM_KEYS]; //keys pressed in last frame

    //Mouse buttons
    private final int NUM_BUTTONS = 5; //Number of mouse buttons
    private boolean[] mouseButtons = new boolean[NUM_BUTTONS]; //True = pressed, False = released
    private boolean[] mouseButtonsLast = new boolean[NUM_BUTTONS]; //mouse buttons pressed in last frame

    //Mouse Location
    private int mouseX, mouseY;
    private int scroll;

    public Input(GameContainer gameContainer){

        this.gameContainer = gameContainer;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gameContainer.getWindow().getCanvas().addKeyListener(this);
        gameContainer.getWindow().getCanvas().addMouseMotionListener(this);
        gameContainer.getWindow().getCanvas().addMouseListener(this);
        gameContainer.getWindow().getCanvas().addMouseWheelListener(this);
    }

    /**
     * Takes current input and stores it in last input
     */
    public void update(){
        scroll = 0; //ensure scroll resets every frame;
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);

        System.arraycopy(mouseButtons, 0, mouseButtonsLast, 0, NUM_BUTTONS);
    }


    /**
     * Checks if the specified key is currently pressed.
     *
     * @param keyCode the keycode to be checked
     * @return True - key is pressed; False - key is not pressed
     */
    public boolean isKey(int keyCode){
        return keys[keyCode];
    }

    /**
     * Checks if the specified key is released in the current frame
     * @param keyCode the keycode to be checked
     * @return True - key is released in this frame; False - key is not released in this frame
     */
    public boolean isKeyUp(int keyCode){
        return !keys[keyCode] && keysLast[keyCode];
    }

    /**
     * Checks if the specified key is down in the current frame
     * @param keyCode the keycode to be checked
     * @return True - key is down in this frame; False - key is not down in this frame
     */
    public boolean isKeyDown(int keyCode){
        return keys[keyCode] && !keysLast[keyCode];
    }

    /**
     * Checks if the specified mouse button is pressed
     * @param button the button to check
     * @return True - the button is pressed; False - the button is not pressed
     */
    public boolean isMouseButton(int button){
        return mouseButtons[button];
    }

    /**
     * Checks if the specified button is released in the current frame
     * @param button the keycode to be checked
     * @return True - button is up in this frame; False - button is not up in this frame
     */
    public boolean isMouseButtonUp(int button){
        return !mouseButtons[button] && mouseButtonsLast[button];
    }

    /**
     * Checks if the specified button is down in the current frame
     * @param button the keycode to be checked
     * @return True - button is down in this frame; False - button is not down in this frame
     */
    public boolean isMouseButtonDown(int button){
        return mouseButtons[button] && !mouseButtonsLast[button];
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mouseButtons[e.getButton()] = true;
    }

    public void mouseReleased(MouseEvent e) {
        mouseButtons[e.getButton()] = false;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        // Note: in order to get accurate mouse coordinates
        // we must consider the window scale

        mouseX = (int)(e.getX() / gameContainer.getScale());
        mouseY = (int)(e.getY() / gameContainer.getScale());
    }

    public void mouseMoved(MouseEvent e) {
        // Note: in order to get accurate mouse coordinates
        // we must consider the window scale

        mouseX = (int)(e.getX() / gameContainer.getScale());
        mouseY = (int)(e.getY() / gameContainer.getScale());
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }
}
