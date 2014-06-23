/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.game;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional details about its purpose, what
 * abstraction it represents, and how to use it.
 * 
 * @param <T>
 *            The type of result from a game event. This could be an {@code Integer}
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface GameEventListener<T>
{
    void onGameEvent(T t, GameEngine<T> engine);
    
    
    void onGameException(Exception ex);
    
}
