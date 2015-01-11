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

package com.xorprogramming.io.utils;

import android.annotation.TargetApi;
import android.os.Build;
import com.xorprogramming.XorUtils;
import java.io.Closeable;
import java.io.IOException;

// -------------------------------------------------------------------------
/**
 * A utility class containing commonly used methods for handling IO operations
 *
 * @author Steven Roberts
 * @version 1.0.1
 */
public final class IOUtils
{
    private IOUtils()
    {
        XorUtils.assertConstructNoninstantiability();
    }


    // ----------------------------------------------------------
    /**
     * Closes a {@link Closeable}, and if an {@link IOException} is thrown, it is silently caught.
     *
     * @param closeable
     *            The {@link Closeable}, which may be null
     * @return true if sucessfully closed, false otherwise
     */
    public static boolean closeStream(Closeable closeable)
    {
        if (closeable == null)
        {
            return false;
        }
        try
        {
            closeable.close();
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }


    // ----------------------------------------------------------
    /**
     * Closes a {@link AutoCloseable}, and if an {@link Exception} is thrown, it is silently caught.
     *
     * @param autoCloseable
     *            The {@link AutoCloseable}, which may be null
     * @return true if sucessfully closed, false otherwise
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean closeStream(AutoCloseable autoCloseable)
    {
        if (autoCloseable == null)
        {
            return false;
        }
        try
        {
            autoCloseable.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
