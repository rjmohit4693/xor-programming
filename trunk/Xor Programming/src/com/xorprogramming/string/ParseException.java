/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.string;

// -------------------------------------------------------------------------
/**
 * This Exception is thrown when an error occurs while parsing data
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class ParseException
    extends Exception
{
    private static final long serialVersionUID = 5933538226865089632L;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new ParseException object.
     * 
     * @param message
     *            A informative String describing the nature and cause of the exception
     */
    public ParseException(String message)
    {
        super(message);
    }
}
