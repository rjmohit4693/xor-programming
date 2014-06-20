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
        return read(new File(c.getCacheDir(), file));
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
        return read(new File(Environment.getExternalStorageDirectory(), file));
    }


    public static String read(File file)
        throws IOException
    {
        return read(new FileInputStream(file));
    }


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


    public static void writeToInternalStorage(Context c, String fileName, int mode, String out)
        throws IOException
    {
        write(c.openFileOutput(fileName, mode), out);
    }


    public static void writeToExternalStorage(String fileName, String out)
        throws IOException
    {
        write(new File(Environment.getExternalStorageDirectory(), fileName), out);
    }


    public static void writeToCache(Context c, String fileName, String out)
        throws IOException
    {
        write(new File(c.getCacheDir(), fileName), out);
    }


    public static void write(File file, String out)
        throws IOException
    {
        write(new FileOutputStream(file), out);
    }


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
