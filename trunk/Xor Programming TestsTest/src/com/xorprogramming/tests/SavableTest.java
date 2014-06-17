package com.xorprogramming.tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import com.xorprogramming.io.savable.SavableMap;
import com.xorprogramming.io.savable.SavableMapFactory;

public class SavableTest
    extends AndroidTestCase
{
    private static final String SHARED_PREFS_NAME = "test";

    @Override
    protected void setUp()
        throws Exception
    {

    }


    public void testSharedPrefSavableMap()
    {
        SharedPreferences prefs = getContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SavableMap map = SavableMapFactory.newSharedPrefsSavableMap(prefs);
        assertFalse(map.contains(null));
    }


    private void testBoolean(SavableMap map)
    {
        map.putBoolean("boolean", true);
        map.commit();
    }
}
