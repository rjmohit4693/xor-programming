package com.xorprogramming.example.updaterthread;

import java.util.Random;

// -------------------------------------------------------------------------
/**
 * A very simple test of the UpdaterThread
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class UpdaterThreadExample
    implements Updatable
{
    private final Random rand = new Random();
    private int                 counter;


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
        new UpdaterThreadExample();
    }


    private UpdaterThreadExample()
        throws InterruptedException
    {
        UpdaterThread updaterThread = new UpdaterThread(this, 5);

        System.out.println("Starting...");
        updaterThread.start();
        System.out.println("Started\n");

        Thread.sleep(2000); // Sleep for 2 seconds

        System.out.println("Terminating...");
        updaterThread.terminate(true);
        System.out.println("Terminated");
    }


    @Override
    public void update(float deltaT)
    {
        counter++;
        System.out.printf("Update #%d\nTime since last update: %f\n\n", counter, deltaT);

        try
        {
            Thread.sleep(rand.nextInt(100)); // Sleep between 0-99 ms to simulate a variable update period
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
