package Body;

import city.cs.engine.*;


/**
 * Class for blue rupee (five points if caught by player). Also rare.
 */
public class BlueRupee extends DynamicBody
{
    private static final float m = 2;
    private static final Shape bluegreenRupeeShape = new PolygonShape(0.006f*m,0.489f*m, -0.26f*m,0.26f*m, 
            -0.26f*m,-0.26f*m, 0.006f*m,-0.506f*m, 
            0.26f*m,-0.26f*m, 0.263f*m,0.251f*m);
    private static final String blueRupeePath = "data/blueRupee.png";
    private static final BodyImage bluegreenRupeeImage = new BodyImage(blueRupeePath, m);
    
    public BlueRupee(World world)
    {
        super(world);
        
        SolidFixture fixture = new SolidFixture(this, bluegreenRupeeShape, 5);
        fixture.setDensity(-9999);
        fixture.setRestitution(-9999);
        
        setImage(bluegreenRupeeImage);
        setFixedRotation(false);
    }
    
    public String getPath()
    {
        return blueRupeePath;
    }
    
    public Shape getShape()
    {
        return bluegreenRupeeShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
