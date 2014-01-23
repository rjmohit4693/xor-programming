package com.xorprogramming.io.savable;

import android.content.Context;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SavableUtil
{
    private SavableUtil()
    {
        // No constructor needed for utility class
    }


    public static void save(Context c, Savable s, String file, int mode)
        throws IOException, SaveException
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try
        {
            fos = c.openFileOutput(file, mode);
            oos = new ObjectOutputStream(fos);
            s.save(oos);
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
        throws IOException, SaveException
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try
        {
            fis = c.openFileInput(file);
            ois = new ObjectInputStream(fis);
            s.restore(ois);
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

}
