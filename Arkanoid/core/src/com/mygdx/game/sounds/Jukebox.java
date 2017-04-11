package com.mygdx.game.sounds;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Jukebox
{
	private static HashMap<String, Sound> sounds;
	//private Clip clip;
	
	static
	{
		sounds = new HashMap<String, Sound>();
	}
	
	public static void load(String path, String name)
	{
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name, sound);
	}
	
	public static void play(String name)
	{
		sounds.get(name).play();
	}
	
	public static void stop(String name)
	{
		sounds.get(name).stop();
	}
	
	public static void loop(String name)
	{
		sounds.get(name).loop();
	}
	
	public static void stopAll()
	{
		for(Sound s: sounds.values())
		{
			s.stop();
		}
	}
	
	
	 
	/*private void SoundEffect(URL url) 
	{
	    try {
	        // Set up an audio input stream piped from the sound file.
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	        // Get a clip resource.
	        clip = AudioSystem.getClip();
	        // Open audio clip and load samples from the audio input stream.
	        clip.open(audioInputStream);
	    } catch (UnsupportedAudioFileException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (LineUnavailableException e) {
	        e.printStackTrace();
    }
}

// Play or Re-play the sound effect from the beginning, by rewinding.
	public void playTheSound(String path) 
	{
	    URL url = getClass().getResource(path);//You can change this to whatever other sound you have
	    SoundEffect(url);//this method will load the sound
	
	    if (clip.isRunning())
	    {
	    	clip.stop();   // Stop the player if it is still running
	    }
	    clip.setFramePosition(0); // rewind to the beginning
	    clip.start();     // Start playing
    }*/
	 
}
