package Body;

import Collision.AmmoCollision;
import Survivor.Game;
import city.cs.engine.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.jbox2d.common.Vec2;


/**
 * The class for the player controlled by the user. It holds the images, shapes,
 * damage counts, sounds, animation and everything related to how the player interacts
 * with the world.
 */
public class PlayerBody extends DynamicBody
{   
    private static final float m = 2.25f; 
    private static final float m2 = m - 0.5f;
    private static final float m3 = m - 0.25f;
    private static final float ammoM = 2;
    
    private static final Shape idleShape = new PolygonShape(-0.1f*m,0.329f*m, -0.263f*m,0.168f*m, 
            -0.202f*m,-0.356f*m, 0.231f*m,-0.353f*m,
            0.29f*m,0.176f*m, 0.132f*m,0.329f*m);   
    private static final Shape runLeftShape = new PolygonShape(-0.122f*m,0.297f*m, -0.263f*m,0.201f*m, 
            -0.295f*m,-0.19f*m, -0.156f*m,-0.361f*m, 
            0.282f*m,-0.326f*m, 0.344f*m,0.094f*m, 0.162f*m,0.299f*m);
    private static final Shape runRightShape = new PolygonShape(-0.194f*m,0.297f*m, -0.344f*m,0.086f*m, 
            -0.279f*m,-0.318f*m, 0.164f*m,-0.366f*m, 
            0.287f*m,-0.198f*m, 0.258f*m,0.206f*m, 0.119f*m,0.297f*m);
    private static final Shape descendShape = new PolygonShape(-0.12f*m,0.329f*m, -0.273f*m,0.179f*m, 
            -0.27f*m,-0.008f*m, -0.144f*m,-0.382f*m, 
            0.136f*m,-0.385f*m, 0.27f*m,0.003f*m, 0.267f*m,0.179f*m, 0.115f*m,0.329f*m);
    private static final Shape rollJumpShape = new PolygonShape(-0.137f*m3,0.285f*m3, -0.315f*m3,0.099f*m3, 
            -0.32f*m3,-0.062f*m3, -0.148f*m3,-0.309f*m3, 
            0.116f*m3,-0.309f*m3, 0.32f*m3,-0.129f*m3, 0.323f*m3,0.102f*m3, 0.126f*m3,0.282f*m3);
    private static final Shape damageShape = new PolygonShape(-0.02f*m,0.302f*m, -0.287f*m,0.021f*m, 
            -0.242f*m,-0.286f*m, -0.151f*m,-0.388f*m, 
            0.199f*m,-0.235f*m, 0.314f*m,-0.064f*m, 0.287f*m,0.171f*m, 0.164f*m,0.299f*m);
    private static final Shape shootLeftShape = new PolygonShape(0.097f*m2,0.467f*m2, -0.263f*m2,0.354f*m2, 
            -0.483f*m2,-0.081f*m2, -0.174f*m2,-0.398f*m2, 0.335f*m2,
            -0.428f*m2, 0.491f*m2,0.147f*m2, 0.404f*m2,0.4f*m2);
    private static final Shape shootRightShape = new PolygonShape(-0.299f*m2,0.464f*m2, -0.499f*m2,0.28f*m2, 
            -0.348f*m2,-0.444f*m2, 0.176f*m2,-0.41f*m2, 0.473f*m2,-0.073f*m2, 
            0.286f*m2,0.326f*m2, 0.082f*m2,0.454f*m2);
    private static final Shape idleLeftShape = new PolygonShape(-0.135f*m,0.297f*m, -0.261f*m,0.201f*m, 
            -0.199f*m,-0.321f*m, -0.049f*m,-0.356f*m, 0.189f*m,
            -0.235f*m, 0.344f*m,0.072f*m, 0.199f*m,0.267f*m);
    private static final Shape idleRightShape = new PolygonShape(0.116f*m,0.307f*m, 0.258f*m,0.198f*m, 
            0.18f*m,-0.353f*m, -0.108f*m,-0.332f*m, -0.354f*m,0.029f*m, -0.221f*m,0.238f*m);
    private static final Shape swordLeftPlayerShape = new PolygonShape(0.054f*m3,0.457f*m3, -0.21f*m3,0.255f*m3, 
            -0.134f*m3,-0.368f*m3, 0.417f*m3,-0.228f*m3, 0.519f*m3,0.134f*m3);
    private static final Shape swordLeftBladeShape = new PolygonShape(-0.266f*m3,0.261f*m3, -0.583f*m3,-0.099f*m3, 
            -0.551f*m3,-0.323f*m3, -0.22f*m3,-0.368f*m3, -0.108f*m3,-0.124f*m3);
    private static final Shape swordRightPlayerShape = new PolygonShape(-0.094f*m3,0.492f*m3, -0.559f*m3,0.102f*m3,
            -0.414f*m3,-0.255f*m3, 0.169f*m3,-0.403f*m3, 0.185f*m3,0.368f*m3);
    private static final Shape swordRightBladeShape = new PolygonShape(0.272f*m3,0.253f*m3, 0.578f*m3,-0.065f*m3, 
            0.53f*m3,-0.331f*m3, 0.185f*m3,-0.392f*m3, 0.102f*m3,-0.11f*m3);
    private static final Shape ammoLeftShape = new PolygonShape(-0.258f*ammoM,0.068f*ammoM, -0.309f*ammoM,0.004f*ammoM, 
            -0.261f*ammoM,-0.065f*ammoM, 0.348f*ammoM,-0.111f*ammoM, 0.345f*ammoM,0.109f*ammoM);
    private static final Shape ammoRightShape = new PolygonShape(0.307f*ammoM,-0.004f*ammoM, 0.258f*ammoM,0.063f*ammoM, 
            -0.348f*ammoM,0.109f*ammoM, -0.348f*ammoM,-0.106f*ammoM, 0.261f*ammoM,-0.068f*ammoM); 
    
    private static final BodyImage idleImage = new BodyImage("data/idle.png", m);
    private static final BodyImage runLeftImage = new BodyImage("data/runLeft.gif", m);
    private static final BodyImage runRightImage = new BodyImage("data/runRight.gif", m);
    private static final BodyImage descendImage = new BodyImage("data/descend.png", m);
    private static final BodyImage rollJumpImage = new BodyImage("data/rollJump.gif", m3);
    private static final BodyImage damageImage = new BodyImage("data/damage.png", m);
    private static final BodyImage shootLeftImage = new BodyImage("data/shootLeft.gif", m2);
    private static final BodyImage shootRightImage = new BodyImage("data/shootRight.gif", m2);
    private static final BodyImage idleLeftImage = new BodyImage("data/idleLeft.png", m);
    private static final BodyImage idleRightImage = new BodyImage("data/idleRight.png", m);
    private static final BodyImage swordLeftImage = new BodyImage("data/swordLeft.gif", m3);
    private static final BodyImage swordRightImage = new BodyImage("data/swordRight.gif", m3);
    private static final BodyImage ammoLeftImage = new BodyImage("data/ammoLeft.png", ammoM);
    private static final BodyImage ammoRightImage = new BodyImage("data/ammoRight.png", ammoM);
    
    private final World world;
    
    private SoundClip steps, jump1, jump2, hurt1, hurt2, hurt3, hurt4, currentHurt, 
            currentJump, descend, catchHeart, catchRupee, ammoHit, 
            bulletHit, ammoShoot, bombHit, fireBallHit, laserHit, soldierHitPlayer, getAmmo,
            currentAttack, attack1, attack2, attack3, attack4;
    
    private SolidFixture swordLeftPlayerFixture, swordLeftBladeFixture, swordRightPlayerFixture, 
            swordRightBladeFixture, playerFixture, bladeFixture;
    
    private CatchEffects cEffect;
    private Game game;
    private Random random;
    private Shape currentShape;  
    private FlashEffect fEffect;
    private SolidFixture defaultFixture;
    private Ammo amBody;
    private AmmoSmashEffect ammoSmashEffect;
    private Soldier sBody1, sBody2, sBody3, sBody4, sBody5;
      
    private int healthBar, scoreCount, 
            level, randomClip, intComplete;
    
    private int ammo = 0;
    private long completed = 0;
    private boolean gotQuiver = false;
    private boolean isSwordFixture = false;
    
    
    public PlayerBody(World world, Game game)
    {
        super(world);
        
        this.world = world;
        this.game = game;
        
        currentShape = idleShape;   // Sets default shape to the current shape.
        
        random = new Random();
        defaultFixture = new SolidFixture(this, currentShape, m);
        setPlayerFixtureSettings(defaultFixture);
        
        setImage(idleImage);   
        setFixedRotation(true);  
  
        try 
        {
            steps = new SoundClip("data/steps.wav");
            jump1 = new SoundClip("data/jump1.wav");
            jump2 = new SoundClip("data/jump2.wav");
            hurt1 = new SoundClip("data/hurt1.wav");
            hurt2 = new SoundClip("data/hurt2.wav");
            hurt3 = new SoundClip("data/hurt3.wav");
            hurt4 = new SoundClip("data/hurt4.wav");
            descend = new SoundClip("data/descend.wav");
            catchHeart = new SoundClip("data/catchHeart.wav");
            catchRupee = new SoundClip("data/catchRupee.wav");
            ammoHit = new SoundClip("data/ammoHit.wav");
            bulletHit = new SoundClip("data/bulletHit.wav");
            ammoShoot = new SoundClip("data/ammoShoot.wav");
            bombHit = new SoundClip("data/bombHit.wav");
            fireBallHit = new SoundClip("data/fireBallHit.wav");
            laserHit = new SoundClip("data/laserHit.wav");
            soldierHitPlayer = new SoundClip("data/soldierHitPlayer.wav");
            getAmmo = new SoundClip("data/getAmmo.wav");
            attack1 = new SoundClip("data/attack1.wav");
            attack2 = new SoundClip("data/attack2.wav");
            attack3 = new SoundClip("data/attack3.wav");
            attack4 = new SoundClip("data/attack4.wav");
            steps.setVolume(0.4);
            jump1.setVolume(1);
            jump2.setVolume(1);
            hurt1.setVolume(1);
            hurt2.setVolume(1);
            hurt3.setVolume(1);
            hurt4.setVolume(1.2);
            descend.setVolume(0.2);
            catchHeart.setVolume(0.9);
            catchRupee.setVolume(1.1);
            ammoHit.setVolume(1.2);
            bulletHit.setVolume(1.8);
            ammoShoot.setVolume(1.2);
            bombHit.setVolume(1.2);
            fireBallHit.setVolume(1.4);
            laserHit.setVolume(1.7);
            soldierHitPlayer.setVolume(1.2);
            getAmmo.setVolume(1.2);
            attack1.setVolume(1);
            attack2.setVolume(1);
            attack3.setVolume(1);
            attack4.setVolume(1);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) 
        {
            Logger.getLogger(PlayerBody.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Updates the percentage completion of the entire game and converts it to an int.
     */
    public void updatePercentage()
    {
        completed = Math.round(getScoreCount()*100f)/80;
        DecimalFormat df = new DecimalFormat("#");
        df.format(completed);
        intComplete = (int) completed;
    }
    
    /**
     * Updates the image of the animation according to player controls and 
     * in-game events.
     */
    public void animate(String code)
    {
        if(code.equals("idle"))
        {
            setImage(idleImage);
            setCurrentShape(idleShape);
        }
        
        if(code.equals("runLeft"))
        {
            setImage(runLeftImage);
            setCurrentShape(runLeftShape);
        }
        
        if(code.equals("runRight"))
        {
            setImage(runRightImage);
            setCurrentShape(runRightShape);
        }
        
        if(code.equals("rollJump"))
        {
            setImage(rollJumpImage);
            setCurrentShape(rollJumpShape);
        }
        
        if(code.equals("descend"))
        {
            setImage(descendImage);
            setCurrentShape(descendShape);
        }
        
        if(code.equals("damage"))
        {
            setImage(damageImage);
            setCurrentShape(damageShape);
        }
        
        if(code.equals("shootLeft"))
        {
            setImage(shootLeftImage);
            setCurrentShape(shootLeftShape);
        }
                
        if(code.equals("shootRight"))
        {
            setImage(shootRightImage);
            setCurrentShape(shootRightShape);
        }
 
        if(code.equals("idleLeft"))
        {
            setImage(idleLeftImage);
            setCurrentShape(idleLeftShape);
        } 
        
        if(code.equals("idleRight"))
        {
            setImage(idleRightImage);
            setCurrentShape(idleRightShape);
        } 
        
        if(code.equals("swordLeft"))
        {
            setImage(swordLeftImage);
            setSwordShapes(swordLeftPlayerFixture, swordLeftBladeFixture, swordLeftPlayerShape, swordLeftBladeShape);
        }
        
        if(code.equals("swordRight"))
        {
            setImage(swordRightImage);
            setSwordShapes(swordRightPlayerFixture, swordRightBladeFixture, swordRightPlayerShape, swordRightBladeShape);
        }
    }
    
    /**
     * Method which identifies what the player has caught and displays it above 
     * player head.
     */
    public void newCatchEffect(Heart hBody, GreenRupee gRBody, BlueRupee bRBody, 
            RedRupee rRBody, AmmoBunch aBBody, int timeMax)
    {
        if(gRBody != null)
        {
            cEffect = new CatchEffects(world, this, gRBody.getShape() , 
            gRBody.getPath(), gRBody.getMultiplyer()/2, timeMax);
        }
        
        else if(bRBody != null)
        {
            cEffect = new CatchEffects(world, this, bRBody.getShape() , 
            bRBody.getPath(), bRBody.getMultiplyer()/2, timeMax);
        }
        
        else if(rRBody != null)
        {
            cEffect = new CatchEffects(world, this, rRBody.getShape() , 
            rRBody.getPath(), rRBody.getMultiplyer()/2, timeMax); 
        }
        
        else if(hBody != null)
        {
            cEffect = new CatchEffects(world, this, hBody.getShape(), 
            hBody.getPath(), hBody.getMultiplyer()/2, timeMax);
        }
        
        else if(aBBody != null)
        {
            cEffect = new CatchEffects(world, this, aBBody.getShape(), 
            aBBody.getPath(), aBBody.getMultiplyer()/2, timeMax);
        }
        
        cEffect.setPosition(new Vec2(this.getPosition().x, this.getPosition().y+1.25f));
        world.addStepListener(cEffect);
        cEffect.setStartCount(true);
    }
    
    /**
    * Creates a new flash effect.
    */
    public void newFlashEffect(Vec2 position)
    {
        fEffect = new FlashEffect(world, 35);
        fEffect.setPosition(position);
    }
    
    
    /**
     * Creates a new ammo effect.
     */
    public void newAmmoSmashEffect(Vec2 position)
    {
        ammoSmashEffect = new AmmoSmashEffect(world);
        ammoSmashEffect.setPosition(position);
    }
    
    /**
     * Method which shoots the ammo from the player. 
     * Also takes into consideration which direction player is facing.
     */
    public void shootAmmo(int multiplyer)
    {
        if(ammo > 0)
        {
            ammo--;

            if(multiplyer > 0)
            {
                amBody = new Ammo(world, ammoLeftImage, ammoLeftShape);
            }

            if(multiplyer < 0)
            {
                amBody = new Ammo(world, ammoRightImage, ammoRightShape);
            }

            amBody.setPosition(new Vec2(this.getPosition().x -(1.75f*multiplyer), this.getPosition().y));
            amBody.setLinearVelocity(new Vec2((-50*multiplyer), 3));
            amBody.addCollisionListener(new AmmoCollision(this, sBody1, sBody2, 
                    sBody3, sBody4, sBody5, amBody));
            ammoShoot.stop();
            ammoShoot.play();
        }
    }
    
    public void damageByBomb()
    {
        healthBar = healthBar - 7;
        bombHit.stop();
        bombHit.play();
    }
    
    public void damageByFireBall()
    {
        healthBar = healthBar - 7;
        
        fireBallHit.stop();
        fireBallHit.play();
    }
    
    public void damageByLaser()
    {
        healthBar = healthBar - 5;
        
        laserHit.stop();
        hurt4.stop();
        laserHit.play();
        hurt4.play();
    }
    
    public void damageBySoldierBullet()
    {
        healthBar = healthBar - 5;
        
        bulletHit.stop();
        bulletHit.play();
    }
    
    public void damageBySoldierBody()
    {
        healthBar = healthBar - 15;
        
        soldierHitPlayer.stop();
        soldierHitPlayer.play();
        hurt4.stop();
        hurt4.play();
    }
    
    public void damageByBombSoldier()
    {
        healthBar = healthBar - 2;
        
        hurt4.stop();
        hurt4.play();
    }
    
    public void healthBoost()
    {
        healthBar = healthBar + 15; 
        
        catchHeart.stop();
        catchHeart.play();
        
        if(healthBar > 100) 
        {
            healthBar = 100;
        }
    }
    
    public void addOnePoint()
    {
        scoreCount++;
        
        catchRupee.stop();
        catchRupee.play();
    }
    
    public void addFivePoints()
    {
        scoreCount = scoreCount + 5;
        
        catchRupee.stop();
        catchRupee.play();
    }
    
    public void addTenPoints()
    {
        scoreCount = scoreCount + 10;
        
        catchRupee.stop();
        catchRupee.play();
    }
    
    public void addAmmo()
    {
        ammo = ammo + 7;
        
        getAmmo.stop();
        getAmmo.play();
    }

    public int getHealthBar()
    {
        return healthBar;
    }
    
    public PlayerBody getPlayerBody()
    {
        return this;
    }
    
    public int getScoreCount()
    {
        return scoreCount;
    }
    
    public int getCompleted()
    {
        return intComplete;
    }
    
    public Shape getCurrentShape()
    {
        return currentShape;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public SoundClip getSteps()
    {
        return steps;
    }
    
    public SoundClip getJump1()
    {
        return jump1;
    } 
    
    public SoundClip getJump2()
    {
        return jump2;
    } 
    
    public SoundClip getCurrentHurtClip()
    {
        return currentHurt;
    }
    
    public SoundClip getCurrentJumpClip()
    {
        return currentJump;
    }
    
    public SoundClip getCurrentAttackClip()
    {
        return currentAttack;
    }
    
    public SoundClip getAttack1()
    {
        return attack1;
    } 
        
    public SoundClip getAttack2()
    {
        return attack2;
    } 
            
    public SoundClip getAttack3()
    {
        return attack3;
    } 
                
    public SoundClip getAttack4()
    {
        return attack4;
    } 
    
    public SoundClip getDescend()
    {
        return descend;
    }
    
    public SoundClip getAmmoHit()
    {
        return ammoHit;
    }
    
    public boolean getGotQuiver()
    {
        return gotQuiver;
    }
    
    public int getAmmo()
    {
        return ammo;
    }
    
    public void setAmmo(int ammo)
    {
        this.ammo = ammo;
    }
    
    public void setGotQuiver(boolean gotQuiver)
    {
        this.gotQuiver = gotQuiver;
    }
    
    public void setCurrentHurt(SoundClip currentHurt)
    {
        this.currentHurt = currentHurt;
    }
    
    public void setHealthBar(int healthBar)
    {
        this.healthBar = healthBar;
    }
    
    public void setScoreCount(int scoreCount)
    {
        this.scoreCount = scoreCount;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public void setSoldierBody1(Soldier sBody1)
    {
        this.sBody1 = sBody1;
    }
    
    public void setSoldierBody2(Soldier sBody2)
    {
        this.sBody2 = sBody2;
    }
    
    public void setSoldierBody3(Soldier sBody3)
    {
        this.sBody3 = sBody3;
    }
    
    public void setSoldierBody4(Soldier sBody4)
    {
        this.sBody4 = sBody4;
    }
    
    public void setCurrentJump(SoundClip currentJump)
    {
        this.currentJump = currentJump;
    }
    
    public void setCurrentAttack(SoundClip currentAttack)
    {
        this.currentAttack = currentAttack;
    }
    
    /**
     * Sets the current shape of the player depending on which animation is in the current image.
     */
    public void setCurrentShape(Shape currentShape)
    {
        if(!isSwordFixture)
        {
            defaultFixture.destroy();
        }
        
        if(isSwordFixture)
        {
            this.playerFixture.destroy();
            this.bladeFixture.destroy();
            isSwordFixture = false;
        }
        
        this.currentShape = currentShape;
        defaultFixture = new SolidFixture(this, currentShape, m);
        setPlayerFixtureSettings(defaultFixture);
    }
 
    /**
     * Sets the shape of the player body when the sword is being swung.
     */
    public void setSwordShapes(SolidFixture playerFixture, SolidFixture bladeFixture,
            Shape playerShape, Shape bladeShape)
    {
        if(!isSwordFixture)
        {
            defaultFixture.destroy();
            this.playerFixture = playerFixture;
            this.bladeFixture = bladeFixture;
            this.playerFixture = new SolidFixture(this, playerShape, m);
            this.bladeFixture = new SolidFixture(this, bladeShape, m);
            setPlayerFixtureSettings(this.playerFixture);
            setBladeFixtureSettings(this.bladeFixture);
            isSwordFixture = true;
        } 
    }
    
    /**
     * Chooses a random clip to be played to account for getting hurt.
     */
    public void setRandomHurtClip()
    {
        randomClip = random.nextInt(3);
        
        if(randomClip == 0)
        {
            setCurrentHurt(hurt1);
        }
        
        if(randomClip == 1)
        {
            setCurrentHurt(hurt2);
        }
        
        if(randomClip == 2)
        {
            setCurrentHurt(hurt3);
        }
    }
    
    /**
     * Chooses a random clip to be played to account for jumping.
     */
    public void setRandomJumpClip()
    {
        randomClip = random.nextInt(2);
        
        if(randomClip == 0)
        {
            setCurrentJump(jump1);
        }
        
        if(randomClip == 1)
        {
            setCurrentJump(jump2);
        }
    }
    
    /**
     * Chooses a random clip to be played to account for random attack.
     */
    public void setRandomAttackClip()
    {
        randomClip = random.nextInt(4);
        
        if(randomClip == 0)
        {
            setCurrentAttack(attack1);
        }
        
        if(randomClip == 1)
        {
            setCurrentAttack(attack2);
        }
        
        if(randomClip == 2)
        {
            setCurrentAttack(attack3);
        }
         
        if(randomClip == 3)
        {
            setCurrentAttack(attack4);
        }
    }
    
    public void setPlayerFixtureSettings(SolidFixture fixture)
    {
        fixture.setDensity(50);
        fixture.setRestitution(0.1f);
        fixture.setFriction(11.5f);
    }
    
    public void setBladeFixtureSettings(SolidFixture fixture)
    {
        fixture.setDensity(100);
        fixture.setRestitution(0.1f);
        fixture.setFriction(11.5f);
    }
    
    public SolidFixture getSwordLeftBladeFixture()
    {
        return swordLeftBladeFixture;
    }
    
    public SolidFixture getSwordRightBladeFixture()
    {
        return swordRightBladeFixture;
    }
}
