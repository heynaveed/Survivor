package Body;

import city.cs.engine.*;


/**
 * Class which creates the body of the bullets used by a regular soldier. 
 * It is initialised in this way to account for both left and right shooting images.
 */
public class Bullet extends DynamicBody 
{
    public Bullet(World world, BodyImage image, Shape shape)
    {
        super(world, shape);
        setImage(image);
    }
}
