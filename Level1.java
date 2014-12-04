package Level;

import Body.BombSoldier;
import Collision.PlatformCollision;
import Collision.PlayerCollision;
import Survivor.Game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Builds and populates level 1.
 */
public class Level1 extends GameUniverse implements StepListener
{ 
    private static final int LEVEL_ONE_GOAL = 20;
    
    private Shape groundShape, innerLeftWallShape, 
            innerRightWallShape;
    private Body groundBody, innerLeftWallBody, 
            innerRightWallBody;
    private Game game;
    
    private boolean isTutorialThemeOn = false;
    private boolean updateOnce = false;
    
        @Override
    public void populate(Game game)
    {
        super.populate(game);
        
        this.game = game;
        
        groundShape = new BoxShape(getScreenPlatformLength(), 0.85f);
        groundBody = new StaticBody(this, groundShape);
        regularBlockFixture(groundBody, groundShape);
        groundBody.setPosition(new Vec2(0, -11.645f));
        groundBody.setFillColor(getGreenColor());
        setGroundBody(groundBody);
        
        innerLeftWallShape = new BoxShape(0.25f, 7f);
        innerLeftWallBody = new StaticBody(this, innerLeftWallShape);  
        SolidFixture innerLeftWallFixture = new SolidFixture(innerLeftWallBody, innerLeftWallShape);
        innerLeftWallFixture.setRestitution(0.25f); 
        teleportBlockFixture(innerLeftWallBody, innerLeftWallShape);
        innerLeftWallBody.setPosition(new Vec2(-24.9f, -4.65f));  
        innerLeftWallBody.setFillColor(getVioletColor());  
        
        innerRightWallShape = new BoxShape(0.25f, 7f);  
        innerRightWallBody = new StaticBody(this, innerRightWallShape);
        SolidFixture innerRightWallFixture = new SolidFixture(innerRightWallBody, innerRightWallShape);
        innerRightWallFixture.setRestitution(0.25f); 
        teleportBlockFixture(innerRightWallBody, innerRightWallShape);
        innerRightWallBody.setPosition(new Vec2(24.9f, -4.65f));
        innerRightWallBody.setFillColor(getVioletColor());
        
        getPlayerBody().addCollisionListener(new PlatformCollision(innerLeftWallBody, 
                innerRightWallBody, 1, getPlayerBody(), game));
        
        setBombControl(150);
        setRupeeControl(1000);
        
        this.addStepListener(this);
    }
    
        @Override
    public void preStep(StepEvent e) 
    {
        if(game.isCurrentLevelCompleted()) 
        {
            game.goNextLevel(getPlayerBody().getScoreCount(), getPlayerBody().getHealthBar());
            this.removeStepListener(this);
        }
        
        checkWrongScore();
        checkHealthZero();
        checkDifficulty();
        updateHeartProbability();
        getPlayerBody().updatePercentage(); 
    }

        @Override
    public void postStep(StepEvent e) 
    {
       /* bombWave(getNewRandomX1(getScreenPlatformLength() - 2) - 
                getNewRandomX2(getScreenPlatformLength() - 2), getDefaultSpawnHeight(), getBombControl());
       */
        greenRupeeWave(getNewRandomX1(getScreenPlatformLength() - 2) - 
                getNewRandomX2(getScreenPlatformLength() - 2), getDefaultSpawnHeight(), getRupeeControl());
        
        blueRupeeWave(getNewRandomX1(getScreenPlatformLength() - 2) - 
                getNewRandomX2(getScreenPlatformLength() - 2), getDefaultSpawnHeight());
        
        redRupeeWave(getNewRandomX1(getScreenPlatformLength() - 2) - 
                getNewRandomX2(getScreenPlatformLength() - 2), getDefaultSpawnHeight());
           
        heartWave(getNewRandomX1(getScreenPlatformLength() - 2) - 
                getNewRandomX2(getScreenPlatformLength() - 2), getDefaultSpawnHeight(), 0.6f); 
    }
    
    public void checkDifficulty()
    {
        if(!isTutorialThemeOn && getPlayerBody().getScoreCount() >= 5)
        {
            game.getTutorialTheme().loop();
            isTutorialThemeOn = true;
            setBombSpawnProbability(10);
            BombSoldier bSoldier = new BombSoldier(this, 3500, 0, 7.5f, getScreenPlatformLength()-1, getPlayerBody(), getGroundBody());
            getPlayerBody().addCollisionListener(new PlayerCollision(bSoldier, getPlayerBody()));
        }
        /*
        if(getPlayerBody().getScoreCount() >= 10 && getPlayerBody().getScoreCount() < 15 && !updateOnce)
        {
            setBombSpawnProbability(12);
            setBombGravity(1.75f);
            updateOnce = !updateOnce;
        }
        
        if(getPlayerBody().getScoreCount() >= 15 && getPlayerBody().getScoreCount() < 20 && updateOnce)
        {
            setBombSpawnProbability(15);
            setBombGravity(2.5f);
            updateOnce = !updateOnce;
        }
        */
    }
    
        @Override
    public Vec2 startPosition() 
    {
        return new Vec2(0, -10);
    }

        @Override
    public boolean isCompleted() 
    {
        return getPlayerBody().getScoreCount() == LEVEL_ONE_GOAL;
    }

    private void checkWrongScore() 
    {
        if(getPlayerBody().getScoreCount() > LEVEL_ONE_GOAL)
        {
            getPlayerBody().setScoreCount(LEVEL_ONE_GOAL);
        }
    }
}
