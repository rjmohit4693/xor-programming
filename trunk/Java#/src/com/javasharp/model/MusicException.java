package com.javasharp.model;

public class MusicException
    extends Exception
{
    private static final long serialVersionUID = -5299773264301916956L;
    
    
    public MusicException(String message)
    {
        super(message);
    }
    
    
    public MusicException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
