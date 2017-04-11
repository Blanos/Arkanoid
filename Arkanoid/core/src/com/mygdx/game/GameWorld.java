package com.mygdx.game;

import com.mygdx.game.objects.BrickType;
import com.mygdx.game.objects.PowerUp;
import com.mygdx.game.objects.PowerUpType;
import com.mygdx.game.objects.Brick;

import com.mygdx.game.score.InputFile;
import com.mygdx.game.sounds.Jukebox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class GameWorld {

	private float screenWidth;
	private float screenHeight;
	private int desktopWidth;
	private int desktopHeight;
	
	private List<Brick> blocks; //lista blokow
	private List<PowerUp> buffs; //lista powerupow na planszy
	
	private Rectangle start; //przycisk rozpoczecia gry
	private Rectangle instructions; //przycisk przejscia do instrukcji
	private Rectangle highscore; //przycisk przejscia do tabeli wynikow
	private Rectangle textField; //bedzie sie na nim pisac nick
	private Rectangle begin; //przycisk rozpoczecia gry w oknie wpisywania imienia
	private Rectangle back1; //przycisk powrotu z okna highscore
	private Rectangle back2; //przycisk powrotu z okna instrukcji
	private Rectangle back3; //przycisk wyjscia z okna wpisywania imienia
	private Rectangle exit1; //przycisk wyjscia z gry w oknie startowym
	private Rectangle exit2; //przycisk wyjscia z gry gdy straci sie wszystkie zycia 
	private Rectangle submit; //przycisk kontynuacji w momencie zakonczenia gry przez strate wszystkich zyc
	private Rectangle playAgain; //przycisk ponownego rozpoczecia gry w momencie starty wszystkich zyc
	
	private boolean startWindow; //okresla czy wyswietlac okno startowe
	private boolean highscoreWindow; //okresla czy wyswietlic okno najlepszych wynikow
	private boolean instructionsWindow; //okresla czy wyswietlic okno instrukcji
	private boolean nameWindow; //okresla czy wyswietlic okno z wprwadzeniem imienia
	private boolean gameWindow; //okresla czy wyswietlac okno gry
	private boolean scoreWindow; //okresla czy wyswietlac okno wynikow
	private boolean exitWindow; //okresla czy wyswietlac okno wyjscia/ponownej rozgrywki
	
	private boolean stickyPowerUp;
	private boolean lifePowerUp;
	private boolean expandRectPowerUp;
	private boolean slowPowerUp;
	private boolean strengthPowerUp;
	
	private int ballStrength; //okresla jaka sile ma pilka podczas zderzenia z klockiem
	private float slowDown; //okresla o ile spowalnia sie pilke podczas slowPowerUp
	
	private boolean emptyName;
	private boolean goodJob;
	
	private Rectangle rect; //paletka
	private int rectSpeed;
	
	private boolean byRightSide; //okresla czy pilka jest z prawej strony paletki ponizej rect.y
	private boolean byLeftSide; //okresla czy pilka jest z lewej strony paletki ponizej rect.y
	private boolean sideReflect; //okresla czy pilka sie odbila od boku paletki 
	
	private Circle circle;
	private float cXDirection; //szybkosc pilki po osi x
	private float cYDirection; //szybkosc pilki po osi y
	private int baseCXDirection; //podstawowa szybkosc pilki po osi X
	private int baseCYDirection; //podstawowa szybkosc pilki po osi Y
	private int Cr;	//promien pilki
	private float lastXpos; //ostatnia pozycja pilki na osi X
	private float lastYpos; //ostatnia pozycja pilki na osi Y 

	private long startTime; //moemnt w ktorym zostal uruchomiony bonus
	private long middleTime; //okresla ile zostalo do zakonczenia dzialania bonusu
	
	private int points; //ilosc zdobyttych punktow
	private int level; //licznik rund
	private int lifePoints; //licznik punktow zycia
	private int destroyedBlocks; //licznik zniszczonych blokow

	private int blockWidth; //dlugosc klockow
	private int blockHeight; //wysokosc klockow
	private int spaceBtwnBlocks; //odstep miedzy klockami
	private int powerUpWidth;
	private int powerUpHeight;
	private int rectWidth;
	private int rectHeight;
	
	private int blockSize; //dlugosc przycisku
	private int blockSpace; //odstep miedzy blokami
	
	private String gameText = new String("Innotech Labs");
	private String winText = new String("YOU WIN");
	private String loseText = new String("YOU LOSE");
	private String clickToPlay = new String("CLICK TO PLAY");
		
	private boolean end; //okresla czy gra sie skonczyla
	private boolean restart; //okresla czy wcisnelismy Play Again
	private boolean pause; //okresla czy jest wlaczona pauza
	private boolean ballInPlay = false; //oznacza czy pilka ma sie poruszac po planszy czy tez stac na paletce
	
	private Random brickRand; 
	private Random brickTypeRand;
	private Random powerUpRand; 
	private Random powerUpTypeRand;

	private int X; //pozycja myszy
	
	private char name[];
	
	private InputFile load;
	private String file;
	private String names[];
	private String highscores[];
	private String levels[];
	private String blocksDestroyed[];

	public GameWorld(float screenWidth, float screenHeight, int desktopWidth, int desktopHeight)
	{
		this.setScreenHeight(screenHeight);
		this.setScreenWidth(screenWidth);
		this.setDesktopHeight(desktopHeight);
		this.setDesktopWidth(desktopWidth);
		
		buffs = new ArrayList<PowerUp>();
		
		startWindow = true;
		highscoreWindow = false;
		instructionsWindow = false;
		nameWindow = false;
		gameWindow = false;
		scoreWindow = false;
		exitWindow = false;	
		
		stickyPowerUp = false;
		lifePowerUp = false;
		expandRectPowerUp = false;
		slowPowerUp = false;
		strengthPowerUp = false;
		
		ballStrength = 1;
		slowDown = 1;
		
		emptyName = false;
		goodJob = false;
		
		blockWidth = 16; //dlugosc klockow
		blockHeight = 8; //wysokosc klockow
		spaceBtwnBlocks = 2; //odstep miedzy klockami
		powerUpWidth = 8;
		powerUpHeight = 4;
		rectWidth = 20;
		rectHeight = 7;
		
		this.rect = new Rectangle((screenWidth/2), screenHeight-20, rectWidth, rectHeight); //ostatniargument = 10 drugi argument -20
		rectSpeed = 3;

		byRightSide = false;
		byLeftSide = false;
		sideReflect = false;
		
		cXDirection = 0; //szybkosc pilki po osi x
		cYDirection = -2; //szybkosc pilki po osi y
		baseCXDirection = 0; //podstawowa szybkosc pilki po osi X
		baseCYDirection = -2; //podstawowa szybkosc pilki po osi Y
		Cr = 2;	//promien pilki
		
		this.circle = new Circle((rect.x+rect.width/2), rect.y-Cr, Cr);
			
		points = 0; //ilosc zdobyttych punktow
		level = 0; //licznik rund
		lifePoints = 3; //licznik punktow zycia
		destroyedBlocks = 0; //licznik zniszczonych blokow

		blockSize = 9;
		blockSpace = 1;
		
		ballInPlay = false; //oznacza czy pilka ma sie poruszac po planszy czy tez stac na paletce
		restart = false;
		end = false; 
		
		brickRand = new Random();
		brickTypeRand = new Random();
		powerUpRand = new Random();
		powerUpTypeRand = new Random();

		name = new char[10]; //{' ', ' ', ' ', ' ', ' '};
		
		load = new InputFile(); //?
		names = new String[10];
		highscores = new String[10];
		levels = new String[10];
		blocksDestroyed = new String[10];
		
		load();
		loadFile();
		initBlocks();
		initButtons();
	}

	
	public void initBlocks() //inicjalizuje bloki do zbijania
	{
		blocks = new ArrayList<Brick>();
		
		for(int y = (int) (0.1*screenHeight); y < 0.4*screenHeight; y=y+spaceBtwnBlocks+blockHeight)
		{
			for(int x = (int) (0.1*screenWidth); x < 0.8 * screenWidth; x=x+spaceBtwnBlocks+blockWidth)
			{
				Rectangle rect = new Rectangle(x, y, blockWidth, blockHeight);
				if(brickRand.nextInt(4) < 3) //to taka moja wariacja na temat roznorodnosci poziomow
				{
					int rand = brickTypeRand.nextInt(20);
					if(rand < 4)
					{
						blocks.add(new Brick(rect, BrickType.ICE, 2));
					}
					else if(rand > 15)
					{
						blocks.add(new Brick(rect, BrickType.METAL, 3));
					}
					else
					{
						blocks.add(new Brick(rect, BrickType.WEAK, 1));
					}					
				}
			}		
		}
	}
	
	public void initButtons() //tworzy wszystkie przyciski w grze
	{
		start = new Rectangle(screenWidth*2/6-((blockSize+blockSpace)*3/2), screenHeight*3/5, (blockSize+blockSpace)*3, (blockSize+blockSpace)); // jest ulamkiem wiec dzielac przez niego tak naprawde zwiekszamy liczbe 
		highscore = new Rectangle(screenWidth*4/6-(blockSize+blockSpace)*3/2, screenHeight*3/5, (blockSize+blockSpace)*3, (blockSize+blockSpace));
		instructions = new Rectangle(screenWidth*2/6-((blockSize+blockSpace)*3/2), screenHeight*4/5, (blockSize+blockSpace)*3, (blockSize+blockSpace));
		exit1 = new Rectangle(screenWidth*4/6-(blockSize+blockSpace)*3/2, screenHeight*4/5, (blockSize+blockSpace)*3, (blockSize+blockSpace));
		
		back1 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		back2 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*5/6, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		
		textField = new Rectangle(screenWidth/2-(blockSize+blockSpace) * 3 / 2, screenHeight/3, (blockSize+blockSpace)*3, (blockSize+blockSpace));
		begin = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight/2, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		back3 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			
		submit = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight/2, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		
		playAgain = new Rectangle(screenWidth/2-(blockSize+blockSpace)*3/2, screenHeight/3, (blockSize+blockSpace)*3, (blockSize+blockSpace));
		exit2 = new Rectangle(screenWidth/2-(blockSize+blockSpace)*3/2, screenHeight/2, (blockSize+blockSpace)*3, (blockSize+blockSpace));	
	}
	
	public void initPowerUps(float x, float y) //w trakcie gry tworzy bonusy po zbiciu klockow
	{
		if(powerUpRand.nextInt(100) > 80)
		{
			int rand = powerUpTypeRand.nextInt(70);
			if(rand < 10)
			{
				buffs.add(new PowerUp(new Rectangle(x + blockWidth/2 - powerUpWidth/2, y + blockHeight/2 - powerUpHeight/2, powerUpWidth, powerUpHeight), PowerUpType.LIFE));
			}
			else if(rand >= 10 && rand < 30)
			{
				buffs.add(new PowerUp(new Rectangle(x + blockWidth/2 - powerUpWidth/2, y + blockHeight/2 - powerUpHeight/2, powerUpWidth, powerUpHeight), PowerUpType.EXPAND_RECT));
			}
			else if(rand >= 30 && rand < 50)
			{
				buffs.add(new PowerUp(new Rectangle(x + blockWidth/2 - powerUpWidth/2, y + blockHeight/2 - powerUpHeight/2, powerUpWidth, powerUpHeight), PowerUpType.SLOW));
			}	
			else
			{
				buffs.add(new PowerUp(new Rectangle(x + blockWidth/2 - powerUpWidth/2, y + blockHeight/2 - powerUpHeight/2, powerUpWidth, powerUpHeight), PowerUpType.STRENGTH));
			}
		}
	}
	
	public boolean saveFileExists()
	{
		File f = new File("highscore.txt");
		return f.exists();
	}
	
	public void load() 
	{
		try
		{
			if(!saveFileExists())
			{
				initSaveFile();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	public void initSaveFile() //tworzy wyzerowany plik z highscore
	{
		String everything = "";
		for(int i = 0; i < 10; i++)
		{
			names[i] = "-----";
			highscores[i] = "0";
			levels[i] = "0";
			blocksDestroyed[i] = "0";
			
			if(i == 9)
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + levels[i] + "," + blocksDestroyed[i]);
			}
			else
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + levels[i] + "," + blocksDestroyed[i] + ":");
			}	
		}
		load.writeFile("highscore.txt", everything);
	}
	
	public void saveToFile() //zapisuje wszystkie highscore do juz istniejacego pliku tekstowego
	{
		String everything = "";
		
		for(int i = 0; i < highscores.length; i++)
		{
			if(i == highscores.length - 1 )
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + levels[i] + "," + blocksDestroyed[i]);
			}
			else
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + levels[i] + "," + blocksDestroyed[i] + ":");
			}
		}
		load.writeFile("highscore.txt", everything);
	}
	
	public void loadFile() //zczytuje z pliku highscore
	{		
		file = load.readFile("highscore.txt");
		String[] splitFile = file.split(":");
				
		for(int i = 0; i < splitFile.length; i++)
		{
			String[] splitString = splitFile[i].split(",");
			names[i] = splitString[0];
			highscores[i] = splitString[1];
			levels[i] = splitString[2];
			blocksDestroyed[i] = splitString[3];
		}
	}
	
	public String showHighscore(int i) //wyswietla ladnie uporzadkowane highscore 
	{	
		if(i == 9)
		{
			return String.format("%02d. %12s %17s %10s   %20s", (i + 1), highscores[i], names[i], levels[i], blocksDestroyed[i]); //"%02d.%12s%17s%10s%20s"
		}
		else
		{
			return String.format("%02d. %12s %17s %10s %20s", (i + 1), highscores[i], names[i], levels[i], blocksDestroyed[i]); //"%02d.%12s%17s%10s%20s"
		}
	}
	
	public boolean isNewHighscore()
	{
		for(int i = highscores.length - 1; i >= 0; i--)
		{
			if(points >= Integer.parseInt(highscores[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public void sortHighscore() //sortuje highscore. Jesli ta metoda zostaje wykonana to znaczy ze gracz zdobyl wystarczajaca ilosc punktow aby byc na 10 miejscu
								//dlatego juz na poczatku zapisuje punkty na pozycji 10 w highscore
	{
		String swapPoints;
		String swapNames;
		String swapLevels;
		String swapDestroyed;
		
		highscores[highscores.length-1] = String.valueOf(points);
		names[names.length-1] = String.valueOf(getName());
		levels[levels.length-1] = String.valueOf(level);
		blocksDestroyed[blocksDestroyed.length-1] = String.valueOf(destroyedBlocks);
		
		for(int i = highscores.length - 1; i >= 1; i--)
		{	
		    if(points >= Integer.parseInt(highscores[i - 1]))
		    {
		    	swapPoints = highscores[i - 1];
				swapNames = names[i - 1];
				swapLevels = levels[i - 1];
				swapDestroyed = blocksDestroyed[i - 1];
				
				highscores[i - 1] = String.valueOf(points);
				names[i - 1] = String.valueOf(getName());
				levels[i - 1] = String.valueOf(level);
				blocksDestroyed[i - 1] = String.valueOf(destroyedBlocks);
				
				highscores[i] = swapPoints;
				names[i] = swapNames;
				levels[i] = swapLevels;
				blocksDestroyed[i] = swapDestroyed;
		    }
		}
	}
	
	public void clearTextField()
	{
		for(int i = 0; i < name.length; i++)
		{
			name[i] = 32;
		}
	}
	
	public void insertName(char character) //sluzy do wpisywania imienia po zakonczeniu gry
	{
		for(int i = 0; i < name.length; i++)
		{
			//if sprawdzajacy czy pozycja do ktorej ma byc zapisany znak jest pusta oraz czy wcisniety klawisz to litera
			if(name[i] == 32 && (character  >= 65 && character <= 90 || character >= 97 && character <= 122) )
			{
				name[i] = character;
				break;
			}
			else if(i == name.length - 1 && character == 8)
			{
				name[i] = 32;
				break;
			}
			else if(character == 8 && name[i + 1] == 32)
			{
				name[i] = 32;
				break;
			}
		}
	}
	
	public boolean isNameEmpty() //sprawdza czy zostal wprowadzony jakis znak do imienia
	{
		for(int i = 0; i < name.length; i++)
		{
			if( name[i] != 32) //name[i] != 62 &&
			{
				return true;
			}
		}
		return false;
	}
	
	public String goodJob()
	{
		for(int i = name.length - 1; i >= 0; i--)
		{
			if(i == name.length - 1 && name[i] != 32)
			{
				System.out.println("pierwszy");
				return String.valueOf(getName()) + "!";
			}
			else if(name[i] == 32 && name[i - 1] != 32 )
			{
				System.out.println("drugi");
				if(goodJob)
				{
					return String.valueOf(getName());
				}
				else
				{
					name[i] = '!';
					goodJob = true;
					return String.valueOf(getName());
				}
			}
		}
		return String.valueOf(getName()) + "";
	}
	
	public void pause(int keycode) //wywoluje pause w grze
	{
		if(keycode == 44 && pause)
		{
			pause = false;
		}
		else if(keycode == 44 && pause == false)
		{
			pause = true;
		}
	}
	
	public void maveRect(int keyNo) 
	{
	
	}
		
	public void maveRectAlphabetic(char character) 
	{
		
	}
	
	public void click(int screenX, int screenY, int button)
	{
		System.out.println("click");
		if(startWindow  && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= start.x && (screenWidth/desktopWidth)*screenX <= start.x+start.width &&
				(screenWidth/desktopWidth)*screenY >= start.y && (screenWidth/desktopWidth)*screenY <= start.y+start.height)
			{
				startWindow = false;
				nameWindow = true;
				clearTextField();
			}
			else if((screenWidth/desktopWidth)*screenX >= highscore.x && (screenWidth/desktopWidth)*screenX <= highscore.x+highscore.width &&
					(screenWidth/desktopWidth)*screenY >= highscore.y && (screenWidth/desktopWidth)*screenY <= highscore.y+highscore.height)
			{
				startWindow = false;
				highscoreWindow = true;
			}
			else if((screenWidth/desktopWidth)*screenX >= instructions.x && (screenWidth/desktopWidth)*screenX <= instructions.x+instructions.width &&
					(screenWidth/desktopWidth)*screenY >= instructions.y && (screenWidth/desktopWidth)*screenY <= instructions.y+instructions.height)
			{
				startWindow = false;
				instructionsWindow = true;
			}
			else if((screenWidth/desktopWidth)*screenX >= exit1.x && (screenWidth/desktopWidth)*screenX <= exit1.x+exit1.width &&
					(screenWidth/desktopWidth)*screenY >= exit1.y && (screenWidth/desktopWidth)*screenY <= exit1.y+exit1.height)
			{
				Gdx.app.exit();
			}
		}
		else if(nameWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= begin.x && (screenWidth/desktopWidth)*screenX <= begin.x+begin.width &&
					(screenWidth/desktopWidth)*screenY >= begin.y && (screenWidth/desktopWidth)*screenY <= begin.y+begin.height)
			{
				if(isNameEmpty())
				{
					nameWindow = false;
					gameWindow = true;
				}	
				else
				{
					emptyName = true;
				}
			}
			else if((screenWidth/desktopWidth)*screenX >= back3.x && (screenWidth/desktopWidth)*screenX <= back3.x+back3.width &&
					(screenWidth/desktopWidth)*screenY >= back3.y && (screenWidth/desktopWidth)*screenY <= back3.y+back3.height)
			{
				nameWindow = false;
				startWindow = true;
				emptyName = false;
			}
		}
		else if(scoreWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= submit.x && (screenWidth/desktopWidth)*screenX <= submit.x+submit.width &&
					(screenWidth/desktopWidth)*screenY >= submit.y && (screenWidth/desktopWidth)*screenY <= submit.y+submit.height)
			{
				scoreWindow = false;
				exitWindow = true;	
			}
		}
		else if(exitWindow && button==0)
		{
			if((screenWidth/desktopWidth)*screenX >= playAgain.x && (screenWidth/desktopWidth)*screenX <= playAgain.x+playAgain.width &&
					(screenWidth/desktopWidth)*screenY >= playAgain.y && (screenWidth/desktopWidth)*screenY <= playAgain.y+playAgain.height)
			{	
				restart = true;
			}
			else if((screenWidth/desktopWidth)*screenX >= exit2.x && (screenWidth/desktopWidth)*screenX <= exit2.x+exit2.width &&
					(screenWidth/desktopWidth)*screenY >= exit2.y && (screenWidth/desktopWidth)*screenY <= exit2.y+exit2.height)
			{	
				Gdx.app.exit();
			}
		}
		else if(highscoreWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= back1.x && (screenWidth/desktopWidth)*screenX <= back1.x+back1.width &&
					(screenWidth/desktopWidth)*screenY >= back1.y && (screenWidth/desktopWidth)*screenY <= back1.y+back1.height)
			{
				highscoreWindow = false;
				startWindow = true;
			}
		}
		else if(instructionsWindow && button ==0)
		{
			if((screenWidth/desktopWidth)*screenX >= back2.x && (screenWidth/desktopWidth)*screenX <= back2.x+back2.width &&
					(screenWidth/desktopWidth)*screenY >= back2.y && (screenWidth/desktopWidth)*screenY <= back2.y+back2.height)
			{
				instructionsWindow = false;
				startWindow = true;
			}
		}
		else if(gameWindow && button == 0)
		{
			if(end)
			{
				gameWindow = false;
				scoreWindow = true;
				if(isNewHighscore())
				{
					loadFile();
					sortHighscore();
					saveToFile();
				}
			}
			else if(ballInPlay == false)
			{
				ballInPlay=true;
			}
		}
	}
	
	public void moveByMouse(int screenX) //zczytuje tylko pozycje myszy
	{
		X = screenX;
	}
	
	public void move(int axisX) //wykorzystuje moveByMouse i dopiero porusza odpowiednio paletka
	{
		if((screenWidth/desktopWidth)*axisX - rect.x <= 1.5 && (screenWidth/desktopWidth)*axisX - rect.x > 0 ||
			rect.x - (screenWidth/desktopWidth)*axisX  <= 1.5 && rect.x - (screenWidth/desktopWidth)*axisX > 0)
		{

		}
		else if((screenWidth/desktopWidth)*axisX < rect.x)
		{
			rect.x = rect.x - rectSpeed;
		}
		else if((screenWidth/desktopWidth)*axisX > rect.x)
		{
			rect.x = rect.x + rectSpeed;	
		}	
		if(rect.x + rect.width > screenWidth)
		{
			rect.x = screenWidth - rect.width;
		}
		else if(rect.x < 0)
		{
			rect.x = 0;
		}
	}

	public void update(float delta) 
	{
		
		if(gameWindow)
		{
			if(blocks.isEmpty()) //tworzenie nowych blokow w przypadku gdy wszystkie zostaly zniszczone 
			{
				initBlocks();
				level = level + 1;
				cXDirection = 1; //szybkosc pilki po osi x
				cYDirection = -2 * slowDown; //szybkosc pilki po osi y
				ballInPlay = false;
				Jukebox.play("win");
			}
			updatePowerUp();
			isBuffEnd();
			if(pause == false)
			{
				updateBall();
			}
		}
	}
	
	public long timer() //liczy czas jaki pozostal do wygasniecia bonusu. Sluzy wyswietlaniu czasu
	{
		if(slowPowerUp || stickyPowerUp || expandRectPowerUp || strengthPowerUp)
		{
			middleTime = System.nanoTime()/1000000000 - startTime;
			return 10 - middleTime;
		}
		else
		{
			middleTime = 0;
			return middleTime;
		}
	}
	
	public void isBuffEnd()	//sprawdza czy buff sie skonczyl przez uplyw czasu i wylacza bonus ktory sie skonczyl
	{
		if(System.nanoTime()/1000000000 - startTime >= 10)
		{
			if(slowPowerUp)
			{
				removeSlow();
			}
			else if(expandRectPowerUp)
			{
				removeExpand();
			}
			else if(strengthPowerUp)
			{
				removeStrength();
			}
		}
	}
	
	public void removeExpand()
	{
		rect.width = rectWidth;
		expandRectPowerUp = false;
	}
	
	public void removeStrength()
	{
		ballStrength = 1;
		strengthPowerUp = false;
	}
	
	public void removeSlow()
	{
		if(cYDirection > 0)
		{
			cYDirection = 2;
		}
		else if(cYDirection < 0)
		{
			cYDirection = -2;
		}
		slowDown = 1;				
		slowPowerUp = false;
	}
	
	public void updatePowerUp() //sprawdza czy zostal zebrany bonus i ewentualnie nadpisuje juz istniejacy
	{
		for(int i = 0; i < buffs.size(); i++) 
		{
			buffs.get(i).getRectangle().y = buffs.get(i).getRectangle().y + 1;
			
			if(buffs.get(i).getRectangle().y + buffs.get(i).getRectangle().getHeight() > rect.y &&
			   buffs.get(i).getRectangle().y < rect.y + rect.height &&
			   buffs.get(i).getRectangle().x + buffs.get(i).getRectangle().getWidth() >= rect.x	&&
			   buffs.get(i).getRectangle().x <= rect.x + rect.width)
			{
				if(buffs.get(i).getType() == PowerUpType.SLOW)
				{
					if(cYDirection > 0)
					{
						cYDirection = 1;
					}
					else if(cYDirection < 0)
					{
						cYDirection = -1;
					}
					slowDown = (float) 0.5;
					slowPowerUp = true;
					startTime = System.nanoTime()/1000000000;
					removeExpand();
					removeStrength();					
				}
				else if(buffs.get(i).getType() == PowerUpType.EXPAND_RECT)
				{
					if(expandRectPowerUp == false)
					{
						rect.width = rect.width + rect.width/2;
					}
					expandRectPowerUp = true;
					startTime = System.nanoTime()/1000000000;
					removeSlow();
					removeStrength();
				}
				else if(buffs.get(i).getType() == PowerUpType.LIFE)
				{
					lifePoints = lifePoints + 1;
				}
				else if(buffs.get(i).getType() == PowerUpType.STRENGTH)
				{
					strengthPowerUp = true;
					ballStrength = ballStrength + 1;
					startTime = System.nanoTime()/1000000000;
					removeSlow();
					removeExpand();
				}
				
				buffs.remove(i);
				points = points + 5;
				
			}
			else if(buffs.get(i).getRectangle().y + buffs.get(i).getRectangle().getHeight() >= screenHeight)
			{
				buffs.remove(i);
			}
		}
	}
	
	public void updateBall()
	{
		move(X);

		if(ballInPlay == false) //ustala pozycje pilki w przypadku jej straty
		{
			move(X);
			circle.x = rect.x + rect.width/2;	
			circle.y = rect.y - circle.radius;
			lastXpos = circle.x;
			lastYpos = circle.y;
			cXDirection = baseCXDirection; 
			cYDirection = baseCYDirection * slowDown; 
		}
		else
		{	
			circle.y = circle.y + cYDirection;
			circle.x = circle.x + cXDirection;
			
			//sprawdza czy pilka uderzyla w lewy gorny rog paletki
			if(Math.round(Math.sqrt((Math.pow((rect.x - circle.x), 2) + Math.pow((rect.y - circle.y), 2) ))) == Cr ||
				Math.round(Math.sqrt((Math.pow((rect.x - circle.x), 2) + Math.pow((rect.y - circle.y), 2) ))) == Cr - 1)
			{
				cYDirection = cYDirection * (-1);
				cXDirection = (float) -1.5 * slowDown;
				circle.x = (float) (((rect.x - circle.radius) - rect.x) * Math.cos(Math.toRadians(45)) - (rect.y - rect.y) * Math.sin(Math.toRadians(45)) + rect.x); // obrót odcinka AB o k¹t \alpha, gdzie œrodkiem obrotu jest punkt B 
				circle.y = (float) (((rect.x - circle.radius) - rect.x) * Math.sin(Math.toRadians(45)) + (rect.y - rect.y) * Math.cos(Math.toRadians(45)) + rect.y);
				Jukebox.play("bounce");
			}
			//prawy gorny rog paletki
			else if(Math.round(Math.sqrt((Math.pow((rect.x + rect.width - circle.x), 2) + Math.pow((rect.y - circle.y), 2) ))) == Cr || 
					Math.round(Math.sqrt((Math.pow((rect.x + rect.width - circle.x), 2) + Math.pow((rect.y - circle.y), 2) ))) == Cr - 1)
			{
				cYDirection = cYDirection * (-1);
				cXDirection = (float) 1.5 * slowDown;
				circle.x = (float) (((rect.x + rect.width + circle.radius - (rect.x + rect.width)) * Math.cos(Math.toRadians(315))) - ((rect.y - rect.y) * Math.sin(Math.toRadians(315))) + rect.x + rect.width); // obrót odcinka AB o k¹t \alpha, gdzie œrodkiem obrotu jest punkt B 
				circle.y = (float) (((rect.x + rect.width + circle.radius - (rect.x + rect.width)) * Math.sin(Math.toRadians(315))) + ((rect.y - rect.y) * Math.cos(Math.toRadians(315))) + rect.y);
				Jukebox.play("bounce");
			}
			//lewy dolny rog paletki
			else if(Math.round(Math.sqrt((Math.pow((rect.x - circle.x), 2) + Math.pow((rect.y + rect.height - circle.y), 2) ))) == Cr)// || 
					//Math.round(Math.sqrt((Math.pow((rect.x - circle.x), 2) + Math.pow((rect.y + rect.height - circle.y), 2) ))) == Cr - 1)
			{
				cXDirection = (float) -1.5 * slowDown;
				circle.x = (float) (((rect.x - circle.radius) - rect.x) * Math.cos(Math.toRadians(315)) - (rect.y + rect.height + circle.radius - (rect.y + rect.height)) * Math.sin(Math.toRadians(315)) + rect.x); // obrót odcinka AB o k¹t \alpha, gdzie œrodkiem obrotu jest punkt B 
				circle.y = (float) (((rect.x - circle.radius) - rect.x) * Math.sin(Math.toRadians(315)) + (rect.y + rect.height + circle.radius - (rect.y + rect.height)) * Math.cos(Math.toRadians(315)) + rect.y + rect.height);
				Jukebox.play("bounce");
			}
			//prawy dolny rog paletki
			else if(Math.round(Math.sqrt((Math.pow((rect.x + rect.width - circle.x), 2) + Math.pow((rect.y + rect.height - circle.y), 2) ))) == Cr) //|| 
					//Math.round(Math.sqrt((Math.pow((rect.x + rect.width - circle.x), 2) + Math.pow((rect.y + rect.height - circle.y), 2) ))) == Cr - 1)
			{
				cXDirection = (float) 1.5 * slowDown;
				circle.x = (float) (((rect.x + rect.width + circle.radius) - (rect.x + rect.width)) * Math.cos(Math.toRadians(45)) - (rect.y + rect.height + circle.radius - (rect.y + rect.height)) * Math.sin(Math.toRadians(45)) + rect.x + rect.width); // obrót odcinka AB o k¹t \alpha, gdzie œrodkiem obrotu jest punkt B 
				circle.y = (float) (((rect.x + rect.width + circle.radius) - (rect.x + rect.width)) * Math.sin(Math.toRadians(45)) + (rect.y + rect.height + circle.radius - (rect.y + rect.height)) * Math.cos(Math.toRadians(45)) + rect.y + rect.height);
				Jukebox.play("bounce");
			}
			else if(circle.y + circle.radius > rect.y && circle.y - circle.radius < rect.y + rect.height &&//pilka jest ponizej gornej krawedzi paletki
			   circle.x + circle.radius <= rect.x + 4) //prawa strona pilki jest na zewnatrz paletki
			{
				byLeftSide = true;
			}
			else if(circle.y + circle.radius > rect.y && circle.y - circle.radius < rect.y + rect.height &&//pilka jest ponizej gornej krawedzi paletki
				    circle.x - circle.radius >= rect.x + rect.width - 4) //lewa strona pilki jest na zewnatrz paletki
			{
				byRightSide = true;
			}
			else
			{
				byLeftSide = false;
				byRightSide = false;
			}
			
			if(byLeftSide && circle.x + circle.radius > rect.x)
			{
				circle.x = rect.x - circle.radius;
				if(sideReflect == false)
				{
					cXDirection = cXDirection * (-1);
				}
				sideReflect = true;
				Jukebox.play("bounce");
			}
			else if(byRightSide && circle.x - circle.radius < rect.x + rect.width)
			{
				circle.x = rect.x + rect.width + circle.radius;
				if(sideReflect == false)
				{
					cXDirection = cXDirection * (-1);
				}
				
				sideReflect = true;
				Jukebox.play("bounce");
			}
			else
			{
				for(int i = 0; i < blocks.size(); i++) //odbijanie pilki od klockow
				{
					if(Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y - circle.y), 2) ))) == Cr ||
					   Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y - circle.y), 2) ))) == Cr - 1)
					{
						if(lastXpos - circle.x < 0)
						{
							cXDirection = (float) -1.5 * slowDown;
						}
						if(lastYpos - circle.y < 0)
						{
							cYDirection = cYDirection * (-1);
						}
						
						blocks.get(i).setStrength(blocks.get(i).getStrength() - ballStrength);
						Jukebox.play("bounce");
						
						if(blocks.get(i).getStrength() <= 0)
						{
							initPowerUps(blocks.get(i).getRectangle().x, blocks.get(i).getRectangle().y);
							blocks.remove(i);
							points = points + 10;
							destroyedBlocks = destroyedBlocks + 1;
						}
						break;
					}
					else if(Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x + blocks.get(i).getRectangle().width - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y - circle.y), 2) ))) == Cr || 
							Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x + blocks.get(i).getRectangle().width - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y - circle.y), 2) ))) == Cr - 1)
					{
						if(lastXpos - circle.x > 0)
						{
							cXDirection = (float) 1.5 * slowDown;
						}
						if(lastYpos - circle.y < 0)
						{
							cYDirection = cYDirection * (-1);
						}
						
						blocks.get(i).setStrength(blocks.get(i).getStrength() - ballStrength);
						Jukebox.play("bounce");
						
						if(blocks.get(i).getStrength() <= 0)
						{
							initPowerUps(blocks.get(i).getRectangle().x, blocks.get(i).getRectangle().y);
							blocks.remove(i);
							points = points + 10;
							destroyedBlocks = destroyedBlocks + 1;
						}
						break;
					}
					else if(Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y + blocks.get(i).getRectangle().height - circle.y), 2) ))) == Cr ||
							Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y + blocks.get(i).getRectangle().height - circle.y), 2) ))) == Cr - 1)
					{
						if(lastXpos - circle.x < 0)
						{
							cXDirection = (float) -1.5 * slowDown;
						}
						if(lastYpos - circle.y > 0)
						{
							cYDirection = cYDirection * (-1);
						}
						
						blocks.get(i).setStrength(blocks.get(i).getStrength() - ballStrength);
						Jukebox.play("bounce");
						
						if(blocks.get(i).getStrength() <= 0)
						{
							initPowerUps(blocks.get(i).getRectangle().x, blocks.get(i).getRectangle().y);
							blocks.remove(i);
							points = points + 10;
							destroyedBlocks = destroyedBlocks + 1;
						}
						break;
					}
					else if(Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x + blocks.get(i).getRectangle().width - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y + blocks.get(i).getRectangle().height - circle.y), 2) ))) == Cr || 
						Math.round(Math.sqrt((Math.pow((blocks.get(i).getRectangle().x + blocks.get(i).getRectangle().width - circle.x), 2) + Math.pow((blocks.get(i).getRectangle().y + blocks.get(i).getRectangle().height - circle.y), 2) ))) == Cr - 1)
					{
						if(lastXpos - circle.x > 0)
						{
							cXDirection = (float) 1.5 * slowDown;
						}
						if(lastYpos - circle.y > 0)
						{
							cYDirection = cYDirection * (-1);
						}
						
						blocks.get(i).setStrength(blocks.get(i).getStrength() - ballStrength);
						Jukebox.play("bounce");
						
						if(blocks.get(i).getStrength() <= 0)
						{
							initPowerUps(blocks.get(i).getRectangle().x, blocks.get(i).getRectangle().y);
							blocks.remove(i);
							points = points + 10;
							destroyedBlocks = destroyedBlocks + 1;
						}
						break;
					}
					else if(circle.y - circle.radius <= blocks.get(i).getRectangle().y + blocks.get(i).getRectangle().height && 
							circle.x >= blocks.get(i).getRectangle().x &&
							circle.x <= blocks.get(i).getRectangle().x + blocks.get(i).getRectangle().width && 
							circle.y + circle.radius >= blocks.get(i).getRectangle().y) 	    
					{
						blocks.get(i).setStrength(blocks.get(i).getStrength() - ballStrength);
						cYDirection = cYDirection * (-1);
						Jukebox.play("bounce");
						
						if(blocks.get(i).getStrength() <= 0)
						{
							initPowerUps(blocks.get(i).getRectangle().x, blocks.get(i).getRectangle().y);
							blocks.remove(i);
							points = points + 10;
							destroyedBlocks = destroyedBlocks + 1;
						}
						break;
					}
					else if(circle.x - circle.radius <= blocks.get(i).getRectangle().x + blocks.get(i).getRectangle().width &&
							circle.y >= blocks.get(i).getRectangle().y && 
							circle.y <= blocks.get(i).getRectangle().y + blocks.get(i).getRectangle().height &&
							circle.x + circle.radius >= blocks.get(i).getRectangle().x)
					{
						blocks.get(i).setStrength(blocks.get(i).getStrength() - ballStrength);
						cXDirection = cXDirection * (-1);
						Jukebox.play("bounce");
						
						if(blocks.get(i).getStrength() <= 0)
						{
							initPowerUps(blocks.get(i).getRectangle().x, blocks.get(i).getRectangle().y);
							blocks.remove(i);
							points = points + 10;
							destroyedBlocks = destroyedBlocks + 1;
						}
						break;
					}
				}
				//odbijanie pilki od paletki
				if(circle.y + circle.radius >= rect.y && circle.x >= rect.x && circle.x <= rect.x+rect.width && 
						circle.y - circle.radius <= rect.y+rect.height)  
				{
					
					Jukebox.play("bounce");
					cYDirection = cYDirection * (-1);
					if(circle.x > rect.x && circle.x < rect.x + rect.width/13)
					{
						cXDirection = (float) -1.5 * slowDown;
					}
					else if(circle.x >= rect.x + rect.width/13 && circle.x < rect.x + 2 * rect.width/12)
					{
						cXDirection = (float) -1.25 * slowDown;
					}
					else if(circle.x >= rect.x + 2 * rect.width/12 && circle.x < rect.x + 3 * rect.width/12)
					{
						cXDirection = -1 * slowDown;
					}
					else if(circle.x >= rect.x + 3 * rect.width/12 && circle.x < rect.x + 4 * rect.width/12)
					{
						cXDirection = (float) -0.75 * slowDown;
					}
					else if(circle.x >= rect.x + 4 * rect.width/12 && circle.x < rect.x + 5 * rect.width/12)
					{
						cXDirection = (float) -0.5 * slowDown;
					}
					else if(circle.x >= rect.x + 5 * rect.width/12 && circle.x < rect.x + 6 * rect.width/12)
					{
						cXDirection = (float) -0.25 * slowDown;
					}
					else if(circle.x >= rect.x + 6 * rect.width/12 && circle.x < rect.x + 7 * rect.width/12)
					{
						cXDirection = (float) 0.25 * slowDown;
					}
					else if(circle.x >= rect.x + 7 * rect.width/12 && circle.x < rect.x + 8 * rect.width/12)
					{
						cXDirection = (float) 0.5 * slowDown;
					}
					else if(circle.x >= rect.x + 8 * rect.width/12 && circle.x < rect.x + 9 * rect.width/12)
					{
						cXDirection = (float) 0.75 * slowDown;
					}
					else if(circle.x >= rect.x + 9 * rect.width/12 && circle.x < rect.x + 10 * rect.width/12)
					{
						cXDirection = 1 * slowDown;
					}
					else if(circle.x >= rect.x + 10 * rect.width/12 && circle.x < rect.x + 11 * rect.width/12)
					{
						cXDirection = (float) 1.25 * slowDown;
					}
					else if(circle.x >= rect.x + 11 * rect.width/12 && circle.x < rect.x + rect.width)
					{
						cXDirection = (float) 1.5 * slowDown;
					}
										
					if(stickyPowerUp == true)
					{
						ballInPlay = false;
					}
				}
				else if(circle.x + circle.radius >= rect.x && circle.x - circle.radius <= rect.x + rect.width &&
						circle.y >= rect.y && circle.y <= rect.y + rect.height)
				{
					cXDirection = cXDirection * (-1);
				}
				else //odbijanie pilki od brzegow ekranu
				{
					if(circle.x+circle.radius >= screenWidth)
					{
						circle.x = screenWidth - circle.radius;
						cXDirection = cXDirection * (-1);
					} 
					else if(circle.x - circle.radius <= 0)
					{
						circle.x = circle.radius;
						cXDirection = cXDirection * (-1);
					}
					
					if(circle.y + circle.radius >= screenHeight)
					{
						cYDirection = cYDirection * (-1);
						lifePoints = lifePoints - 1;
						ballInPlay = false;
						sideReflect = false;
						Jukebox.play("lose");
						if(lifePoints == 0)
						{
							end = true;
							ballInPlay = false;
						}
					}
					else if(circle.y - circle.radius <= 0)
					{
						circle.y = circle.radius;
						cYDirection = cYDirection * (-1);
					}
				}
			}
		}
		lastXpos = circle.x;
		lastYpos = circle.y;
	}
	
	public float getScreenWidth() 
	{
		return screenWidth;
	}

	public float getScreenHeight() 
	{
		return screenHeight;
	}
	
	public float getDesktopWidth()
	{
		return desktopWidth;
	}
	
	public float getDesktopHeight()
	{
		return desktopHeight;
	}
	
	public void setScreenWidth(float screenWidth)
	{
		this.screenWidth = screenWidth;
	}
	
	public void setScreenHeight(float screenHeight)
	{
		this.screenHeight = screenHeight;
	}
	
	public void setDesktopWidth(int desktopWidth)
	{
		this.desktopWidth = desktopWidth;
	}
	
	public void setDesktopHeight(int desktopHeight) 
	{
		this.desktopHeight = desktopHeight;
	}
	
	public List<Brick> getBlocks()
	{
		return blocks;
	}
	
	public List<PowerUp> getBuffs()
	{
		return buffs;
	}
	
	public Rectangle getRect() 
	{
		return rect;
	}
	
	public Circle getCircle() 
	{
		return circle;
	}
	
	public Rectangle getStart()
	{
		return start;
	}
	
	public Rectangle getHighscore()
	{
		return highscore;
	}
	
	public Rectangle getBack1()
	{
		return back1;
	}
	
	public Rectangle getInstructions()
	{
		return instructions;
	}
	
	public Rectangle getBack2()
	{
		return back2;
	}
	
	public Rectangle getExit1()
	{
		return exit1;
	}
	
	public Rectangle getBegin()
	{
		return begin;
	}
	
	public Rectangle getBack3()
	{
		return back3;
	}
	
	public Rectangle getExit2()
	{
		return exit2;
	}
	
	public Rectangle getPlayAgain()
	{
		return playAgain;
	}
	
	public Rectangle getSubmit()
	{
		return submit;
	}
	
	public Rectangle getTextField()
	{
		return textField;
	}

	public String getGameText() 
	{
		return gameText;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public int getLifePoints()
	{
		return lifePoints;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getDestroyedBlocks()
	{
		return destroyedBlocks;
	}

	public boolean getBallInPlay()
	{
		return ballInPlay;
	}
	
	public boolean getStartWindow()
	{
		return startWindow;
	}
	
	public boolean getInstructionsWindow()
	{
		return instructionsWindow;
	}
	
	public boolean getHighscoreWindow()
	{
		return highscoreWindow;
	}
	
	public boolean getNameWindow()
	{
		return nameWindow;
	}
	
	public boolean getGameWindow()
	{
		return gameWindow;
	}
	
	public boolean getScoreWindow()
	{
		return scoreWindow;
	}
	
	public boolean getExitWindow()
	{
		return exitWindow;
	}

	public char[] getName()
	{
		return name;
	}
	
	public String[] getHighscores()
	{
		return highscores;
	}
	
	public boolean getEmptyName()
	{
		return emptyName;
	}

	public boolean getEnd()
	{
		return end;
	}
	
	public boolean getPause()
	{
		return pause;
	}
	
	public String getWinText()
	{
		return winText;
	}
	
	public String getLoseText()
	{
		return loseText;
	}
	
	public String getClickToPlay()
	{
		return clickToPlay;
	}
	
	public boolean isRestart()
	{
		return restart;
	}
	
	public boolean getSticky()
	{
		return stickyPowerUp;
	}
	
	public boolean getSlow()
	{
		return slowPowerUp;
	}
	
	public boolean getExpand()
	{
		return expandRectPowerUp;
	}
	
	public boolean getStrength()
	{
		return strengthPowerUp;
	}
	
	public int getPowerUpHeight()
	{
		return powerUpHeight;
	}
	
	public int getPowerUpWidth()
	{
		return powerUpWidth;
	}
}