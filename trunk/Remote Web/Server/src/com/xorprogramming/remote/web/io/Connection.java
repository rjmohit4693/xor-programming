package com.xorprogramming.remote.web.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

// -------------------------------------------------------------------------
/**
 *  A wrapper class for a {@code Socket} and its {@code OutputStream} storing information about the socket.
 *
 *  @author Steven Roberts
 *  @version 1.0.0
 */
public class Connection
{
    private static int   curId = 0;

    /**
     * The connection socket
     */
    final Socket         socket;
    /**
     * The socket's output stream
     */
    final OutputStream   out;

    private final int    id;
    private final String hostName;


    // ----------------------------------------------------------
    /**
     * Create a new Connection object.
     * @param socket The connection socket
     * @throws IOException If unable to get socket's output stream
     */
    public Connection(Socket socket)
        throws IOException
    {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.id = ++curId;
        InetAddress address = socket.getInetAddress();
        this.hostName = address.getHostName();
    }


    // ----------------------------------------------------------
    /**
     * Closes the connection's socket
     * @throws IOException If error occurs closing socket
     */
    void close()
        throws IOException
    {
        socket.close();
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Connection)
        {
            return ((Connection)obj).id == id;
        }
        return false;
    }


    @Override
    public String toString()
    {
        return hostName;
    }

}
