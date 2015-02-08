package com.javasharp.model;

import javax.sound.midi.Instrument;

public class Part
    extends GroupInstructable<ScoreContext, PartContext>
{
    private final PartContext partContext;
    
    
    public Part(Instrument instrument, int channel, ScoreContext scoreContext)
    {
        partContext = new PartContext(scoreContext, instrument, channel);
    }
    
    
    @Override
    public void instruct(ScoreContext context)
    {
        super.instructChildren(partContext);
    }
    
    
    @Override
    public int getLength()
    {
        return super.getChildLengthSum();
    }
    
    
    @Override
    public void onTimeSignatureChanged(TimeSignature timeSignature)
    {
        // TODO Auto-generated method stub
        
    }
    
}
