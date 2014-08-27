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
 * A simple interface that lets a game engine be updated
 * 
 * @param <T>
 *            The type of result from a game event
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface GameEngine<T>
{
    // ----------------------------------------------------------
    /**
     * Updates the engine to the next state
     * 
     * @param deltaT
     *            The time since last called. This allows the engine to be independent of fps.
     * @param controller
     *            The {@link GameEventController} that notifies {@link GameEventListener}s of game events
     * @return true if the game state changed, false otherwise
     */
    boolean update(float deltaT, GameEventController<T> controller);
}
