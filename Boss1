package Body;

import Collision.PlayerCollision;
import Survivor.Game;
import city.cs.engine.*;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;


/**
 * Class for the Boss in level3 which throws fireballs and shoots a laser at the
 * player.
 */
public class Boss1 extends StaticBody
{
    private static final float m = 3.5f;
    private static final float BOSS_HEIGHT = 8.5f;
    private static final float BOSS_SPAN = 17.5f;
    private static final Shape shape = new CircleShape(1.75f);
    private static final BodyImage bossImage1 = new BodyImage("data/boo1.png", m);
    private static final BodyImage bossImage2 = new BodyImage("data/boo2.png", m);
    private static final BodyImage bossImage3 = new BodyImage("data/boo3.png", m);
    private static final BodyImage bossImage4 = new BodyImage("data/boo4.png", m);
    
    private FireBall fBallBody;
    private Laser lBody;
    private PlayerBody pBody;
    private Body groundBody;
    private World world;
    private Game game;
    private FlashEffect fEffect;
    private Random random; 
    private SoundClip bossFire, bossRest, bossLaser, bossTeleport;
    
    private int randomPlacementX, randomPlacementY, randomWaveNumber, 
            randomTeleport1, randomTeleport2;
    
    private float speed;

    private int fireBallCount = 1;
    private float fireBallGravity = 0.5f;
    private boolean isLaserOn = false; 
    private boolean isHalfway = false;
    private boolean isBossOn = false;
    
    
    public Boss1(World world, PlayerBody pBody, Body groundBody, Game game, float speed)
    {
        super(world, shape);
        
        this.world = world;
        this.pBody = pBody;
        this.groundBody = groundBody;
        this.game = game;
        this.speed = speed;
        
        random = new Random(); 

        setImage(bossImage1);   
 
        try 
        {
            bossLaser = new SoundClip("data/laser.wav");
            bossFire = new SoundClip("data/bossFire.wav");
            bossRest = new SoundClip("data/bossRest.wav");
            bossTeleport = new SoundClip("data/bossTeleport.wav");
            bossLaser.setVolume(0.4);
            bossFire.setVolume(0.6);
            bossRest.setVolume(0.8);
            bossTeleport.setVolume(1.8);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) 
        {
            Logger.getLogger(Boss1.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
      
    /**
     * Method responsible for animating the boss body.
     */
    public void animateBossBody(int randomAnimationChange, int randomAttackChance, 
            int randomOtherTrait, int randomTeleportTime)
    {
        if(randomAnimationChange < 1) 
        {                                                         
            if(randomAttackChance == 2) 
            {
                setImage(bossImage2); 
                fireBallWave(-1); 
                updateDifficulty(); 
                moveDirection("left"); 
                
                bossFire.stop();
                bossFire.play();
            }
            
            if(randomAttackChance == 4)
            {
                setImage(bossImage4);
                fireBallWave(1); 
                updateDifficulty();
                moveDirection("right");
                
                bossFire.stop();
                bossFire.play();
            }
            
            else
            {
                if(randomOtherTrait == 1)  
                {
                    setImage(bossImage1);
                    moveDirection("left");   
                    
                    bossRest.stop();
                    bossRest.play();
                }

                if(randomOtherTrait == 2)
                {
                    setImage(bossImage3); 
                    moveDirection("right");
                    
                    bossRest.stop();
                    bossRest.play();
                }
                
                if(randomTeleportTime == 7 && isHalfway)  
                {
                    randomTeleport1 = random.nextInt(20);
                    randomTeleport2 = random.nextInt(20);
                    
                    this.setPosition(new Vec2(randomTeleport1 - randomTeleport2, BOSS_HEIGHT));
                    
                    bossTeleport.stop();
                    bossTeleport.play();
                }
                
                if(randomTeleportTime == 8 && isHalfway) 
                {
                    randomPlacementX = random.nextInt(2);
                    
                    if(randomPlacementX == 0)
                    {
                        this.setPosition(new Vec2(pBody.getPosition().x -2.5f, BOSS_HEIGHT));
                    }
                    if(randomPlacementX == 1)
                    {
                        this.setPosition(new Vec2(pBody.getPosition().x +2.5f, BOSS_HEIGHT));
                    }
                    
                    bossTeleport.stop();
                    bossTeleport.play();
                }
                
                if(randomOtherTrait > 5 && isHalfway)    
                {                                                         
                    if(!isLaserOn) 
                    {
                        lBody = new Laser(world);
                        
                        bossLaser.stop();
                        bossLaser.play();
                        
                        isLaserOn = true;
                    }
                    
                    lBody.setPosition(new Vec2(this.getPosition().x, 0));  
                    
                    pBody.addCollisionListener(new PlayerCollision(lBody, pBody));
                    lBody.addCollisionListener(new PlayerCollision(groundBody));
                }
                
                if(randomOtherTrait < 6)
                {
                    if(isLaserOn)
                    {
                        lBody.destroy();    
                    }
                    
                    isLaserOn = false; 
                }
            }
        }
    }
    
    /**
     * Method responsible for creating a wave of fireballs to attack player.
     */
    public void fireBallWave(int multiplyer)   
    {                                           
        randomWaveNumber = random.nextInt(fireBallCount);
       
        for(int i = 0; i < (randomWaveNumber+1)*4; i++)
        {
            fEffect = new FlashEffect(world, 10);
            fEffect.setPosition(new Vec2((randomPlacementX*multiplyer), randomPlacementY - 2.5f));
            
            randomPlacementX = random.nextInt(25);
            randomPlacementY = random.nextInt(7);
            
            fBallBody = new FireBall(world, fireBallGravity);
            fBallBody.setPosition(new Vec2((randomPlacementX*multiplyer), randomPlacementY - 2.5f));
            
            pBody.addCollisionListener(new PlayerCollision(fBallBody, pBody));
            fBallBody.addCollisionListener(new PlayerCollision(groundBody));
        } 
    }
    
    /**
     * Method which updates the difficulty of the fireball waves depending
     * on the score achieved by the player.
     */
    public void updateDifficulty()
    {
        if(pBody.getScoreCount() >= 40 && pBody.getScoreCount() < 45)
        {
            fireBallCount = 1;
        }
        
        if(pBody.getScoreCount() >= 45 && pBody.getScoreCount() < 50)
        {
            fireBallGravity = 1;
            fireBallCount = 2;
        }
        
        if(pBody.getScoreCount() >= 50 && pBody.getScoreCount() < 55)
        {
            fireBallGravity = 1.5f;
            fireBallCount = 3;
        }
        
        if(pBody.getScoreCount() >= 55 && pBody.getScoreCount() < 60)
        {
            fireBallGravity = 2;
            fireBallCount = 4;
        }
    }
    
    /**
     * Moves the boss in different direction.
     */
    public void moveDirection(String direction)
    {
        if(this.getPosition().x <= -BOSS_SPAN)  
        {
            this.setPosition(new Vec2(this.getPosition().x +speed, BOSS_HEIGHT));
        }
        
        if(this.getPosition().x >= BOSS_SPAN)   
        {
            this.setPosition(new Vec2(this.getPosition().x -speed, BOSS_HEIGHT));
        }
        
        if(this.getPosition().x > -BOSS_SPAN && this.getPosition().x < BOSS_SPAN)
        {
            if(direction.equals("left"))
            {
                this.setPosition(new Vec2(this.getPosition().x -speed, BOSS_HEIGHT));
            }
            if(direction.equals("right"))
            {
                this.setPosition(new Vec2(this.getPosition().x +speed, BOSS_HEIGHT));
            } 
        }
    }

    public void destroyLaser()
    {
        if(lBody != null)
        {
            lBody.destroy();
        }
    }
    
    public boolean getIsBossOn()
    {
        return isBossOn;
    }    
    
    public void setIsBossOn(boolean isBossOn)
    {
        this.isBossOn = isBossOn;
    }
    
    public void setIsHalfway(boolean isScore50)
    {
        this.isHalfway = isScore50;
    }
}
