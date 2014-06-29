package com.xorprogramming.game;

public class RenderException
    extends Exception
{
    private static final long serialVersionUID = -7540060817781366221L;


    public RenderException(String message)
    {
        super(message);
    }


    public RenderException(Throwable t)
    {
        super(t);
    }


    public RenderException(String message, Throwable t)
    {
        super(message, t);
    }
}
