package com.xorprogramming.example;

import com.xorprogramming.wallpaper.OpenGLWallpaperService;

public class OpenGLLiveWallpaper
    extends OpenGLWallpaperService
{

    @Override
    public Engine onCreateEngine()
    {
        return new Engine();
    }


    public class Engine
        extends OpenGLEngine<Scene>
    {

        public Engine()
        {
            super(new Scene(), 2);
        }

    }
}
