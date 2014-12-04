package Body;

import city.cs.engine.*;


/**
 * Class for the Bomb bodies thrown by the Bomb Soldier.
 */
public class Bomb extends DynamicBody
{   
    private static final float m = 2;
    private static final Shape bombShape = new CircleShape(0.75f);
    private static final BodyImage bombImage = new BodyImage("data/bombBody.png", m);
   
    
    public Bomb(World world, float gravity)
    {
        super(world, bombShape);
        
        setImage(bombImage);
        setFixedRotation(true);
        setGravityScale(gravity);
    }
}
