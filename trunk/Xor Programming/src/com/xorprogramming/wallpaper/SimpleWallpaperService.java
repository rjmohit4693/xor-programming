package com.xorprogramming.wallpaper;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public abstract class SimpleWallpaperService
    extends WallpaperService
{

    public class SimpleEngine<T extends WallpaperScene>
        extends Engine
    {
        private static final float MILLIS_PER_SEC = 1e3f;
        private static final float NANOS_PER_SEC  = 1e9f;
        private final T            scene;
        private final Handler      handler        = new Handler();
        private final Runnable     updater        = new Runnable()
                                                  {
                                                      public void run()
                                                      {
                                                          update();
                                                      }
                                                  };
        private final float        targetFrameTime;
        private long               lastTime;
        private boolean            isVisible;
        private int                width;
        private int                height;


        public SimpleEngine(T scene)
        {
            this.scene = scene;
            targetFrameTime = -1;
        }


        public SimpleEngine(float targetFPS, T scene)
        {
            if (Float.isInfinite(targetFPS) || Float.isNaN(targetFPS) || targetFPS < 0)
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


        private void handleInvisibility()
        {
            isVisible = false;
            handler.removeCallbacks(updater);
            lastTime = 0;
        }


        @Override
        public void onDestroy()
        {
            super.onDestroy();
            handleInvisibility();
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
        public void onSurfaceChanged(SurfaceHolder holder, int format, int newWidth, int newHeight)
        {
            super.onSurfaceChanged(holder, format, width, height);
            width = newWidth;
            height = newHeight;
            update();
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder)
        {
            super.onSurfaceDestroyed(holder);
            handleInvisibility();
        }


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


        protected T getWallpaperScene()
        {
            return scene;
        }

    }
}
