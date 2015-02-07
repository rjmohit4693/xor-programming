package com.javasharp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import com.javasharp.controller.MenuController;

public class JavaSharp
{
    private final JFrame mainFrame;
    private MenuController menuController;
    
    private static final String TITLE = "Java #";
    private static final Dimension MIN_DIMENSION = new Dimension(600, 400);
    
    private JavaSharp() {
        mainFrame = new JFrame(TITLE);
        menuController = new MenuController();
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(MIN_DIMENSION);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(new MenuBar(menuController));
        mainFrame.add(new StatusBar(), BorderLayout.SOUTH);
        mainFrame.add(new HomeScreen(), BorderLayout.CENTER);
        
        
        
        
        mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new JavaSharp();
    }
}
