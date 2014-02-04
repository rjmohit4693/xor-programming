package com.xorprogramming.wallpaper;

import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import com.xorprogramming.thread.Updatable;
import com.xorprogramming.thread.UpdaterThread;

public abstract class ThreadedWallpaperService
    extends WallpaperService
{

    public abstract class ThreadedEngine<T extends WallpaperScene>
        extends Engine
    {
        private final Object         lock = new Object();

        private final Updatable      u;
        private final UpdaterThread  thread;
        private final T scene;
        private int                  width;
        private int                  height;


        public ThreadedEngine(float targetFPS, T scene)
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


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder)
        {
            super.onSurfaceDestroyed(holder);
            thread.stop(true);
        }


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
