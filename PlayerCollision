package Collision;

import Body.AmmoBunch;
import Body.BlueRupee;
import Body.Bomb;
import Body.BombSoldier;
import Body.Boss1;
import Body.Bullet;
import Body.FireBall;
import Body.GreenRupee;
import Body.Heart;
import Body.Laser;
import Body.MysteryBox;
import Body.PlayerBody;
import Body.RedRupee;
import Body.Soldier;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Class which handles all the collisions with the player.
 */
public class PlayerCollision implements CollisionListener    
{
    private PlayerBody pBody;
    private GreenRupee gRBody;
    private BlueRupee bRBody;
    private RedRupee rRBody;
    private Heart hBody;
    private Laser lBody;
    private FireBall fBallBody;
    private Body groundBody;
    private Bullet bullBody;
    private Soldier sBody;
    private Bomb bombBody;
    private MysteryBox mBox;
    private Boss1 b1Body;
    private DynamicBody quiver;
    private AmmoBunch aBBody;
    private BombSoldier bSoldier;
    
    private int level;
    

    public PlayerCollision(Bomb bombBody, PlayerBody pBody)
    {
        this.bombBody = bombBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(GreenRupee gRBody, PlayerBody pBody)
    {
        this.gRBody = gRBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(RedRupee rRBody, PlayerBody pBody)
    {
        this.rRBody = rRBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(BlueRupee bRBody, PlayerBody pBody)
    {
        this.bRBody = bRBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(Heart hBody, PlayerBody pBody)
    {
        this.hBody = hBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(Laser lBody, PlayerBody pBody)
    {
        this.lBody = lBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(FireBall fBallBody, PlayerBody pBody)
    {
        this.fBallBody = fBallBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(Bullet bullBody, PlayerBody pBody)
    {
        this.bullBody = bullBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(Soldier sBody, PlayerBody pBody)
    {
        this.sBody = sBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(MysteryBox mBox, PlayerBody pBody, Boss1 b1Body, int level)
    {
        this.mBox = mBox;
        this.pBody = pBody;
        this.b1Body = b1Body;
        this.level = level;
    }
    
    public PlayerCollision(DynamicBody quiver, PlayerBody pBody)
    {
        this.quiver = quiver;
        this.pBody = pBody;
    }
    
    public PlayerCollision(AmmoBunch aBBody, PlayerBody pBody)
    {
        this.aBBody = aBBody;
        this.pBody = pBody;
    }
    
    public PlayerCollision(BombSoldier bSoldier, PlayerBody pBody)
    {
        this.bSoldier = bSoldier;
        this.pBody = pBody;
    }

    public PlayerCollision(Body groundBody)
    {
        this.groundBody = groundBody;
    }
    
        @Override
    public void collide(CollisionEvent e) 
    {
        if (e.getOtherBody() == bombBody) 
        {
            pBody.damageByBomb();
            pBody.animate("damage");   
            pBody.setRandomHurtClip();
            pBody.getCurrentHurtClip().play();
            pBody.newFlashEffect(pBody.getPosition());
            
            e.getOtherBody().destroy(); 
        }
        
        if(e.getOtherBody() == gRBody)
        {
            pBody.addOnePoint();    
            pBody.newCatchEffect(null, gRBody, null, null, null, 30);

            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == bRBody)
        {
            pBody.addFivePoints();
            pBody.newCatchEffect(null, null, bRBody, null, null, 30);
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == rRBody)
        {
            pBody.addTenPoints();
            pBody.newCatchEffect(null, null, null, rRBody, null, 30);
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == hBody)
        {
            pBody.healthBoost();  
            pBody.newCatchEffect(hBody, null, null, null, null, 30);
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == lBody)
        {
            pBody.damageByLaser();  
            pBody.animate("damage"); 
            pBody.newFlashEffect(pBody.getPosition());
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == fBallBody)
        {
            pBody.damageByFireBall();  
            pBody.animate("damage"); 
            pBody.setRandomHurtClip();
            pBody.getCurrentHurtClip().play();
            pBody.newFlashEffect(pBody.getPosition());
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == bullBody)
        {
            pBody.damageBySoldierBullet();  
            pBody.animate("damage"); 
            pBody.setRandomHurtClip();
            pBody.getCurrentHurtClip().play();
            pBody.newFlashEffect(pBody.getPosition());
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == sBody)
        {
            pBody.damageBySoldierBody();
            pBody.animate("damage");
            pBody.newFlashEffect(pBody.getPosition());
            pBody.setPosition(new Vec2(pBody.getPosition().x, pBody.getPosition().y + 2.5f));
        }
        
        if(e.getOtherBody() == mBox)
        {
            if(level == 3)
            {
                b1Body.setIsBossOn(true);
            }
            mBox.destroy();
        }      
        
        if(e.getOtherBody() == aBBody)
        {
            pBody.addAmmo();
            pBody.newCatchEffect(null, null, null, null, aBBody, 30);
            
            e.getOtherBody().destroy();
        }
        
        if(e.getOtherBody() == bSoldier)
        {
            pBody.animate("damage");
            pBody.damageByBombSoldier();
            pBody.newFlashEffect(pBody.getPosition());
        }
        
        if(e.getOtherBody() == quiver)
        {
            pBody.setGotQuiver(true);
            
            e.getOtherBody().destroy();
        }
        
        else if (e.getOtherBody() == groundBody) 
        {
            e.getReceivingBody().destroy();
        }
    }
}

