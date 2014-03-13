/*
 * Copyright (C) 2014 Xor Programming
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
