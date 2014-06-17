package com.xorprogramming.io.savable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import com.xorprogramming.io.IOUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FileSavableMap
    implements SavableMap
{
    private final Map<String, Object> map;
    private final File file;


    @SuppressWarnings("unchecked")
    public FileSavableMap(File file)
    {
        ObjectInputStream ois = null;
        Map<String, Object> temp;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(file));
            temp = (Map<String, Object>)ois.readObject();
        }
        catch (Exception ex)
        {
            temp = new HashMap<String, Object>();
        }
        finally
        {
            IOUtil.closeStream(ois);
        }
        map = temp;
        this.file = file;
    }


    @Override
    public boolean getBoolean(String key, boolean defaultValue)
    {
        Boolean value = (Boolean)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public byte getByte(String key, byte defaultValue)
    {
        Byte value = (Byte)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public byte[] getBytes(String key, byte[] defaultValue)
    {
        byte[] value = (byte[])map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public char getChar(String key, char defaultValue)
    {

        Character value = (Character)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public short getShort(String key, short defaultValue)
    {
        Short value = (Short)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public float getFloat(String key, float defaultValue)
    {
        Float value = (Float)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public double getDouble(String key, double defaultValue)
    {
        Double value = (Double)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public int getInt(String key, int defaultValue)
    {
        Integer value = (Integer)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public long getLong(String key, long defaultValue)
    {
        Long value = (Long)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public String getString(String key, String defaultValue)
    {
        String value = (String)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public Serializable getSerializable(String key, Serializable defaultValue)
    {
        Serializable value = (Serializable)map.get(key);
        return value == null ? defaultValue : value;
    }


    @Override
    public void putBoolean(String key, boolean value)
    {
        map.put(key, value);
    }


    @Override
    public void putByte(String key, byte value)
    {
        map.put(key, value);
    }


    @Override
    public void putBytes(String key, byte[] value)
    {
        map.put(key, value);
    }


    @Override
    public void putChar(String key, char value)
    {
        map.put(key, value);
    }


    @Override
    public void putShort(String key, short value)
    {
        map.put(key, value);
    }


    @Override
    public void putFloat(String key, float value)
    {
        map.put(key, value);
    }


    @Override
    public void putDouble(String key, double value)
    {
        map.put(key, value);
    }


    @Override
    public void putInt(String key, int value)
    {
        map.put(key, value);
    }


    @Override
    public void putLong(String key, long value)
    {
        map.put(key, value);
    }


    @Override
    public void putString(String key, String value)
    {
        map.put(key, value);
    }


    @Override
    public void putSerializable(String key, Serializable value)
    {
        map.put(key, value);
    }


    @Override
    public boolean commit()
    {
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(file, false));
            oos.writeObject(map);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
        finally
        {
            IOUtil.closeStream(oos);
        }
    }


    @Override
    public boolean contains(String key)
    {
        return map.containsKey(key);
    }

}
