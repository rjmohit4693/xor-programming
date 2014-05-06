/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.io;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * A utility class containing methods to read the content of files.
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class IOUtil
{
    private IOUtil()
    {
        // No constructor needed for utility class
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file in the assets directory.
     * 
     * @param assets
     *            To provide access to the assets directory
     * @param file
     *            The name of the asset to open. This name can be hierarchical
     * @return The content of the file
     * @throws IOException
     *             If an error occurs opening the file
     */
    public static String readFromAssets(AssetManager assets, String file)
        throws IOException
    {
        return read(assets.open(file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file in internal storage
     * 
     * @param c
     *            To provide access to internal storage
     * @param file
     *            The name of the file to open; can not contain path separators
     * @return The content of the file
     * @throws IOException
     *             If an error occurs opening the file
     */
    public static String readFromInternalStorage(Context c, String file)
        throws IOException
    {
        return read(c.openFileInput(file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file in the cache directory
     * 
     * @param c
     *            To provide access to the cache directory
     * @param file
     *            The name of the asset to open. This name can be hierarchical
     * @return The content of the file
     * @throws IOException
     *             If an error occurs opening the file
     */
    public static String readFromCahce(Context c, String file)
        throws IOException
    {
        return read(new FileInputStream(new File(c.getCacheDir(), file)));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file in external storage. Note that {@code READ_EXTERNAL_STORAGE} permission must be
     * acquired in order to read from external storage.
     * 
     * @param file
     *            The name of the file to open. This name can be hierarchical
     * @return The content of the file
     * @throws IOException
     *             If an error occurs opening the file
     */
    public static String readFromExternalStorage(String file)
        throws IOException
    {
        return read(new FileInputStream(new File(Environment.getExternalStorageDirectory(), file)));
    }
    
    
    private static String read(InputStream is)
    {
        Scanner scan = null;
        try
        {
            scan = new Scanner(is);
            StringBuilder sb = new StringBuilder();
            while (scan.hasNextLine())
            {
                sb.append(scan.nextLine());
                sb.append('\n');
            }
            return sb.toString();
        }
        finally
        {
            if (scan != null)
            {
                scan.close();
            }
        }
    }
}
