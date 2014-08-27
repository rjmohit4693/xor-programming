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
 * An interface that can notify listeners of game events.
 *
 * @param <T>
 *            The type of result from a game event
 * @author Steven Roberts
 * @version 1.0.0
 * @see GameEventListener
 */
public interface GameEventController<T>
{
    // ----------------------------------------------------------
    /**
     * Notifies listeners of a generic game event
     *
     * @param t
     *            Data related to the event
     */
    void notifyGameEvent(T t);
    
    
    // ----------------------------------------------------------
    /**
     * Notifies listeners that the game is over
     *
     * @param win
     *            true if the game was won, false if the game was lost
     */
    void notiftyGameOver(boolean win);
}
