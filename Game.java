package Survivor;

import Level.*;
import Body.PlayerBody;
import Controller.PlayerController;
import Controller.Tracker;
import city.cs.engine.*;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


/**
 * Main class which is responsible for the switching of levels 
 * and options for the game.
 */
public class Game 
{
    private GameUniverse world;
    private UserView view;
    private PlayerController pController;
    private SoundClip tutorialTheme, bossBattleTheme, newLevelUnlock, 
            adventureTheme, warp;
    
    private int level;

    private final JFrame frame = new JFrame("Survivor");
    JFrame debugView;
    
    
    public Game()
    {
        level = 1;
        
        world = new Level1();
        world.populate(this);
        
        view = new MyView(world, 1000, 500, this);  // Calls new background class
        //view.setGridResolution(1);
        
        getPlayerBody().setLevel(level);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(view);
        frame.setResizable(false);
        frame.add(new Panel(world, view, this), BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();
        pController = new PlayerController(world.getPlayerBody(), world.getPlayerBody());
        frame.addKeyListener(pController);
        
        //debugView = new DebugViewer(world, 1000, 500);
        
        world.addStepListener(new Tracker(view, world.getPlayerBody(), world.getPlayerBody()));
        world.start();
        
        try 
        {
            tutorialTheme = new SoundClip("data/tutorialMusic.wav");  
            bossBattleTheme = new SoundClip("data/bossBattle.wav");     
            newLevelUnlock = new SoundClip("data/newLevelUnlock.wav");   
            adventureTheme = new SoundClip("data/adventureTheme.wav");   
            warp = new SoundClip("data/warp.wav");
            tutorialTheme.setVolume(0.7);
            bossBattleTheme.setVolume(0.7);
            newLevelUnlock.setVolume(0.4);
            adventureTheme.setVolume(0.7);
            warp.setVolume(1.1);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) 
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PlayerBody getPlayerBody()
    {
        return world.getPlayerBody();
    }
    
    public boolean isCurrentLevelCompleted()
    {
        return world.isCompleted();
    }
    
    public void goNextLevel(int currentScore, int currentHealth)
    {
        world.setPaused(true);
        
        if(level == 4)
        {
            System.exit(0);
        }
        
        if(level == 3)
        {
            world = new Level4();
            setNextLevel(currentScore, currentHealth);
            world.addStepListener(new Tracker(view, world.getPlayerBody(), world.getPlayerBody()));
            adventureTheme.loop();
            //debugView = new DebugViewer(world, 1000, 500);
        }   
        
        if(level == 2)
        {
            tutorialTheme.close();
            world = new Level3();
            setNextLevel(currentScore, currentHealth);
        }
        
        if(level == 1)
        {
            world = new Level2();
            setNextLevel(currentScore, currentHealth);
        }        
    }
    
    /**
     * Contains information to set next level.
     */
    public void setNextLevel(int currentScore, int currentHealth)
    {
        level++;
        world.populate(this);
        pController.setBody(world.getPlayerBody());
        getPlayerBody().setLevel(level);
        frame.addKeyListener(new PlayerController(world.getPlayerBody(), world.getPlayerBody()));
        //debugView = new DebugViewer(world, 1000, 500);
        getPlayerBody().setScoreCount(currentScore);
        getPlayerBody().setHealthBar(currentHealth);
        view.setWorld(world); 
        world.start();
        newLevelUnlock.play();
    }
    
    public World getWorld()
    {
        return world;
    }
    
    public SoundClip getTutorialTheme()
    {
        return tutorialTheme;
    }
    
    public SoundClip getBossBattleTheme()
    {
        return bossBattleTheme;
    }
    
    public SoundClip getWarp()
    {
        return warp;
    }
    
    public void setWorld(GameUniverse world)
    {
        this.world = world;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public static void main(String[] args)
    {
        new Game();
    }
}
