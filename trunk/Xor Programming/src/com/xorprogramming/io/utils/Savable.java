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

package com.xorprogramming.io.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// -------------------------------------------------------------------------
/**
 * An interface that defines an object that can be saved and restored. Consider using the {@link SavableIOUtils} class
 * to conveniently save and restore {@code Savable}s.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface Savable
{
    // ----------------------------------------------------------
    /**
     * Restores the state of the object from an {@link ObjectInputStream}
     *
     * @param ois
     *            The input stream containing the state
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     */
    void restore(ObjectInputStream ois)
        throws IOException,
        ClassNotFoundException;
    
    
    // ----------------------------------------------------------
    /**
     * Saves the state of the object to an {@link ObjectOutputStream}
     *
     * @param oos
     *            The output stream to which the state is written
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    void save(ObjectOutputStream oos)
        throws IOException;
}
