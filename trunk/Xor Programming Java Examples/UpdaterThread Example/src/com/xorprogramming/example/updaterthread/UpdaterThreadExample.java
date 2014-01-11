package com.xorprogramming.example.updaterthread;

import java.util.List;


// -------------------------------------------------------------------------
/**
 * A very simple test of the UpdaterThread
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class UpdaterThreadExample
{

    // ----------------------------------------------------------
    /**
     * Starts the execution of the code
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args)
        throws InterruptedException
    {
        Updatable u = new Updatable()
        {
            @Override
            public void update(float deltaT)
            {
                System.out.println(deltaT);
            }
        };
        final UpdaterThread thread = new UpdaterThread(u, 30);
        new Thread()
        {
            public void run()
            {
                thread.start();
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                thread.stop(false);
            }
        }.start();
    }


}
