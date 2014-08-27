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

// -------------------------------------------------------------------------
/**
 * A listener interface for responding to game events.
 *
 * @param <T1>
 *            The type of {@link GameEngine} that triggers events
 * @param <T2>
 *            The type of result from a game event
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface GameEventListener<T1 extends GameEngine<T2>, T2>
{
    // ----------------------------------------------------------
    /**
     * Called when a generic game event occurs in a {@link GameEngine}
     *
     * @param t
     *            Data related to the event
     * @param engine
     *            The {@link GameEngine} that triggered the event
     */
    void onGameEvent(T2 t, T1 engine);


    // ----------------------------------------------------------
    /**
     * Called when a {@link RenderException} is thrown by a {@link GameRenderer}
     *
     * @param ex
     *            The thrown exception
     */
    void onRenderException(RenderException ex);


    // ----------------------------------------------------------
    /**
     * Called when a {@link GameEngine} determines that the game is over
     *
     * @param win
     *            true if the game was won, false if the game was lost
     */
    void onGameOver(boolean win);
}
