package com.xorprogramming.logging;

public interface LoggerListener
{
    void onLog(LoggingType type, String tag, String message);
}
