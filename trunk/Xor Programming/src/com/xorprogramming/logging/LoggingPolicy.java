/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.logging;

import java.util.EnumSet;

// -------------------------------------------------------------------------
/**
 * This class stores which {@code LoggingType}s are actually sent to output by the {@code Logger}. Note that this class
 * is immutable and therefore, is thread-safe. Some of the most commonly used logging policies are defined as
 *
 * @see LoggingType
 * @see Logger
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class LoggingPolicy
{
    public static final LoggingPolicy  FULL_LOGGING       = new LoggingPolicy(EnumSet.allOf(LoggingType.class));
    public static final LoggingPolicy  PRODUCTION_LOGGING = instanceOf(
                                                              LoggingType.ERROR,
                                                              LoggingType.WARNING,
                                                              LoggingType.WTF);
    public static final LoggingPolicy  NO_LOGGING         = new LoggingPolicy(EnumSet.noneOf(LoggingType.class));

    private final EnumSet<LoggingType> loggingTypes;


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
        return loggingTypes.contains(type);
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
    public Object clone()
    {
        return loggingTypes.clone();
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
