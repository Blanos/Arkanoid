package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.sounds.Jukebox;

public class MyGame extends Game {
    
	private int desktopWidth;
	private int desktopHeight;
	
	public MyGame(int desktopWidth, int desktopHeight)
	{
		super(); //odwolujemy sie do klasy ktora jest rodzicem tej klasy?? wywolujemy tutaj cos co czuwa nad ta klasa. bedziemy to przerabiac w nastepnym semetrze
		this.desktopWidth=desktopWidth;
		this.desktopHeight=desktopHeight;
	}
	
    @Override
    public void create() {
        Gdx.app.log("Test game", "created");
        Jukebox.load("youwin.ogg", "win"); // trzeba to dodac
        Jukebox.load("youlose2.ogg", "lose");
        Jukebox.load("tick.ogg", "bounce");
        //Jukebox.load("eat.ogg", "eat");
        setScreen(new GameScreen(desktopWidth, desktopHeight));
    }
    
    
}
