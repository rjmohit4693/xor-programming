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
