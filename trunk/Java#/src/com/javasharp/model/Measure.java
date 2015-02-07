package com.javasharp.model;

import java.util.ArrayList;
import java.util.Collection;

public class Measure
    implements Instructable<PartContext>
{
    
    private Collection<Instructable> notes;
    
    
    public Measure()
    {
        notes = new ArrayList<Instructable>();
    }
    
    
    @Override
    public void instruct(PartContext context)
    {
        // TODO Auto-generated method stub
        
    }
    
    
    @Override
    public int getLength()
    {
        int totalLength = 0;
        for (Instructable inst : notes)
        {
            totalLength += inst.getLength();
        }
        return totalLength;
    }
    
    
    @Override
    public void onTimeSignatureChanged()
    {
        // TODO Auto-generated method stub
        
    }
    
}
