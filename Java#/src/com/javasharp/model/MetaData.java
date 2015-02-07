package com.javasharp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores arbitrary metadata about a score, such as author, date, copyright info, etc.
 *
 */
public class MetaData
{
	
	private final Map<String, String> metaData;
	
	public MetaData() {
		metaData = new HashMap<String, String>();
	}
    

	public String getMetaData(String key) {
		return metaData.get(key);
	}
	
	public void addMetaData(String key, String value) {
		metaData.put(key, value);
	}
	
}
