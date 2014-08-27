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
 * Signals an error that occurred while handling rendering.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class RenderException
    extends Exception
{
    private static final long serialVersionUID = -7540060817781366221L;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new RenderException object.
     *
     * @param message
     *            The detail message for this exception.
     */
    public RenderException(String message)
    {
        super(message);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new RenderException object.
     *
     * @param t
     *            The cause of the exception
     */
    public RenderException(Throwable t)
    {
        super(t);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new RenderException object.
     *
     * @param message
     *            The detail message for this exception.
     * @param t
     *            The cause of the exception
     */
    public RenderException(String message, Throwable t)
    {
        super(message, t);
    }
}
