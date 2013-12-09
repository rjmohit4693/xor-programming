package com.xorprogramming.io;

import android.content.res.AssetManager;
import java.io.IOException;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Reads the contents of a file within the assets directory or any subdirectories
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class FileReader
{
    private final AssetManager assets;
    private final String       path;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new FileReader object.
     * 
     * @param assets
     *            The AssetsManager required to create an InputStream
     * @param path
     *            The path relative to the assets directory
     */
    public FileReader(AssetManager assets, String path)
    {
        if (assets == null)
        {
            throw new NullPointerException("The assets must be non-null");
        }
        else if (path == null)
        {
            throw new NullPointerException("The path must be non-null");
        }
        this.assets = assets;
        this.path = path;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Reads the contents of the file and writes to a string
     * 
     * @return The content of the file
     * @throws IOException
     */
    public String read()
        throws IOException
    {
        Scanner scan = null;
        try
        {
            scan = new Scanner(assets.open(path));
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
