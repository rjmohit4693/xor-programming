package com.javasharp.model;

import sun.security.util.Length;

public class Note
    implements Instructable
{
    
    private StaffPosition staffPos;
    private Articulation  articulation;
    private Accidental    accidental;
    
    
    public Note()
    {
        
    }
    
    
    @Override
    public void instruct()
    {
        // TODO Auto-generated method stub
        
    }
    
    
    @Override
    public Length getLength()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    @Override
    public void onTimeSignatureChanged()
    {
        // TODO Auto-generated method stub
        
    }
    
}
