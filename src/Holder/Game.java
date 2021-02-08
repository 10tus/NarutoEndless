package Holder;

import javax.imageio.ImageIO;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
 * @author: Lotus-Devs!
 *
 * note: if error just change all arguments requiring path location in the entire code
 *
 * */

public class Game extends Canvas
{
    int keyCode;
    int pointer=0;
    Image img;
    BufferedImage bgImage;
    private boolean over=false;
    ArrayList<Obstacle> obsList = new ArrayList<>();
    Player p;
    Score s;
    Timer timer;
    public Game()
    {

        setBackground(Color.DARK_GRAY);
        Start();

    }

    void Start()
    {
        //set background image
        DrawBg();
        SoundManager.getInstance().
                PlaySfx("NarutoEndless\\assets\\sfx\\bgMusic.wav",true,-10); //change path location from where you place/download the folder
        p = new Player(this);
        s = new Score();
        s.ScoreStart();
        MoveObjects();
        ListenKeys();
        this.setFocusable(true);
        this.requestFocusInWindow();
        CreateObstaclePool();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        UpdateObjects(g);
    }

    private void ResetGame(Graphics g)
    {
        if(over)
        {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over",350,200);

            Rectangle again = new Rectangle(this.getWidth()/2-110,this.getHeight()/2-30,150,50);
            g.drawRect(again.x,again.y,again.width,again.height); //border
            //g.drawLine(this.getWidth()/2-110,this.getHeight()/2-30,this.getWidth()/2+40,this.getHeight()/2+20);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Play Again",400,250);
            MouseListener(this);
        }
    }

    private void RestartComponents()
    {
        //System.out.println("restarting");
        obsList.get(pointer).SetObstacleToStart();
        p.PlayerAnimate();
        over=false;
        s.resetScore();


    }

    private void MouseListener(Canvas c)
    {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //x and y gets values of mouse's pointer position
                if(over)
                {
                    int x = e.getX();
                    int y = e.getY();
                    //area for "button" play again
                    if(x>=c.getWidth()/2-110 && x<=c.getWidth()/2+40)
                    {
                        if(y>=c.getHeight()/2-30 && y<= c.getHeight()/2+20)
                        {
                            RestartComponents();

                        }
                    }
                }



            }
        });
    }


    void UpdateObjects(Graphics g)
    {
        s.setOver(over);
        ChangeDifficulty();
        g.drawImage(bgImage,0,0,this);
        obsList.get(pointer).setOver(over);
        obsList.get(pointer).draw(g);
        ResetGame(g);
        s.draw(g);
        p.draw(g);

        Gameplay();

        CheckCollision();

    }

    void DrawBg()
    {
        try
        {
            bgImage = ImageIO.read(new File("NarutoEndless\\assets\\sprites\\bg.jpg")); //change path location from where you place/download the folder
            img = bgImage.getScaledInstance(1000, 500, Image.SCALE_DEFAULT);
            bgImage = new BufferedImage(1000,500,BufferedImage.TYPE_INT_RGB);
            bgImage.getGraphics().drawImage(img,0,0,this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void ListenKeys()
    {
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keyCode = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                keyCode = 0;
            }
        });


    }

    void Gameplay()
    {
        if (keyCode==KeyEvent.VK_SPACE)
        {
            if(p.canJump)
            {
                p.Jump();
            }
        }

    }

    void MoveObjects()
    {

        timer = new Timer(true);
        TimerTask task = new TimerTask() {
            public void run() {
                SpawnObstacle();
            }//end run
        };
        timer.schedule(task, 1000, 30);

    }

    void SpawnObstacle()
    {

        if(!p.isDead())
        {
            Random random = new Random();
            obsList.get(pointer).MoveObstacle();
            if(obsList.get(pointer).outScreen())
            {
                obsList.get(pointer).SetObstacleToStart();
                pointer = random.nextInt(3);
            }
        }

        repaint();
    }

    void ChangeDifficulty()
    {

        //check if score mod 100 is equal to 0 and score is not 0
        //if true increase difficulty of game by increasing velocity x-value for all obstacles
        if(s.getScore()%100 == 0 && s.getScore() !=0)
        {
            SoundManager.getInstance().PlaySfx("NarutoEndless\\assets\\sfx\\levelChange.wav",false,2); //change path location from where you place/download the folder
            for (Obstacle obs: obsList )
            {
                obs.changeVelocityX(over);

            }

        }
        //resets difficulty of game if game is over
        else if(over)
        {

            for (Obstacle obs: obsList )
            {
                obs.changeVelocityX(over);

            }
        }

    }

    void CheckCollision()
    {

        if(obsList.get(pointer).Collision(p.rect))
        {
            p.Death();
            over=true;
        }
    }


    void CreateObstaclePool()
    {
        if(obsList.size() == 0)
        {
            for (int i = 0; i < 3; i++)
            {
                obsList.add(new Obstacle(i,this));
            }
        }
    }
}

