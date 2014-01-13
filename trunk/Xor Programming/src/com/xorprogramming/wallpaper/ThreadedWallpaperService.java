package com.xorprogramming.wallpaper;

import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import com.xorprogramming.thread.Updatable;
import com.xorprogramming.thread.UpdaterThread;

public abstract class ThreadedWallpaperService
    extends WallpaperService
{

    public abstract class ThreadedEngine
        extends Engine
    {
        private final Updatable      u;
        private final UpdaterThread  thread;
        private final WallpaperScene scene;
        private int                  width;
        private int                  height;


        public ThreadedEngine(float targetFPS, final WallpaperScene scene)
        {
            this.scene = scene;
            u = new WallpaperUpdatable();
            thread = new UpdaterThread(u, targetFPS);
        }


        @Override
        public void onDestroy()
        {
            super.onDestroy();
            thread.stop(true);
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
        public synchronized void onSurfaceChanged(SurfaceHolder holder, int format, int newWidth, int newHeight)
        {
            super.onSurfaceChanged(holder, format, width, height);
            synchronized (scene)
            {
                width = newWidth;
                height = newHeight;
            }
            u.update(0);
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder)
        {
            super.onSurfaceDestroyed(holder);
            thread.stop(true);
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
                        synchronized (scene)
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
