package com.javasharp.model.util;

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
}
