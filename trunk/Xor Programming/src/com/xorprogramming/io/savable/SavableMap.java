package com.xorprogramming.io.savable;

import java.io.Serializable;

public interface SavableMap
{
    boolean getBoolean(String key, boolean defaultValue);


    byte getByte(String key, byte defaultValue);


    byte[] getBytes(String key, byte[] defaultValue);


    char getChar(String key, char defaultValue);


    short getShort(String key, short defaultValue);


    float getFloat(String key, float defaultValue);


    double getDouble(String key, double defaultValue);


    int getInt(String key, int defaultValue);


    long getLong(String key, long defaultValue);


    String getString(String key, String defaultValue);


    Serializable getSerializable(String key, Serializable defaultValue);


    void putBoolean(String key, boolean value);


    void putByte(String key, byte value);


    void putBytes(String key, byte[] value);


    void putChar(String key, char value);


    void putShort(String key, short value);


    void putFloat(String key, float value);


    void putDouble(String key, double value);


    void putInt(String key, int value);


    void putLong(String key, long value);


    void putString(String key, String value);


    void putSerializable(String key, Serializable value);


    boolean commit();


    boolean contains(String key);
}
