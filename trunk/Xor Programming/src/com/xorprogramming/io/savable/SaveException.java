package com.xorprogramming.io.savable;

public class SaveException
    extends Exception
{
    public SaveException(String detailMessage)
    {
        super(detailMessage);
    }


    public SaveException(Throwable throwable)
    {
        super(throwable);
    }


    public SaveException(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
    }
}
