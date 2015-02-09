package com.javasharp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeScreen
    extends JPanel
{
    
    private static final long serialVersionUID = 1L;
    
    private static final String CONTENT = "<HTML><BODY><center><img src=\"file:res\\music_graphics\\music.jpg\"/><p><font size=\"48\" color=\"#66AAFF\">Welcome to Java# " +
        System.getProperty("user.name") +
        "</font><p><b>Version: 2.0 alpha</b><p><b>Created by:<br></b>Steven Roberts and Taylor Mattison<p><br><br>Press File > New to begin!</center></BODY></HTML>";
    
    public HomeScreen()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        try
        {
            BufferedImage myPicture = ImageIO.read(new File("res\\music_graphics\\music.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(picLabel);
        }
        catch (IOException e)
        {
            add(new JLabel("Error: Couldn't load image."));
        }
        
        JLabel welcome = new JLabel("Welcome to Java#, " + System.getProperty("user.name"));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setFont(new Font("TimesRoman", Font.PLAIN, 38));
        welcome.setForeground(new Color(0.4f, 0.66f, 1.0f));
        add(welcome);
        
        JLabel version = new JLabel("Version 2.0 alpha");
        version.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setFont(new Font("TimesRoman", Font.BOLD, 24));
        add(version);
    }
    
}
