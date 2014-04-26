/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.wallpaper;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public abstract class GLWallpaperService
    extends WallpaperService
{
    public abstract class ThreadedEngine<T extends Renderer>
        extends Engine
    {
        private final T renderer;
        private WallpaperGLSurfaceView surfaceView;


        public ThreadedEngine(T renderer, float targetFPS)
        {
            this.renderer = renderer;
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder)
        {
            super.onCreate(surfaceHolder);
            surfaceView = new WallpaperGLSurfaceView(getApplicationContext());
            surfaceView.setRenderer(renderer);
        }


        @Override
        public void onVisibilityChanged(boolean visible)
        {
            if (visible)
            {
                surfaceView.onResume();
            }
            else
            {
                surfaceView.onPause();
            }
        }


        @Override
        public void onDestroy()
        {
        	super.onDestroy();
        	surfaceView.onWallpaperDestroy();
        }


        public void queueEvent(Runnable runnable)
        {
        	surfaceView.queueEvent(runnable);
        }


        protected T getWallpaperRenderer()
        {
            return renderer;
        }


        private class WallpaperGLSurfaceView
            extends GLSurfaceView
        {
            public WallpaperGLSurfaceView(Context context)
            {
                super(context);
            }


            @Override
            public SurfaceHolder getHolder()
            {
                return getSurfaceHolder();
            }


            public void onWallpaperDestroy()
            {
                super.onDetachedFromWindow();
            }
        }
    }
}
