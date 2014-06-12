package com.xorprogramming.remote.web.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

// -------------------------------------------------------------------------
/**
 * Manages a network connection with multiple clients on two separate threads. Users of the {@code NetworkManager} class
 * can be notified of errors, client connections, and client disconnections on the AWT event dispatching thread with a
 * {@code NetworkListener}.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class NetworkManager
    implements Closeable
{
    private static final long           PING_TIME = 1;
    private static final String         NEWLINE   = "\n";
    private static final String         EMPTY     = "";

    private final NetworkListener       listener;
    private final BlockingQueue<String> messages;
    private final SocketAccepter        accepter;
    private final SocketWriter          writer;


    // ----------------------------------------------------------
    /**
     * Create a new NetworkManager object.
     * @param port The port on which the server will run
     * @param listener The event listener
     * @throws IOException If unable to create server socket
     */
    public NetworkManager(int port, NetworkListener listener)
        throws IOException
    {
        try
        {
            accepter = new SocketAccepter(port);
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
        this.listener = listener;
        messages = new LinkedBlockingQueue<String>();
        writer = new SocketWriter();
    }


    // ----------------------------------------------------------
    /**
     * Starts the threads that run the server
     */
    public void start()
    {
        accepter.start();
        writer.start();
    }


    // ----------------------------------------------------------
    /**
     * Sends a message to all the clients
     * @param message The message
     */
    public void sendMessage(String message)
    {
        messages.add(message);
    }


    @Override
    public void close()
    {
        writer.terminate();
        accepter.terminate();
    }


    private void postMessage(final String message)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                listener.onError(message);
            }
        });
    }


    // -------------------------------------------------------------------------
    /**
     *  This thread accepts clients and sends the newly created sockets to the writer thread
     *
     *  @author Steven Roberts
     *  @version 1.0.0
     */
    private class SocketAccepter
        extends Thread
    {
        private final ServerSocket serverSocket;


        public SocketAccepter(int port)
            throws Exception
        {
            serverSocket = new ServerSocket(port);
        }


        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    writer.addConnection(socket);
                }
                catch (SocketException se)
                {
                    // the socket was close from the terminate method so break the loop
                    break;
                }
                catch (Exception e)
                {
                    postMessage("Unable to accept socket");
                }
            }
        }


        public void terminate()
        {
            try
            {
                serverSocket.close();
            }
            catch (Exception e)
            {
                postMessage("Unable to close server socket");
            }
        }

    }


    // -------------------------------------------------------------------------
    /**
     *  This thread writes messages to the clients and manages the connections.
     *
     *  @author Steven Roberts
     *  @version 1.0.0
     */
    private class SocketWriter
        extends Thread
    {
        /**
         * This variable must be used in a synchronized block with itself as the lock
         */
        private final List<Connection> connections;


        public SocketWriter()
        {
            connections = new LinkedList<Connection>();
        }


        public void addConnection(Socket socket)
        {
            synchronized (connections)
            {
                try
                {
                    Connection c = new Connection(socket);
                    connections.add(c);
                    postConnection(c);
                }
                catch (IOException e)
                {
                    postMessage("Error establishing connection");
                }
            }
        }


        private void postConnection(final Connection c)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    listener.onConnection(c);
                }
            });
        }


        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    String message = messages.poll(PING_TIME, TimeUnit.SECONDS);
                    sendMessage(message == null ? EMPTY : message);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
        }


        private void sendMessage(String message)
        {
            synchronized (connections)
            {
                byte[] bytes = (message + NEWLINE).getBytes();
                for (Iterator<Connection> itr = connections.iterator(); itr.hasNext();)
                {
                    Connection c = itr.next();
                    try
                    {
                        c.out.write(bytes);
                        c.out.flush();
                    }
                    catch (Exception e)
                    {
                        // An error occurred in the connection so remove the client
                        closeConnection(c);
                        itr.remove();
                    }
                }
            }
        }


        private void closeConnection(Connection c)
        {
            try
            {
                c.close();
                postDisconnection(c);
            }
            catch (Exception e)
            {
                postMessage("Unable to close connection: " + c);
            }
        }


        private void postDisconnection(final Connection c)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    listener.onDisconnection(c);
                }
            });
        }


        public void terminate()
        {
            synchronized (connections)
            {
                interrupt();
                for (Connection c : connections)
                {
                    closeConnection(c);
                }
            }
        }

    }

}