package Holder;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

enum state{
    death,
    jump,
    idle
}

public class Player {

    private state currState = state.idle;
    boolean canJump=true;
    private final BufferedImage[] img = new BufferedImage[5];
    private BufferedImage currImage;
    private final Canvas c;
    public Timer timer;
    final int gravity = 1;
    Rectangle rect;
    final int x=100;
    private int velY=0;
    private int y=400;
    private int frame;

    public Player(Canvas c)
    {
        this.c=c;
        rect = new Rectangle(x,y,50,50);
        LoadPlayerFrames();
        timer = new Timer(true);
        timer.schedule(Task(), 1000, 100);
        PlayerAnimate();
    }

    private void LoadPlayerFrames()
    {


        for (int f = 1; f <= 5; f++) {
            DrawP("NarutoEndless\\assets\\sprites\\R" + f + ".png",f-1); //change path location from where you place/download the folder

        }
        currImage=img[0]; //set 1st frame in currImage to avoid null exceptions when repaint()


    }

    void ApplyGravity()
    {
        if(y<400-rect.height) //not grounded
        {
            velY+=gravity;
            y+=velY;
        }
        else //grounded
        {
            velY=gravity;
            if(currState==state.jump && !canJump)
            {
                //resets state and jump
                currState=state.idle;
            }
            y=400;
        }

    }

    void draw(Graphics g)
    {
        ApplyGravity();
        rect.x = x;
        rect.y = y;
        rect.width=currImage.getWidth(c)/2;
        rect.height=currImage.getHeight(c);
        Image holdTemp = currImage;
        currImage.getGraphics().drawImage(holdTemp,x,y,c);
        g.drawImage(currImage, x, y, c);
        StateChanged();
    }

    private TimerTask Task()
    {
        return new TimerTask() {
            @Override
            public void run() {
                if(currState!=state.death)
                {
                    frame = (frame+1) % 4;
                    currImage=img[frame];

                }
            }
        };

    }

    public void PlayerAnimate()
    {
        currState=state.idle;

    }

    private void DrawP(String path,int ctr)
    {
        try
        {
            currImage = ImageIO.read(new File(path));
            img[ctr] = currImage;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void StateChanged()
    {
        switch (currState)
        {
            case jump:

                JumpAction();
                break;

            case death:
                canJump=false;
                currImage=img[4];
                //System.out.println("death");
                break;
            case idle:
                canJump=true;


        }
    }

    public void Jump()
    {
        currState=state.jump;
        canJump=true;
    }

    public void Death()
    {
        currState=state.death;
    }

    public boolean isDead()
    {
        return currState == state.death;
    }


    private void JumpAction()
    {
        if(canJump)
        {
            SoundManager.getInstance().PlaySfx("NarutoEndless\\assets\\sfx\\narutoJump.wav",false,-10); //change path location from where you place/download the folder
            y-= 200;
            canJump=false;

        }
    }
}

