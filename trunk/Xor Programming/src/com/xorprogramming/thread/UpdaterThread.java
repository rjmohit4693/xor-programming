package com.xorprogramming.thread;

// -------------------------------------------------------------------------
/**
 * This class regularly updates a given {@code Updatable}. An {@code UpdaterThread} can be started and terminated
 * repeatedly, and all of its methods are thread-safe. Note that unlike a {@code Thread}, {@code UpdaterThread} is not
 * made for inheritance.
 * <p>
 * Memory consistency is guaranteed for data whose access is confined to an {@code UpdaterThread}'s {@code update}
 * method. However, if data is not confined to the {@code update} method, addition synchronization measures must be
 * taken.
 * </p>
 *
 * @author Steven Roberts
 * @version 3.1.1
 */
public final class UpdaterThread
{
    /**
     * Use this field when setting the UPS to prevent the {@code UpdaterThread} from sleeping or yielding, thus
     * achieving maximum UPS.
     */
    public static final float           MAX_UPS         = Float.POSITIVE_INFINITY;

    /*
     * Ensures starting and stopping are mutually exclusive
     */
    private final Object                startStopLock   = new Object();

    /*
     * Ensures the inner thread cannot start before the previous instance finished
     */
    private final Object                innerThreadLock = new Object();

    private final Updatable             u;

    private volatile UpdaterThreadState state;
    private volatile float              targetUPS;
    private Thread                      innerThread;


    // ----------------------------------------------------------
    /**
     * Create a new {@code UpdaterThread} object with max ups.
     *
     * @param u
     *            The object to update
     * @throws NullPointerException
     *             if the {@code Updatable} is null
     * @see #MAX_UPS
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
     *            The number of thread updates every second. It must be a positive number or
     *            {@code UpdaterThread.MAX_UPS}, which prevents the UpdaterThread from sleeping or yielding.
     * @throws NullPointerException
     *             if the {@code Updatable} is null
     * @throws IllegalArgumentException
     *             if {@code targetUPS} is not a positive number or {@code MAX_UPS}
     * @see #MAX_UPS
     * @see UpdaterThread#targetUPS
     */
    public UpdaterThread(Updatable u, float targetUPS)
    {
        if (u == null)
        {
            throw new NullPointerException("The updatable must be non-null");
        }
        setTargetUPS(targetUPS);
        this.u = u;
        state = UpdaterThreadState.NOT_YET_STARTED;
    }


    // ----------------------------------------------------------
    /**
     * Sets the number of UPS that the thread will try to attain.
     *
     * @param targetUPS
     *            The target UPS or {@code UpdaterThread.MAX_UPS}
     * @throws IllegalArgumentException
     *             if {@code targetUPS} is not a positive number or {@code MAX_UPS}
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
     * Starts the thread provided it is not running. Note that if the {@code UpdaterThread} is stopping when
     * {@code start} is called, the thread starts, but will not run until the thread finishes stopping. This method does
     * not block.
     *
     * @return true if started successfully, false otherwise
     */
    public boolean start()
    {
        synchronized (startStopLock)
        {
            if (innerThread == null)
            {
                innerThread = new InnerThread();
                state = UpdaterThreadState.RUNNING;
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
     * Gets the current state of the {@code UpdaterThread}
     *
     * @return the current state of the {@code UpdaterThread}
     * @see UpdaterThreadState
     */
    public UpdaterThreadState getState()
    {
        synchronized (startStopLock)
        {
            return state;
        }
    }


    // ----------------------------------------------------------
    /**
     * Requests the thread to stop and changes the state to stopping.
     *
     * @param join
     *            If true, the method will block until the thread is stopped. Otherwise, the method will request
     *            termination and immediately return
     * @return true if the request to stop the thread was successful, false otherwise. The request is successful iff the
     *         thread is running.
     * @throws IllegalStateException
     *             if {@code stop(true)} is called within an {@code Updatable}'s {@code update} method becase the thread
     *             cannot wait for itself to finish.
     */
    public boolean stop(boolean join)
    {
        synchronized (startStopLock)
        {
            if (innerThread == null)
            {
                return false;
            }

            state = UpdaterThreadState.STOPPING;
            innerThread.interrupt();
            if (join)
            {
                if (Thread.currentThread() == innerThread)
                {
                    throw new IllegalStateException("The Updatable cannot wait for itself to terminate");
                }
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


    // ----------------------------------------------------------
    /**
     * Handles the updating of the {@code Updatable}. While multiple instances of this class can exist within an
     * {@code UpdaterThread}, only one can run at a time.
     */
    private class InnerThread
        extends Thread
    {
        private static final float NANOS_PER_SEC  = 1e9f;
        private static final float MILLIS_PER_SEC = 1e3f;


        @Override
        public void run()
        {
            long prevTime = -1; // In nanoseconds
            synchronized (innerThreadLock)
            {
                while (state == UpdaterThreadState.RUNNING)
                {
                    float curTargetUPS = targetUPS; // Take a snapshot of the current targetUPS

                    long curTime = System.nanoTime();
                    if (prevTime == -1)
                    {
                        u.update(1 / targetUPS);
                    }
                    else
                    {
                        u.update((curTime - prevTime) / NANOS_PER_SEC);
                    }
                    prevTime = curTime;

                    if (curTargetUPS != MAX_UPS)
                    {
                        long updateTime = System.nanoTime() - curTime; // In nanoseconds

                        // Sleep time in milliseconds
                        long sleep =
                            (long)(MILLIS_PER_SEC / curTargetUPS - MILLIS_PER_SEC * updateTime / NANOS_PER_SEC);
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
                            // Continue
                        }
                    }
                }
                state = UpdaterThreadState.STOPPED;
            }
        }
    }
}
