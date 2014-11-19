package Body;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Class which displays what the player has caught for a limited amount of time.
 */
public class CatchEffects extends StaticBody implements StepListener
{
    private PlayerBody pBody;
    
    private int timeCount;
    private int timeMax;
    
    private boolean startCount = false;
    
    
    public CatchEffects(World world, PlayerBody pBody, Shape shape, String imagePath, float m, int timeMax)
    {
        super(world);
        
        this.pBody = pBody;
        this.timeMax = timeMax;
        
        GhostlyFixture fixture = new GhostlyFixture(this, shape, 5);
        fixture.setDensity(0);
        
        setImage(new BodyImage(imagePath, m));
    }
    
        @Override
    public void preStep(StepEvent e) 
    {
        if(startCount && timeCount <= timeMax)  //If the count has started and while the time count is less than the maximum time limit
        {
            this.setPosition(new Vec2(pBody.getPosition().x, pBody.getPosition().y+1.25f));
            timeCount++;
        }
    }
    
        @Override
    public void postStep(StepEvent e) 
    {
        if(timeCount > timeMax)
        {
            this.destroy();
            
            startCount = false;  
        }
    }
    
    public void setStartCount(boolean startCount)
    {
        this.startCount = startCount;
        timeCount = 0;  
    }
}
