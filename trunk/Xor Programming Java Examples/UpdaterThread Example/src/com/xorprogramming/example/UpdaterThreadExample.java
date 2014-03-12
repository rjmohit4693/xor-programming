package com.xorprogramming.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;

// -------------------------------------------------------------------------
/**
 * A very simple test of the UpdaterThread
 *
 * @author Steven Roberts
 * @version 2.0.0
 */
public class UpdaterThreadExample
    extends JFrame
    implements Updatable
{
    private final Random rand;


    // ----------------------------------------------------------
    /**
     * Starts the execution of the code
     *
     * @param args
     */
    public static void main(String[] args)
    {
        new UpdaterThreadExample();
    }


    private UpdaterThreadExample()
    {
        super("Updater Thread Example");

        rand = new Random();

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createBufferStrategy(1);
        final UpdaterThread thread = new UpdaterThread(this, 4);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowIconified(WindowEvent e)
            {
                thread.stop(false);
            }


            @Override
            public void windowDeiconified(WindowEvent e)
            {
                thread.start();
            }


            @Override
            public void windowClosing(WindowEvent e)
            {
                thread.stop(true);
            }
        });
        setVisible(true);
        thread.start();
    }


    @Override
    public void update(float deltaT)
    {
        Graphics g = getBufferStrategy().getDrawGraphics();

        g.setColor(randColor());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.dispose();
        getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
    }


    private Color randColor()
    {
        return Color.getHSBColor(rand.nextFloat(), 1, 1);
    }

}
