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
import android.graphics.Canvas;

// -------------------------------------------------------------------------
/**
 * An interface that defines the fundamental operations required for a live wallpaper
 *
 * @author Steven Roberts
 * @version 1.1.0
 */
public interface WallpaperScene
{
    // ----------------------------------------------------------
    /**
     * Called when the state of the scene needs to be updated
     *
     * @param deltaT
     *            The time interval since the method was last called
     * @param width
     *            The width of the live wallpaper
     * @param height
     *            The height of the live wallpaper
     */
    public void update(float deltaT, int width, int height);


    // ----------------------------------------------------------
    /**
     * Used for loading and initializing the graphics of the scene
     *
     * @param c
     *            The context used for graphics initialization
     */
    public void initialize(Context c);


    // ----------------------------------------------------------
    /**
     * Called when the size of the live wallpaper changes. Note that method should be called once after initialization
     * but before rendering
     *
     * @param c
     *            The context used for graphics initialization
     * @param newWidth
     *            The new width of the live wallpaper
     * @param newHeight
     *            The new height of the live wallpaper
     */
    public void onSizeChange(Context c, int newWidth, int newHeight);


    // ----------------------------------------------------------
    /**
     * Called when the scene needs to be rendered
     *
     * @param c
     *            The {@code Canvas} on which the rendering occurs
     * @param width
     *            The width of the live wallpaper
     * @param height
     *            The height of the live wallpaper
     */
    public void render(Canvas c, int width, int height);


    // ----------------------------------------------------------
    /**
     * Used for disposing of resources such as graphics
     */
    public void dispose();
}
