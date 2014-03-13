/*
 * Copyright (C) 2014 Xor Programming
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
