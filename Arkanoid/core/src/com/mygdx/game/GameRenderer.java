package com.mygdx.game;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.objects.BrickType;
import com.mygdx.game.objects.PowerUp;

import com.mygdx.game.objects.Brick;

public class GameRenderer {

	private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    
    private SpriteBatch batch;
    private BitmapFont redFont;
    private BitmapFont yellowFont;
    private BitmapFont blueFont;
    private BitmapFont greenFont;
    private BitmapFont font5;
    private BitmapFont font6;
    private BitmapFont bigYellowFont;
    private BitmapFont arkanoidFont;
    private BitmapFont buttonFont;
    private GlyphLayout glyphLayout;
	private Font fonts;
	private FreeTypeFontGenerator gen;
	private FreeTypeFontParameter parameter;
	
	private Texture weakTexture;
	private Texture iceTexture;
	private Texture brokenIceTexture;
	private Texture metalTexture;
	private Texture littleDeformedMetalTexture;
	private Texture deformedMetalTexture;
	
	private Texture slowTexture;
	private Texture lifeTexture;
	private Texture expandTexture;
	private Texture strengthTexture; 

    public GameRenderer(GameWorld world) {
        myWorld = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, myWorld.getScreenWidth(), myWorld.getScreenHeight());
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        gen = new FreeTypeFontGenerator(Gdx.files.internal("Consolas Bold.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 15;
		glyphLayout = new GlyphLayout();
        
		batch = new SpriteBatch();    
        redFont = new BitmapFont();
        redFont = gen.generateFont(parameter);
        redFont.setColor(1.0f, 0.0f, 0.0f, 1);
        yellowFont = new BitmapFont();
		yellowFont = gen.generateFont(parameter);
		yellowFont.setColor(Color.YELLOW);
		blueFont = new BitmapFont();
        blueFont = gen.generateFont(parameter);
        blueFont.setColor(0.2f, 0.2f, 1.0f, 1);
        greenFont = new BitmapFont();
        greenFont = gen.generateFont(parameter);
        greenFont.setColor(0.0f, 0.8f, 0.0f, 1);
        font5 = new BitmapFont();
        font5 = gen.generateFont(parameter);
        font5.setColor(1.0f, 0.0f, 0.0f, 1);
        font6 = new BitmapFont();
        font6 = gen.generateFont(parameter);
        font6.setColor(1.0f, 0.0f, 0.0f, 1);
        parameter.size = 30;
        bigYellowFont = new BitmapFont();
        bigYellowFont = gen.generateFont(parameter);
        bigYellowFont.setColor(Color.YELLOW);
        gen = new FreeTypeFontGenerator(Gdx.files.internal("ARKANOID.TTF"));
        parameter.size = 100;
        arkanoidFont = new BitmapFont();
        arkanoidFont = gen.generateFont(parameter);
		buttonFont = new BitmapFont();
        buttonFont.setColor(Color.GRAY);
        
        weakTexture = new Texture(Gdx.files.internal("weak.png"));
        iceTexture = new Texture(Gdx.files.internal("ice.png"));
        brokenIceTexture = new Texture(Gdx.files.internal("brokenIce.png"));
        metalTexture = new Texture(Gdx.files.internal("metal.png"));
    	littleDeformedMetalTexture = new Texture(Gdx.files.internal("littleDeformedMetal.png"));
    	deformedMetalTexture = new Texture(Gdx.files.internal("deformedMetal.png"));
    	
    	slowTexture = new Texture(Gdx.files.internal("slowPower.png"));
    	lifeTexture = new Texture(Gdx.files.internal("lifePower.png"));
    	expandTexture = new Texture(Gdx.files.internal("expandPower.png"));
    	strengthTexture = new Texture(Gdx.files.internal("strengthPower.png"));
    	
    	gen.dispose();
    }
	    
    public void render()
    {
        //Gdx.app.log("GameRenderer", "render");

       //Rysowanie tla

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(myWorld.getStartWindow())
		{
        	batch.begin();
			glyphLayout.setText(arkanoidFont, "ARKANOID");
			arkanoidFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * 1 / 9 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()),// - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - myWorld.getScreenHeight() * 1 / 3 * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height);// / 2);
			batch.end();
			
        	shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);//d5d5d5 059,131,189 /26, 53, 199
			shapeRenderer.rect(myWorld.getStart().x, myWorld.getStart().y, myWorld.getStart().width, myWorld.getStart().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "NEW GAME");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getStart().x + myWorld.getStart().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getStart().y + myWorld.getStart().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getHighscore().x, myWorld.getHighscore().y,	myWorld.getHighscore().width, myWorld.getHighscore().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, "HIGHSCORE");
			blueFont.draw(batch, glyphLayout, 
			(myWorld.getHighscore().x + myWorld.getHighscore().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getHighscore().y + myWorld.getHighscore().height /2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getInstructions().x, myWorld.getInstructions().y, myWorld.getInstructions().width, myWorld.getInstructions().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "INSTRUCTIONS");
			greenFont.draw(batch, glyphLayout, 
			(myWorld.getInstructions().x + myWorld.getInstructions().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getInstructions().y + myWorld.getInstructions().height /2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getExit1().x, myWorld.getExit1().y, myWorld.getExit1().width, myWorld.getExit1().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "EXIT");
			redFont.draw(batch, glyphLayout,
			(myWorld.getExit1().x + myWorld.getExit1().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getExit1().y + myWorld.getExit1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
		}
        else if(myWorld.getHighscoreWindow())
		{
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack1().x, myWorld.getBack1().y, myWorld.getBack1().width, myWorld.getBack1().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack1().x + myWorld.getBack1().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack1().y + myWorld.getBack1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);  
			batch.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, "HIGHSCORES"); //niebieski
			blueFont.draw(batch, glyphLayout,
			(myWorld.getBack1().x + myWorld.getBack1().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - myWorld.getBack1().height);
			batch.end();
			
			batch.begin();  
			blueFont.draw(batch, "No.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "POINTS", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 2 / 8,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "NAME", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 9,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "LEVEL", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 5 / 8,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "DESTROYED BLOCKS", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 6 / 8,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			for(int i = 0; i < myWorld.getHighscores().length; i++)
			{	
				batch.begin();
				glyphLayout.setText(yellowFont, myWorld.showHighscore(i));
				yellowFont.draw(batch, glyphLayout, 
				myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
				myWorld.getDesktopHeight() - 40 * (i + 2));
				batch.end();
			}
		}
        else if(myWorld.getInstructionsWindow())
		{
			batch.begin();
			yellowFont.draw(batch, "UZYJ MYSZY DO PORUSZANIA PALETKA.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 9 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "NA POCZATKU GRY MASZ DO DYSPOZYCJI 3 PILKI. \nJESLI PILKA DOTKNIE DOLNEJ KRAWEDZI PLANSZY \nTO STRACISZ JEDNA PILKE.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 8 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "WCISNIJ 'P' W TAKCIE GRY ABY WLACZYC PAUSE. \nWCISNIJ 'P' PONOWNIE ABY WZNOWIC GRE.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 7 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "ZA POMOCA PALETKI ZBIERAJ BONUSY WYPADAJACE ZE ZBITYCH BLOKOW. \nULATWIA CI ONE GRE.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 6 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "KAZDY BONUS TRWA 10s, DODATKOWE PILKI SA PERMANENTNE. \nJESLI W TRAKCIE TRWANIA JEDNEGO BONUSU ZBIERZESZ INNY, \nTO AUTOMATYCZNIE ZASTAPI ON OBECNIE AKTYWNY BONUS", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 5 / 10);
			batch.end();
			
			//poczatek wyswietlania spowolnienia 
			batch.begin();
			batch.draw(slowTexture, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8 + myWorld.getPowerUpWidth() * 1 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 10 - myWorld.getPowerUpHeight() * 6 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()));
			batch.end();
            
			batch.begin();
			glyphLayout.setText(yellowFont, "S");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8 + (myWorld.getPowerUpWidth()/2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "- SPOWALNIA PILKE", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8 + myWorld.getPowerUpWidth() * 2 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()),
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 10);
			batch.end();
			//koniec wyswietlanai spowolnienia
			
			//poczatek wyswietlania dodatkowej pilki
			batch.begin();
			batch.draw(lifeTexture, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 8 + myWorld.getPowerUpWidth() * 1 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 10 - myWorld.getPowerUpHeight() * 6 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()));
			batch.end();
            
			batch.begin();
			glyphLayout.setText(yellowFont, "L");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 8 + (myWorld.getPowerUpWidth()/2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "- OTRZYMUJESZ DODATKOWA PILKE", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 8 + myWorld.getPowerUpWidth()* 2 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()),
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 10);
			batch.end();
			//koniec wyswietlania dodatkowej pilki
			
			//poczatek wyswietlania rozszerzenia paletki 
			batch.begin();
			batch.draw(expandTexture, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8 + myWorld.getPowerUpWidth() * 1 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 10 - myWorld.getPowerUpHeight() * 6 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()));
			batch.end();
            
			batch.begin();
			glyphLayout.setText(yellowFont, "E");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8 + (myWorld.getPowerUpWidth()/2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "- POSZERZA PALETKE", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8 + myWorld.getPowerUpWidth() * 2 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()),
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 10);
			batch.end();
			//koniec wyswietlanai rozszerzenia paletki
			
			//poczatek wyswietlania ulepszenia pilki
			batch.begin();
			batch.draw(strengthTexture, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 8 + myWorld.getPowerUpWidth() * 1 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 10 - myWorld.getPowerUpHeight() * 6 / 7 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()));
			batch.end();
            
			batch.begin();
			glyphLayout.setText(yellowFont, "P");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 8 + (myWorld.getPowerUpWidth()/2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()), 
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 10);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "- PILKA MA WIEKSZA MOC", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 8 + myWorld.getPowerUpWidth()* 2 * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()),
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 10);
			batch.end();
			//koniec wyswietlania ulepszenia pilki
			
			batch.begin();
			yellowFont.draw(batch, "ZYCZE MILEJ ZABAWY.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 2 / 10);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack2().x, myWorld.getBack2().y, myWorld.getBack2().width, myWorld.getBack2().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack2().x + myWorld.getBack2().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack2().y + myWorld.getBack2().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);  
			batch.end();
		}
        else if(myWorld.getNameWindow())
		{		
			if(myWorld.getEmptyName() == false)
			{
				batch.begin();
				glyphLayout.setText(yellowFont, "WPROWADZ SWOJE IMIE:");
				yellowFont.draw(batch, glyphLayout,
				(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
				myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height + 60);
				batch.end();
			}
			else if(myWorld.isNameEmpty() == false || myWorld.getEmptyName())
			{
				batch.begin();
				glyphLayout.setText(yellowFont, "WPROWADZ SWOJE IMIE KONIECZNIE:");
				yellowFont.draw(batch, glyphLayout,
				(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
				myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height + 60);
				batch.end();
			}
				
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getTextField().x, myWorld.getTextField().y, myWorld.getTextField().width, myWorld.getTextField().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, String.valueOf(myWorld.getName())); 
			blueFont.draw(batch, glyphLayout,
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBegin().x, myWorld.getBegin().y, 
						myWorld.getBegin().width, myWorld.getBegin().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "PLAY!");
			greenFont.draw(batch, glyphLayout, 
			(myWorld.getBegin().x + myWorld.getBegin().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBegin().y + myWorld.getBegin().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack3().x, myWorld.getBack3().y,
						myWorld.getBack3().width, myWorld.getBack3().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack3().x + myWorld.getBack3().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack3().y + myWorld.getBack3().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
		}
        else if(myWorld.getScoreWindow())
		{						
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getSubmit().x, myWorld.getSubmit().y, myWorld.getSubmit().width, myWorld.getSubmit().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "CONTINUE");
			greenFont.draw(batch, glyphLayout,
			(myWorld.getSubmit().x + myWorld.getSubmit().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getSubmit().y + myWorld.getSubmit().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			if(myWorld.isNewHighscore())
			{
				batch.begin();
				glyphLayout.setText(blueFont, "!!!NEW HIGHSCORE!!!");
				blueFont.draw(batch, glyphLayout, 
				(myWorld.getSubmit().x + myWorld.getSubmit().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
				myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) + myWorld.getTextField().height * 13); 
				batch.end();
			}
			
			batch.begin();
			
			glyphLayout.setText(yellowFont, "Summary:");
			yellowFont.draw(batch, glyphLayout,
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height * 11);
			
			glyphLayout.setText(yellowFont, "Points: " + String.valueOf(myWorld.getPoints()));
			yellowFont.draw(batch, "Points: " + String.valueOf(myWorld.getPoints()),
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height * 9);
			
			glyphLayout.setText(yellowFont, "Level: " + String.valueOf(myWorld.getLevel()));
			yellowFont.draw(batch, "Level: " + String.valueOf(myWorld.getLevel()),
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height * 7);
			
			glyphLayout.setText(yellowFont, "Destroyed blocks: " + String.valueOf(myWorld.getDestroyedBlocks()));
			yellowFont.draw(batch, glyphLayout,
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height * 5);
			
			glyphLayout.setText(yellowFont, "Good job " + String.valueOf(myWorld.goodJob()));
			yellowFont.draw(batch, glyphLayout,
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height);
			batch.end();
		}
        else if(myWorld.getExitWindow())
		{
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getPlayAgain().x, myWorld.getPlayAgain().y, myWorld.getPlayAgain().width, myWorld.getPlayAgain().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "PLAY AGAIN");
			greenFont.draw(batch, glyphLayout, 
			(myWorld.getPlayAgain().x + myWorld.getPlayAgain().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getPlayAgain().y + myWorld.getPlayAgain().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getExit2().x, myWorld.getExit2().y, myWorld.getExit2().width, myWorld.getExit2().height);
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "EXIT");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getExit2().x + myWorld.getExit2().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getExit2().y + myWorld.getExit2().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);  
			batch.end();
		}
        else if(myWorld.getGameWindow())
		{
        	//GameBlock block : myWorld.getBlocks()
            for(Brick block: myWorld.getBlocks())
            {
               	//shapeRenderer.begin(ShapeType.Filled);
               	
               	switch(block.getType())
				{
				case ICE:
						batch.begin();
						if(block.getStrength() == 2)
						{
							batch.draw(iceTexture, 
									   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
									  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						}
						else
						{
							batch.draw(brokenIceTexture, 
									   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
									  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						}
						batch.end();
						break;
					case METAL:
						batch.begin();
						if(block.getStrength() == 3)
						{
							batch.draw(metalTexture, 
									   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
									  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						}
						else if(block.getStrength() == 2)
						{
							batch.draw(littleDeformedMetalTexture, 
									   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
									  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						}
						else
						{
							batch.draw(deformedMetalTexture, 
									   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
									  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						}
						batch.end();
						break;
					case WEAK:
						batch.begin();
						batch.draw(weakTexture, 
								   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
								  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						batch.end();
						break;
					default:
						break;	
				}
            }
            //Rysowanie obiektow z GameWorld
            for(PowerUp block: myWorld.getBuffs())
            {
               	//shapeRenderer.begin(ShapeType.Filled);
               	
               	switch(block.getType())
				{
					case SLOW:
						batch.begin();
						batch.draw(slowTexture, 
								   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
								  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						batch.end();

			            batch.begin();
		    			glyphLayout.setText(yellowFont, "S");
		    			yellowFont.draw(batch, glyphLayout, 
		    			(block.getRectangle().x + block.getRectangle().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2,
		    			myWorld.getDesktopHeight() - (block.getRectangle().y + block.getRectangle().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
		    			batch.end();
		    			break;
					case LIFE:
						batch.begin();
						batch.draw(lifeTexture, 
								   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
								  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						batch.end();
						
			            batch.begin();
		    			glyphLayout.setText(yellowFont, "L");
		    			yellowFont.draw(batch, glyphLayout, 
		    			(block.getRectangle().x + block.getRectangle().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2,
		    			myWorld.getDesktopHeight() - (block.getRectangle().y + block.getRectangle().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
		    			batch.end();
						break;
					case EXPAND_RECT:
						batch.begin();
						batch.draw(expandTexture, 
								   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
								  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						batch.end();
						
			            batch.begin();
		    			glyphLayout.setText(yellowFont, "E");
		    			yellowFont.draw(batch, glyphLayout, 
		    			(block.getRectangle().x + block.getRectangle().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2,
		    			myWorld.getDesktopHeight() - (block.getRectangle().y + block.getRectangle().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
		    			batch.end();
						break;
					case STRENGTH:
						batch.begin();
						batch.draw(strengthTexture, 
								   myWorld.getDesktopWidth() / myWorld.getScreenWidth() * (block.getRectangle().x), 
								  (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) * (myWorld.getScreenHeight() - block.getRectangle().y - block.getRectangle().height));
						batch.end();
						
			            batch.begin();
		    			glyphLayout.setText(yellowFont, "P");
		    			yellowFont.draw(batch, glyphLayout, 
		    			(block.getRectangle().x + block.getRectangle().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2,
		    			myWorld.getDesktopHeight() - (block.getRectangle().y + block.getRectangle().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
		    			batch.end();
						break;
					default:
						break;	
				}
            }
            
            // Tells shapeRenderer to begin drawing filled shapes
            shapeRenderer.begin(ShapeType.Filled);

            // Chooses RGB Color of 87, 109, 120 at full opacity
            shapeRenderer.setColor(1.0f, 0.6f, 0.0f, 1);

            // Draws the rectangle from myWorld (Using ShapeType.Filled)
            shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y, myWorld.getRect().width, myWorld.getRect().height);

            // Tells the shapeRenderer to finish rendering
            // We MUST do this every time.
            shapeRenderer.end();

            // Tells shapeRenderer to draw an outline of the following shapes
            shapeRenderer.begin(ShapeType.Line);

            // Chooses RGB Color of 255, 109, 120 at full opacity
            shapeRenderer.setColor(0.83f, 0.83f, 0.83f, 1);

            // Draws the rectangle from myWorld (Using ShapeType.Line)
            shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y, myWorld.getRect().width, myWorld.getRect().height);

            shapeRenderer.end();
                
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0.83f, 0.83f, 0.83f, 1);
            shapeRenderer.circle(myWorld.getCircle().x, myWorld.getCircle().y, myWorld.getCircle().radius);
            shapeRenderer.end();
         
            batch.begin();
            redFont.draw(batch, myWorld.getGameText(), 10, 17);
            redFont.draw(batch, "Points: " + String.valueOf(myWorld.getPoints()), 150, 17);
    		redFont.draw(batch, "Lifes: " + String.valueOf(myWorld.getLifePoints()), 270, 17);
    		redFont.draw(batch, "Level: " + String.valueOf(myWorld.getLevel()), 400, 17);
    		redFont.draw(batch, "Gra: " + String.valueOf(myWorld.getName()), 500, 17);
    		bigYellowFont.draw(batch, "  " + String.valueOf(myWorld.timer()) + "s", 0, myWorld.getDesktopHeight() - 5);
    		batch.end();
    		if(myWorld.getStrength())
    		{
    			batch.begin();
    			glyphLayout.setText(bigYellowFont, "  " + String.valueOf(myWorld.timer()) + "s");
    			bigYellowFont.draw(batch, " STRENGTH", 0 + glyphLayout.width, myWorld.getDesktopHeight() - 5);
    			batch.end();
    		}
    		else if(myWorld.getSlow())
    		{
    			batch.begin();
    			glyphLayout.setText(bigYellowFont, "  " + String.valueOf(myWorld.timer()) + "s");
    			bigYellowFont.draw(batch, " SLOW", 0 + glyphLayout.width, myWorld.getDesktopHeight() - 5);
    			batch.end();
    		}
    		else if(myWorld.getExpand())
    		{
    			batch.begin();
    			glyphLayout.setText(bigYellowFont, "  " + String.valueOf(myWorld.timer()) + "s");
    			bigYellowFont.draw(batch, " EXPAND", 0 + glyphLayout.width, myWorld.getDesktopHeight() - 5);
    			batch.end();
    		}
    		
			if(myWorld.getBallInPlay() == false && myWorld.getEnd() == false)
            {
            	batch.begin();
				glyphLayout.setText(bigYellowFont, myWorld.getClickToPlay());
				bigYellowFont.draw(batch, glyphLayout, 
				(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
				(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
				batch.end();
            }
            
            batch.begin();
			
			if(myWorld.getEnd())
			{
				glyphLayout.setText(bigYellowFont, myWorld.getLoseText());
				bigYellowFont.draw(batch, glyphLayout, 
				(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
				(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
			}
			else if(myWorld.getPause())
			{
				glyphLayout.setText(bigYellowFont, "GAME PAUSED. PRESS P TO CONTINUE");
				bigYellowFont.draw(batch, glyphLayout, 
				(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
				(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
			}
			batch.end();
		}     
    }  
}
