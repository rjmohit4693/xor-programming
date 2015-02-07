package com.javasharp.model;

public abstract class LengthedInstructable<T> implements Instructable<T>
{
    private final Length length;
    private final int numDots;
    
    public LengthedInstructable(Length length, int numDots) {
        this.length = length;
        this.numDots = numDots;
    }
    
    public final int getLength()
    {
        return length.getLength(numDots);
    }
    
    @Override
    public final void onTimeSignatureChanged()
    {
        //Do nothing
    }
    
}
