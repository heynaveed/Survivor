package Survivor;

import city.cs.engine.*;
import java.awt.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics2D;


/**
 * This class handles the interface and background for the game.
 */
public class MyView extends UserView 
{
    private static final float alpha = 0.9f;
    
    private final Image background, background2, healthHeart, groupRupee, ammoBunch;
    private final Game game;
    private final Color blue, black, ice;

    
    public MyView(World world, int width, int height, Game game) 
    {
        super(world, width, height);    
       
        this.game = game;
        
        background = Toolkit.getDefaultToolkit().createImage("data/background.gif"); 
        background2 = Toolkit.getDefaultToolkit().createImage("data/background2.jpg");
        healthHeart = Toolkit.getDefaultToolkit().createImage("data/heart.png");
        groupRupee = Toolkit.getDefaultToolkit().createImage("data/groupRupee.png");
        ammoBunch = Toolkit.getDefaultToolkit().createImage("data/ammoBunch.png");
        
        blue = new Color (0.23f, 0.37f, 0.63f);
        black = new Color (0, 0, 0);
        ice = new Color(0.5f, 1, 1);
    }
    
        @Override
    public void paintBackground(Graphics2D g)
    {
        super.paintBackground(g); 
        
        if(game.getPlayerBody().getLevel() < 4)
        {
            g.drawImage(background, 0, 0, null);   
        }
        
        if(game.getPlayerBody().getLevel() == 4)
        {
            g.drawImage(background2, -80, 0, null);
        }
    }
   
        @Override
    public void paintForeground(Graphics2D g)
    {
        super.paintForeground(g);
        
        g.setColor(black);
        g.setStroke(new BasicStroke(4));
        g.setColor(blue);
        g.setComposite(makeComposite(alpha));
        
        g.draw3DRect(704, 2, 300, 40, true);
        g.fill3DRect(704, 2, 300, 40, true);
        
        g.draw3DRect(2, 466, 105, 32, true);
        g.fill3DRect(2, 466, 105, 32, true);
        
        g.drawImage(healthHeart, 718, 13, 28, 20, null);
        g.drawImage(groupRupee, 820, 6, 27, 32, null);
        g.drawImage(ammoBunch, 907, -1, 45, 45, null);
        
        g.setColor(black);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        
        g.drawString("Level: " + game.getPlayerBody().getLevel() + "/4", 7, 479);
        g.drawString("Completed: (" + game.getPlayerBody().getCompleted() + "%)", 7, 494);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        
        g.drawString("x " + game.getPlayerBody().getScoreCount(), 855, 28);
        
        colorHealth(g);
        colorAmmo(g);
    }
    
    public AlphaComposite makeComposite(float alpha)
    {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }
    
    /**
     * If the maximum number of a certain attribute is reached, 
     * it is coloured in a different colour.
     */
    public void colorHealth(Graphics2D g)
    {
        if(game.getPlayerBody().getHealthBar() >= 100)
        {
            g.setColor(ice);
        }
        if(game.getPlayerBody().getHealthBar() < 100)
        {
            g.setColor(black);
        }
        
        g.drawString(game.getPlayerBody().getHealthBar() + "%", 755, 28);
    }
    
    /**
    * If the maximum number of a certain attribute is reached, 
    * it is coloured in a different colour.
    */
    public void colorAmmo(Graphics2D g)
    {
        if(game.getPlayerBody().getAmmo() >= 30)
        {
            g.setColor(ice);
        }
        if(game.getPlayerBody().getAmmo() < 30)
        {
            g.setColor(black);
        }
        g.drawString("x " + game.getPlayerBody().getAmmo(), 957, 28);
    }
}
