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

package com.xorprogramming.logging;

import android.util.Log;

// -------------------------------------------------------------------------
/**
 * Defines the various type and levels of logging as found in {@code android.util.Log}
 *
 * @see Log
 * @author Steven Roberts
 * @version 1.0.0
 */
public enum LoggingType
{
    /**
     * For displaying debugging information
     */
    DEBUG
    {
        @Override
        void log(String tag, String message)
        {
            Log.d(tag, message);
        }
    },
    /**
     * For displaying error messages such as {@code Exception} messages
     */
    ERROR
    {
        @Override
        void log(String tag, String message)
        {
            Log.e(tag, message);
        }
    },
    /**
     * For displaying general information
     */
    INFO
    {
        @Override
        void log(String tag, String message)
        {
            Log.i(tag, message);
        }
    },
    /**
     * For displaying large amounts of thorough logging
     */
    VERBOSE
    {
        @Override
        void log(String tag, String message)
        {
            Log.v(tag, message);
        }
    },
    /**
     * For displaying warning messages
     */
    WARNING
    {
        @Override
        void log(String tag, String message)
        {
            Log.w(tag, message);
        }
    },
    /**
     * For displaying information on a terrible failure or something that should never happen
     */
    WTF
    {
        @Override
        void log(String tag, String message)
        {
            Log.wtf(tag, message);
        }
    };
    
    // ----------------------------------------------------------
    /**
     * Logs the given message with the given tag
     *
     * @param tag
     *            Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs
     * @param message
     *            The message you would like logged
     */
    abstract void log(String tag, String message);
}
