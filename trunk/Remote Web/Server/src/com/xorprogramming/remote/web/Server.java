package com.xorprogramming.remote.web;

import com.xorprogramming.remote.web.io.Connection;
import com.xorprogramming.remote.web.io.NetworkListener;
import com.xorprogramming.remote.web.io.NetworkManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.*;

// -------------------------------------------------------------------------
/**
 * The GUI for the server. It displays the currently connected clients and allows users to send requests to open
 * webpages on the clients. The server can also tells client programs to close themselves with the "exit" command.
 *
 * @author Steven Roberts
 * @version 2.0.0
 */
public class Server
    implements NetworkListener, ActionListener
{
    private static final int                   PORT    = 2573;
    private static final Dimension             SIZE    = new Dimension(300, 300);
    private static final int                   PADDING = 8;

    private final NetworkManager               manager;
    private final JFrame                       frame;
    private final JTextField                   urlField;
    private final DefaultListModel<Connection> listModel;


    // ----------------------------------------------------------
    /**
     * Starts the server and calls constructor
     *
     * @param args
     *            The command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            new Server();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private Server()
        throws IOException
    {
        this.manager = new NetworkManager(PORT, this);

        frame = new JFrame("Remote Web");
        frame.setMinimumSize(SIZE);
        frame.setLocationRelativeTo(null);

        urlField = new JTextField();
        listModel = new DefaultListModel<Connection>();
        setUpGUI();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
                manager.close();
            }
        });
        manager.start();
    }


    private void setUpGUI()
    {
        JPanel mainPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        JList<Connection> list = new JList<Connection>(listModel);
        JLabel title = new JLabel("Server IP: " + getIP(), JLabel.CENTER);
        title.setFont(new Font(null, Font.BOLD, 16));
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        bottomPanel.add(urlField, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }


    private String getIP()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception e)
        {
            return "?";
        }
    }


    @Override
    public void onConnection(Connection connection)
    {
        listModel.addElement(connection);
    }


    @Override
    public void onDisconnection(Connection connection)
    {
        listModel.removeElement(connection);
    }


    @Override
    public void onError(String message)
    {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void actionPerformed(ActionEvent event)
    {
        String message = urlField.getText().trim();
        manager.sendMessage(message);
    }
}
