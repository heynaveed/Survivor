package Body;

import city.cs.engine.*;


/**
 * A class used to display an effect for multiple events of the game, works by the
 * use of a time count variable in the parameter.
 */
public class FlashEffect extends DynamicBody implements StepListener
{
    private static final float m = 1.75f;
    private static final Shape flashEffectShape = new PolygonShape(0.035f*m,0.497f*m, -0.55f*m,-0.005f*m, 
            0.035f*m,-0.497f*m, 0.545f*m,-0.003f*m);
    private static final String flashEffectPath = "data/flashEffect.gif"; 
    private static final BodyImage flashEffectImage = new BodyImage(flashEffectPath, m);
    
    private final World world;
   
    private int timeCount;
    private int maxTime;
    
    public FlashEffect(World world, int maxTime)
    {
        super(world);
        
        this.world = world;
        this.maxTime = maxTime;
        
        GhostlyFixture fixture = new GhostlyFixture(this, flashEffectShape, m);
        fixture.setDensity(0);
        setImage(flashEffectImage);
        
        timeCount = 0;
        
        world.addStepListener(this);
    }
    
        @Override
    public void preStep(StepEvent e) 
    {
        if(timeCount < maxTime)
        {
            timeCount++;
        }
    }
    
        @Override
    public void postStep(StepEvent e) 
    {
        if(timeCount >= maxTime)
        {
            this.destroy();
            world.removeStepListener(this);
        }
    }
    
    public String getPath()
    {
        return flashEffectPath;
    }
    
    public Shape getShape()
    {
        return flashEffectShape;
    }
    
    public float getMultiplyer()
    {
        return m;
    }
}
