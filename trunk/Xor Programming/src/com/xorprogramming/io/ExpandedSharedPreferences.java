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

// -------------------------------------------------------------------------
/**
 * <p>
 * A wrapper class for {@link SharedPreferences} that provides support for saving and retrieving additional data types.
 * Note that there are two ways to modify the data. First, there is {@link #edit()}, which return a standard
 * {@link android.content.SharedPreferences.Editor}. Second, there is {@link #expandedEdit()}, which return a
 * {@link ExandedEditor}.
 * </p>
 * <p>
 * <b>Example Usage:</b><br/>
 *
 * <pre>
 * {@code SharedPreferences prefs = context.getSharedPreferences(name, mode);}
 * {@code ExpandedSharedPreferences prefs = new ExpandedSharedPreferences();}
 * {@code prefs.getSerializable("cereal", null);}
 * {@code prefs.expandedEdit().putDouble("test", 42).commit();}
 * </pre>
 *
 * </p>
 *
 * @see SharedPreferences
 * @see ExpandedSharedPreferences.ExandedEditor
 * @author Steven Roberts
 * @version 1.0.1
 */
public class ExpandedSharedPreferences
    implements SharedPreferences
{
    private static final String     STRING_MARKER = "";
    private static final byte[]     BYTES_MARKER  = {};
    private final SharedPreferences preferences;


    // ----------------------------------------------------------
    /**
     * Create a new ExpandedSharedPreferences object that wraps the given preferences.
     *
     * @param preferences
     *            The preferences to wrap
     */
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


    // ----------------------------------------------------------
    /**
     * Retrieve a byte value from the preferences.
     *
     * @param key
     *            The name of the preference to retrieve.
     * @param defaultValue
     *            Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     *             If there is a preference with this name that is not a byte
     */
    public byte getByte(String key, byte defaultValue)
    {
        int value = preferences.getInt(key, defaultValue);
        if (value > Byte.MAX_VALUE || value < Byte.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a byte", value));
        }
        return (byte)value;
    }


    // ----------------------------------------------------------
    /**
     * Retrieve a byte array value from the preferences.
     *
     * @param key
     *            The name of the preference to retrieve.
     * @param defaultValue
     *            Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     *             If there is a preference with this name that is not a byte array
     */
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


    // ----------------------------------------------------------
    /**
     * Retrieve a cgar value from the preferences.
     *
     * @param key
     *            The name of the preference to retrieve.
     * @param defaultValue
     *            Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     *             If there is a preference with this name that is not a char
     */
    public char getChar(String key, char defaultValue)
    {
        int value = preferences.getInt(key, defaultValue);
        if (value > Character.MAX_VALUE || value < Character.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a char", value));
        }
        return (char)value;
    }


    // ----------------------------------------------------------
    /**
     * Retrieve a short value from the preferences.
     *
     * @param key
     *            The name of the preference to retrieve.
     * @param defaultValue
     *            Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     *             If there is a preference with this name that is not a short
     */
    public short getShort(String key, short defaultValue)
    {
        int value = preferences.getInt(key, defaultValue);
        if (value > Short.MAX_VALUE || value < Short.MIN_VALUE)
        {
            throw new ClassCastException(String.format("Cannot convert %d to a short", value));
        }
        return (short)value;
    }


    // ----------------------------------------------------------
    /**
     * Retrieve a double value from the preferences.
     *
     * @param key
     *            The name of the preference to retrieve.
     * @param defaultValue
     *            Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     *             If there is a preference with this name that is not a double
     */
    public double getDouble(String key, double defaultValue)
    {
        return Double.longBitsToDouble(getLong(key, Double.doubleToLongBits(defaultValue)));
    }


    // ----------------------------------------------------------
    /**
     * Retrieve a Serializable value from the preferences.
     *
     * @param key
     *            The name of the preference to retrieve.
     * @param defaultValue
     *            Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     *             If there is a preference with this name that is not a Serializable
     */
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
            throw new ClassCastException(ex.getMessage());
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


    // ----------------------------------------------------------
    /**
     * Create a new {@link ExandedEditor} for these preferences, through which you can make modifications to the data in
     * the preferences and atomically commit those changes back to the SharedPreferences object. Note that you must call
     * {@link ExandedEditor#commit} to have any changes you perform in the Editor actually show up in the
     * SharedPreferences.
     *
     * @return Returns a new instance of the {@link ExandedEditor} interface, allowing you to modify the values in this
     *         SharedPreferences object.
     */
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


    // -------------------------------------------------------------------------
    /**
     * Interface used for modifying values in a {@link ExpandedSharedPreferences} object. All changes you make in an
     * editor are batched, and not copied back to the original {@link ExpandedSharedPreferences} until you call
     * {@link #commit} or {@link #apply}
     *
     * @author Steven Roberts
     * @version 1.0.0
     */
    public static class ExandedEditor
    {
        private final Editor edit;


        private ExandedEditor(Editor edit)
        {
            this.edit = edit;
        }


        // ----------------------------------------------------------
        /**
         * Set a String value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putString(String key, String value)
        {
            edit.putString(key, value);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * Set a set of String values in the preferences editor, to be written back once {@link #commit} or
         * {@link #apply} are called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param values
         *            The new values for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public ExandedEditor putStringSet(String key, Set<String> values)
        {
            edit.putStringSet(key, values);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * Set a int value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putInt(String key, int value)
        {
            edit.putInt(key, value);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * Set a long value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putLong(String key, long value)
        {
            edit.putLong(key, value);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * Set a float value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putFloat(String key, float value)
        {
            edit.putFloat(key, value);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * Set a boolean value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putBoolean(String key, boolean value)
        {
            edit.putBoolean(key, value);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * Set a byte value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putByte(String key, byte value)
        {
            return putInt(key, value);
        }


        // ----------------------------------------------------------
        /**
         * Set a byte array value in the preferences editor, to be written back once {@link #commit} or {@link #apply}
         * are called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putBytes(String key, byte[] value)
        {
            return putString(key, Base64.encodeToString(value, Base64.DEFAULT));
        }


        // ----------------------------------------------------------
        /**
         * Set a char value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putChar(String key, char value)
        {
            return putInt(key, value);
        }


        // ----------------------------------------------------------
        /**
         * Set a short value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putShort(String key, short value)
        {
            return putInt(key, value);
        }


        // ----------------------------------------------------------
        /**
         * Set a double value in the preferences editor, to be written back once {@link #commit} or {@link #apply} are
         * called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor putDouble(String key, double value)
        {
            return putLong(key, Double.doubleToLongBits(value));
        }


        // ----------------------------------------------------------
        /**
         * Set a Serializable value in the preferences editor, to be written back once {@link #commit} or {@link #apply}
         * are called.
         *
         * @param key
         *            The name of the preference to modify.
         * @param value
         *            The new value for the preference. Supplying null as the value is equivalent to calling
         *            {@link #remove(String)} with this key.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         * @throws IllegalArgumentException
         *             if value cannot be serialized
         */
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
                throw new IllegalArgumentException(ex.getMessage());
            }
            catch (IOException ex)
            {
                // Should never happen
                throw new AssertionError(ex);
            }
            return this;
        }


        // ----------------------------------------------------------
        /**
         * <p>
         * Mark in the editor that a preference value should be removed, which will be done in the actual preferences
         * once {@link #commit} is called.
         * </p>
         * <p>
         * Note that when committing back to the preferences, all removals are done first, regardless of whether you
         * called remove before or after put methods on this editor.
         * </p>
         *
         * @param key
         *            The name of the preference to remove.
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor remove(String key)
        {
            edit.remove(key);
            return this;
        }


        // ----------------------------------------------------------
        /**
         * <p>
         * Mark in the editor to remove all values from the preferences. Once commit is called, the only remaining
         * preferences will be any that you have defined in this editor.
         * </p>
         * <p>
         * Note that when committing back to the preferences, the clear is done first, regardless of whether you called
         * clear before or after put methods on this editor.
         * </p>
         *
         * @return Returns a reference to the same ExandedEditor object, so you can chain put calls together.
         */
        public ExandedEditor clear()
        {
            edit.clear();
            return this;
        }


        // ----------------------------------------------------------
        /**
         * <p>
         * Commit your preferences changes back from this ExandedEditor to the {@link ExpandedSharedPreferences} object
         * it is editing. This atomically performs the requested modifications, replacing whatever is currently in the
         * ExpandedSharedPreferences.
         * </p>
         * <p>
         * Note that when two editors are modifying preferences at the same time, the last one to call commit wins.
         * </p>
         * <p>
         * If you don't care about the return value and you're using this from your application's main thread, consider
         * using {@link #apply} instead.
         * </p>
         *
         * @return Returns true if the new values were successfully written to persistent storage.
         */
        public boolean commit()
        {
            return edit.commit();
        }


        // ----------------------------------------------------------
        /**
         * <p>
         * Commit your preferences changes back from this {@link ExandedEditor} to the {@link ExpandedSharedPreferences}
         * object it is editing. This atomically performs the requested modifications, replacing whatever is currently
         * in the ExpandedSharedPreferences.
         * </p>
         * <p>
         * Note that when two editors are modifying preferences at the same time, the last one to call apply wins.
         * </p>
         * <p>
         * Unlike {@link #commit}, which writes its preferences out to persistent storage synchronously, apply commits
         * its changes to the in-memory ExpandedSharedPreferences immediately but starts an asynchronous commit to disk
         * and you won't be notified of any failures. If another editor on this ExpandedSharedPreferences does a regular
         * commit while a apply is still outstanding, the commit will block until all async commits are completed as
         * well as the commit itself.
         * </p>
         * <p>
         * As {@link SharedPreferences} instances are singletons within a process, it's safe to replace any instance of
         * commit with apply if you were already ignoring the return value.
         * </p>
         * <p>
         * You don't need to worry about Android component lifecycles and their interaction with apply() writing to
         * disk. The framework makes sure in-flight disk writes from apply() complete before switching states.
         * </p>
         */
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public void apply()
        {
            edit.apply();
        }

    }
}
