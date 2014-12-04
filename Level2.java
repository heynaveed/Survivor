package Level;

import Body.BombSoldier;
import Collision.PlatformCollision;
import Collision.PlayerCollision;
import Survivor.Game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Builds and populates level 2.
 */
public class Level2 extends GameUniverse implements StepListener
{
    private static final int LEVEL_TWO_GOAL = 40;
        
    private Shape mainPlatformLeftShape, mainPlatformRightShape, 
            mainPlatformMiddleShape, middleFloatingPlatformShape, 
            leftBlockWallShape, rightBlockWallShape, middleLeftShape, middleRightShape;
    private Body mainPlatformLeftBody, mainPlatformRightBody,
            mainPlatformMiddleBody, middleFloatingPlatformBody,
            leftBlockWallBody, rightBlockWallBody, middleLeftBody, middleRightBody;
    private Game game;
    private BombSoldier bSoldier;
    
    private boolean isGroundSwitched = false;
    
    
        @Override
    public void populate(Game game)
    {
        super.populate(game);
        
        this.game = game;
        
        mainPlatformMiddleShape = new BoxShape(5, 3);
        mainPlatformMiddleBody = new StaticBody(this, mainPlatformMiddleShape);
        teleportBlockFixture(mainPlatformMiddleBody, mainPlatformMiddleShape);
        mainPlatformMiddleBody.setPosition(new Vec2(0, -13.5f));
        mainPlatformMiddleBody.setFillColor(getVioletColor());
        
        mainPlatformLeftShape = new BoxShape(12.5f, 3);
        mainPlatformLeftBody = new StaticBody(this, mainPlatformLeftShape);
        iceBlockFixture(mainPlatformLeftBody, mainPlatformLeftShape);
        mainPlatformLeftBody.setPosition(new Vec2(-17.5f, -12));
        mainPlatformLeftBody.setFillColor(getIceColor());
        
        mainPlatformRightShape = new BoxShape(12.5f, 3);
        mainPlatformRightBody = new StaticBody(this, mainPlatformRightShape);
        regularBlockFixture(mainPlatformRightBody, mainPlatformRightShape);
        mainPlatformRightBody.setPosition(new Vec2(17.5f, -12));
        mainPlatformRightBody.setFillColor(getGreenColor());
        setGroundBody(mainPlatformRightBody);
        
        middleFloatingPlatformShape = new BoxShape(4.5f, 0.5f);
        middleFloatingPlatformBody = new StaticBody(this, middleFloatingPlatformShape);
        deathGripBlockFixture(middleFloatingPlatformBody, middleFloatingPlatformShape);
        middleFloatingPlatformBody.setPosition(new Vec2(0, 2.5f));     
        middleFloatingPlatformBody.setFillColor(getPurpleColor());
        setMiddleFloatingPlatformBody(middleFloatingPlatformBody);
        
        leftBlockWallShape = new BoxShape(0.25f, 7f);
        leftBlockWallBody = new StaticBody(this, leftBlockWallShape);
        teleportBlockFixture(leftBlockWallBody, leftBlockWallShape);
        leftBlockWallBody.setPosition(new Vec2(-24.9f, -2f));
        leftBlockWallBody.setFillColor(getVioletColor());
        
        rightBlockWallShape = new BoxShape(0.25f, 7f);
        rightBlockWallBody = new StaticBody(this, rightBlockWallShape);
        teleportBlockFixture(rightBlockWallBody, rightBlockWallShape);
        rightBlockWallBody.setPosition(new Vec2(24.9f, -2f));
        rightBlockWallBody.setFillColor(getVioletColor());
        
        middleLeftShape = new BoxShape(0.25f, 3.5f);
        middleLeftBody = new StaticBody(this, middleLeftShape);
        teleportBlockFixture(middleLeftBody, middleLeftShape);
        middleLeftBody.setPosition(new Vec2(-4.75f, -0.5f));
        middleLeftBody.setFillColor(getPurpleColor());
        
        middleLeftShape = new BoxShape(0.25f, 3.5f);
        middleRightBody = new StaticBody(this, middleLeftShape);
        teleportBlockFixture(middleRightBody, middleLeftShape);
        middleRightBody.setPosition(new Vec2(4.75f, -0.5f));
        middleRightBody.setFillColor(getPurpleColor());
        
        getPlayerBody().addCollisionListener(new PlatformCollision(mainPlatformMiddleBody,
        leftBlockWallBody, rightBlockWallBody, 2, getPlayerBody(), game));
        
        setBombControl(85);
        setRupeeControl(1000);
        setBombSpawnProbability(5);
        setBombGravity(2);
        
        
        
        this.addStepListener(this);
    }
    
        @Override
    public void preStep(StepEvent e) 
    {
        if (game.isCurrentLevelCompleted()) 
        {
            game.goNextLevel(getPlayerBody().getScoreCount(), getPlayerBody().getHealthBar());
            this.removeStepListener(this);
        }
        
        checkWrongScore();
        checkGroundAndBombSoldier();
        checkHealthZero();
        updateHeartProbability();
        getPlayerBody().updatePercentage();
    }

        @Override
    public void postStep(StepEvent e) 
    {
        if(getPlayerBody().getScoreCount() >= 30)
        {
            //bombWave(-8 - getNewRandomX1(15), getDefaultSpawnHeight(), getBombControl());
            greenRupeeWave(-6 - getNewRandomX1(18), getDefaultSpawnHeight(), getRupeeControl());
            
            blueRupeeWave(-6 - getNewRandomX1(18), getDefaultSpawnHeight());
            
            redRupeeWave(-6 - getNewRandomX1(18), getDefaultSpawnHeight());
        }
        
        if(getPlayerBody().getScoreCount() < 30)
        {
           //bombWave(8 + getNewRandomX1(15), getDefaultSpawnHeight(), getBombControl());
           greenRupeeWave(6 + getNewRandomX1(18), getDefaultSpawnHeight(), getRupeeControl());
           
           blueRupeeWave(6 + getNewRandomX1(18), getDefaultSpawnHeight());
           
           redRupeeWave(6 + getNewRandomX1(18), getDefaultSpawnHeight());
        }

        heartWave(getNewRandomX1(5) - getNewRandomX2(5), getDefaultSpawnHeight(), 0.0755f); 
    }
    
    public void checkGroundAndBombSoldier()
    {
        if(getPlayerBody().getScoreCount() < 30 && !isGroundSwitched)
        {
            bSoldier = new BombSoldier(this, 6500, 15, 9, 7, getPlayerBody(), getGroundBody());
            getPlayerBody().addCollisionListener(new PlayerCollision(bSoldier, getPlayerBody()));
            isGroundSwitched = true;
        }
        
        if(getPlayerBody().getScoreCount() >= 30 && isGroundSwitched)
        {
            setGroundBody(mainPlatformLeftBody);
            bSoldier.destroy();
            bSoldier = new BombSoldier(this, 6500, -15, 9, 7, getPlayerBody(), getGroundBody());
            getPlayerBody().addCollisionListener(new PlayerCollision(bSoldier, getPlayerBody()));
            isGroundSwitched = false;
        }
    }
   
    /**
     * Ensures that the score is capped off to 40.
     */
    private void checkWrongScore() 
    {
        if(getPlayerBody().getScoreCount() > LEVEL_TWO_GOAL)
        {
            getPlayerBody().setScoreCount(LEVEL_TWO_GOAL);
        }
    }
    
        @Override
    public Vec2 startPosition() 
    {
        return new Vec2(0, -10);
    }

        @Override
    public boolean isCompleted() 
    {
        return getPlayerBody().getScoreCount() == LEVEL_TWO_GOAL;
    }
}
