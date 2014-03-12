package com.xorprogramming.nbodysim;

import java.awt.Dimension;
import javax.swing.JFrame;

public class SimFrame
{
    private static final String TITLE = "N-Body Simulator";
    private static final Dimension MIN_SIZE = new Dimension(800, 600);

    private final JFrame frame;

    public static void main(String[] args)
    {
        new SimFrame();
    }

    private SimFrame()
    {
        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(MIN_SIZE);
    }

}
