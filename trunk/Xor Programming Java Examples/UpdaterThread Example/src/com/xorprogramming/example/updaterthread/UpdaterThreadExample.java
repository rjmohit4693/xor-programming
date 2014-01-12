package com.xorprogramming.example.updaterthread;

import com.xorprogramming.thread.Updatable;
import com.xorprogramming.thread.UpdaterThread;
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
    private static UpdaterThread thread;

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
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() < start + 2500);
            }
        };
        thread = new UpdaterThread(u);
        new Thread()
        {
            public void run()
            {
                System.out.println(thread.start());
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(thread.stop(false));
                System.out.println(thread.start());
                try
                {
                    Thread.sleep(4000);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(thread.stop(true));
            }
        }.start();
    }

}
