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

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * <p>
 * A {@link View} that manages the rendering and updating of a game. Note that this class is designed for inheritance.
 * Subclasses should perform initialization and handle input events.
 * </p>
 * <p>
 * Lifecycle:
 * <ol>
 * <li>{@link #initialize(GameEngine, GameRenderer)}</li>
 * <li>{@link #initializeRenderer()}</li>
 * <li>{@link #startRendering()}</li>
 * <li>{@link #stopRendering()}</li>
 * <li>{@link #disposeRenderer()}</li>
 * </ol>
 * </p>
 *
 * @param <T1>
 *            The type of {@link GameEngine}
 * @param <T2>
 *            The type of result from a game event
 * @author Steven Roberts
 * @version 1.0.0
 * @see GameEngine
 * @see GameRenderer
 * @see GameActivity
 */
public abstract class GameView<T1 extends GameEngine<T2>, T2>
    extends View
{
    private static final float                    DEFAULT_UPS = 60;
    
    private final Runnable                        updater     = new Updater();
    private final Controller                      controller  = new Controller();
    private final List<GameEventListener<T1, T2>> listeners   = new ArrayList<GameEventListener<T1, T2>>();
    private T1                                    engine;
    private GameRenderer<T1>                      renderer;
    
    private long                                  prevTime;
    private boolean                               done;
    private float                                 targetUPS;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new GameView object.
     *
     * @param context
     */
    public GameView(Context context)
    {
        super(context);
        init();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new GameView object.
     *
     * @param context
     * @param attrs
     */
    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new GameView object.
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public GameView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    
    private void init()
    {
        done = true;
        targetUPS = DEFAULT_UPS;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Sets the {@link GameEngine} and {@link GameRenderer}. Classes that extend {@link GameView} should call this
     * method in the constructor.
     *
     * @param gameEngine
     *            The {@link GameEngine}
     * @param gameRenderer
     *            The {@link GameRenderer}
     * @throws NullPointerException
     *             if any parameter is null
     */
    public final void initialize(T1 gameEngine, GameRenderer<T1> gameRenderer)
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
    
    
    private void checkInitialization()
    {
        if (engine == null || renderer == null)
        {
            throw new IllegalStateException("The GameEngine and GameRenderer have not been initialized");
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param targetUPS
     *            The number of updates per seconds for the view. For best performance, this value should be a divisor
     *            of 60.
     * @throws IllegalArgumentException
     *             if the {@code targetUPS} is non-positive
     */
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
        if (!isInEditMode() && !done)
        {
            try
            {
                renderer.render(engine, c, getWidth(), getHeight());
            }
            catch (RenderException ex)
            {
                controller.notifyRenderException(ex);
            }
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the {@link GameEventController} responsible for notifying {@link GameEventListener}s of game events
     *
     * @return The {@link GameEventController}
     */
    public final GameEventController<T2> getController()
    {
        return controller;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the {@link GameEngine}
     *
     * @return The {@link GameEngine} or null if not yet initialized
     */
    public final T1 getGameEngine()
    {
        return engine;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Starts the game and begins rendering.
     *
     * @return true if started, false if already started
     * @throws IllegalStateException
     *             if {@link #initialize(GameEngine, GameRenderer)} has not been called
     */
    public final boolean startRendering()
    {
        checkInitialization();
        if (done)
        {
            prevTime = -1;
            done = false;
            post(updater);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Stops the game and rendering.
     *
     * @return true if stopped, false if already stopped
     * @throws IllegalStateException
     *             if {@link #initialize(GameEngine, GameRenderer)} has not been called
     */
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
            removeCallbacks(updater);
            return true;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Initializes the {@link GameRenderer}.
     *
     * @throws IllegalStateException
     *             if {@link #initialize(GameEngine, GameRenderer)} has not been called
     */
    public final void initializeRenderer()
    {
        checkInitialization();
        try
        {
            renderer.initialize(getResources());
        }
        catch (RenderException ex)
        {
            controller.notifyRenderException(ex);
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Disposes the {@link GameRenderer}.
     *
     * @throws IllegalStateException
     *             if {@link #initialize(GameEngine, GameRenderer)} has not been called
     */
    public final void disposeRenderer()
    {
        checkInitialization();
        if (done)
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
        else
        {
            throw new IllegalStateException("The GameRenderer cannot be disposed while running");
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Adds a {@link GameEventListener} to listen to events triggered by the {@link GameEngine}
     *
     * @param listener
     *            The {@link GameEventListener} to add
     */
    public final void addGameEventListener(GameEventListener<T1, T2> listener)
    {
        listeners.add(listener);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Removes a {@link GameEventListener} if it has already been added
     *
     * @param listener
     *            The {@link GameEventListener} to remove
     */
    public final void removeGameEventListener(GameEventListener<T1, T2> listener)
    {
        listeners.remove(listener);
    }
    
    
    private class Controller
        implements GameEventController<T2>
    {
        @Override
        public void notifyGameEvent(T2 t)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onGameEvent(t, engine);
            }
        }
        
        
        public void notifyRenderException(RenderException ex)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onRenderException(ex);
            }
        }
        
        
        @Override
        public void notiftyGameOver(boolean win)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).onGameOver(win);
            }
        }
    }
    
    
    private class Updater
        implements Runnable
    {
        private static final float NANOS_PER_SEC  = 1e9f;
        private static final float MILLIS_PER_SEC = 1e3f;
        
        
        @Override
        public void run()
        {
            if (done)
            {
                return;
            }
            
            postDelayed(this, (long)(MILLIS_PER_SEC / targetUPS));
            
            long now = System.nanoTime();
            float deltaT = prevTime == -1 ? 0 : (now - prevTime) / NANOS_PER_SEC;
            boolean dirty = engine.update(deltaT, controller);
            
            if (dirty)
            {
                invalidate();
                prevTime = now;
            }
        }
    }
}
