package com.xorprogramming.tests;

import android.content.Context;
import android.graphics.RectF;
import android.test.AndroidTestCase;
import com.xorprogramming.io.ExpandedSharedPreferences;
import com.xorprogramming.io.ExpandedSharedPreferences.ExandedEditor;
import java.io.Serializable;
import java.util.Arrays;

public class ExpandedSharedPreferencesTest
    extends AndroidTestCase
{
    private static final String       NAME = ExpandedSharedPreferencesTest.class.getSimpleName();
    private ExpandedSharedPreferences prefs;


    @Override
    protected void setUp()
    {
        prefs = new ExpandedSharedPreferences(getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE));
        prefs.edit().clear().commit();
    }


    public void testContains()
    {
        assertFalse(prefs.contains("test"));
        assertFalse(prefs.contains(""));

        ExandedEditor edit = prefs.expandedEdit();
        edit.putBoolean("test", true);
        assertFalse(prefs.contains("test"));
        edit.apply();

        assertTrue(prefs.contains("test"));
        assertFalse(prefs.contains("test "));
        assertFalse(prefs.contains(""));

        edit.putByte("byte", (byte)3);
        edit.putString("string", "string");
        edit.apply();

        assertTrue(prefs.contains("string"));
        assertTrue(prefs.contains("byte"));

        edit.clear().apply();

        assertFalse(prefs.contains("string"));
        assertFalse(prefs.contains("byte"));
        assertFalse(prefs.contains("test"));
    }


    public void testExpandedMethods()
    {
        ExandedEditor edit = prefs.expandedEdit();
        edit.putByte("byte", (byte)1);
        edit.putBytes("bytes", new byte[] { 1, 2, 3, 4, 5 });
        edit.putChar("char", 'q');
        edit.putDouble("double", Math.PI);
        edit.putShort("short", (short)43);

        Serializable s = new Test("hi", 42);
        edit.putSerializable("serial", s);

        edit.apply();

        assertEquals((byte)1, prefs.getByte("byte", (byte)0));
        assertEquals((byte)9, prefs.getByte("does not exits", (byte)9));
        assertTrue(Arrays.equals(new byte[] { 1, 2, 3, 4, 5 }, prefs.getBytes("bytes", null)));
        assertNull(prefs.getBytes("does not exits", null));
        assertEquals('q', prefs.getChar("char", 'a'));
        assertEquals('5', prefs.getChar("does not exits", '5'));
        assertEquals(Math.PI, prefs.getDouble("double", 2));
        assertEquals(-9.9, prefs.getDouble("does not exits", -9.9));
        assertEquals((short)43, prefs.getShort("short", (short)0));
        assertEquals((short)4444, prefs.getShort("does not exits", (short)4444));
        assertEquals(s, prefs.getSerializable("serial", null));
        assertNull(prefs.getSerializable("does not exits", null));

        assertEquals(6, prefs.getAll().size());

        edit.clear();
    }


    public void testExceptions()
    {
        ExandedEditor edit = prefs.expandedEdit();

        Exception ex = null;
        try
        {
            edit.putSerializable("serial", new CantSerializeMe()).commit();
        }
        catch (IllegalArgumentException iae)
        {
            ex = iae;
        }

        assertTrue(ex instanceof IllegalArgumentException);

        edit.putBytes("corrupt", new byte[] { 3, 1, 4 }).commit();

        ex = null;
        try
        {
            prefs.getSerializable("corrupt", null);
        }
        catch (ClassCastException cce)
        {
            ex = cce;
        }

        assertTrue(ex instanceof ClassCastException);

        edit.putInt("int", Integer.MAX_VALUE).commit();

        ex = null;
        try
        {
            prefs.getShort("int", (short)0);
        }
        catch (ClassCastException cce)
        {
            ex = cce;
        }

        assertTrue(ex instanceof ClassCastException);
    }


    private static class Test
        implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private String            a;
        private int               b;


        public Test(String a, int b)
        {
            this.a = a;
            this.b = b;
        }


        @Override
        public boolean equals(Object o)
        {
            if (o instanceof Test)
            {
                Test t = (Test)o;
                return a.equals(t.a) && b == t.b;
            }
            return false;
        }
    }


    private static class CantSerializeMe
        implements Serializable
    {
        private static final long serialVersionUID = 1L;
        @SuppressWarnings("unused")
        private final RectF       rect             = new RectF();
    }
}
