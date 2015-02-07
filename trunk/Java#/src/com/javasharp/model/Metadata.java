package com.javasharp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores arbitrary metadata about a score, such as author, date, copyright info, etc.
 */
public class Metadata
{
    
    private final Map<String, String> metaData;
    
    
    public Metadata()
    {
        metaData = new HashMap<String, String>();
    }
    
    
    public String getMetaData(String key)
    {
        return metaData.get(key);
    }
    
    
    public void addMetaData(String key, String value)
    {
        metaData.put(key, value);
    }
    
}
