package Body;

import city.cs.engine.*;


/**
 * Class for red rupee (five points if caught by player). Also super rare.
 */
public class RedRupee extends DynamicBody
{
    private static final float m = 2;
    private static final Shape redRupeeShape = new PolygonShape(0.006f*m,0.489f*m, -0.26f*m,0.26f*m, 
            -0.26f*m,-0.26f*m, 0.006f*m,-0.506f*m, 
            0.26f*m,-0.26f*m, 0.263f*m,0.251f*m);
    private static final String redRupeePath = "data/redRupee.png";
    private static final BodyImage redRupeeImage = new BodyImage(redRupeePath, m);
    
    public RedRupee(World world)
    {
        super(world);
        
        SolidFixture fixture = new SolidFixture(this, redRupeeShape, 5);
        fixture.setDensity(-9999);
        fixture.setRestitution(-9999);

        setImage(redRupeeImage);
        setFixedRotation(false);
    }
    
    public String getPath()
    {
        return redRupeePath;
    }
    
    public Shape getShape()
    {
        return redRupeeShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
