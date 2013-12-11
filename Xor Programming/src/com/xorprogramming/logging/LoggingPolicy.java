package com.xorprogramming.logging;

import java.util.EnumSet;

public final class LoggingPolicy
{
    public static final LoggingPolicy FULL_LOGGING = new LoggingPolicy(EnumSet.allOf(LoggingType.class));
    public static final LoggingPolicy NO_LOGGING   = new LoggingPolicy(null);
    
    private EnumSet<LoggingType>      loggingTypes;
    
    
    private LoggingPolicy(EnumSet<LoggingType> loggingTypes)
    {
        this.loggingTypes = loggingTypes;
    }
    
    
    public static LoggingPolicy instanceOf(LoggingType firstType, LoggingType... otherLoggingTypes)
    {
        return new LoggingPolicy(EnumSet.of(firstType, otherLoggingTypes));
    }
    
    
    public boolean isLogging(LoggingType type)
    {
        return loggingTypes == null ? false : loggingTypes.contains(type);
    }
    
    
    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }
        if (o instanceof LoggingPolicy)
        {
            LoggingPolicy policy = (LoggingPolicy)o;
            return policy.loggingTypes.equals(loggingTypes);
        }
        return false;
    }
    
    
    @Override
    public int hashCode()
    {
        return loggingTypes.hashCode();
    }
    
    
    @Override
    public String toString()
    {
        return loggingTypes.toString();
    }
}
