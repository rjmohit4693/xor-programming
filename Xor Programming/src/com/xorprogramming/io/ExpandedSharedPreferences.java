package com.xorprogramming.io;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class ExpandedSharedPreferences
    implements SharedPreferences
{
    private static final String     STRING_MARKER = "";
    private static final byte[]     BYTES_MARKER  = {};
    private final SharedPreferences preferences;


    public ExpandedSharedPreferences(SharedPreferences preferences)
    {
        if (preferences == null)
        {
            throw new NullPointerException("The SharedPreferences must be non-null");
        }
        this.preferences = preferences;
    }


    @Override
    public Map<String, ?> getAll()
    {
        return preferences.getAll();
    }


    @Override
    public String getString(String key, String defValue)
    {
        return preferences.getString(key, defValue);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues)
    {
        return preferences.getStringSet(key, defValues);
    }


    @Override
    public int getInt(String key, int defValue)
    {
        return preferences.getInt(key, defValue);
    }


    @Override
    public long getLong(String key, long defValue)
    {
        return preferences.getLong(key, defValue);
    }


    @Override
    public float getFloat(String key, float defValue)
    {
        return preferences.getFloat(key, defValue);
    }


    @Override
    public boolean getBoolean(String key, boolean defValue)
    {
        return preferences.getBoolean(key, defValue);
    }


    public byte getByte(String key, byte defaultValue)
    {
        int value = preferences.getInt(key, defaultValue);
        if (value > Byte.MAX_VALUE || value < Byte.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a byte", value));
        }
        return (byte)value;
    }


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


    public char getChar(String key, char defaultValue)
    {
        int value = preferences.getInt(key, defaultValue);
        if (value > Character.MAX_VALUE || value < Character.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a char", value));
        }
        return (char)value;
    }


    public short getShort(String key, short defaultValue)
    {
        int value = preferences.getInt(key, defaultValue);
        if (value > Short.MAX_VALUE || value < Short.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a short", value));
        }
        return (short)value;
    }


    public double getDouble(String key, double defaultValue)
    {
        return Double.longBitsToDouble(getLong(key, Double.doubleToLongBits(defaultValue)));
    }


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
    public boolean contains(String key)
    {
        return preferences.contains(key);
    }


    @Override
    public Editor edit()
    {
        return preferences.edit();
    }


    public ExandedEditor expandedEdit()
    {
        return new ExandedEditor(edit());
    }


    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)
    {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }


    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)
    {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


    public class ExandedEditor
    {
        private final Editor edit;


        public ExandedEditor(Editor edit)
        {
            this.edit = edit;
        }


        public ExandedEditor putString(String key, String value)
        {
            edit.putString(key, value);
            return this;
        }


        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public ExandedEditor putStringSet(String key, Set<String> values)
        {
            edit.putStringSet(key, values);
            return this;
        }


        public ExandedEditor putInt(String key, int value)
        {
            edit.putInt(key, value);
            return this;
        }


        public ExandedEditor putLong(String key, long value)
        {
            edit.putLong(key, value);
            return this;
        }


        public ExandedEditor putFloat(String key, float value)
        {
            edit.putFloat(key, value);
            return this;
        }


        public ExandedEditor putBoolean(String key, boolean value)
        {
            edit.putBoolean(key, value);
            return this;
        }


        public ExandedEditor putByte(String key, byte value)
        {
            return putInt(key, value);
        }


        public ExandedEditor putBytes(String key, byte[] value)
        {
            return putString(key, Base64.encodeToString(value, Base64.DEFAULT));
        }


        public ExandedEditor putChar(String key, char value)
        {
            return putInt(key, value);
        }


        public ExandedEditor putShort(String key, short value)
        {
            return putInt(key, value);
        }


        public ExandedEditor putDouble(String key, double value)
        {
            return putLong(key, Double.doubleToLongBits(value));
        }


        public ExandedEditor putSerializable(String key, Serializable value)
        {
            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                putBytes(key, bos.toByteArray());
            }
            catch (NotSerializableException ex)
            {
                throw new RuntimeException(ex.getMessage());
            }
            catch (IOException ex)
            {
                // Should never happen
                throw new AssertionError(ex);
            }
            return this;
        }


        public ExandedEditor remove(String key)
        {
            edit.remove(key);
            return this;
        }


        public ExandedEditor clear()
        {
            edit.clear();
            return this;
        }


        public boolean commit()
        {
            return edit.commit();
        }


        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public void apply()
        {
            edit.apply();
        }

    }
}
