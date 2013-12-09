package com.xorprogramming.game;

import android.content.res.Resources;
import android.graphics.Canvas;

public interface GameRenderer<T1 extends GameEngine<?>>
{
    void render(T1 engine, Canvas c, int width, int height);
    
    
    void initialize(Resources res);
    
    
    void dispose();
}
