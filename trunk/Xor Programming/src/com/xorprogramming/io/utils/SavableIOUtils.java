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

public final class SavableIOUtils
{
    private SavableIOUtils()
    {
        // No constructor needed
    }


    public static void saveToInternalStorage(Context c, Savable savable, String file, int mode)
        throws IOException,
        ClassNotFoundException
    {
        save(savable, c.openFileOutput(file, mode));
    }


    public static void saveToCahce(Context c, Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        save(savable, new File(c.getCacheDir(), file));
    }


    public static void saveToExternalStorage(Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        save(savable, new File(Environment.getExternalStorageDirectory(), file));
    }


    public static void save(Savable savable, File file)
        throws IOException
    {
        save(savable, new FileOutputStream(file));
    }


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


    public static void restoreFromInternalStorage(Context c, Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, c.openFileInput(file));
    }


    public static void restoreFromCahce(Context c, Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, new File(c.getCacheDir(), file));
    }


    public static void restoreFromExternalStorage(Savable savable, String file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, new File(Environment.getExternalStorageDirectory(), file));
    }


    public static void restore(Savable savable, File file)
        throws IOException,
        ClassNotFoundException
    {
        restore(savable, new FileInputStream(file));
    }


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
}
