package com.javasharp.model;

public class Score
    extends GroupInstructable<ScoreContext, ScoreContext>
{
    private static final String DEFAULT_TITLE = "unnamed";
    
    
    private String              title;
    private final Metadata      meta;
    private final ScoreContext  scoreContext;


    public Score()
    {
        this.title = DEFAULT_TITLE;
        meta = new Metadata();
        scoreContext = new ScoreContext();
    }
    
    
    public void setTitle(String newTitle)
    {
        String trimmed;
        if (newTitle == null || (trimmed = newTitle.trim()).isEmpty())
        {
            title = DEFAULT_TITLE;
        }
        else
        {
            title = trimmed;
        }
    }


    @Override
    public void instruct(ScoreContext context)
    {
        super.instructChildren(context);
    }
    
    
    @Override
    public int getLength()
    {
        // TODO
        return 0;
    }
    
    
    @Override
    public void onTimeSignatureChanged(TimeSignature timeSignature)
    {
        super.notifyChildrenTimeSignatureChanged(timeSignature);
    }
    
}
