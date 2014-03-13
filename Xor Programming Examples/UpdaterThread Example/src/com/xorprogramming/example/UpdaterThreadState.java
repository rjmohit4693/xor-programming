package com.xorprogramming.example;

// -------------------------------------------------------------------------
/**
 *  The various states in which an {@code UpdaterThread} can be.
 *
 *  @author Steven Roberts
 *  @version 1.1.0
 */
public enum UpdaterThreadState
{
    /**
     * When an {@code UpdaterThread} is created and before an {@code start} is called
     */
    NOT_YET_STARTED("Not yet started"),
    /**
     * When the thread is running and the an {@code Updatable} is being updated
     */
    RUNNING("Running"),
    /**
     * When a stop request was sent, but before the thread is actually stopped
     */
    STOPPING("Stopping"),
    /**
     * When the thread is completely stopped
     */
    STOPPED("Stopped");

    private String name;

    private UpdaterThreadState(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
