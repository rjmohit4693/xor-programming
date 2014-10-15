/*-
Copyright 2014 Xor Programming

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.xorprogramming.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

// -------------------------------------------------------------------------
/**
 * An interface providing the basic framework for rendering a {@link GameEngine}.
 *
 * @param <T>
 *            The type of {@link GameEngine} to render
 * @author Steven Roberts
 * @version 1.0.0
 * @see GameEngine
 * @see GameView
 */
public interface GameRenderer<T extends GameEngine<?>>
{

    // ----------------------------------------------------------
    /**
     * Initializes the graphic objects need for rendering such as a {@link Bitmap} or {@link Paint}.
     *
     * @param res
     *            The {@link Resources} with which to use for initialization
     * @throws RenderException
     *             If an error occurs initializing graphics objects
     */
    void initialize(Resources res)
        throws RenderException;


    // ----------------------------------------------------------
    /**
     * Performs the rendering of the {@link GameEngine} to the give {@link Canvas}
     *
     * @param engine
     *            The {@link GameEngine} to render
     * @param c
     *            The {@link Canvas} on which to draw
     * @param width
     *            The width of the {@link GameView}
     * @param height
     *            The height of the {@link GameView}
     * @throws RenderException
     *             If an error occurs rendering
     */
    void render(T engine, Canvas c, int width, int height)
        throws RenderException;


    // ----------------------------------------------------------
    /**
     * Disposes graphics objects. Note that a {@link GameRenderer} can be initialized after it has been disposed.
     *
     * @throws RenderException
     *             If an error occurs disposing graphics
     */
    void dispose()
        throws RenderException;
}
