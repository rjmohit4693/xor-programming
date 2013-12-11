package com.xorprogramming.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;
import java.util.ArrayList;
import java.util.List;

public abstract class GameView<T1 extends GameEngine<T2>, T2>
    extends android.view.View
    implements GameController<T2>
{
    private static final float     DEFAULT_UPS    = 60;
    private static final float     NANOS_PER_SEC  = 1e9f;
    private static final float     MILLIS_PER_SEC = 1e3f;
    
    private T1                     gameEngine;
    private GameRenderer<T1>       gameRenderer;
    private List<GameListener<T2>> listeners;
    
    private Thread                 thread;
    private long                   prevTime;
    private volatile boolean       done;
    private volatile float         targetUPS;
    
    
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
        listeners = new ArrayList<GameListener<T2>>();
        done = true;
        targetUPS = DEFAULT_UPS;
    }
    
    
    public void initializeComponents(T1 engine, GameRenderer<T1> renderer)
    {
        if (gameEngine == null)
        {
            throw new NullPointerException(Logger.log(LoggingType.ERROR, "The gameEngine must be non-null"));
        }
        else if (gameRenderer == null)
        {
            throw new NullPointerException(Logger.log(LoggingType.ERROR, "The gameRenderer must be non-null"));
        }
        this.gameEngine = engine;
        this.gameRenderer = renderer;
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
            throw new IllegalStateException(Logger.log(
                LoggingType.ERROR,
                "The gameEngine and gameRenderer must be initialized before the view is started"));
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
            Logger.log(LoggingType.WARNING, "Unable to start rendering game view");
            return false;
        }
    }
    
    
    public final boolean stopRendering()
    {
        if (done)
        {
            Logger.log(LoggingType.WARNING, "Unable to stop rendering game view");
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
            throw new IllegalStateException(Logger.log(
                LoggingType.ERROR,
                "The controller cannot dispose the gameRenderer while running"));
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
