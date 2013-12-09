package com.xorprogramming.game;

import android.app.Activity;
import android.view.View;

public class GameActivity<T extends GameView<?, ?>>
    extends Activity
{
    private T gameView;


    public void setGameView(T gameView)
    {
        if (gameView == null)
        {
            throw new NullPointerException("The game view must be non-null");
        }
        this.gameView = gameView;
    }


    @SuppressWarnings("unchecked")
    public void setGameView(int gameViewRes)
    {
        View v = findViewById(gameViewRes);
        if (v == null)
        {
            throw new IllegalArgumentException("Invalid resource id");
        }
        try
        {
            gameView = (T)v;
        }
        catch (ClassCastException ex)
        {
            throw new IllegalArgumentException("Invalid resource id");
        }
    }


    public T getGameView()
    {
        if (gameView == null)
        {
            throw new IllegalStateException("The game view has not been set yet");
        }
        return gameView;
    }

}
