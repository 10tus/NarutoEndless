package Holder;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

public class Score {
    private int score=0;
    private int hi=0;
    public Timer timer;
    private boolean over;

    public void setOver(boolean flag)
    {
        over = flag;
    }

    public int getScore()
    {
        return score;
    }
    public void resetScore()
    {
        score=0;
    }


    public void draw(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Press space bar to play",0,20);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(score+"m",450,100);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("High: "+hi+"m",850,50);

    }




    public void ScoreStart()
    {
        timer = new Timer(true);
        TimerTask task = new TimerTask() {
            public void run() {
                if(!over)
                {
                    score++;
                    if(score>hi)
                    {
                        hi=score;
                    }

                }


            }//end run
        };
        timer.schedule(task, 1000, 100);

    }

}
