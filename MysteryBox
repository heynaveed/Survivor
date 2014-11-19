package Body;

import city.cs.engine.*;


/**
 * A mystery box which can be used to trigger events in the game.
 */
public class MysteryBox extends StaticBody
{   
    private static final float m = 2.5f;
    private static final Shape shape = new PolygonShape(-0.326f*m,0.357f*m, -0.326f*m,-0.312f*m, 
            0.344f*m,-0.312f*m, 0.341f*m,0.357f*m);
    private static final BodyImage boxImage = new BodyImage("data/mysteryBox.png", m);
    
    
    public MysteryBox(World world) 
    {
        super(world, shape);
        setImage(boxImage);
    }
}
