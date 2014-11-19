package Controller;

import Body.PlayerBody;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Class which tracks the player by updating the view each frame.
 */
public class Tracker implements StepListener 
{
    private final WorldView view;
    private final Body body;
    private final PlayerBody pBody;

    public Tracker(WorldView view, Body body, PlayerBody pBody) 
    {
        this.view = view;
        this.body = body;
        this.pBody = pBody;
    }

        @Override
    public void preStep(StepEvent e) 
    {
    }

        @Override
    public void postStep(StepEvent e) 
    {
        if(pBody.getScoreCount() < 60)
        {
            view.setView(new Vec2(0, 0), 20);
        }
       
        if(pBody.getScoreCount() >= 60)
        {
            view.setCentre(new Vec2(body.getPosition().x, body.getPosition().y + 10));    
        }
    }
}
