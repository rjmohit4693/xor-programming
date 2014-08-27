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

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * A utility class containing convenient methods for reading and writing a {@link String}.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class StringIOUtils
{
    private StringIOUtils()
    {
        // No constructor needed
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file in the assets directory.
     *
     * @param assets
     *            The assets to open
     * @param file
     *            The name of the asset to open. This name can be hierarchical
     * @return The content of the file
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @see AssetManager#open(String)
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
     *            The context with which to open file input
     * @param file
     *            The name of the file to open; can not contain path separators
     * @return The content of the file
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @see Context#openFileInput(String)
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
     *            The context with which to open cache input
     * @param file
     *            The path of the file to open relative to the cache directory
     * @return The content of the file
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @see Context#getCacheDir()
     */
    public static String readFromCahce(Context c, String file)
        throws IOException
    {
        return read(new File(c.getCacheDir(), file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file in external storage.
     *
     * @param file
     *            The path of the file to open relative to the external storage directory
     * @return The content of the file
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @see Environment#getExternalStorageDirectory()
     */
    public static String readFromExternalStorage(String file)
        throws IOException
    {
        return read(new File(Environment.getExternalStorageDirectory(), file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of a file
     *
     * @param file
     *            The file from which to read
     * @return The content of the file
     * @throws IOException
     *             If an error occurs while reading from the stream
     */
    public static String read(File file)
        throws IOException
    {
        return read(new FileInputStream(file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the content of an {@link InputStream}
     *
     * @param is
     *            The name of the asset to open. This name can be hierarchical
     * @return The content of the file
     */
    public static String read(InputStream is)
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
            IOUtils.closeStream(is);
            IOUtils.closeStream(scan);
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link String} to the internal storage associated with the context.
     *
     * @param c
     *            The context with which to open file output
     * @param out
     *            The object to save
     * @param file
     *            The name of the file to open; can not contain path separators.
     * @param mode
     *            Operating mode. Use 0 or {@link Context#MODE_PRIVATE} for the default operation,
     *            {@link Context# MODE_APPEND} to append to an existing file, {@link Context#MODE_WORLD_READABLE} and
     *            {@link Context#MODE_WORLD_WRITEABLE} to control permissions.
     * @throws IOException
     *             If an error occurs while writing to the stream
     * @see Context#openFileOutput(String, int)
     */
    public static void writeToInternalStorage(Context c, String file, int mode, String out)
        throws IOException
    {
        write(c.openFileOutput(file, mode), out);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link String} to the cache directory of the filesystem.
     *
     * @param c
     *            The context with which to open cache output
     * @param out
     *            The object to save
     * @param file
     *            The path of the file to open relative to the cache directory
     * @throws IOException
     *             If an error occurs while writing to the stream
     * @see Context#getCacheDir()
     */
    
    public static void writeToCache(Context c, String file, String out)
        throws IOException
    {
        write(new File(c.getCacheDir(), file), out);
    }
    
    
    // ----------------------------------------------------------``
    /**
     * Writes a {@link String} to the primary external storage directory.
     *
     * @param out
     *            The object to save
     * @param file
     *            The path of the file to open relative to the external storage directory
     * @throws IOException
     *             If an error occurs while writing to the stream
     * @see Environment#getExternalStorageDirectory()
     */
    public static void writeToExternalStorage(String file, String out)
        throws IOException
    {
        write(new File(Environment.getExternalStorageDirectory(), file), out);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link String} to the given file.
     *
     * @param out
     *            The object to save
     * @param file
     *            The file at which to save
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    public static void write(File file, String out)
        throws IOException
    {
        write(new FileOutputStream(file), out);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link String} to the given {@link OutputStream}.
     *
     * @param out
     *            The object to save
     * @param os
     *            The stream to which to write
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    public static void write(OutputStream os, String out)
        throws IOException
    {
        try
        {
            os.write(out.getBytes());
        }
        finally
        {
            IOUtils.closeStream(os);
        }
    }
}
