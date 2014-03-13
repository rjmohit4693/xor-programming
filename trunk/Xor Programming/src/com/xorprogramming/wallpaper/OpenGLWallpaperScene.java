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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// -------------------------------------------------------------------------
/**
 * An interface that defines the fundamental operations required for a live wallpaper
 *
 * @author Steven Roberts
 * @version 1.1.0
 */
public interface OpenGLWallpaperScene
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
     * Called when the size of the live wallpaper changes
     *
     * @param gl10
     *            The OpenGL graphics
     * @param newWidth
     *            The new width of the live wallpaper
     * @param newHeight
     *            The new height of the live wallpaper
     */
    public void onSizeChange(GL10 gl10, int newWidth, int newHeight);


    // ----------------------------------------------------------
    /**
     * Used for loading and initializing the graphics of the scene
     *
     * @param gl10
     *            The OpenGL graphics
     * @param config
     *            The OpenGL config
     */
    public void initialize(GL10 gl10, EGLConfig config);


    // ----------------------------------------------------------
    /**
     * Called when the scene needs to be rendered
     *
     * @param gl10
     *            The OpenGL graphics
     * @param width
     *            The width of the live wallpaper
     * @param height
     *            The height of the live wallpaper
     */
    public void render(GL10 gl10, int width, int height);


    // ----------------------------------------------------------
    /**
     * Used for disposing of resources such as graphics
     */
    public void dispose();
}
