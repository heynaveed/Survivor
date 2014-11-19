package Collision;

import Body.PlayerBody;
import Survivor.Game;
import city.cs.engine.Body;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;


/**
 * Class which handles collisions for all the teleport platforms in the worlds.
 */
public class PlatformCollision implements CollisionListener
{
    private Body innerLeftWallBody, innerRightWallBody, mainPlatformMiddleBody, 
            leftBlockWallBody, rightBlockWallBody, middleFloatingPlatform, 
            largeLeftBlockBody, largeRightBlockBody;
    
    private PlayerBody pBody;
    private Game game;
            
    public PlatformCollision(Body body1, Body body2, int level, PlayerBody pBody, 
            Game game)
    {
        this.pBody = pBody;
        this.game = game;
        
        if(level == 1 || level == 3)
        {
            this.innerLeftWallBody = body1;
            this.innerRightWallBody = body2;
        }
        
        if(level == 4)
        {
            this.largeLeftBlockBody = body1;
            this.largeRightBlockBody = body2;
        }
    }
    
    public PlatformCollision(Body body1, Body body2, Body body3, int level, PlayerBody pBody, 
            Game game)
    {
        this.pBody = pBody;
        this.game = game;
        
        if(level == 2)
        {
            this.mainPlatformMiddleBody = body1;
            this.leftBlockWallBody = body2;
            this.rightBlockWallBody = body3;
        }
    }
    
    public PlatformCollision(Body body1, int level)
    {
        if(level == 2)
        {
            middleFloatingPlatform = body1;
        }
    }
    
        @Override
    public void collide(CollisionEvent e) 
    {
        if (e.getOtherBody() == innerLeftWallBody)                                               
        {
            pBody.setPosition(new Vec2(23.5f, pBody.getPosition().y));
            game.getWarp().play();
        }
        
        if(e.getOtherBody() == innerRightWallBody)
        {
            pBody.setPosition(new Vec2(-23.5f, pBody.getPosition().y));
            game.getWarp().play();
        }
        
        if(e.getOtherBody() == mainPlatformMiddleBody)
        {
            pBody.setPosition(new Vec2(0, 4));
            game.getWarp().play();
        }
        
        if(e.getOtherBody() == leftBlockWallBody)
        {
            pBody.setPosition(new Vec2(23.5f, pBody.getPosition().y));
            game.getWarp().play();
        }
        
        if(e.getOtherBody() == rightBlockWallBody)
        {
            pBody.setPosition(new Vec2(-23.5f, pBody.getPosition().y));
            game.getWarp().play();
        }
        
        if(e.getOtherBody() == largeLeftBlockBody || 
                e.getOtherBody() == largeRightBlockBody)
        {
            pBody.setPosition(new Vec2(pBody.getPosition().x, 
                    pBody.getPosition().y-0.01f));
        }
        
        if(e.getOtherBody() == middleFloatingPlatform)
        {
            e.getReceivingBody().destroy();
        }
    }   
}
