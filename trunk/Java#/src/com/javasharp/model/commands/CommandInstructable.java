package com.javasharp.model.commands;

import com.javasharp.model.Instructable;

public abstract class CommandInstructable<T>
    implements Instructable<T>
{
    @Override
    public int getLength()
    {
        return 0;
    }
    
    
    @Override
    public void onTimeSignatureChanged()
    {
        // Do nothing
    }
}
