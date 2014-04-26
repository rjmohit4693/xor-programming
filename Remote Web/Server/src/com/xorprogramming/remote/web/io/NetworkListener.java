package com.xorprogramming.remote.web.io;

import java.net.InetAddress;

public interface NetworkListener
{
    void onConnection(Connection connection);

    void onDisconnection(Connection connection);

    void onError(String message);
}
