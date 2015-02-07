package com.javasharp.model;

public class Measure
    extends GroupInstructable<PartContext, PartContext>
{
    @Override
    public void instruct(PartContext context)
    {
        super.instructChildren(context);
    }
    
    
    @Override
    public int getLength()
    {
        return super.getChildLengthSum();
    }
    
    
    @Override
    public void onTimeSignatureChanged(TimeSignature timeSignature)
    {
        super.notifyChildrenTimeSignatureChanged(timeSignature);
    }
}
