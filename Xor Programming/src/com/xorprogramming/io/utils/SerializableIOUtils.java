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

public final class SerializableIOUtils
{
    private SerializableIOUtils()
    {
        // No constructor needed
    }


    public static <T extends Serializable> T readFromInternalStorage(Context c, String file)
        throws IOException,
        ClassNotFoundException
    {
        return read(c.openFileInput(file));
    }


    public static <T extends Serializable> T readFromCahce(Context c, String file)
        throws IOException,
        ClassNotFoundException
    {
        return read(new File(c.getCacheDir(), file));
    }


    public static <T extends Serializable> T readFromExternalStorage(String file)
        throws IOException,
        ClassNotFoundException
    {
        return read(new File(Environment.getExternalStorageDirectory(), file));
    }


    public static <T extends Serializable> T read(File file)
        throws IOException,
        ClassNotFoundException
    {
        return read(new FileInputStream(file));
    }


    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T read(InputStream is)
        throws IOException,
        ClassNotFoundException
    {
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(is);
            return (T)ois.readObject();
        }
        catch (ClassCastException ex)
        {
            throw new ClassNotFoundException("Cannot cast Serializable to generic type", ex);
        }
        finally
        {
            IOUtils.closeStream(is);
            IOUtils.closeStream(ois);
        }
    }


    public static void writeToInternalStorage(Context c, String fileName, int mode, Serializable out)
        throws IOException
    {
        write(c.openFileOutput(fileName, mode), out);
    }


    public static void writeToExternalStorage(String fileName, Serializable out)
        throws IOException
    {
        write(new File(Environment.getExternalStorageDirectory(), fileName), out);
    }


    public static void writeToCache(Context c, String fileName, Serializable out)
        throws IOException
    {
        write(new File(c.getCacheDir(), fileName), out);
    }


    public static void write(File file, Serializable out)
        throws IOException
    {
        write(new FileOutputStream(file), out);
    }


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
