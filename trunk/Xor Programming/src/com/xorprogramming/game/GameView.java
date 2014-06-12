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
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public abstract class GameView<T1 extends GameEngine<T2>, T2>
    extends View
{
    private static final float                DEFAULT_UPS    = 60;
    private static final float                NANOS_PER_SEC  = 1e9f;
    private static final float                MILLIS_PER_SEC = 1e3f;

    private final Controller                  controller;
    private final List<GameEventListener<T2>> listeners;
    private T1                                engine;
    private GameRenderer<T1>                  renderer;

    private Thread                            thread;
    private long                              prevTime;
    private volatile boolean                  done;
    private volatile float                    targetUPS;


    public GameView(Context context)
    {
        super(context);
        controller = new Controller();
        listeners = new ArrayList<GameEventListener<T2>>();
        init();
    }


    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        controller = new Controller();
        listeners = new ArrayList<GameEventListener<T2>>();
        init();
    }


    public GameView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        controller = new Controller();
        listeners = new ArrayList<GameEventListener<T2>>();
        init();
    }


    public void initialize(T1 gameEngine, GameRenderer<T1> gameRenderer)
    {
        if (gameEngine == null)
        {
            throw new NullPointerException("The GameEngine must be non-null");
        }
        else if (gameRenderer == null)
        {
            throw new NullPointerException("The GameRenderer must be non-null");
        }

        engine = gameEngine;
        renderer = gameRenderer;
    }


    private void init()
    {
        done = true;
        targetUPS = DEFAULT_UPS;
    }


    private void checkInitialization()
    {
        if (renderer == null)
        {
            throw new IllegalStateException("The GameRenderer has not been initialized");
        }
        else if (engine == null)
        {
            throw new IllegalStateException("The GameEngine has not been initialized");
        }
    }


    public final void setTargetUPS(float targetUPS)
    {
        if (Float.isInfinite(targetUPS) || Float.isNaN(targetUPS) || targetUPS <= 0)
        {
            throw new IllegalArgumentException("The target UPS must be a number greater than 0");
        }
        else
        {
            this.targetUPS = targetUPS;
        }
    }


    @Override
    protected final void onDraw(Canvas c)
    {
        if (!done && engine != null && renderer != null)
        {
            long now = System.nanoTime();
            if (prevTime == -1)
            {
                engine.update(1 / targetUPS, controller);
            }
            else
            {
                engine.update((now - prevTime) / NANOS_PER_SEC, controller);
            }

            if (!done)
            {
                renderer.render(engine, c, getWidth(), getHeight(), controller);
                prevTime = now;
            }
        }
    }


    public final boolean startRendering()
    {
        checkInitialization();
        if (done)
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
        checkInitialization();
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
        checkInitialization();
        renderer.initialize(getResources(), controller);
    }


    public final void disposeRenderer()
    {
        checkInitialization();
        if (done)
        {
            renderer.dispose(controller);
        }
        else
        {
            throw new IllegalStateException("The GameRenderer cannot be disposed while running");
        }
    }


    public final void addListener(GameEventListener<T2> listener)
    {
        listeners.add(listener);
    }


    public final void removeListener(GameEventListener<T2> listener)
    {
        listeners.remove(listener);
    }


    private class Controller
        implements GameEventController<T2>
    {
        @Override
        public final void notifyListeners(T2 t)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onGameEvent(t, engine);
            }
        }


        @Override
        public void notifyListeners(Exception ex)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onGameException(ex);
            }
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
