package com.javasharp.model;

public class MusicException extends Exception
{
    public MusicException(String message)
    {
        super(message);
    }

    public MusicException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
