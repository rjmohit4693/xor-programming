package com.xorprogramming.jcolor;

import com.xorprogramming.thread.Updatable;
import com.xorprogramming.thread.UpdaterThread;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JColor
    extends JFrame
{
    private static final String    NAME        = "JColor";
    private static final Dimension MIN_SIZE    = new Dimension(200, 200);
    private static final String    NO_NAME     = "None";
    private static final String    NAME_FORMAT = "Name: %s";
    private static final String    RGB_FORMAT  = "RGB: %d, %d, %d";
    private static final String    HEX_FORMAT  = "Hex: %02X%02X%02X";
    private static final int       UPS         = 10;

    private final Robot            robo;

    private final JLabel           nameLabel;
    private final JLabel           rgbLabel;
    private final JLabel           hexLabel;
    private final JComponent       colorPanel;


    public static void main(String[] args)
    {
        try
        {
            new JColor(new Robot());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Unable to get pixel colors", NAME, JOptionPane.ERROR_MESSAGE);
        }
    }


    private JColor(Robot robo)
    {
        super(NAME);

        this.robo = robo;
        nameLabel = new JLabel();
        rgbLabel = new JLabel();
        hexLabel = new JLabel();
        colorPanel = new JPanel();
        final UpdaterThread thread = new UpdaterThread(new Updatable()
        {
            private final Runnable updateRunnable = new Runnable()
                                                  {
                                                      @Override
                                                      public void run()
                                                      {
                                                          JColor.this.update();
                                                      }
                                                  };


            @Override
            public void update(float deltaT)
            {
                try
                {
                    EventQueue.invokeAndWait(updateRunnable);
                }
                catch (Exception ex)
                {
                    // This cannot happen
                }
            }
        }, UPS);

        setSize(MIN_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setUpGUI();
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowIconified(WindowEvent e)
            {
                thread.stop(false);
            }


            @Override
            public void windowDeiconified(WindowEvent e)
            {
                thread.start();
            }


            @Override
            public void windowClosing(WindowEvent e)
            {
                thread.stop(true);
            }
        });
        setVisible(true);
        thread.start();
    }


    private void setUpGUI()
    {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        panel.add(colorPanel);
        panel.add(nameLabel);
        panel.add(rgbLabel);
        panel.add(hexLabel);
        add(panel);
    }


    private void update()
    {
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        Color curColor = robo.getPixelColor(mouseLoc.x, mouseLoc.y);
        NamedColor namedColor = NamedColor.getMatch(curColor);
        nameLabel.setText(String.format(NAME_FORMAT, namedColor == null ? NO_NAME : namedColor));
        rgbLabel.setText(String.format(RGB_FORMAT, curColor.getRed(), curColor.getGreen(), curColor.getBlue()));
        hexLabel.setText(String.format(HEX_FORMAT, curColor.getRed(), curColor.getGreen(), curColor.getBlue()));
        colorPanel.setBackground(curColor);
    }

}
