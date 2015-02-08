package com.javasharp.controller;

import com.javasharp.view.JavaSharp;

public final class MenuController
{
    
    private JavaSharp view;
    
    
    public MenuController(JavaSharp view)
    {
        this.view = view;
    }
    
    
    public void newMenuItem()
    {
        
    }
    
    
    public void openMenuItem()
    {
        
    }
    
    
    public void saveMenuItem()
    {
        
    }
    
    
    public void exportMenuItem()
    {
        
    }
    
    
    public void printMenuItem()
    {
        
    }
    
    
    public void exitMenuItem()
    {
        // TODO add unsaved work prompt
        System.exit(0);
    }
    
    
    public void showHelpMenuItem()
    {
        view.showHelpDialog();
    }
    
    
    public void aboutMenuItem()
    {
        view.showAboutDialog();
    }
    
}
