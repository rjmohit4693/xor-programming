package com.xorprogramming.logging;

import android.util.Log;

public enum LoggingType
{
    DEBUG
    {
        @Override
        void log(String tag, String message)
        {
            Log.d(tag, message);
        }
    },
    ERROR
    {
        @Override
        void log(String tag, String message)
        {
            Log.e(tag, message);
        }
    },
    INFO
    {
        @Override
        void log(String tag, String message)
        {
            Log.i(tag, message);
        }
    },
    VERBOSE
    {
        @Override
        void log(String tag, String message)
        {
            Log.v(tag, message);
        }
    },
    WARNING
    {
        @Override
        void log(String tag, String message)
        {
            Log.w(tag, message);
        }
    },
    WTF
    {
        @Override
        void log(String tag, String message)
        {
            Log.wtf(tag, message);
        }
    };
    
    abstract void log(String tag, String message);
}
