package com.xorprogramming.wallpaper;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// -------------------------------------------------------------------------
/**
 * An abstraction of the {@code WallpaperService} that uses a {@code OpenGLWallpaperScene} for updating and rendering.
 * The {@code ThreadedWallpaperService} is intend to be extended and have an inner class that extends
 * {@code OpenGLEngine} .
 *
 * @see OpenGLEngine
 * @see OpenGLWallpaperScene
 * @author Steven Roberts
 * @version 1.0.0
 */
public abstract class OpenGLWallpaperService
    extends WallpaperService
{

    // -------------------------------------------------------------------------
    /**
     * Handles the rendering of a {@code OpenGLWallpaperScene} and threading required to update it. The scene is only
     * updated when the live wallpaper can be seen by a user. Note that the {@code OpenGLEngine} runs on a non-UI
     * thread. Thus, any interaction in a {@code OpenGLWallpaperScene} by the UI thread with fields used in the
     * {@code render} and {@code update} methods must be synchronized.
     *
     * @param <T>
     *            The type of {@code WallpaperScene}
     * @see OpenGLWallpaperScene
     * @see ThreadedWallpaperService
     * @author Steven Roberts
     * @version 1.0.1
     */
    public class OpenGLEngine<T extends OpenGLWallpaperScene>
        extends Engine
    {
        private final Object  lock = new Object();
        private final T       scene;
        private final float   targetFrameTime;
        private GLSurfaceView surfaceView;
        private long          lastTime;
        private int           width;
        private int           height;


        // ----------------------------------------------------------
        /**
         * Create a new OpenGLEngine object.
         *
         * @param scene
         *            The {@code OpenGLWallpaperScene} for the live wallpaper
         * @param targetFPS
         *            The number of thread updates every second. It must be a positive number or
         *            {@code UpdaterThread.MAX_UPS}, which prevents the thread from sleeping or yielding.
         * @throws NullPointerException
         *             If the {@code WallpaperScene} is null
         * @throws IllegalArgumentException
         *             If {@code targetUPS} is not a positive number or {@code UpdaterThread.MAX_UPS}
         */
        public OpenGLEngine(T scene, float targetFPS)
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


        @Override
        public void onVisibilityChanged(boolean visible)
        {
            super.onVisibilityChanged(visible);
            if (visible)
            {
                surfaceView.onResume();
                surfaceView.requestRender();
            }
            else
            {
                synchronized (lock)
                {
                    lastTime = 0;
                }
                surfaceView.onPause();
            }
        }


        @Override
        public void onSurfaceCreated(SurfaceHolder holder)
        {
            if (surfaceView == null)
            {
                surfaceView = new GLSurfaceView(getApplicationContext())
                {
                    @Override
                    public SurfaceHolder getHolder()
                    {
                        return OpenGLEngine.this.getSurfaceHolder();
                    }
                };

                surfaceView.setRenderer(new RenderDelegator());
                surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            }
            surfaceView.surfaceCreated(holder);
        }


        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int newWidth, int newHeight)
        {
            synchronized (lock)
            {
                surfaceView.surfaceChanged(holder, format, newWidth, newHeight);
                width = newWidth;
                height = newHeight;
            }
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder)
        {
            surfaceView.surfaceDestroyed(holder);
            synchronized (lock)
            {
                lastTime = 0;
            }
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


        private class RenderDelegator
            implements Renderer
        {
            private static final float MILLIS_PER_SEC = 1e3f;
            private static final float NANOS_PER_SEC  = 1e9f;


            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config)
            {
                scene.initialize(gl, config);
            }


            @Override
            public void onSurfaceChanged(GL10 gl, int newWidth, int newHeight)
            {
                scene.onSizeChange(gl, newWidth, newHeight);
            }


            @Override
            public void onDrawFrame(GL10 gl)
            {
                long start = System.nanoTime();
                synchronized (lock)
                {
                    if (lastTime == 0)
                    {
                        scene.update(targetFrameTime == -1 ? 0 : targetFrameTime, width, height);
                    }
                    else
                    {
                        scene.update((start - lastTime) / NANOS_PER_SEC, width, height);
                    }
                    scene.render(gl, width, height);
                }
                long sleep = (long)(MILLIS_PER_SEC * (targetFrameTime + (start - System.nanoTime()) / NANOS_PER_SEC));
                try
                {
                    if (sleep < 0)
                    {
                        Thread.yield();
                    }
                    else
                    {
                        Thread.sleep(sleep);
                    }
                }
                catch (InterruptedException e)
                {
                    // Continue
                }
                lastTime = start;
            }
        }
    }
}
