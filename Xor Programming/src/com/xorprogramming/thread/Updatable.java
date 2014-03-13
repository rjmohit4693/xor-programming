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

package com.xorprogramming.thread;

// -------------------------------------------------------------------------
/**
 * This interface provides a structure for an object than can be updated over a defined time interval.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface Updatable
{
    // ----------------------------------------------------------
    /**
     * This is called when the {@code Updatable} object should be updated
     *
     * @param deltaT
     *            The time interval since the method was last called
     */
    void update(float deltaT);
}
