package com.xorprogramming.io.savable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import java.io.File;

public final class SavableMapFactory
{
    private SavableMapFactory()
    {
        // No constructor needed
    }


    public static SavableMap newSharedPrefsSavableMap(SharedPreferences prefs)
    {
        return new SharedPrefsSavableMap(prefs);
    }


    public static SavableMap newCacheSavableMap(Context c, String path)
    {
        return new FileSavableMap(new File(c.getCacheDir(), path));
    }


    public static SavableMap newInternalStorageSavableMap(Context c, String path)
    {
        return new FileSavableMap(new File(c.getFilesDir(), path));
    }


    public static SavableMap newExternalStorageSavableMap(String path)
    {
        return new FileSavableMap(new File(Environment.getExternalStorageDirectory(), path));
    }
}
