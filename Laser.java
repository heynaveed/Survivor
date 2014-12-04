package Body;

import city.cs.engine.*;


/**
 * Class for the body of a laser shot by the Boss.
 */
public class Laser extends StaticBody 
{
    private static final float m = 22.5f; 
    private static final Shape shape = new PolygonShape(0.005f*m,0.338f*m, -0.033f*m,0.311f*m,
            -0.053f*m,-0.414f*m, 0.003f*m,-0.487f*m, 
            0.053f*m,-0.419f*m, 0.025f*m,0.306f*m);
    private static final BodyImage laserImage = new BodyImage("data/laser.gif", m);
    
    
    public Laser(World world)
    {
        super(world, shape);
        
        setImage(laserImage);
    }
}
