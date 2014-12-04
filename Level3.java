package Level;

import Body.BombSoldier;
import Body.Boss1;
import Body.MysteryBox;
import Body.TreasureChest;
import Collision.PlatformCollision;
import Collision.PlayerCollision;
import Survivor.Game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Builds and populates level 3.
 */
public class Level3 extends GameUniverse implements StepListener
{ 
    private static final int LEVEL_THREE_GOAL = 60;
    
    private Shape groundShape, leftBlockWallShape, rightBlockWallShape;
    private Body groundBody, leftBlockWallBody, rightBlockWallBody;
    private Boss1 b1Body;
    private Game game;
    private TreasureChest chestBody;
    private MysteryBox mBox;
    private BombSoldier bSoldier;
 
    private int randomAttackChance, randomAnimationChange, randomOtherTrait, randomTeleportTime;
    
    private boolean isMusicOn = false;
       
    
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
           
        leftBlockWallShape = new BoxShape(0.25f, 7f);
        leftBlockWallBody = new StaticBody(this, leftBlockWallShape);
        teleportBlockFixture(leftBlockWallBody, leftBlockWallShape);
        leftBlockWallBody.setPosition(new Vec2(-24.9f, -5f));
        leftBlockWallBody.setFillColor(getVioletColor());
        
        rightBlockWallShape = new BoxShape(0.25f, 7f);
        rightBlockWallBody = new StaticBody(this, rightBlockWallShape);
        teleportBlockFixture(rightBlockWallBody, rightBlockWallShape);
        rightBlockWallBody.setPosition(new Vec2(24.9f, -5f));
        rightBlockWallBody.setFillColor(getVioletColor());
        
        b1Body = new Boss1(this, getPlayerBody(), groundBody, game, 1.5f);
        b1Body.setPosition(new Vec2(0, 8.5f));
        
        mBox = new MysteryBox(this);
        mBox.setPosition(new Vec2(15, -6.5f));
        
        getPlayerBody().addCollisionListener(new PlayerCollision(mBox, getPlayerBody(), b1Body, 3));
        getPlayerBody().addCollisionListener(new PlatformCollision(leftBlockWallBody, 
                rightBlockWallBody, 3, getPlayerBody(), game));
        
        setBombControl(2000);
        setRupeeControl(1000);
        setBombSpawnProbability(10);
        setBombGravity(5);
        
        this.addStepListener(this);
    }
    
        @Override
    public void preStep(StepEvent e) 
    {
        if(game.isCurrentLevelCompleted() && getPlayerBody().getGotQuiver()) 
        {
            game.goNextLevel(getPlayerBody().getScoreCount(), getPlayerBody().getHealthBar());
            this.removeStepListener(this);
        }
        
        checkMusicAndBomb();
        checkBossProgress();
        checkHealthZero();
        updateHeartProbability();
        getPlayerBody().updatePercentage();
        
        randomAttackChance = getRandom().nextInt(30);
        randomAnimationChange = getRandom().nextInt(4);
        randomOtherTrait = getRandom().nextInt(10);
        randomTeleportTime = getRandom().nextInt(90);
    }

        @Override
    public void postStep(StepEvent e) 
    {        
        if(b1Body.getIsBossOn())
        {
            b1Body.animateBossBody(randomAnimationChange, randomAttackChance, 
                    randomOtherTrait, randomTeleportTime);
            
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
    }
    
    public void checkBossProgress()
    {
        if(getPlayerBody().getScoreCount() >= 50)
        {
            b1Body.setIsHalfway(true);
        }
        
        if(getPlayerBody().getScoreCount() > LEVEL_THREE_GOAL)
        {
            getPlayerBody().setScoreCount(LEVEL_THREE_GOAL);
        }
        
        if(getPlayerBody().getScoreCount() == 60 && b1Body.getIsBossOn())
        {
            b1Body.setIsBossOn(false);
            b1Body.destroyLaser();
            b1Body.destroy();
            
            chestBody = new TreasureChest(this, getPlayerBody(), 3);
            chestBody.setPosition(new Vec2(-15, -10));
            chestBody.chestShine();
        }  
    }
    
    /**
     * Toggles the boss and music on and off.
     */
    public void checkMusicAndBomb()
    {
        if(!isMusicOn && b1Body.getIsBossOn())
        {
           game.getBossBattleTheme().loop();
           isMusicOn = true;
           bSoldier = new BombSoldier(this, 15000, 0, 4, getScreenPlatformLength()-1, getPlayerBody(), getGroundBody());
           getPlayerBody().addCollisionListener(new PlayerCollision(bSoldier, getPlayerBody()));
        }
        
        if(isMusicOn && getPlayerBody().getScoreCount() >= 60 && b1Body.getIsBossOn())
        {
           game.getBossBattleTheme().stop(); 
           bSoldier.destroy();
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
        return getPlayerBody().getScoreCount() == LEVEL_THREE_GOAL;
    }
}
