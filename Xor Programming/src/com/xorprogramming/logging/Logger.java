/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.logging;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// -------------------------------------------------------------------------
/**
 * A utility class that manages logging to output. It uses a {@code LoggingPolicy} to determine which logging types
 * actually get logged. It also notifies {@code LoggerListener}s when anything is logged regardless of the policy. This
 * can be used for a central error handling, for example.
 * <p/>
 * Note that this class can be accessed concurrently. Also, when a thread logs a message, the
 * {@code LoggerListener.onLog} method is called by that thread. Thus, in a multithreaded environment utilizing the
 * {@code Logger}, a {@code LoggerListener.onLog} may require synchronization.
 *
 * @see LoggingPolicy
 * @see LoggerListener
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class Logger
{
    /**
     * Tag for all logging within the API
     */
    public static final String               LOG_TAG      = "XOR";

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


    // ----------------------------------------------------------
    /**
     * Sets a new logging policy.
     *
     * @param newPolicy
     *            The new policy. If null, the policy is set to {@link LoggingPolicy#NO_LOGGING}
     */
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


    // ----------------------------------------------------------
    /**
     * Adds a {@code LoggerListener} to be notified of logging events.
     *
     * @param listener
     *            The {@code LoggerListener} to add
     * @throws NullPointerException
     *             if the listener is null
     */
    public void addLoggerListener(LoggerListener listener)
    {
        if (listener == null)
        {
            throw new NullPointerException("The LoggerListener must be non-null");
        }
        logListeners.add(listener);
    }


    // ----------------------------------------------------------
    /**
     * Removes the {@code LoggerListener} from the list of listeners. The listener will no longer be notified of logging
     * events
     *
     * @param listener
     *            The {@code LoggerListener} to remove
     */
    public void removeLoggerListener(LoggerListener listener)
    {
        logListeners.remove(listener);
    }


    // ----------------------------------------------------------
    /**
     * Logs a message to output with the given tag.
     *
     * @param type
     *            The type of message being logged
     * @param tag
     *            Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param message
     *            The message to log
     * @return The message
     */
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


    // ----------------------------------------------------------
    /**
     * Logs a formatted message to output with the given tag.
     *
     * @param type
     *            The type of message being logged
     * @param tag
     *            Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param messageFormat
     *            The format string (see {@link java.util.Formatter#format})
     * @param args
     *            The list of arguments passed to the formatter. If there are more arguments than required, additional
     *            arguments are ignored.
     * @return The message
     */
    public static String logf(LoggingType type, String tag, String messageFormat, Object... args)
    {
        return log(type, tag, String.format(messageFormat, args));
    }


    // ----------------------------------------------------------
    /**
     * Logs a {@code Throwable} to output with {@link Logger#LOG_TAG} as the tag.
     *
     * @param type
     *            The type of message being logged
     * @param tag
     *            Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param throwable
     *            The exception or error being logged
     * @return The throwable
     */
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
