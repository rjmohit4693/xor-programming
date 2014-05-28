/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

public abstract class GameView<T1 extends GameEngine<T2>, T2>
    extends android.view.View
    implements GameController<T2>
{
    private static final float     DEFAULT_UPS    = 60;
    private static final float     NANOS_PER_SEC  = 1e9f;
    private static final float     MILLIS_PER_SEC = 1e3f;

    private final T1                     gameEngine;
    private final GameRenderer<T1>       gameRenderer;
    private final List<GameListener<T2>> listeners;

    private Thread                 thread;
    private long                   prevTime;
    private volatile boolean       done;
    private volatile float         targetUPS;


    public GameView(Context context, T1 gameEngine, GameRenderer<T1> gameRenderer)
    {
        super(context);
        this.gameEngine = gameEngine;
        this.gameRenderer = gameRenderer;
        init();
        listeners = new ArrayList<GameListener<T2>>();
    }


    public GameView(Context context, AttributeSet attrs, T1 gameEngine, GameRenderer<T1> gameRenderer)
    {
        this(context, attrs, 0, gameEngine, gameRenderer);
    }


    public GameView(Context context, AttributeSet attrs, int defStyleAttr, T1 gameEngine, GameRenderer<T1> gameRenderer)
    {
        super(context, attrs, defStyleAttr);
        this.gameEngine = gameEngine;
        this.gameRenderer = gameRenderer;
        init();
        listeners = new ArrayList<GameListener<T2>>();
    }


    private void init()
    {
        if (gameEngine == null)
        {
            throw new NullPointerException("The GameEngine must be non-null");
        }
        else if (gameRenderer == null)
        {
            throw new NullPointerException("The GameRenderer must be non-null");
        }
        done = true;
        targetUPS = DEFAULT_UPS;
    }


    public final void setTargetUPS(float targetUPS)
    {
        this.targetUPS = targetUPS;
    }


    @Override
    protected final void onDraw(Canvas c)
    {
        if (!done && gameEngine != null && gameRenderer != null)
        {
            long now = System.nanoTime();
            if (prevTime == -1)
            {
                gameEngine.update(1 / targetUPS, this);
            }
            else
            {
                gameEngine.update((now - prevTime) / NANOS_PER_SEC, this);
            }

            if (!done)
            {
                gameRenderer.render(gameEngine, c, getWidth(), getHeight());
                prevTime = now;
            }
        }
    }


    public final boolean startRendering()
    {
        if (gameEngine == null || gameRenderer == null)
        {
            throw new IllegalStateException(
                "The GameEngine and GameRenderer must be initialized before rendering is started");
        }
        else if (done)
        {
            prevTime = -1;
            done = false;
            thread = new InnerThread();
            thread.start();
            return true;
        }
        else
        {
            return false;
        }
    }


    public final boolean stopRendering()
    {
        if (done)
        {
            return false;
        }
        else
        {
            done = true;
            thread.interrupt();
            thread = null;
            return true;
        }
    }


    public final void initializeRenderer()
    {
        gameRenderer.initialize(getResources());
    }


    public final void disposeRenderer()
    {
        if (done)
        {
            gameRenderer.dispose();
        }
        else
        {
            throw new IllegalStateException("The GameRenderer cannot be disposed while running");
        }
    }


    public final void addListener(GameListener<T2> listener)
    {
        listeners.add(listener);
    }


    public final void removeListener(GameListener<T2> listener)
    {
        listeners.remove(listener);
    }


    public final void updateListeners(T2 t)
    {
        for (int i = 0; i < listeners.size(); i++)
        {
            listeners.get(i).onGameEvent(t, gameEngine);
        }
    }


    private class InnerThread
        extends Thread
    {
        @Override
        public void run()
        {
            while (!done)
            {
                postInvalidate();
                try
                {
                    Thread.sleep((long)(MILLIS_PER_SEC / targetUPS));
                }
                catch (InterruptedException e)
                {
                    return;
                }
            }
        }
    }
}
