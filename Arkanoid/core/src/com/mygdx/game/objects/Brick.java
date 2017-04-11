package com.mygdx.game.objects;
import com.badlogic.gdx.math.Rectangle;

public class Brick 
{
	private Rectangle rectangle;
	private BrickType type; //okresla rodzaj bloku
	private int strength;
		
	public Brick(Rectangle rectangle, BrickType type, int strength) 
	{
		this.rectangle = rectangle;
		this.type = type;
		this.strength = strength;
	}

	public Rectangle getRectangle() 
	{
		return rectangle;
	}
		
	public void setRectangle(Rectangle rectangle)
	{
		this.rectangle = rectangle;
	}
	
	public BrickType getType() 
	{
		return type;
	}
		
	public void setType(BrickType type) 
	{
		this.type = type;
	}
	
	public int getStrength()
	{
		return strength;
	}
	
	public void setStrength(int strength)
	{
		this.strength = strength;
	}
}
