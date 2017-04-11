package com.mygdx.game.objects;
import com.badlogic.gdx.math.Rectangle;
public class PowerUp 
{
	private Rectangle rectangle;
	private PowerUpType type; //okresla rodzaj bloku
			
	public PowerUp(Rectangle rectangle, PowerUpType type) 
	{
		this.rectangle = rectangle;
		this.type = type;
	}

	public Rectangle getRectangle() 
	{
		return rectangle;
	}
			
	public void setRectangle(Rectangle rectangle)
	{
		this.rectangle = rectangle;
	}
		
	public PowerUpType getType() 
	{
		return type;
	}
			
	public void setType(PowerUpType type) 
	{
		this.type = type;
	}
}
