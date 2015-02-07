package com.javasharp.model;

public class Rest
    extends LengthedInstructable
{
    private Length length;
    
    public Rest(Length length, int numDots)
    {
        super(length, numDots);
        this.length = length;
    }
    
    
    @Override
    public void instruct(PartContext context)
    {
        //Do nothing
    }
    
}
