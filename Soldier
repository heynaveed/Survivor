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
 * Class for an NPC enemy soldier which shoots bullets at the player.
 * The difficulty for each enemy is determined by the parameters of the class.
 */
public class Soldier extends DynamicBody implements StepListener
{
    private static final float m = 2.25f; 
    private static final float bulletM = 1;
                                       
    private static final Shape walkLeftShape = new PolygonShape(0.03f*m,0.446f*m, -0.37f*m,0.343f*m,
            -0.332f*m,-0.068f*m, 0.03f*m,-0.397f*m, 0.181f*m,-0.33f*m, 0.346f*m,0.284f*m);
    private static final Shape walkRightShape = new PolygonShape(0.005f*m,0.446f*m, -0.341f*m,0.284f*m, 
            -0.103f*m,-0.381f*m, 0.322f*m,-0.068f*m, 0.392f*m,0.335f*m);
    
    private static final Shape bulletLeftShape = new PolygonShape(-0.761f*bulletM,0.155f*bulletM, -0.418f*bulletM,
            0.377f*bulletM, 0.628f*bulletM,0.375f*bulletM, 0.63f*bulletM,-0.375f*bulletM,
            -0.426f*bulletM,-0.375f*bulletM, -0.763f*bulletM,-0.111f*bulletM);
    private static final Shape bulletRightShape = new PolygonShape(0.753f*bulletM,0.114f*bulletM, 0.418f*bulletM,
            0.364f*bulletM, -0.636f*bulletM,0.364f*bulletM, -0.63f*bulletM,-0.285f*bulletM, -0.421f*bulletM,
            -0.372f*bulletM, 0.418f*bulletM,-0.37f*bulletM, 0.756f*bulletM,-0.121f*bulletM);

    private static final BodyImage soldierWalkLeft = new BodyImage("data/soldierLeft.gif", m);
    private static final BodyImage soldierWalkRight = new BodyImage("data/soldierRight.gif", m);
    private static final BodyImage bulletLeftImage = new BodyImage("data/bulletLeft.gif", bulletM);
    private static final BodyImage bulletRightImage = new BodyImage("data/bulletRight.gif", bulletM);
        
    private final PlayerBody pBody;
    private final World world;
    private final Body groundBody;
  
    private final float speed, landCover, power;
    private final int jumpChance, bulletSpray;
    
    private Shape currentShape;
    private Bullet bullBody;
    private SoundClip soldierHit, soldierDie;
    private Random random;
    private BlueRupee bRBody;
    
    private int randomBullet, randomMove, randomJump, healthBar;
    
    private float newPosition = 0;
    private boolean isWalkingLeft = true;
    
    
    public Soldier(World world, PlayerBody pBody, int bulletSpray,
            float landCover, float speed, float power, 
            Body groundBody, int healthBar, int jumpChance)
    {
        super(world);
        
        this.world = world;
        this.pBody = pBody;
        this.bulletSpray = bulletSpray;
        this.landCover = landCover;
        this.speed = speed;
        this.power = power;
        this.groundBody = groundBody;
        this.healthBar = healthBar;
        this.jumpChance = jumpChance;
        
        currentShape = walkLeftShape;
          
        random = new Random();  
        
        SolidFixture fixture = new SolidFixture(this, currentShape, m);
        fixture.setDensity(2000);
        
        setImage(soldierWalkLeft);   
        setFixedRotation(true);
            
        pBody.addCollisionListener(new PlayerCollision(this, pBody));
        world.addStepListener(this);
       
        try 
        {
            soldierHit = new SoundClip("data/soldierHit.wav");
            soldierDie = new SoundClip("data/soldierDie.wav");
            soldierHit.setVolume(0.8);
            soldierDie.setVolume(0.8);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) 
        {
            Logger.getLogger(Soldier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        @Override
    public void preStep(StepEvent e) 
    {
        if(healthBar <= 0)
        {
            bRBody = new BlueRupee(world);
            bRBody.setPosition(new Vec2(this.getPosition().x, this.getPosition().y +2.5f));
            
            pBody.addCollisionListener(new PlayerCollision(bRBody, pBody));
            soldierDie.play();
            this.destroy();
        }
        
        randomBullet = random.nextInt(60);
        randomMove = random.nextInt(250);
        randomJump = random.nextInt(120);
    }

        @Override
    public void postStep(StepEvent e) 
    {
        animateSoldier();
    }
    
    /**
     * Animates the soldier depending on whether it is walking left or right.
     */
    public void animateSoldier()
    {
        if(isWalkingLeft)
        {
            this.setPosition(new Vec2(this.getPosition().x - speed, this.getPosition().y));
            newPosition = newPosition - speed;
            
            if(newPosition < -landCover || randomMove < 5)
            {
                isWalkingLeft = false;
                setImage(soldierWalkRight);
                currentShape = walkRightShape;
            }
            if(randomBullet < bulletSpray)
            {
                shootBullet(-1, bulletLeftImage, bulletLeftShape);
            }
        }
        
        if(!isWalkingLeft)
        {
            this.setPosition(new Vec2(this.getPosition().x + speed, this.getPosition().y));
            newPosition = newPosition + speed;
            
            if(newPosition > landCover || randomMove < 5)
            {
                isWalkingLeft = true;
                setImage(soldierWalkLeft);
                currentShape = walkLeftShape;
            }
            if(randomBullet < bulletSpray)
            {
                shootBullet(1, bulletRightImage, bulletRightShape);
            }
        } 
        
        if(randomJump < jumpChance)
        {
            this.setPosition(new Vec2(this.getPosition().x, this.getPosition().y +2));
        }
    }
    
    public void shootBullet(int directionMultiplyer, BodyImage image, Shape shape)
    {   
        bullBody = new Bullet(world, image, shape);
        
        bullBody.setPosition(new Vec2(this.getPosition().x +(1*directionMultiplyer), this.getPosition().y));
        bullBody.setLinearVelocity(new Vec2((power*directionMultiplyer), 2));
        
        pBody.addCollisionListener(new PlayerCollision(bullBody, pBody));
        bullBody.addCollisionListener(new PlayerCollision(groundBody));
    }
    
    public void damageByPlayerArrow()
    {
        healthBar = healthBar - 25;
        soldierHit.play();
    }
}
