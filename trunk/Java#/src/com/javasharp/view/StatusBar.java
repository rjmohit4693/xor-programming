package com.javasharp.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class StatusBar
    extends JTextField
{
    private static final long serialVersionUID = 1L;
    
    
    public StatusBar()
    {
        setEditable(false);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setFont(new Font(null, Font.BOLD, 16));
    }
    
    
    public void postError(String s)
    {
        Toolkit.getDefaultToolkit().beep();
        setForeground(Color.RED);
        setText(s);
    }
    
    
    public void postMessage(String s)
    {
        setForeground(Color.BLACK);
        setText(s);
    }
    
    
    public void clear()
    {
        setText("");
    }
    
}
