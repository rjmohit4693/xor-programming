package com.javasharp.model.util;

import java.io.File;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 *  Reads the content from a file
 *
 *  @author Taylor Mattison, Steven Roberts, Kais Sorrels
 *  @version May 5, 2013
 */
public class FileReader
{
    private String path;

    // ----------------------------------------------------------
    /**
     * Create a new FileReader object.
     * @param path the file location
     */
    public FileReader(String path)
    {
        this.path = path;
    }

    // ----------------------------------------------------------
    /**
     * Reads the text from the file
     * @return the content of the file
     * @throws Exception
     */
    public String read() throws Exception
    {
        Scanner scan = new Scanner(new File(path));
        StringBuilder sb = new StringBuilder();
        while (scan.hasNextLine())
        {
            sb.append(scan.nextLine());
        }
        scan.close();
        return sb.toString();
    }
}