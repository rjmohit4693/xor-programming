package com.xorprogramming.wallpaper;

import android.graphics.Canvas;

public interface WallpaperScene
{
    public void update(float deltaT, int width, int height);


    public void render(Canvas c, int width, int height);
}
