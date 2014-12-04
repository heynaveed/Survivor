package Level;

import Body.Soldier;
import Collision.PlatformCollision;
import Survivor.Game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 * Builds and populates level 4.
 */
public class Level4 extends GameUniverse implements StepListener
{   
    private static final int LEVEL_FOUR_GOAL = 80;
    private static final int LEVEL_FOUR_PLATFORM_LENGTH = 250;
    private static final float signPostM = 5;
    private static final float flagM = 4.5f;
    
    private static final Shape signPostHandleShape = new PolygonShape(-0.015f*signPostM,0.246f*signPostM, -0.011f*signPostM,
            -0.447f*signPostM, 0.057f*signPostM,-0.447f*signPostM, 0.053f*signPostM,0.246f*signPostM);
    private static final Shape signPostSignShape = new PolygonShape(-0.318f*signPostM,0.413f*signPostM, -0.314f*signPostM,0.239f*signPostM, 
            0.216f*signPostM,0.193f*signPostM, 0.39f*signPostM,0.333f*signPostM, 0.205f*signPostM,0.455f*signPostM);
    private static final Shape flagShape = new PolygonShape(-0.244f*flagM,0.253f*flagM, -0.247f*flagM,
            0.025f*flagM, 0.167f*flagM,-0.378f*flagM, 0.231f*flagM,-0.378f*flagM, 0.228f*flagM,0.278f*flagM, 0.125f*flagM,0.3f*flagM);
    
    private Body groundBody, largeLeftBlockBody, largeRightBlockBody;
    private Shape groundShape, largeLeftBlockShape, largeRightBlockShape;
    private Game game;
    private Soldier sBody;
    
    
        @Override
    public void populate(Game game)
    {
        super.populate(game);
        
        this.game = game;
        

        
        groundShape = new BoxShape(LEVEL_FOUR_PLATFORM_LENGTH, 15);
        groundBody = new StaticBody(this, groundShape);
        regularBlockFixture(groundBody, groundShape);
        groundBody.setPosition(new Vec2(LEVEL_FOUR_PLATFORM_LENGTH, -15));
        groundBody.setFillColor(getGreenColor());
        setGroundBody(groundBody);
        

        
        largeLeftBlockShape = new BoxShape(12.5f, 85);
        largeLeftBlockBody = new StaticBody(this, largeLeftBlockShape);
        deathGripBlockFixture(largeLeftBlockBody, largeLeftBlockShape);
        largeLeftBlockBody.setPosition(new Vec2(-12.5f, 50));
        largeLeftBlockBody.setFillColor(getPurpleColor());
        
        largeRightBlockShape = new BoxShape(12.5f, 85);
        largeRightBlockBody = new StaticBody(this, largeRightBlockShape);
        deathGripBlockFixture(largeRightBlockBody, largeRightBlockShape);
        largeRightBlockBody.setPosition(new Vec2(512.5f, 50));
        largeRightBlockBody.setFillColor(getPurpleColor());
        
        
        for(int i = 25; i < 500; i = i + 25)
        {
            StaticBody flagBody = new StaticBody(this);
            flagBody.setImage(new BodyImage("data/flag.png", flagM));
            GhostlyFixture gateFixture = new GhostlyFixture(flagBody, flagShape, flagM);
            gateFixture.setDensity(0);
            flagBody.setPosition(new Vec2(i, 1.25f));
        }
        
        StaticBody signPostBody = new StaticBody(this);
        signPostBody.setImage(new BodyImage("data/signPost.png", signPostM));
        GhostlyFixture signPostHandleFixture = new GhostlyFixture(signPostBody, signPostHandleShape, signPostM);
        signPostHandleFixture.setDensity(0);
        GhostlyFixture signPostSignFixture = new GhostlyFixture(signPostBody, signPostSignShape, signPostM);
        signPostSignFixture.setDensity(0);
        signPostBody.setPosition(new Vec2(5, 2));
        

        
        sBody = new Soldier(this, getPlayerBody(), 1, 2, 0.02f, 9, groundBody, 100, 3);
        sBody.setPosition(new Vec2(100, 0.8f));
        getPlayerBody().setSoldierBody1(sBody);
        
        sBody = new Soldier(this, getPlayerBody(), 2, 4, 0.04f, 12, groundBody, 100, 4);
        sBody.setPosition(new Vec2(175, 0.8f));
        getPlayerBody().setSoldierBody2(sBody);
        
        sBody = new Soldier(this, getPlayerBody(), 3, 6, 0.06f, 15, groundBody, 100, 5);
        sBody.setPosition(new Vec2(250, 0.8f));
        getPlayerBody().setSoldierBody3(sBody);
        
        sBody = new Soldier(this, getPlayerBody(), 4, 8, 0.08f, 18, groundBody, 125, 6);
        sBody.setPosition(new Vec2(325, 0.8f));
        getPlayerBody().setSoldierBody4(sBody);
        
        getPlayerBody().addCollisionListener(new PlatformCollision(largeLeftBlockBody, largeRightBlockBody, 4, 
                getPlayerBody(), game));
        
        getPlayerBody().setAmmo(30);
        getPlayerBody().setGotQuiver(true);
        
        this.addStepListener(this);
    }
    
        @Override
    public Vec2 startPosition() 
    {
        return new Vec2(25, 0);
    }

        @Override
    public boolean isCompleted() 
    {
        return getPlayerBody().getScoreCount() == LEVEL_FOUR_GOAL;
    }

        @Override
    public void preStep(StepEvent e) 
    {
        if (game.isCurrentLevelCompleted()) 
        {
            game.goNextLevel(getPlayerBody().getScoreCount(), getPlayerBody().getHealthBar());
            this.removeStepListener(this);
        }
        
        checkAmmoMaxMin();
        checkHealthZero();
        updateAmmoBunchControl();
        updateHeartProbability();
        getPlayerBody().updatePercentage();
    }

        @Override
    public void postStep(StepEvent e) 
    {   
        heartWave((getPlayerBody().getPosition().x) + 
                (getNewRandomX1(25) - getNewRandomX2(25)), 80, 0.15f);
        
        ammoBunchWave((getPlayerBody().getPosition().x) + 
                (getNewRandomX1(25) - getNewRandomX2(25)), 70);
    }
}
