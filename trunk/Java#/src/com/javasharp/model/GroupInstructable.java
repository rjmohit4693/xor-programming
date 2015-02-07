package com.javasharp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupInstructable<T, C>
    implements Instructable<T>
{
    private final List<Instructable<C>> children;
    
    
    public GroupInstructable()
    {
        children = new ArrayList<>();
    }
    
    
    public void insertChild(int index, Instructable<C> instructable)
    {
        children.add(index, instructable);
    }
    
    
    public void addChild(Instructable<C> instructable)
    {
        children.add(instructable);
    }
    
    
    public void removeChild(int index)
    {
        children.remove(index);
    }
    
    
    protected final void instructChildren(C childContext)
    {
        for (Instructable<C> child : children)
        {
            child.instruct(childContext);
        }
    }
    
    
    protected final int getChildLengthSum()
    {
        int lengthSum = 0;
        for (Instructable<C> child : children)
        {
            lengthSum += child.getLength();
        }
        return lengthSum;
    }
    
    
    protected final void notifyChildrenTimeSignatureChanged(TimeSignature timeSignature)
    {
        for (Instructable<C> child : children)
        {
            child.onTimeSignatureChanged(timeSignature);
        }
    }
}
