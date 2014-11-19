package Controller;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Class which makes the player walk.
 */
public class Walker implements StepListener 
{
    private final float speed;
    
    private DynamicBody body;
    
    
    Walker(DynamicBody body, float speed) 
    {
        this.body = body;
        this.speed = speed;
    }
 
        @Override
    public void preStep(StepEvent e) 
    {
        Vec2 v = body.getLinearVelocity();
        body.setLinearVelocity(new Vec2(speed, v.y));
    }

        @Override
    public void postStep(StepEvent e) 
    {
    }
    
    public void setBody(DynamicBody body) 
    {
        this.body = body;
    }
}
