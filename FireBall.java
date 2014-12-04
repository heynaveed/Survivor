package Body;

import city.cs.engine.*;


/**
 * Class for the fireball bodies thrown by the Boss.
 */
public class FireBall extends DynamicBody 
{
    private static final float m = 1.5f;
    private static final Shape shape = new CircleShape(0.5f);
    private static final BodyImage fireBallImage = new BodyImage("data/fireball.gif", m);
    
    
    public FireBall(World world, float gravity)
    {
        super(world, shape);
        
        setImage(fireBallImage);
        setFixedRotation(false);
        setGravityScale(gravity);
    }
}
