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
import android.os.Looper;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.xorprogramming.thread.Updatable;
import com.xorprogramming.thread.UpdaterThread;
import java.util.ArrayList;
import java.util.List;

public abstract class GameView<T1 extends GameEngine<T2>, T2>
    extends SurfaceView
{
    private final Object                      lock       = this;
    private final UpdaterThread               thread     = new UpdaterThread(new Updater());
    private final Controller                  controller = new Controller();
    private final List<GameEventListener<T2>> listeners  = new ArrayList<GameEventListener<T2>>();
    private T1                                engine;
    private GameRenderer<T1>                  renderer;
    private boolean                           initialized;
    private int                               width;
    private int                               height;


    public GameView(Context context)
    {
        super(context);
        init();
    }


    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }


    public GameView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init()
    {
        getHolder().addCallback(new Callback());
    }


    protected final void initialize(T1 gameEngine, GameRenderer<T1> gameRenderer)
    {
        if (gameEngine == null)
        {
            initialized = false;
            throw new NullPointerException("The GameEngine must be non-null");
        }
        else if (gameRenderer == null)
        {
            initialized = false;
            throw new NullPointerException("The GameRenderer must be non-null");
        }
        synchronized (lock)
        {
            engine = gameEngine;
            renderer = gameRenderer;
        }
        initialized = true;
    }


    private void checkInitialization()
    {
        if (!initialized)
        {
            throw new IllegalStateException("The GameEngine and GameRenderer have not been initialized");
        }
    }


    public final void setTargetUPS(float targetUPS)
    {
        thread.setTargetUPS(targetUPS);
    }


    protected final GameEventController<T2> getController()
    {
        return controller;
    }


    protected final T1 getGameEngine()
    {
        return engine;
    }


    public final boolean start()
    {
        checkInitialization();
        return thread.start();
    }


    public final boolean stop()
    {
        checkInitialization();
        return thread.stop(isOnUiThread());
    }


    public final void addListener(GameEventListener<T2> listener)
    {
        listeners.add(listener);
    }


    public final void removeListener(GameEventListener<T2> listener)
    {
        listeners.remove(listener);
    }


    @Override
    protected final void onDraw(Canvas canvas)
    {
        render(canvas);
    }


    private void render(Canvas c)
    {
        synchronized (lock)
        {
            try
            {
                renderer.render(engine, c, width, height);

            }
            catch (RenderException ex)
            {
                controller.notifyRenderException(ex);
            }
        }
    }


    private boolean isOnUiThread()
    {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    private class Callback
        implements SurfaceHolder.Callback
    {

        @Override
        public void surfaceCreated(SurfaceHolder holder)
        {
            synchronized (lock)
            {
                try
                {
                    renderer.initialize(getResources());
                }
                catch (RenderException ex)
                {
                    controller.notifyRenderException(ex);
                }
            }
        }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int newWidth, int newHeight)
        {
            synchronized (lock)
            {
                width = newWidth;
                height = newHeight;
                try
                {
                    renderer.onSizeChange(getResources(), newWidth, newHeight);
                }
                catch (RenderException ex)
                {
                    controller.notifyRenderException(ex);
                }
            }
        }


        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        {
            synchronized (lock)
            {
                try
                {
                    renderer.dispose();
                }
                catch (RenderException ex)
                {
                    controller.notifyRenderException(ex);
                }
            }
        }

    }


    private class Controller
        implements GameEventController<T2>
    {
        @Override
        public final void notifyGameEvent(T2 t)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onGameEvent(t, engine);
            }
        }


        @Override
        public void notifyGameOver(boolean won)
        {
            // TODO Auto-generated method stub

        }


        @Override
        public void stopGame()
        {

        }


        public void notifyRenderException(RenderException ex)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onRenderException(ex);
            }
        }


        private void blockAndRunOnUI(Runnable r)
        {
            if (isOnUiThread())
            {
                r.run();
            }
            else
            {
                // TODO wait
            }
        }


        private abstract class SyncRunner implements Runnable
        {
            private boolean done;

            @Override
            public void run()
            {
                for (int i = 0; i < listeners.size(); i++)
                {
                    runListener(listeners.get(i));
                }
                synchronized (this)
                {
                    done = true;
                    notifyAll();
                }
            }


            public void waitToComplete()
            {
                getHandler().post(r)
            }


            protected abstract void runListener(GameEventListener<T2> listener);
        }
    }


    private class Updater
        implements Updatable
    {

        @Override
        public void update(float deltaT)
        {
            SurfaceHolder holder = getHolder();
            Canvas c = null;
            try
            {
                c = holder.lockCanvas();
                if (c != null)
                {
                    synchronized (lock)
                    {
                        if (engine.update(deltaT, controller))
                        {
                            render(c);
                        }
                    }
                }
            }
            finally
            {
                if (c != null)
                {
                    holder.unlockCanvasAndPost(c);
                }
            }
        }

    }
}
