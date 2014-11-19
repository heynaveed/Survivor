package Collision;

import Body.Ammo;
import Body.PlayerBody;
import Body.Soldier;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;


/**
 * Class which handles all the collisions for the ammo shot by player.
 */
public class AmmoCollision implements CollisionListener
{
    private final PlayerBody pBody;
    private final Soldier sBody1;
    private final Soldier sBody2;
    private final Soldier sBody3;
    private final Soldier sBody4;
    private final Soldier sBody5;
    private final Ammo amBody;
    
    public AmmoCollision(PlayerBody pBody, Soldier sBody1, Soldier sBody2, 
            Soldier sBody3, Soldier sBody4, Soldier sBody5, Ammo amBody)
    {
        this.pBody = pBody;
        this.sBody1 = sBody1;
        this.sBody2 = sBody2;
        this.sBody3 = sBody3;
        this.sBody4 = sBody4;
        this.sBody5 = sBody5;
        this.amBody = amBody;
    }
    
        @Override
    public void collide(CollisionEvent e) 
    {
        if(e.getOtherBody() == sBody1)
        {
            ammoToSoldierPackage(e, sBody1, false);
        }
        
        if(e.getOtherBody() == sBody2)
        {
            ammoToSoldierPackage(e, sBody2, false);
        }
        
        if(e.getOtherBody() == sBody3)
        {
            ammoToSoldierPackage(e, sBody3, false);
        }
        
        if(e.getOtherBody() == sBody4)
        {
            ammoToSoldierPackage(e, sBody4, false);
        }
        
        if(e.getOtherBody() == sBody5)
        {
            ammoToSoldierPackage(e, sBody5, false);
        }
        
        else
        {
            ammoToSoldierPackage(e, null, true);
        }
    }
    
    public void ammoToSoldierPackage(CollisionEvent e, Soldier sBody, boolean isAnyHit)
    {
        if(!isAnyHit)
        {
            sBody.damageByPlayerArrow();
            pBody.newFlashEffect(sBody.getPosition());
        }
        
        pBody.newAmmoSmashEffect(amBody.getPosition());
        pBody.getAmmoHit().play();
        e.getReceivingBody().destroy();
    }
}
