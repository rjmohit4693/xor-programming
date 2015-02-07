package com.javasharp.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar
{
    private static final long serialVersionUID = 1L;

    public MenuBar() {
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('f');
        fileMenu.add(setUpMenuItem("New", "control N", 'n'));
        fileMenu.add(setUpMenuItem("Open", "control O", 'o'));
        fileMenu.add(setUpMenuItem("Save", "control S", 's'));
        fileMenu.add(setUpMenuItem("Export", "control E", 'e'));
        fileMenu.addSeparator();
        fileMenu.add(setUpMenuItem("Print", "control P", 'p'));
        fileMenu.addSeparator();
        fileMenu.add(setUpMenuItem("Exit", "", 'x'));
        this.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('h');
        helpMenu.add(setUpMenuItem("Help Menu", "F1", 'h'));
        helpMenu.add(setUpMenuItem("About", null, 'a'));
        this.add(helpMenu);
    }
    
    private JMenuItem setUpMenuItem(String name, String keystroke, char mnemonic) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keystroke));
        menuItem.setMnemonic(mnemonic);
        //TODO actionlistener
        return menuItem;
    }
}
