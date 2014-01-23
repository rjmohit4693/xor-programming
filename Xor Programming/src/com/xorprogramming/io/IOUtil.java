package com.xorprogramming.io;

import android.content.Context;
import android.content.res.AssetManager;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class IOUtil
{
    private IOUtil()
    {
        // No constructor needed for utility class
    }


    public static String readFromAssets(AssetManager assets, String file)
        throws IOException
    {
        InputStream is = null;
        try
        {
            return read(assets.open(file));
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException ex)
                {
                    Logger.log(LoggingType.WARNING, ex.getMessage());
                }
            }
        }
    }


    public static String readFromInternalStorage(Context c, String file) throws IOException
    {
        InputStream is = null;
        try
        {
            return read(c.openFileInput(file));
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException ex)
                {
                    Logger.log(LoggingType.WARNING, ex.getMessage());
                }
            }
        }
    }


    private static String read(InputStream is)
        throws IOException
    {
        Scanner scan = null;
        try
        {
            scan = new Scanner(is);
            StringBuilder sb = new StringBuilder();
            while (scan.hasNextLine())
            {
                sb.append(scan.nextLine());
                sb.append("\n");
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
