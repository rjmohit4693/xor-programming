package com.javasharp.view;

import com.javasharp.controller.MenuController;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar
    extends JMenuBar
{
    private static final long serialVersionUID = 1L;
    
    
    public MenuBar(MenuController controller)
    {
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('f');
        fileMenu.add(setUpMenuItem("New", "control N", 'n', controller::newMenuItem));
        fileMenu.add(setUpMenuItem("Open", "control O", 'o', controller::openMenuItem));
        fileMenu.add(setUpMenuItem("Save", "control S", 's', controller::saveMenuItem));
        fileMenu.add(setUpMenuItem("Export", "control E", 'e', controller::exportMenuItem));
        fileMenu.addSeparator();
        fileMenu.add(setUpMenuItem("Print", "control P", 'p', controller::printMenuItem));
        fileMenu.addSeparator();
        fileMenu.add(setUpMenuItem("Exit", null, 'x', controller::exitMenuItem));
        this.add(fileMenu);
        
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        helpMenu.add(setUpMenuItem("Show Help", "F1", 'h', controller::showHelpMenuItem));
        helpMenu.add(setUpMenuItem("About", null, 'a', controller::aboutMenuItem));
        this.add(helpMenu);
    }
    
    
    private JMenuItem setUpMenuItem(String name, String keystroke, char mnemonic, final Runnable listener)
    {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keystroke));
        menuItem.setMnemonic(mnemonic);
        menuItem.addActionListener(ae -> {
            listener.run();
        });
        return menuItem;
    }
}
