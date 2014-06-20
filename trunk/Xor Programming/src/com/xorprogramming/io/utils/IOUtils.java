/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.io.utils;

import java.io.Closeable;
import java.io.IOException;

// -------------------------------------------------------------------------
/**
 * A utility class containing methods to read and write the content of files.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
final class IOUtils
{
    private IOUtils()
    {
        // No constructor needed
    }


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
}
