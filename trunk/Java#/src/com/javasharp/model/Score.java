package com.javasharp.model;

public class Score
    extends GroupInstructable<ScoreContext, ScoreContext>
{
    private final String       title;
    private final Metadata     meta;
    private final ScoreContext scoreContext;

    public Score()
    {
        title = "Untitled";
        meta = new Metadata();
        scoreContext = new ScoreContext();
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
    }


    @Override
    public void onTimeSignatureChanged(TimeSignature timeSignature)
    {
        super.notifyChildrenTimeSignatureChanged(timeSignature);
    }

}
