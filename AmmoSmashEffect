package Body;

import city.cs.engine.*;


/**
 * The class made to create an effect which bursts the Ammo into pieces on collision.
 */
public class AmmoSmashEffect extends DynamicBody implements StepListener
{
    private static final float m = 2; 
    private static final Shape ammoSmashShape = new PolygonShape(0.072f*m,0.362f*m, -0.302f*m,0.283f*m, 
            -0.389f*m,-0.037f*m, -0.386f*m,-0.283f*m, 0.107f*m,-0.364f*m, 
            0.465f*m,-0.234f*m, 0.519f*m,-0.091f*m, 0.425f*m,0.321f*m);
    private static final String ammoSmashPath = "data/ammoSmash.gif";
    private static final int timeMax = 8;
    
    private final World world;
   
    private int timeCount;
    
    public AmmoSmashEffect(World world)
    {
        super(world);
        
        this.world = world;
        timeCount = 0;
        
        setImage(new BodyImage(ammoSmashPath, m));
        
        GhostlyFixture fixture = new GhostlyFixture(this, ammoSmashShape, 5);
        fixture.setDensity(0);
    }
    
        @Override
    public void preStep(StepEvent e) 
    {
        if(timeCount < timeMax)
        {
            timeCount++;
        }
    }
    
        @Override
    public void postStep(StepEvent e) 
    {
        if(timeCount >= timeMax)
        {
            world.removeStepListener(this);
            this.destroy();
        }
    }
    
    public String getPath()
    {
        return ammoSmashPath;
    }
    
    public Shape getShape()
    {
        return ammoSmashShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
