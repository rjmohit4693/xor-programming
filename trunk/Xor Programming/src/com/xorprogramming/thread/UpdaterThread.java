package com.xorprogramming.thread;

// -------------------------------------------------------------------------
/**
 * This class regularly updates a given {@code Updatable}. An {@code UpdaterThread} can be started and terminated
 * repeatedly, and all of its methods are thread-safe.
 * 
 * @author Steven Roberts
 * @version 3.0.1
 */
public final class UpdaterThread
{
    /**
     * Use this field when setting the UPS to prevent the {@code UpdaterThread} from sleeping or yielding, thus
     * achieving maximum ups.
     */
    public static final float MAX_UPS = Float.POSITIVE_INFINITY;
    
    private final Object      lock    = new Object();
    
    private final Updatable   u;
    
    private volatile boolean  done;
    private volatile float    targetUPS;
    private Thread            innerThread;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new {@code UpdaterThread} object with max ups.
     * 
     * @param u
     *            The object to update
     */
    public UpdaterThread(Updatable u)
    {
        this(u, MAX_UPS);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new {@code UpdaterThread} object.
     * 
     * @param u
     *            The object to update
     * @param targetUPS
     *            The number of thread updates every second. It must be a number greater than 0 or
     *            {@code UpdaterThread.MAX_UPS}, which prevents the UpdaterThread from sleeping or yielding.
     */
    public UpdaterThread(Updatable u, float targetUPS)
    {
        if (u == null)
        {
            throw new NullPointerException("The updatable must be non-null");
        }
        setTargetUPS(targetUPS);
        this.u = u;
        this.done = true;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Sets the number of UPS that the thread will try to attain.
     * 
     * @param targetUPS
     *            The target ups or {@code UpdaterThread.MAX_UPS}
     */
    public void setTargetUPS(float targetUPS)
    {
        if (targetUPS == Float.NEGATIVE_INFINITY || Float.isNaN(targetUPS) || targetUPS <= 0)
        {
            throw new IllegalArgumentException("The target UPS must be a number greater than 0");
        }
        else
        {
            this.targetUPS = targetUPS;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the number of UPS that the thread will try to attain.
     * 
     * @return the number of UPS that the thread will try to attain
     */
    public float getTargetUPS()
    {
        return targetUPS;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Starts the thread provided it is not running currently
     * 
     * @return true if started successfully, false otherwise
     */
    public boolean start()
    {
        synchronized (lock)
        {
            if (done)
            {
                done = false;
                innerThread = new InnerThread();
                innerThread.start();
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Checks if thread has terminated
     * 
     * @return true if the thread has terminated, false otherwise
     */
    public boolean isDone()
    {
        synchronized (lock)
        {
            return done;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Requests the thread to stop.
     * 
     * @param waitForTermination
     *            If true, the method will block until the thread has finished, otherwise the method will request
     *            termination and immediately return
     * @return true if terminated successfully, false otherwise
     */
    public boolean terminate(boolean waitForTermination)
    {
        synchronized (lock)
        {
            if (done)
            {
                return false;
            }
            else
            {
                done = true;
                innerThread.interrupt();
                if (waitForTermination)
                {
                    boolean retry = true;
                    while (retry)
                    {
                        try
                        {
                            innerThread.join();
                            retry = false;
                        }
                        catch (InterruptedException e)
                        {
                            // Try again
                        }
                    }
                }
                innerThread = null;
                return true;
            }
        }
    }
    
    
    private class InnerThread
        extends Thread
    {
        private static final float NANOS_PER_SEC  = 1e9f;
        private static final float MILLIS_PER_SEC = 1e3f;
        
        
        @Override
        public void run()
        {
            long loopTime; // In nanoseconds
            long prevTime = -1; // In nanoseconds
            
            while (!done)
            {
                float curTargetUPS = targetUPS;
                
                if (prevTime == -1)
                {
                    loopTime = curTargetUPS == MAX_UPS ? 0 : (long)(NANOS_PER_SEC / curTargetUPS);
                    prevTime = System.nanoTime();
                }
                else
                {
                    long curTime = System.nanoTime();
                    loopTime = curTime - prevTime;
                    prevTime = curTime;
                }
                
                u.update(loopTime / NANOS_PER_SEC);
                
                if (curTargetUPS != MAX_UPS)
                {
                    long updateTime = System.nanoTime() - prevTime;
                    
                    // Sleep time in milliseconds
                    long sleep = (long)(MILLIS_PER_SEC / curTargetUPS - MILLIS_PER_SEC * updateTime / NANOS_PER_SEC);
                    try
                    {
                        if (sleep < 0)
                        {
                            Thread.yield();
                        }
                        else
                        {
                            Thread.sleep(sleep);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        // Nothing needed here. This can only happen if the terminate method is called.
                    }
                }
            }
        }
    }
}
