package Holder;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

    /*
    * @author: Lotus-Devs!
    *
    * note: if error just change all arguments requiring path location in the entire code
    *
    * */
public class Canvas1 extends JFrame
{
    public Canvas1()
    {
        Container pane;
        pane = getContentPane();
        pane.setLayout(null);
        Game drawArea = new Game();
        drawArea.setBounds(0,0,1000,500);
        pane.add(drawArea);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setTitle("Naruto Endless");
        setSize(1000,500);
        setResizable(false);
        setVisible(true);

    }
    public static void main(String[] args) {Canvas1 App = new Canvas1();}
}
