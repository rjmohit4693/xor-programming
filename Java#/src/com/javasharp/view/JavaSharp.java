package com.javasharp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.javasharp.controller.MenuController;

public class JavaSharp
{
    private final JFrame           mainFrame;
    private MenuController         menuController;
    
    private static final String    TITLE         = "Java #";
    private static final Dimension MIN_DIMENSION = new Dimension(600, 400);
    
    
    private JavaSharp()
    {
        mainFrame = new JFrame(TITLE);
        menuController = new MenuController(this);
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(MIN_DIMENSION);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(new MenuBar(menuController));
        mainFrame.add(new StatusBar(), BorderLayout.SOUTH);
        mainFrame.add(new HomeScreen(), BorderLayout.CENTER);
        
        mainFrame.setVisible(true);
    }
    
    
    public static void main(String[] args)
    {
        new JavaSharp();
    }
    
    
    public void showHelpDialog()
    {
        new HelpDialog(mainFrame).setVisible(true);
    }
    
    public void showAboutDialog() {
        JLabel label = new JLabel("<html><center>Created by:<br>Steven Roberts and Taylor Mattison<br>for VT Hacks II 2015<center></html>", JLabel.CENTER);
        JOptionPane.showMessageDialog(mainFrame, label, "About", JOptionPane.PLAIN_MESSAGE);
    }
}
