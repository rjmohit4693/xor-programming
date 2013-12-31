package com.xorprogramming.logging;

// -------------------------------------------------------------------------
/**
 * A class containing shared, static resources for the Xor Programming API
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class Logger
{
    /**
     * Tag for all logging within the API
     */
    private static final String           LOG_TAG = "XOR";
    
    private static volatile LoggingPolicy policy;
    
    static
    {
        policy = LoggingPolicy.NO_LOGGING;
    }
    
    
    private Logger()
    {
        // No constructor needed
    }
    
    
    public static void setLoggingPolicy(LoggingPolicy newPolicy)
    {
        if (newPolicy == null)
        {
            policy = LoggingPolicy.NO_LOGGING;
        }
        else
        {
            policy = newPolicy;
        }
    }
    
    
    public static String log(LoggingType type, String message)
    {
        return log(type, LOG_TAG, message);
    }
    
    
    public static String log(LoggingType type, String tag, String message)
    {
        if (policy.isLogging(type))
        {
            type.log(tag, message);
        }
        return message;
    }
}
