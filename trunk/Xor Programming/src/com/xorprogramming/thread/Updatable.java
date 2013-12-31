package com.xorprogramming.thread;

// -------------------------------------------------------------------------
/**
 * This interface provides a structure for an object than can be updated over a defined time interval.
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface Updatable
{
    // ----------------------------------------------------------
    /**
     * This is called when the {@code Updatable} object should be updated
     * 
     * @param deltaT
     *            The time interval since the method was last called
     */
    void update(float deltaT);
}
