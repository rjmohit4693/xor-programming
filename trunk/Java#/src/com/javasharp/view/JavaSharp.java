package com.javasharp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class JavaSharp
{
    private final JFrame mainFrame;
    
    private static final String TITLE = "Java #";
    private static final Dimension MIN_DIMENSION = new Dimension(600, 400);
    
    private JavaSharp() {
        mainFrame = new JFrame(TITLE);
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(MIN_DIMENSION);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(new MenuBar());
        mainFrame.add(new StatusBar(), BorderLayout.SOUTH);
        mainFrame.add(new HomeScreen(), BorderLayout.CENTER);
        
        
        mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new JavaSharp();
    }
}
