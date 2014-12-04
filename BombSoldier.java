package Body;


import Collision.PlayerCollision;
import city.cs.engine.*;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;


/**
 * Class for the Bomb Soldier NPC enemy which throws bombs at the player.
 */
public class BombSoldier extends StaticBody implements StepListener
{
    private static final float m = 2.75f;
    private static final Shape bombSoldierShape = new PolygonShape(-0.003f*m,0.494f*m, -0.308f*m,0.008f*m, 
            -0.261f*m,-0.45f*m, 0.225f*m,-0.453f*m, 0.308f*m,0.006f*m);
    private static final Random random = new Random();
    private static final int teleportMax = 300;
    private static final BodyImage bombSoldierImage = new BodyImage("data/bombSoldier.gif", m);
    
    private SoundClip bombTeleport;
    private Bomb bombBody;
    private PlayerBody pBody;
    private Body groundBody;
    
    private final World world;
    private final SolidFixture bomb;
    private final int teleportControl;
    private final int areaSpan;
    private final float initialXPosition;
    private final float initialYPosition;
    
    private FlashEffect fEffect;
    
    private int randomX1, randomX2, randomY1, randomY2, randomTeleport;    
    

    public BombSoldier(World world, int teleportControl, float initialXPosition,
            float initialYPosition, int areaSpan, PlayerBody pBody, Body groundBody)
    {
        super(world, bombSoldierShape);

        this.world = world;
        this.teleportControl = teleportControl;
        this.bomb = new SolidFixture(this, bombSoldierShape, m);
        this.initialXPosition = initialXPosition;
        this.initialYPosition = initialYPosition;
        this.areaSpan = areaSpan;
        this.pBody = pBody;
        this.groundBody = groundBody;
        
        setImage(bombSoldierImage);   
  
        world.addStepListener(this);
        
        try 
        {
            bombTeleport = new SoundClip("data/bombTeleport.wav");
            bombTeleport.setVolume(0.1);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Logger.getLogger(BombSoldier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        @Override
    public void preStep(StepEvent e) 
    {
        randomTeleport = random.nextInt(teleportControl);
        randomX1 = random.nextInt(areaSpan);
        randomX2 = random.nextInt(areaSpan);
        randomY1 = random.nextInt(4);
        randomY2 = random.nextInt(4);
    }

        @Override
    public void postStep(StepEvent e) 
    {
        animateBombSoldier();
    }
    
    /**
     * Animates the bomb soldier with the use of random variables.
     */
    public void animateBombSoldier()
    {
        if(randomTeleport < teleportMax)    // Will only teleport occassionally.
        {
            fEffect = new FlashEffect(world, 10);   // Will use a flash effect for every teleport.
            fEffect.setPosition(this.getPosition());    
            
            this.setPosition(new Vec2(initialXPosition + (randomX1 - randomX2), 
                    initialYPosition + (randomY1 - randomY2))); // Chooses the next random place to teleport.
            
            bombTeleport.stop();
            bombTeleport.play();
            
            bombBody = new Bomb(world, 4); // Drops new bomb.
            bombBody.setPosition(new Vec2(this.getPosition().x, this.getPosition().y - 2.5f)); 
            
            pBody.addCollisionListener(new PlayerCollision(bombBody, pBody));
            bombBody.addCollisionListener(new PlayerCollision(groundBody));
        }
    }
    
    public SolidFixture getBomb()
    {
        return bomb;
    }
    
    public Shape getShape()
    {
        return bombSoldierShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
