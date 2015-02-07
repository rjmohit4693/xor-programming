package com.javasharp.model;

import java.util.List;
import javax.sound.midi.Instrument;
import sun.security.util.Length;

public class Part
    implements Instructable<PartContext>
{
    
    private Instrument         instrument;
    private Clef               clef;
    private PartContext        partContext;
    private List<Instructable> measures;
    
    
    public Part()
    {
        
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
        for(Instructable inst : measures) {
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
