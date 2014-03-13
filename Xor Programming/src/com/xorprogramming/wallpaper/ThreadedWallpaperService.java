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

package com.xorprogramming.wallpaper;

import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import com.xorprogramming.thread.Updatable;
import com.xorprogramming.thread.UpdaterThread;

// -------------------------------------------------------------------------
/**
 * An abstraction of the {@code WallpaperService} that uses a {@code WallpaperScene} for updating and rendering. The
 * {@code ThreadedWallpaperService} is intend to be extended and have an inner class that extends {@code ThreadedEngine}
 * .
 *
 * @see ThreadedEngine
 * @see WallpaperScene
 * @author Steven Roberts
 * @version 1.0.0
 */
public abstract class ThreadedWallpaperService
    extends WallpaperService
{
    // -------------------------------------------------------------------------
    /**
     * Handles the rendering of a {@code WallpaperScene} and threading required to update it. The scene is only updated
     * when the live wallpaper can be seen by a user. Note that the {@code ThreadedEngine} runs on a non-UI thread.
     * Thus, any interaction in a {@code WallpaperScene} by the UI thread with fields used in the {@code render} and
     * {@code update} methods must be synchronized.
     *
     * @param <T>
     *            The type of {@code WallpaperScene}
     * @see WallpaperScene
     * @see ThreadedWallpaperService
     * @author Steven Roberts
     * @version 1.0.1
     */
    public abstract class ThreadedEngine<T extends WallpaperScene>
        extends Engine
    {
        private final Object        lock = new Object();

        private final Updatable     u;
        private final UpdaterThread thread;
        private final T             scene;
        private int                 width;
        private int                 height;


        // ----------------------------------------------------------
        /**
         * Create a new ThreadedEngine object.
         *
         * @param scene
         *            The {@code WallpaperScene} for the live wallpaper
         * @param targetFPS
         *            The number of thread updates every second. It must be a positive number or
         *            {@code UpdaterThread.MAX_UPS}, which prevents the thread from sleeping or yielding.
         * @throws NullPointerException
         *             If the {@code WallpaperScene} is null
         * @throws IllegalArgumentException
         *             If {@code targetUPS} is not a positive number or {@code UpdaterThread.MAX_UPS}
         */
        public ThreadedEngine(T scene, float targetFPS)
        {
            this.scene = scene;
            u = new WallpaperUpdatable();
            thread = new UpdaterThread(u, targetFPS);
        }


        @Override
        public void onVisibilityChanged(boolean visible)
        {
            if (visible)
            {
                thread.start();
            }
            else
            {
                thread.stop(true);
            }
        }


        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int newWidth, int newHeight)
        {
            synchronized (lock)
            {
                super.onSurfaceChanged(holder, format, width, height);
                width = newWidth;
                height = newHeight;
            }
            u.update(0);
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


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder)
        {
            super.onSurfaceDestroyed(holder);
            thread.stop(true);
        }


        // ----------------------------------------------------------
        /**
         * Get the {@code WallpaperScene} for the live wallpaper
         *
         * @return The scene
         */
        protected T getWallpaperScene()
        {
            return scene;
        }


        private class WallpaperUpdatable
            implements Updatable
        {

            public void update(float deltaT)
            {
                SurfaceHolder holder = getSurfaceHolder();
                Canvas c = null;
                try
                {
                    c = holder.lockCanvas();
                    if (c != null)
                    {
                        synchronized (lock)
                        {
                            scene.update(deltaT, width, height);
                            scene.render(c, width, height);
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
}
