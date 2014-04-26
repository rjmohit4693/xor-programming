package com.xorprogramming.remote.web.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connection
{
    private static int   curId = 0;

    final Socket         socket;
    final OutputStream   out;

    private final int    id;
    private final String hostName;


    public Connection(Socket socket)
        throws IOException
    {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.id = ++curId;
        InetAddress address = socket.getInetAddress();
        this.hostName = address.getHostName();
    }


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
