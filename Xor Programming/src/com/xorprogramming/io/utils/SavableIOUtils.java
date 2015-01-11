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

import com.xorprogramming.XorUtils;
import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

// -------------------------------------------------------------------------
/**
 * A utility class containing convenient methods for saving and restoring a {@link Savable}.
 *
 * @author Steven Roberts
 * @version 1.0.1
 */
public final class SavableIOUtils
{
    private SavableIOUtils()
    {
        XorUtils.assertConstructNoninstantiability();
    }


    // ----------------------------------------------------------
    /**
     * Restores a {@link Savable} from the internal storage associated with the context.
     *
     * @param c
     *            The context with which to open file input
     * @param savable
     *            The object to restore
     * @param file
     *            The name of the file to open; can not contain path separators.
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @see Context#openFileInput(String)
     */
    public static void restoreFromInternalStorage(Context c, Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, c.openFileInput(file));
    }


    // ----------------------------------------------------------
    /**
     * Restores a {@link Savable} from the cache directory of the filesystem.
     *
     * @param c
     *            The context with which to open cache input
     * @param savable
     *            The object to restore
     * @param file
     *            The path of the file to open relative to the cache directory
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @see Context#getCacheDir()
     */
    public static void restoreFromCache(Context c, Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, new File(c.getCacheDir(), file));
    }


    // ----------------------------------------------------------
    /**
     * Restores a {@link Savable} from the primary external storage directory.
     *
     * @param savable
     *            The object to restore
     * @param file
     *            The path of the file to open relative to the external storage directory
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @see Environment#getExternalStorageDirectory()
     */
    public static void restoreFromExternalStorage(Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, new File(Environment.getExternalStorageDirectory(), file));
    }


    // ----------------------------------------------------------
    /**
     * Restores a {@link Savable} from a file.
     *
     * @param savable
     *            The object to restore
     * @param file
     *            The file from which to restore
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     */
    public static void restore(Savable savable, File file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, new FileInputStream(file));
    }


    // ----------------------------------------------------------
    /**
     * Restores a {@link Savable} from an {@link InputStream}.
     *
     * @param savable
     *            The object to restore
     * @param is
     *            The stream from which to restore
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     */
    public static void restore(Savable savable, InputStream is)
        throws IOException,
        ClassNotFoundException
    {
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(is);
            savable.restore(ois);
        }
        finally
        {
            IOUtils.closeStream(is);
            IOUtils.closeStream(ois);
        }
    }


    // ----------------------------------------------------------
    /**
     * Saves a {@link Savable} to the internal storage associated with the context.
     *
     * @param c
     *            The context with which to open file output
     * @param savable
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
    public static void saveToInternalStorage(Context c, Savable savable, String file, int mode)
        throws IOException
    {
        save(savable, c.openFileOutput(file, mode));
    }


    // ----------------------------------------------------------
    /**
     * Saves a {@link Savable} to the cache directory of the filesystem.
     *
     * @param c
     *            The context with which to open cache output
     * @param savable
     *            The object to save
     * @param file
     *            The path of the file to open relative to the cache directory
     * @throws IOException
     *             If an error occurs while writing to the stream
     * @see Context#getCacheDir()
     */
    public static void saveToCahce(Context c, Savable savable, String file)
        throws IOException
    {
        save(savable, new File(c.getCacheDir(), file));
    }


    // ----------------------------------------------------------
    /**
     * Saves a {@link Savable} to the primary external storage directory.
     *
     * @param savable
     *            The object to save
     * @param file
     *            The path of the file to open relative to the external storage directory
     * @throws IOException
     *             If an error occurs while writing to the stream
     * @see Environment#getExternalStorageDirectory()
     */
    public static void saveToExternalStorage(Savable savable, String file)
        throws IOException
    {
        save(savable, new File(Environment.getExternalStorageDirectory(), file));
    }


    // ----------------------------------------------------------
    /**
     * Saves a {@link Savable} to the given file.
     *
     * @param savable
     *            The object to save
     * @param file
     *            The file at which to save
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    public static void save(Savable savable, File file)
        throws IOException
    {
        save(savable, new FileOutputStream(file));
    }


    // ----------------------------------------------------------
    /**
     * Writes a {@link Savable} to the given {@link OutputStream}.
     *
     * @param savable
     *            The object to save
     * @param os
     *            The stream to which to write
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    public static void save(Savable savable, OutputStream os)
        throws IOException
    {
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(os);
            savable.save(oos);
        }
        finally
        {
            IOUtils.closeStream(os);
            IOUtils.closeStream(oos);
        }
    }
}
