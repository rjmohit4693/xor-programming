/*-
Copyright 2014 Xor Programming

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.xorprogramming.game;

import android.app.Activity;
import android.view.View;

// -------------------------------------------------------------------------
/**
 * An {@link Activity} that simply holds a {@link GameView}. Subclasses should manage starting and stopping the
 * {@link GameView}, which is often done by responding to {@link Activity} lifecycle events.
 *
 * @see SimpleRenderingActivity
 * @param <T>
 *            The type of {@link GameView} that the {@link Activity} uses
 * @author Steven Roberts
 * @version 1.0.0
 */
public class GameActivity<T extends GameView<?, ?>>
    extends Activity
{
    private T gameView;
    
    
    // ----------------------------------------------------------
    /**
     * Sets the {@link GameView}. This method should be called from {@link #onCreate(android.os.Bundle)}.
     * 
     * @param gameView
     *            The new {@link GameView}
     * @throws NullPointerException
     *             if the view is null
     */
    public void setGameView(T gameView)
    {
        if (gameView == null)
        {
            throw new NullPointerException("The game view must be non-null");
        }
        this.gameView = gameView;
    }
    
    
    /**
     * Sets the {@link GameView}. This method should be called from {@link #onCreate(android.os.Bundle)}.
     * 
     * @param gameViewRes
     *            The id of the new {@link GameView}.
     * @throws IllegalArgumentException
     *             if the id does not correspond to a {@link GameView}
     */
    public void setGameView(int gameViewRes)
    {
        View v = findViewById(gameViewRes);
        if (v == null)
        {
            throw new IllegalArgumentException("Invalid game view id: " + gameViewRes);
        }
        try
        {
            @SuppressWarnings("unchecked")
            T gv = (T)v;
            
            gameView = gv;
        }
        catch (ClassCastException ex)
        {
            throw new IllegalArgumentException("Invalid gave view: cannot cast from " + v.getClass().getSimpleName());
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the current {@link GameView}
     * 
     * @return The {@link GameView}
     * @throws IllegalStateException
     *             if the {@link GameView} was not previously set
     */
    public T getGameView()
    {
        if (gameView == null)
        {
            throw new IllegalStateException(
                "The game view has not been set.  Ensure setGameView was called in onCreate");
        }
        return gameView;
    }
}
