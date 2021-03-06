package com.mygdx.game.input;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.GameWorld;

public class InputHandler implements InputProcessor {

	private GameWorld myWorld;
	
    public InputHandler(GameWorld world) {
		this.myWorld = world;
	}

	@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//System.out.println("touchDown "+screenX+" "+screenY);
		myWorld.click(screenX, screenY, button);
        return false;
    }

    @Override
	public boolean keyDown(int keycode) {
		System.out.println("Key down: " + keycode);
		if(myWorld.getGameWindow() && myWorld.getBallInPlay())
		{
			myWorld.pause(keycode);
		}
		//myWorld.maveRect(keycode);
		return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
    		System.out.println("keyTyped: "  +  character);
    		myWorld.maveRectAlphabetic(character);
    		if(myWorld.getNameWindow())
    		{
    			myWorld.insertName(character);
    		}
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	//System.out.println("Mouse moved, x: " + screenX +", y:" + screenY);
        myWorld.moveByMouse(screenX);
    	return false;
    }

    @Override
    public boolean scrolled(int amount) {
    	//System.out.println("scrolled" );
        return false;
    }
}