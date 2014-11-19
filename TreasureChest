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
 * A universal treasure chest which can hold a different item inside every time 
 * a new instance is called.
 */
public class TreasureChest extends StaticBody implements StepListener, SensorListener
{   
    private static final float m = 3.5f;
    private static final float quiverM = 2;
    
    private static final Shape closedChestShape = new PolygonShape(0.354f*m,-0.271f*m, -0.378f*m,-0.268f*m, 
            -0.37f*m,0.227f*m, -0.276f*m,0.313f*m, 0.268f*m,0.313f*m, 0.352f*m,0.232f*m);
    private static final Shape openChestShape = new PolygonShape(-0.339f*m,-0.042f*m, -0.339f*m,-0.393f*m, 
            0.375f*m,-0.396f*m, 0.378f*m,-0.031f*m, 0.313f*m,0.352f*m, -0.294f*m,0.357f*m);    
    
    private static final Shape quiverShape = new PolygonShape(0.389f*quiverM,0.497f*quiverM, 0.084f*quiverM,0.335f*quiverM, 
            -0.535f*quiverM,-0.3f*quiverM, -0.506f*quiverM,-0.452f*quiverM, -0.363f*quiverM,-0.503f*quiverM, 
            0.303f*quiverM,0.125f*quiverM, 0.516f*quiverM,0.354f*quiverM, 0.532f*quiverM,0.49f*quiverM);
    
    private static final BodyImage closedImage = new BodyImage("data/closedTreasureChest.png", m);
    private static final BodyImage openImage = new BodyImage("data/openTreasureChest.png", m);
    
    private Shape currentShape;
    private World world;
    private PlayerBody pBody;
    private Sensor chestSensor;
    private SoundClip chestAppear, chestOpen;
    private FlashEffect fEffect;
    private Random random;
    private DynamicBody quiver;
    
    private int level, randomX1, randomX2, randomX3, randomX4;
    
    private boolean isOpen = false;
    private boolean oneQuiver = false;
    
    
    public TreasureChest(World world, PlayerBody pBody, int level) 
    {
        super(world);
        this.world = world;
        this.pBody = pBody;
        this.level = level;
        
        currentShape = closedChestShape;
 
        setImage(closedImage);
        
        GhostlyFixture ghostChest = new GhostlyFixture(this, currentShape, m);
        ghostChest.setDensity(0);
        
        chestSensor = new Sensor(this, currentShape);
        chestSensor.addSensorListener(this);
       
        random = new Random();

        try 
        {
            chestAppear = new SoundClip("data/chestAppear.wav");
            chestOpen = new SoundClip("data/chestOpen.wav");  
            chestAppear.setVolume(1.5);
            chestOpen.setVolume(1);
            chestAppear.play();
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) 
        {
            Logger.getLogger(TreasureChest.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
   
    public void checkForQuiver()
    {
        if(isOpen && level == 3)
        {
            if(!oneQuiver)
            {
                quiver = new DynamicBody(world, quiverShape);
                quiver.setImage(new BodyImage("data/quiver.png", quiverM));
                oneQuiver = true;
                quiver.setPosition(new Vec2(pBody.getPosition().x, 20));
                pBody.addCollisionListener(new PlayerCollision(quiver, pBody));
            } 
            quiver.setPosition(new Vec2(pBody.getPosition().x, quiver.getPosition().y));
        } 
    }
    
    public void chestShine()
    {
        for(int i = 0; i < 15; i++)
        {
            randomX1 = random.nextInt(5);
            randomX2 = random.nextInt(5);
            randomX3 = random.nextInt(15);
            randomX4 = random.nextInt(15);
            
            fEffect = new FlashEffect(world, 150);
            fEffect.setPosition(new Vec2(this.getPosition().x + (randomX1 - randomX2), 
                    this.getPosition().y + 10+(randomX3 - randomX4)));
        }  
    }
    
    @Override
    public void preStep(StepEvent e) 
    {
        if(isOpen)
        {
            setImage(openImage);
            currentShape = openChestShape;
            checkForQuiver();
        }
    }

        @Override
    public void postStep(StepEvent e) 
    {
    }

        @Override
    public void beginContact(SensorEvent e) 
    {
        if(e.getContactBody() == pBody)
        {
            world.addStepListener(this);
            chestOpen.play();
            isOpen = true;
            this.setPosition(new Vec2(this.getPosition().x, this.getPosition().y + 0.4f));
            chestSensor.removeSensorListener(this);
        }
    }

        @Override
    public void endContact(SensorEvent e) 
    {
    }
    
    public boolean getIsOpen()
    {
        return isOpen;
    }
    
    public Shape getClosedShape()
    {
        return closedChestShape;
    }
}
