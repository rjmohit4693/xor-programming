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
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

// -------------------------------------------------------------------------
/**
 * A utility class containing convenient methods for reading and writing a {@link Serializable}.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class SerializableIOUtils
{
    private SerializableIOUtils()
    {
        // No constructor needed
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads a {@link Serializable} from the internal storage associated with the context.
     *
     * @param c
     *            The context with which to open file input
     * @param file
     *            The name of the file to open; can not contain path separators.
     * @return The deserialized object
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @throws ClassCastException
     *             If the deserialized object cannot be cast to generic type {@code T}
     * @see Context#openFileInput(String)
     */
    public static <T extends Serializable> T readFromInternalStorage(Context c, String file)
        throws IOException,
        ClassNotFoundException
    {
        return read(c.openFileInput(file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads a {@link Serializable} from the cache directory of the filesystem.
     *
     * @param c
     *            The context with which to open cache input
     * @param file
     *            The path of the file to open relative to the cache directory
     * @return The deserialized object
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @throws ClassCastException
     *             If the deserialized object cannot be cast to generic type {@code T}
     * @see Context#getCacheDir()
     */
    public static <T extends Serializable> T readFromCahce(Context c, String file)
        throws IOException,
        ClassNotFoundException
    {
        return read(new File(c.getCacheDir(), file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads a {@link Serializable} from the primary external storage directory.
     *
     * @param file
     *            The path of the file to open relative to the external storage directory
     * @return The deserialized object
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @throws ClassCastException
     *             If the deserialized object cannot be cast to generic type {@code T}
     * @see Environment#getExternalStorageDirectory()
     */
    public static <T extends Serializable> T readFromExternalStorage(String file)
        throws IOException,
        ClassNotFoundException
    {
        return read(new File(Environment.getExternalStorageDirectory(), file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads a {@link Serializable} from a file.
     *
     * @param file
     *            The file from which to read
     * @return The deserialized object
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @throws ClassCastException
     *             If the deserialized object cannot be cast to generic type {@code T}
     */
    public static <T extends Serializable> T read(File file)
        throws IOException,
        ClassNotFoundException
    {
        return read(new FileInputStream(file));
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads a {@link Serializable} from an {@link InputStream}.
     *
     * @param is
     *            The stream from which to read
     * @return The deserialized object
     * @throws IOException
     *             If an error occurs while reading from the stream
     * @throws ClassNotFoundException
     *             If the class of one of the objects in the object graph cannot be found
     * @throws ClassCastException
     *             If the deserialized object cannot be cast to generic type {@code T}
     */
    public static <T extends Serializable> T read(InputStream is)
        throws IOException,
        ClassNotFoundException
    {
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(is);
            
            @SuppressWarnings("unchecked")
            T retValue = (T)ois.readObject();
            
            return retValue;
        }
        finally
        {
            IOUtils.closeStream(is);
            IOUtils.closeStream(ois);
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Saves a {@link Serializable} to the internal storage associated with the context.
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
    public static void writeToInternalStorage(Context c, String file, int mode, Serializable out)
        throws IOException
    {
        write(c.openFileOutput(file, mode), out);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link Serializable} to the cache directory of the filesystem.
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
    public static void writeToCache(Context c, String file, Serializable out)
        throws IOException
    {
        write(new File(c.getCacheDir(), file), out);
    }
    
    
    // ----------------------------------------------------------``
    /**
     * Writes a {@link Serializable} to the primary external storage directory.
     *
     * @param out
     *            The object to save
     * @param file
     *            The path of the file to open relative to the external storage directory
     * @throws IOException
     *             If an error occurs while writing to the stream
     * @see Environment#getExternalStorageDirectory()
     */
    public static void writeToExternalStorage(String file, Serializable out)
        throws IOException
    {
        write(new File(Environment.getExternalStorageDirectory(), file), out);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link Serializable} to the given file.
     *
     * @param out
     *            The object to save
     * @param file
     *            The file at which to save
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    public static void write(File file, Serializable out)
        throws IOException
    {
        write(new FileOutputStream(file), out);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Writes a {@link Serializable} to the given {@link OutputStream}.
     *
     * @param out
     *            The object to save
     * @param os
     *            The stream to which to write
     * @throws IOException
     *             If an error occurs while writing to the stream
     */
    public static void write(OutputStream os, Serializable out)
        throws IOException
    {
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(os);
            oos.writeObject(out);
        }
        finally
        {
            IOUtils.closeStream(os);
            IOUtils.closeStream(oos);
        }
    }
}
