package com.xorprogramming.example.updaterthread;

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
                while (System.currentTimeMillis() < start + 2500)
                    ;
            }
        };
        thread = new UpdaterThread(u, 2);
        System.out.println(thread.start());
        System.out.println(thread.stop(false));
        System.out.println(thread.start());
        System.out.println(thread.stop(false));
        System.out.println(thread.start());
        System.out.println(thread.stop(false));
        System.out.println(thread.start());
    }

}
