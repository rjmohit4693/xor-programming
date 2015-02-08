package com.javasharp.model.commands;

import com.javasharp.model.Instructable;
import com.javasharp.model.TimeSignature;

public abstract class CommandInstructable<T>
    implements Instructable<T>
{
    @Override
    public final int getLength()
    {
        return 0;
    }
    
    
    @Override
    public final void onTimeSignatureChanged(TimeSignature timeSignature)
    {
        // Do nothing
    }
}
