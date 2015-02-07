package com.javasharp.model;


public class Note
    extends LengthedInstructable
{
    
    private StaffPosition staffPos;
    private Articulation  articulation;
    private Accidental    accidental;
    
    
    public Note(Length length, int numDots)
    {
        super(length, numDots);
        
    }
    
    
    @Override
    public void instruct(PartContext context)
    {
        // TODO Auto-generated method stub
        
    }
    
}
