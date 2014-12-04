package Level;

import Body.AmmoBunch;
import Body.BlueRupee;
import Body.Bomb;
import Survivor.Game;
import Body.Heart;
import Body.PlayerBody;
import Body.GreenRupee;
import Body.RedRupee;
import Collision.PlatformCollision;
import Collision.PlayerCollision;
import city.cs.engine.*;
import java.awt.Color;
import java.util.Random;
import org.jbox2d.common.Vec2;


/**
 * Class which holds all the common variables and methods used by each level.
 */
public abstract class GameUniverse extends World 
{
    private static final int FULL_PLATFORM_LENGTH = 26;  
    private static final int INITIAL_HEALTH_PERCENTAGE = 100;   
    private static final float DEFAULT_SPAWN_HEIGHT = 17.5f;
    private static final float RUPEE_GRAVITY = 0.75f;
    private static final float AMMO_BUNCH_GRAVITY = 0.075f;
    
    private static final Color PLATFORM_COLOR_GREEN = Color.getHSBColor(0.47f, 0.53f, 0.47f);
    private static final Color PLATFORM_COLOR_PURPLE = Color.getHSBColor(0.66f, 0.37f, 0.63f);  
    private static final Color PLATFORM_COLOR_VIOLET = Color.getHSBColor(0.79f, 0.64f, 0.64f);   
    private static final Color PLATFORM_COLOR_ICE = Color.getHSBColor(0.53f, 0.77f, 0.80f);  

    private PlayerBody pBody;
    private Heart hBody;
    private GreenRupee gRBody;
    private BlueRupee bRBody;
    private RedRupee rRBody;
    private Body groundBody;
    private Body middleFloatingPlatformBody;
    private Random random;
    private Bomb bombBody;
    private AmmoBunch aBBody;
    
    private int bombControl, rupeeControl, heartControl, ammoBunchControl, randomX1, 
            randomX2, randomBombTime, randomHeartTime, 
            randomRupeeTime, randomAmmoBunchTime;
    
    private int bombSpawnProbability = 0; 
    private float bombGravity = 1;
    
    
    public void populate(Game game)
    {
        pBody = new PlayerBody(this, game);
        pBody.setHealthBar(INITIAL_HEALTH_PERCENTAGE);  
        pBody.setPosition(startPosition()); 
        
        random = new Random();
    }
    
    /**
     * Universal setting for regular blocks.
     */
    public void regularBlockFixture(Body body, Shape shape)
    {
        SolidFixture regularBlock = new SolidFixture(body, shape);
        regularBlock.setFriction(25);    
        regularBlock.setRestitution(0);   
    }
    
    /**
     * Universal setting for death grip blocks.
     */
    public void deathGripBlockFixture(Body body, Shape shape)
    {
        SolidFixture deathGripBlock = new SolidFixture(body, shape);
        deathGripBlock.setFriction(270);   
        deathGripBlock.setRestitution(-5);   
    }
    
    /**
     * Universal setting for ice blocks.
     */
    public void iceBlockFixture(Body body, Shape shape)
    {
        SolidFixture iceBlock = new SolidFixture(body, shape);
        iceBlock.setFriction(1);    
        iceBlock.setRestitution(0);   
    }
    
    /**
     * Universal setting for teleport blocks.
     */
    public void teleportBlockFixture(Body body, Shape shape)
    {
        SolidFixture teleportBlock = new SolidFixture(body, shape);
        teleportBlock.setFriction(720);   
        teleportBlock.setRestitution(-999);
    }
    
    public abstract Vec2 startPosition();
    
    public abstract boolean isCompleted();
    
    /**
     * Creates a wave of green rupees. (Common)
     */
    public void greenRupeeWave(float xPosition, float yPosition, int rupeeControl)
    {
        randomRupeeTime = random.nextInt(rupeeControl);
        
        if(randomRupeeTime < 10)
        {
            gRBody = new GreenRupee(this); 
            gRBody.setPosition(new Vec2(xPosition, yPosition));
            
            pBody.addCollisionListener(new PlayerCollision(gRBody, pBody));
            gRBody.addCollisionListener(new PlayerCollision(groundBody));
            
            gRBody.setGravityScale(RUPEE_GRAVITY);
        }
    }
    
     /**
     * Creates a wave of green rupees. (Rare)
     */
    public void blueRupeeWave(float xPosition, float yPosition)
    {
        randomRupeeTime = random.nextInt(25000);
        
        if(randomRupeeTime < 10)
        {
            bRBody = new BlueRupee(this); 
            bRBody.setPosition(new Vec2(xPosition, yPosition));
            
            pBody.addCollisionListener(new PlayerCollision(bRBody, pBody));
            bRBody.addCollisionListener(new PlayerCollision(groundBody));
            
            bRBody.setGravityScale(RUPEE_GRAVITY);
        }
    }
    
    /**
    * Creates a wave of green rupees. (Super Rare)
    */
    public void redRupeeWave(float xPosition, float yPosition)
    {
        randomRupeeTime = random.nextInt(125000);
        
        if(randomRupeeTime < 10)
        {
            rRBody = new RedRupee(this); 
            rRBody.setPosition(new Vec2(xPosition, yPosition));
            
            pBody.addCollisionListener(new PlayerCollision(rRBody, pBody));
            rRBody.addCollisionListener(new PlayerCollision(groundBody));
            
            rRBody.setGravityScale(RUPEE_GRAVITY);
        }
    }
    
    public void ammoBunchWave(float xPosition, float yPosition)
    {
        randomAmmoBunchTime = random.nextInt(ammoBunchControl);
        
        if(randomAmmoBunchTime < 10 && pBody.getAmmo() < 30)
        {
            aBBody = new AmmoBunch(this, AMMO_BUNCH_GRAVITY);
            aBBody.setPosition(new Vec2(xPosition, yPosition));
            
            pBody.addCollisionListener(new PlayerCollision(aBBody, pBody));
            aBBody.addCollisionListener(new PlayerCollision(groundBody));
        }
    }
    
    public void heartWave(float xPosition, float yPosition, float gravity)
    {
        randomHeartTime = random.nextInt(heartControl);
        
        if (randomHeartTime < 10 && pBody.getHealthBar() < 100) 
        {                                                      
            hBody = new Heart(this, gravity);   
            hBody.setPosition(new Vec2(xPosition, yPosition)); 
            
            pBody.addCollisionListener(new PlayerCollision(hBody, pBody));
            
            if(pBody.getLevel() == 2)
            {
                hBody.addCollisionListener(new PlatformCollision(middleFloatingPlatformBody, 2));
            }
            
            else
            {
                hBody.addCollisionListener(new PlayerCollision(groundBody));
            }
        }   
    }

    /*    
    public void bombWave(float xPosition, float yPosition, int bombControl)
    {
        randomBombTime = random.nextInt(bombControl);
        
        if(randomBombTime < bombSpawnProbability)
        {
            bombBody = new Bomb(this, bombGravity);
            bombBody.setPosition(new Vec2(xPosition, yPosition));
            
            pBody.addCollisionListener(new PlayerCollision(bombBody, pBody));
            bombBody.addCollisionListener(new PlayerCollision(groundBody));
        }
    }
    */  
    
    /*
    public void updateBombProbability()
    {      
        if(pBody.getScoreCount() >= 5 && pBody.getScoreCount() < 10)
        {
            bombSpawnProbability = 10;
        }
        
        if(pBody.getScoreCount() >= 10 && pBody.getScoreCount() < 15)
        {
            bombSpawnProbability = 12;
            bombGravity = 1.75f;
        }
        
        if(pBody.getScoreCount() >= 15 && pBody.getScoreCount() < 20)
        {
            bombSpawnProbability = 15;
            bombGravity = 2.5f;
        }
        
        if(pBody.getLevel() == 2)
        {
            bombSpawnProbability = 5;
            bombGravity = 2;
        }
        
        if(pBody.getLevel() == 3)
        {
            bombSpawnProbability = 10;
            bombGravity = 5;
        }
    }
    */
    
    /**
     * Controls the rate at which a heart will drop.
     */
    public void updateHeartProbability()
    {   
        if(pBody.getHealthBar() >= 80)
        {
            heartControl = 12000;
        }
        
        if(pBody.getHealthBar() >= 60 && pBody.getHealthBar() < 80)
        {
            heartControl = 6000;
        }
        
        if(pBody.getHealthBar() >= 40 && pBody.getHealthBar() < 60)
        {
            heartControl = 3000;
        }
        
        if(pBody.getHealthBar() >= 20 && pBody.getHealthBar() < 40)
        {
            heartControl = 1500;
        }
        
        if(pBody.getHealthBar() >= 0 && pBody.getHealthBar() < 20)
        {
            heartControl = 750;
        }
    }
    
    /**
     * Controls the rate at which ammo bunch will drop.
     */
    public void updateAmmoBunchControl()
    {   
        if(pBody.getAmmo() >= 25)
        {
            ammoBunchControl = 12000;
        }
        
        if(pBody.getAmmo() >= 20 && pBody.getAmmo() < 25)
        {
            ammoBunchControl = 6000;
        }
        
        if(pBody.getAmmo() >= 15 && pBody.getAmmo() < 20)
        {
            ammoBunchControl = 3000;
        }
        
        if(pBody.getAmmo() >= 10 && pBody.getAmmo() < 15)
        {
            ammoBunchControl = 1500;
        }
        
        if(pBody.getAmmo() >= 0 && pBody.getAmmo() < 10)
        {
            ammoBunchControl = 750;
        }
    }
    
    /**
     * Ends game if player health reaches 0.
     */
    public void checkHealthZero()
    {
        if(pBody.getHealthBar() <= 0) 
        {
            System.out.println("Game Over!");
            System.exit(0);
        }
    }
    
    /**
     * Method which does not let ammo fall below 0 and above 30.
     */
    public void checkAmmoMaxMin()
    {
        if(pBody.getAmmo() >= 30) 
        {
            pBody.setAmmo(30);
        }
        
        if(pBody.getAmmo() <= 0)
        {
            pBody.setAmmo(0);
        }
    }

    public PlayerBody getPlayerBody()
    {
        return pBody;
    }
    
    public Color getGreenColor()
    {
        return PLATFORM_COLOR_GREEN;
    }
    
    public Color getPurpleColor()
    {
        return PLATFORM_COLOR_PURPLE;
    }
        
    public Color getVioletColor()
    {
        return PLATFORM_COLOR_VIOLET;
    }
            
    public Color getIceColor()
    {
        return PLATFORM_COLOR_ICE;
    }
    
    public Random getRandom()
    {
        return random;
    }
    
    public int getScreenPlatformLength()
    {
        return FULL_PLATFORM_LENGTH;
    }
    
    public int getNewRandomX1(int newInt)
    {
        randomX1 = random.nextInt(newInt);
        return randomX1;
    }
    
    public int getNewRandomX2(int newInt)
    {
        randomX2 = random.nextInt(newInt);
        return randomX2;
    }
    
    public float getDefaultSpawnHeight()
    {
        return DEFAULT_SPAWN_HEIGHT;
    }
    
    public int getBombControl()
    {
        return bombControl;
    }
    
    public int getRupeeControl()
    {
        return rupeeControl;
    }

    public int getBombSpawnProbability()
    {
        return bombSpawnProbability;
    }
    
    public float getBombGravity()
    {
        return bombGravity;
    }
    
    public Body getGroundBody()
    {
        return groundBody;
    }
    
    public void setGroundBody(Body groundBody)
    {
        this.groundBody = groundBody;
    }
    
    public void setMiddleFloatingPlatformBody(Body middleFloatingPlatformBody)
    {
        this.middleFloatingPlatformBody = middleFloatingPlatformBody;
    }
    
    public void setBombControl(int bombControl)
    {
        this.bombControl = bombControl;
    }
    
    public void setRupeeControl(int rupeeControl)
    {
        this.rupeeControl = rupeeControl;
    }

    public void setBombSpawnProbability(int bombSpawnProbability)
    {
        this.bombSpawnProbability = bombSpawnProbability;
    }

    public void setBombGravity(float bombGravity)
    {
        this.bombGravity = bombGravity;
    }
}
