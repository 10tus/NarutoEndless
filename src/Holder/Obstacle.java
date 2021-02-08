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


public class Obstacle {
    Rectangle rect;
    int name;
    private boolean over=false;
    private int x=900;
    private final int y=380;
    private int velX=15;
    private final BufferedImage[] img = new BufferedImage[2];
    private BufferedImage currImage;
    private final Canvas c;
    private int frame;
    String[] obsType =  {"shuri","fire","spike"};
    public void draw(Graphics g)
    {

        rect.x=x; //apply the x value to rectangle x position for collision
        rect.y=y;
        rect.width=currImage.getWidth(c);
        rect.height=currImage.getHeight(c);
        //g.setColor(Color.red);
        //g.fillRect(rect.x, rect.y,rect.width,rect.height);
        Image holdTemp = currImage;
        currImage.getGraphics().drawImage(holdTemp,x,y,c);
        g.drawImage(currImage,x,y,c);
    }
    public Obstacle(int name,Canvas c)
    {

        this.name=name;
        this.c = c;
        LoadFrames();
        rect = new Rectangle(x,y,50,50);
        Timer timer = new Timer(true);
        timer.schedule(Task(), 1000, 100);
    }

    public void setOver(boolean flag)
    {
        over = flag;
    }

    private TimerTask Task()
    {
        return new TimerTask() {
            @Override
            public void run() {
                if(!over)
                {
                    frame = (frame+1) % 2;
                    //System.out.println(frame+"frames");
                    currImage=img[frame];
                }


            }
        };

    }

    private void LoadFrames()
    {


        for (int f = 0; f < 2; f++) {
            DrawObs("NarutoEndless\\assets\\sprites\\"+obsType[name] + (f+1) + ".png",f); //change path location from where you place/download the folder

        }
        currImage=img[0]; //set 1st frame in currImage to avoid null exceptions when repaint()


    }

    private void DrawObs(String path,int ctr)
    {
        try
        {
            currImage = ImageIO.read(new File(path));
            img[ctr] = currImage;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void changeVelocityX(boolean flag)
    {
        int cappedVelocity=50;
        if(!flag)
        {
            //cap the velocity change
            if(velX>=cappedVelocity)
            {
                velX=cappedVelocity;
            }else
                velX+=2;
        }

        else
            velX=15;
    }



    public void SetObstacleToStart()
    {
        x=900;
    }
    public void MoveObstacle()
    {
        x -= velX;
    }
    public boolean outScreen()
    {
        return x<0;
    }

    public boolean Collision(Rectangle rect2)
    {
        //System.out.println(rect.x+" :: "+rect2.x);
        return rect.intersects(rect2);

    }

}
