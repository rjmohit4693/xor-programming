package com.xorprogramming.game;

public class SimpleRenderingActivity<T extends GameView<?, ?>>
    extends GameActivity<T>
{
    private boolean toBeResumed;
    private boolean lostFocus;
    
    
    @Override
    protected void onStart()
    {
        super.onStart();
        getGameView().initializeRenderer();
    }
    
    
    @Override
    protected void onResume()
    {
        super.onResume();
        toBeResumed = lostFocus;
        if (!lostFocus)
        {
            onActualResume();
        }
    }
    
    
    @Override
    protected void onPause()
    {
        super.onPause();
        lostFocus = true;
        getGameView().stopRendering();
    }
    
    
    @Override
    protected void onStop()
    {
        super.onStop();
        getGameView().disposeRenderer();
    }
    
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        lostFocus = !hasFocus;
        if (toBeResumed && hasFocus)
        {
            toBeResumed = false;
            onActualResume();
        }
    }
    
    
    public void onActualResume()
    {
        getGameView().startRendering();
    }
}
