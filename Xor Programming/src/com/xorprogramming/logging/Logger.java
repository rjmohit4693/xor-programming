package com.xorprogramming.logging;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private static final String               LOG_TAG      = "XOR";

    private static final List<LoggerListener> logListeners = new CopyOnWriteArrayList<LoggerListener>();

    private static volatile LoggingPolicy     policy;

    static
    {
        policy = LoggingPolicy.FULL_LOGGING;
    }


    private Logger()
    {
        // No constructor needed
    }


    public synchronized static void setLoggingPolicy(LoggingPolicy newPolicy)
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


    public void addLoggerListener(LoggerListener listener)
    {
        logListeners.add(listener);
    }


    public void removeLoggerListener(LoggerListener listener)
    {
        logListeners.remove(listener);
    }


    public static String log(LoggingType type, String message)
    {
        return log(type, LOG_TAG, message);
    }


    public static String log(LoggingType type, String tag, String message)
    {
        synchronized (Logger.class)
        {
            if (policy.isLogging(type))
            {
                type.log(tag, message);
            }
        }
        updateListeners(type, tag, message);
        return message;
    }


    public static <T extends Throwable> T log(LoggingType type, T throwable)
    {
        return log(type, LOG_TAG, throwable);
    }


    public static <T extends Throwable> T log(LoggingType type, String tag, T throwable)
    {
        synchronized (Logger.class)
        {
            if (policy.isLogging(type))
            {
                type.log(tag, throwable.getMessage());
            }
        }
        updateListeners(type, tag, throwable.getMessage());
        return throwable;
    }


    private static void updateListeners(LoggingType type, String tag, String message)
    {
        for (LoggerListener l : logListeners)
        {
            l.onLog(type, tag, message);
        }
    }
}
