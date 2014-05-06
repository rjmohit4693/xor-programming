/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.string;

import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * StringChopper provides a similar functionallity to a StringTokenizer, while providing a peek and reset method. It
 * always return the delimiters.
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class StringChopper
{
    private final String text;
    private final String delimiters;
    
    private int          curIndex;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new StringChopper object. It will use \n, \t, \r, and \f as delimiters
     * 
     * @param text
     *            The text to chop
     */
    public StringChopper(String text)
    {
        this(text, " \n\t\r\f");
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new StringChopper object.
     * 
     * @param text
     *            The text to chop
     * @param delimiters
     *            A string with each character as a delimiter to chop the text
     */
    public StringChopper(String text, String delimiters)
    {
        if (text == null)
        {
            throw new IllegalArgumentException("The text must be non-null");
        }
        else if (delimiters == null)
        {
            throw new IllegalArgumentException("The delimiters must be non-null");
        }
        this.text = text;
        this.delimiters = delimiters;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Checks if the
     * 
     * @return False if the end of the text has been reached and no more tokens exist, true otherwise
     */
    public boolean hasNext()
    {
        return curIndex < text.length();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Moves to the next token if it exists
     * 
     * @return The next token
     */
    public String next()
    {
        int start = curIndex;
        while (curIndex < text.length())
        {
            String curChar = text.substring(curIndex, curIndex + 1);
            curIndex++;
            if (delimiters.contains(curChar))
            {
                return curChar;
            }
            else if (hasNext())
            {
                curChar = text.substring(curIndex, curIndex + 1);
                if (delimiters.contains(curChar))
                {
                    return text.substring(start, curIndex);
                }
            }
            else
            {
                return text.substring(start);
            }
        }
        throw new NoSuchElementException();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the next token without moving forward
     * 
     * @return The next token
     */
    public String peek()
    {
        int tempIndex = curIndex;
        while (curIndex < text.length())
        {
            String curChar = text.substring(tempIndex, tempIndex + 1);
            tempIndex++;
            if (delimiters.contains(curChar))
            {
                return curChar;
            }
            else if (tempIndex < text.length())
            {
                curChar = text.substring(tempIndex, tempIndex + 1);
                if (delimiters.contains(curChar))
                {
                    return text.substring(curIndex, tempIndex);
                }
            }
            else
            {
                return text.substring(curIndex);
            }
        }
        throw new NoSuchElementException();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Moves back to the beginning of the text
     */
    public void reset()
    {
        curIndex = 0;
    }
    
    
    @Override
    public String toString()
    {
        return text;
    }
}
