package com.xorprogramming.io.savable;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SharedPrefsSavableMap
    implements SavableMap
{
    private static final String     STRING_MARKER = "";
    private static final byte[]     BYTES_MARKER  = {};

    private final SharedPreferences prefs;
    private Editor                  editor;


    private Editor getEditor()
    {
        return editor == null ? (editor = prefs.edit()) : editor;
    }


    public SharedPrefsSavableMap(SharedPreferences prefs)
    {
        this.prefs = prefs;
    }


    @Override
    public boolean getBoolean(String key, boolean defaultValue)
    {
        return prefs.getBoolean(key, defaultValue);
    }


    @Override
    public byte getByte(String key, byte defaultValue)
    {
        int value = prefs.getInt(key, defaultValue);
        if (value > Byte.MAX_VALUE || value < Byte.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a byte", value));
        }
        return (byte)value;
    }


    @Override
    public byte[] getBytes(String key, byte[] defaultValue)
    {
        String bytesString = getString(key, STRING_MARKER);
        if (bytesString == STRING_MARKER)
        {
            return defaultValue;
        }
        else
        {
            return Base64.decode(bytesString, Base64.DEFAULT);
        }
    }


    @Override
    public char getChar(String key, char defaultValue)
    {
        int value = prefs.getInt(key, defaultValue);
        if (value > Character.MAX_VALUE || value < Character.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a char", value));
        }
        return (char)value;
    }


    @Override
    public short getShort(String key, short defaultValue)
    {
        int value = prefs.getInt(key, defaultValue);
        if (value > Short.MAX_VALUE || value < Short.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a short", value));
        }
        return (short)value;
    }


    @Override
    public float getFloat(String key, float defaultValue)
    {
        return prefs.getFloat(key, defaultValue);
    }


    @Override
    public double getDouble(String key, double defaultValue)
    {
        return Double.longBitsToDouble(getLong(key, Double.doubleToLongBits(defaultValue)));
    }


    @Override
    public int getInt(String key, int defaultValue)
    {
        return prefs.getInt(key, defaultValue);
    }


    @Override
    public long getLong(String key, long defaultValue)
    {
        return prefs.getLong(key, defaultValue);
    }


    @Override
    public String getString(String key, String defaultValue)
    {
        return prefs.getString(key, defaultValue);
    }


    @Override
    public Serializable getSerializable(String key, Serializable defaultValue)
    {
        try
        {
            byte[] bytes = getBytes(key, BYTES_MARKER);
            if (bytes == BYTES_MARKER)
            {
                return defaultValue;
            }
            InputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(is);
            return (Serializable)ois.readObject();
        }
        catch (Exception ex)
        {
            return defaultValue;
        }
    }


    @Override
    public void putBoolean(String key, boolean value)
    {
        getEditor().putBoolean(key, value);
    }


    @Override
    public void putByte(String key, byte value)
    {
        getEditor().putInt(key, value);
    }


    @Override
    public void putBytes(String key, byte[] value)
    {
        putString(key, Base64.encodeToString(value, Base64.DEFAULT));
    }


    @Override
    public void putChar(String key, char value)
    {
        getEditor().putInt(key, value);
    }


    @Override
    public void putShort(String key, short value)
    {
        putInt(key, value);
    }


    @Override
    public void putFloat(String key, float value)
    {
        getEditor().putFloat(key, value);
    }


    @Override
    public void putDouble(String key, double value)
    {
        putLong(key, Double.doubleToLongBits(value));
    }


    @Override
    public void putInt(String key, int value)
    {
        getEditor().putInt(key, value);
    }


    @Override
    public void putLong(String key, long value)
    {
        getEditor().putLong(key, value);
    }


    @Override
    public void putString(String key, String value)
    {
        getEditor().putString(key, value);
    }


    @Override
    public void putSerializable(String key, Serializable value)
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            putBytes(key, bos.toByteArray());
        }
        catch (Exception ex)
        {
            // Should never happen
        }
    }


    @Override
    public boolean commit()
    {
        return editor == null ? true : editor.commit();
    }


    @Override
    public boolean contains(String key)
    {
        return prefs.contains(key);
    }

}
