package Body;

import city.cs.engine.*;


/**
 * Class for a heart body which gives the player extra life.
 */
public class Heart extends DynamicBody
{
    private static final float m = 1.5f;
    private static final Shape heartShape = new PolygonShape(-0.606f*m,0.021f*m, -0.173f*m,-0.5f*m, 
            0.188f*m,-0.5f*m, 0.603f*m,-0.006f*m, 0.606f*m,0.318f*m, 
            0.394f*m,0.497f*m, -0.388f*m,0.494f*m, -0.606f*m,0.309f*m);
    private static final String heartPath = "data/heart.png";
    private static final BodyImage heartImage = new BodyImage(heartPath, m);
   
    
    public Heart(World world, float gravity)
    {
        super(world);
        
        SolidFixture fixture = new SolidFixture(this, heartShape, 5);
        fixture.setDensity(-500);
        fixture.setRestitution(-30);

        setImage(heartImage);
        setFixedRotation(true);
        setGravityScale(gravity);
    }
    
    public String getPath()
    {
        return heartPath;
    }
    
    public Shape getShape()
    {
        return heartShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
