package Body;

import city.cs.engine.*;


/**
 * Class which initialises the bunch of ammo that can be collected by the player
 * to shoot arrows.
 */
public class AmmoBunch extends DynamicBody
{
    private static final float m = 2.25f;
    private static final Shape ammoShape = new PolygonShape(-0.304f*m,0.329f*m, 
            -0.307f*m,-0.321f*m, 0.345f*m,-0.329f*m, 0.348f*m,0.329f*m);
    private static final String ammoBunchPath = "data/ammoBunch.png";
   
    
    public AmmoBunch(World world, float gravity)
    {
        super(world, ammoShape);
        
        setImage(new BodyImage(ammoBunchPath, m));
        setFixedRotation(true);
        setGravityScale(gravity);
    }
    
    public String getPath()
    {
        return ammoBunchPath;
    }
    
    public Shape getShape()
    {
        return ammoShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
