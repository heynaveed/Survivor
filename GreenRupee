package Body;

import city.cs.engine.*;


/**
 * Class for green rupee (five points if caught by player). Also common.
 */
public class GreenRupee extends DynamicBody
{
    private static final float m = 2;
    private static final Shape greenRupeeShape = new PolygonShape(0.006f*m,0.489f*m, -0.26f*m,0.26f*m, 
            -0.26f*m,-0.26f*m, 0.006f*m,-0.506f*m, 
            0.26f*m,-0.26f*m, 0.263f*m,0.251f*m);
    private static final String greenRupeePath = "data/greenRupee.png";
    private static final BodyImage greenRupeeImage = new BodyImage(greenRupeePath, m);
    
    public GreenRupee(World world)
    {
        super(world);
        
        SolidFixture fixture = new SolidFixture(this, greenRupeeShape, 5);
        fixture.setDensity(-9999);
        fixture.setRestitution(-9999);
        
        
        
        setImage(greenRupeeImage);
        setFixedRotation(false);
    }
    
    public String getPath()
    {
        return greenRupeePath;
    }
    
    public Shape getShape()
    {
        return greenRupeeShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
