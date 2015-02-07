package com.javasharp.model;

import java.util.ArrayList;
import java.util.List;

public class Score
    implements Instructable<ScoreContext>
{
    
    private String       title;
    private Metadata     meta;
    private ScoreContext scoreContext;
    private List<Part>   parts;
    
    
    public Score()
    {
        title = "Untitled";
        meta = new Metadata();
        scoreContext = new ScoreContext();
        parts = new ArrayList<Part>();
    }
    
    
    @Override
    public void instruct(ScoreContext context)
    {
        // TODO
    }
    
    
    @Override
    public int getLength()
    {
        int totalLength = 0;
        for (Part part : parts)
        {
            totalLength += part.getLength();
        }
        return totalLength;
    }
    
    
    @Override
    public void onTimeSignatureChanged()
    {
        // TODO Auto-generated method stub
        
    }
    
}
