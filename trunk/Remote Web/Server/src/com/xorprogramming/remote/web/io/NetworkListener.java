package com.xorprogramming.remote.web.io;

// -------------------------------------------------------------------------
/**
 * A listener interface for receiving {@code NetworkManager} events.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface NetworkListener
{
    // ----------------------------------------------------------
    /**
     * Called when a client connects to the server
     *
     * @param connection
     *            The new connection
     */
    void onConnection(Connection connection);


    // ----------------------------------------------------------
    /**
     * Called when a client disconnects from the server
     *
     * @param connection
     *            The closed connection
     */
    void onDisconnection(Connection connection);


    // ----------------------------------------------------------
    /**
     * Called when an exception occurs with the {@code NetworkManager}
     *
     * @param message
     *            The error message
     */
    void onError(String message);
}
