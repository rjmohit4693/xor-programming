package com.javasharp.model;

import java.util.List;
import sun.security.util.Length;

public class Score
    implements Instructable
{
    
    private String       title;
    private MetaData     meta;
    private ScoreContext scoreContext;
    private List<Parts>  parts;
    
    
    public Score()
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
