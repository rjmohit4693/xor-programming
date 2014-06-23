/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

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
            throw new IllegalArgumentException("Invalid game view id: " + gameViewRes);
        }
        try
        {
            gameView = (T)v;
        }
        catch (ClassCastException ex)
        {
            throw new IllegalArgumentException("Invalid gave view: cannot cast from " + v.getClass().getSimpleName());
        }
    }
    
    
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
