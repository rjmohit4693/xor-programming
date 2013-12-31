package com.xorprogramming.io;

import android.content.Context;
import android.content.res.AssetManager;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class IOUtil
{
    private IOUtil()
    {
        // No constructor needed for utility class
    }
    
    
    public static void save(Context c, Savable s, String file, int mode)
        throws IOException
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try
        {
            fos = c.openFileOutput(file, mode);
            oos = new ObjectOutputStream(fos);
            s.save(oos);
        }
        catch (IOException ex)
        {
            Logger.log(LoggingType.ERROR, ex.getMessage());
            throw ex;
        }
        finally
        {
            if (oos != null)
            {
                try
                {
                    oos.close();
                }
                catch (IOException ex)
                {
                    Logger.log(LoggingType.WARNING, ex.getMessage());
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException ex)
                {
                    Logger.log(LoggingType.WARNING, ex.getMessage());
                }
            }
        }
    }
    
    
    public static void restore(Context c, Savable s, String file)
        throws IOException
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try
        {
            fis = c.openFileInput(file);
            ois = new ObjectInputStream(fis);
            s.restore(ois);
        }
        catch (IOException ex)
        {
            Logger.log(LoggingType.ERROR, ex.getMessage());
            throw ex;
        }
        finally
        {
            if (ois != null)
            {
                try
                {
                    ois.close();
                }
                catch (IOException ex)
                {
                    Logger.log(LoggingType.WARNING, ex.getMessage());
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException ex)
                {
                    Logger.log(LoggingType.WARNING, ex.getMessage());
                }
            }
        }
    }
    
    
    public static String readFromAssets(AssetManager assets, String file)
        throws IOException
    {
        InputStream is = null;
        Scanner scan = null;
        try
        {
            is = assets.open(file);
            scan = new Scanner(is);
            StringBuilder sb = new StringBuilder();
            while (scan.hasNextLine())
            {
                sb.append(scan.nextLine());
                sb.append("\n");
            }
            return sb.toString();
        }
        catch (IOException ex)
        {
            Logger.log(LoggingType.ERROR, ex.getMessage());
            throw ex;
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
            if (scan != null)
            {
                scan.close();
            }
        }
    }
}
