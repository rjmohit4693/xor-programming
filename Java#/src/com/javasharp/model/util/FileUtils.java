package com.javasharp.model.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils
{
    public static final String HTML_FILE_EXTENSION = ".html";


    private FileUtils()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }


    public static String getFileNameNoExtension(String fileName)
    {
        int dotLocation = fileName.lastIndexOf('.');
        return dotLocation > 0 ? fileName.substring(0, dotLocation) : fileName;
    }


    public static String readFile(String file)
        throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
