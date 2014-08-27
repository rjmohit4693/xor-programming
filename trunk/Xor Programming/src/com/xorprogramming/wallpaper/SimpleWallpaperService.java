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

package com.xorprogramming.wallpaper;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

// -------------------------------------------------------------------------
/**
 * An abstraction of the {@link WallpaperService} that uses a {@link WallpaperScene} for updating and rendering. The
 * {@link SimpleWallpaperService} is intend to be extended and have an inner class that extends {@link SimpleEngine}.
 *
 * @see SimpleEngine
 * @see WallpaperScene
 * @author Steven Roberts
 * @version 1.0.0
 */
public abstract class SimpleWallpaperService
    extends WallpaperService
{
    
    // -------------------------------------------------------------------------
    /**
     * Handles the rendering of a {@link WallpaperScene} and threading required to update it. The scene is only updated
     * when the live wallpaper can be seen by a user. Note that the {@link SimpleEngine} runs entirely on the UI thread
     * and may not be suitable for a {@link WallpaperScene} that require intensive updating or rendering.
     *
     * @param <T>
     *            The type of {@code WallpaperScene}
     * @see WallpaperScene
     * @see SimpleWallpaperService
     * @author Steven Roberts
     * @version 1.2.1
     */
    public class SimpleEngine<T extends WallpaperScene>
        extends Engine
    {
        private static final float MILLIS_PER_SEC = 1e3f;
        private static final float NANOS_PER_SEC  = 1e9f;
        private final Handler      handler        = new Handler();
        private final Runnable     updater        = new Runnable()
                                                  {
                                                      public void run()
                                                      {
                                                          update();
                                                      }
                                                  };
        private final T            scene;
        private final float        targetFrameTime;
        private long               lastTime;
        private boolean            isVisible;
        private int                width;
        private int                height;
        
        
        // ----------------------------------------------------------
        /**
         * Create a new SimpleEngine object.
         *
         * @param scene
         *            The {@link WallpaperScene} for the live wallpaper
         * @throws NullPointerException
         *             If the {@link WallpaperScene} is null
         */
        public SimpleEngine(T scene)
        {
            if (scene == null)
            {
                throw new NullPointerException("The scene must be non-null");
            }
            this.scene = scene;
            targetFrameTime = -1;
        }
        
        
        // ----------------------------------------------------------
        /**
         * Create a new SimpleEngine object.
         *
         * @param scene
         *            The {@link WallpaperScene} for the live wallpaper
         * @param targetFPS
         *            The frames per second at which the {@link WallpaperScene} will update and render
         * @throws NullPointerException
         *             If the {@link WallpaperScene} is null
         * @throws IllegalArgumentException
         *             If {@code targetUPS} is not a positive number
         */
        public SimpleEngine(T scene, float targetFPS)
        {
            if (scene == null)
            {
                throw new NullPointerException("The scene must be non-null");
            }
            else if (Float.isInfinite(targetFPS) || Float.isNaN(targetFPS) || targetFPS < 0)
            {
                throw new IllegalArgumentException("Invalid FPS: " + targetFPS);
            }
            else if (targetFPS == 0)
            {
                targetFrameTime = -1;
            }
            else
            {
                targetFrameTime = 1 / targetFPS;
            }
            this.scene = scene;
        }
        
        
        // ----------------------------------------------------------
        /**
         * Gets the width of the live wallpaper
         *
         * @return The width
         */
        protected int getWidth()
        {
            return width;
        }
        
        
        // ----------------------------------------------------------
        /**
         * Gets the height of the live wallpaper
         *
         * @return The height
         */
        protected int getHeight()
        {
            return height;
        }
        
        
        private void handleInvisibility()
        {
            isVisible = false;
            handler.removeCallbacks(updater);
            lastTime = 0;
        }
        
        
        @Override
        public void onVisibilityChanged(boolean visible)
        {
            if (visible)
            {
                isVisible = true;
                update();
            }
            else
            {
                handleInvisibility();
            }
        }
        
        
        @Override
        public void onSurfaceCreated(SurfaceHolder holder)
        {
            super.onSurfaceCreated(holder);
            scene.initialize(getApplicationContext());
        }
        
        
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int newWidth, int newHeight)
        {
            super.onSurfaceChanged(holder, format, width, height);
            scene.onSizeChange(getApplicationContext(), newWidth, newHeight);
            width = newWidth;
            height = newHeight;
            update();
        }
        
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder)
        {
            super.onSurfaceDestroyed(holder);
            handleInvisibility();
            scene.dispose();
        }
        
        
        // ----------------------------------------------------------
        /**
         * Get the {@link WallpaperScene} for the live wallpaper
         *
         * @return The scene
         */
        protected T getWallpaperScene()
        {
            return scene;
        }
        
        
        // ----------------------------------------------------------
        /**
         * Forces the {@code WallpaperScene} to update and render itself
         */
        public void update()
        {
            if (!isVisible)
            {
                return;
            }
            SurfaceHolder holder = getSurfaceHolder();
            
            Canvas c = null;
            long start = System.nanoTime();
            try
            {
                c = holder.lockCanvas();
                if (c != null)
                {
                    if (lastTime == 0)
                    {
                        scene.update(targetFrameTime == -1 ? 0 : targetFrameTime, width, height);
                    }
                    else
                    {
                        scene.update((start - lastTime) / NANOS_PER_SEC, width, height);
                    }
                    lastTime = start;
                    scene.render(c, width, height);
                }
            }
            finally
            {
                if (c != null)
                {
                    holder.unlockCanvasAndPost(c);
                }
            }
            handler.removeCallbacks(updater);
            if (isVisible && targetFrameTime != -1)
            {
                handler.postDelayed(updater, (long)(MILLIS_PER_SEC * (targetFrameTime + (start - System.nanoTime())
                    / NANOS_PER_SEC)));
            }
        }
        
    }
}
