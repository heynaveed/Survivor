package Controller;

import Body.PlayerBody;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Class which controls the player using keyboard buttons.
 */
public class PlayerController extends KeyAdapter
{
    private static final float JUMPING_VELOCITY = 7.5f;   
    private static final float WALKING_SPEED = 17.5f;   
    
    private final Walker walkLeft;
    private final Walker walkRight;
    private final PlayerBody pBody;
    
    private World world;
    private Walker currentWalker;
    private DynamicBody body;
    
    private boolean isFacingLeft = false;
    
    private int tempScoreCount;
    
    public PlayerController(DynamicBody body, PlayerBody pBody)
    {
        this.body = body;
        this.pBody = pBody;
        this.world = body.getWorld(); 
        this.walkLeft = new Walker(body, - WALKING_SPEED); 
        this.walkRight = new Walker(body, WALKING_SPEED); 
    }
    
        @Override
    public void keyPressed(KeyEvent e) 
    {
        int code1 = e.getKeyCode();
        int code2 = e.getKeyCode();
        
        if (code1 == KeyEvent.VK_Q) 
        { 
            System.exit(0);
        }
        
        else if (code1 == KeyEvent.VK_UP) 
        { 
            Vec2 v = body.getLinearVelocity();
            
            pBody.animate("rollJump");
            pBody.getJump1().stop();
            pBody.getJump2().stop();
            pBody.setRandomJumpClip();
            pBody.getCurrentJumpClip().play();
            
            if (Math.abs(v.y) < 0.01f)
            {
                body.setLinearVelocity(new Vec2(v.x, JUMPING_VELOCITY));
            }
        } 
        
        else if (code1 == KeyEvent.VK_DOWN)    
        { 
            Vec2 v = body.getLinearVelocity();
            
            pBody.animate("descend");
            pBody.getDescend().stop();
            pBody.getDescend().play();
            
            if (Math.abs(v.y) > 0.01f) 
            {
                body.setLinearVelocity(new Vec2(0, -JUMPING_VELOCITY));
            }
        } 
        
        else if (code2 == KeyEvent.VK_X && isFacingLeft && pBody.getGotQuiver())
        {
            pBody.animate("shootLeft");
            pBody.shootAmmo(1);
        }
        
        else if (code2 == KeyEvent.VK_X && !isFacingLeft && pBody.getGotQuiver())
        {
            pBody.animate("shootRight");
            pBody.shootAmmo(-1);
        }
        
        else if(code1 == KeyEvent.VK_C && isFacingLeft)
        {
            attackSoundPackage();
            pBody.animate("swordLeft");
        }
        
        else if(code1 == KeyEvent.VK_C && !isFacingLeft)
        {
            attackSoundPackage();
            pBody.animate("swordRight");
        }
        
        else if (code1 == KeyEvent.VK_LEFT) 
        {
            isFacingLeft = true;
            setWalker(walkLeft);
            
            pBody.animate("runLeft");
            pBody.getSteps().stop();
            pBody.getSteps().loop();
        } 
        
        else if (code1 == KeyEvent.VK_RIGHT)  
        {
            isFacingLeft = false;
            setWalker(walkRight);
            
            pBody.animate("runRight");
            pBody.getSteps().stop();
            pBody.getSteps().loop();
        }
        
        
        else if (code1 == KeyEvent.VK_L)        // Used to help skip levels quickly.
        {                                       // Admin purposes only!
            tempScoreCount = pBody.getScoreCount();
            tempScoreCount = tempScoreCount + 5;
            
            pBody.setScoreCount(tempScoreCount);
        }
        
        
        else if(isFacingLeft)
        {
            pBody.animate("idleLeft");
        }
        
        else if(!isFacingLeft)
        {
            pBody.animate("idleRight");
        }
    }
    
        @Override
    public void keyReleased(KeyEvent e) 
    {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_LEFT) 
        {
            isFacingLeft = true;
            clearWalker(walkLeft); 
            
            pBody.getSteps().stop();
            pBody.animate("idleLeft");
        } 
        else if (code == KeyEvent.VK_RIGHT) 
        {
            isFacingLeft = false;
            clearWalker(walkRight);
            
            pBody.getSteps().stop();
            pBody.animate("idleRight");
        }
        
        else if(code == KeyEvent.VK_X && isFacingLeft)
        {
            pBody.animate("idleLeft");
        }
        
        else if(code == KeyEvent.VK_X && !isFacingLeft)
        {
            pBody.animate("idleRight");
        }
        
        else if(code == KeyEvent.VK_C && isFacingLeft)
        {
            pBody.animate("idleLeft");
        }
        
        else if(code == KeyEvent.VK_C && !isFacingLeft)
        {
            pBody.animate("idleRight");
        }
        
        else if(code == KeyEvent.VK_DOWN)
        {
            pBody.animate("idle");
        }
    }
    
    public void setBody(DynamicBody body) 
    {
        if (currentWalker != null) 
        {
            world.removeStepListener(currentWalker);
            currentWalker = null;
        }
        
        this.body = body;
        this.world = body.getWorld();
        walkLeft.setBody(body);
        walkRight.setBody(body);
    }
    
    public void setWalker(Walker w)
    {
        if (currentWalker == null) 
        {
            currentWalker = w;
            world.addStepListener(currentWalker);
        }
    }
    
    public void clearWalker(Walker w) 
    {
        if (currentWalker == w) 
        {
            world.removeStepListener(currentWalker);
            currentWalker = null;
        }
    }
    
    public void attackSoundPackage()
    {
        pBody.getAttack1().stop();
        pBody.getAttack2().stop();
        pBody.getAttack3().stop();
        pBody.getAttack4().stop();
        pBody.setRandomAttackClip();
        pBody.getCurrentAttackClip().play();
    }
} 
