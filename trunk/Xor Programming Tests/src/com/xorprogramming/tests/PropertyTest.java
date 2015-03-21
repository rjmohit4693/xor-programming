package com.xorprogramming.tests;

import android.test.AndroidTestCase;
import android.widget.TextView;
import com.xorprogramming.xor2d.binding.TextProperty;

public class PropertyTest
    extends AndroidTestCase
{
    public void testSimpleBinding()
    {
        TextView tv1 = new TextView(getContext());
        tv1.setText("Test 0");
        TextView tv2 = new TextView(getContext());

        TextProperty tp1 = new TextProperty(tv1);
        TextProperty tp2 = new TextProperty(tv2);
        tp1.bindTwoWay(tp2);

        assertEquals("Test 0", tp1.getValue());
        assertEquals("Test 0", tp2.getValue());
        assertEquals("Test 0", tv1.getText().toString());
        assertEquals("Test 0", tv2.getText().toString());

        tv1.setText("Test 1");
        assertEquals("Test 1", tp1.getValue());
        assertEquals("Test 1", tp2.getValue());
        assertEquals("Test 1", tv2.getText().toString());

        tv2.setText("Test 2");
        assertEquals("Test 2", tp1.getValue());
        assertEquals("Test 2", tp2.getValue());
        assertEquals("Test 2", tv1.getText().toString());

        tp1.update("Test 3");
        assertEquals("Test 3", tp2.getValue());
        assertEquals("Test 3", tv1.getText().toString());
        assertEquals("Test 3", tv2.getText().toString());

        tp2.update("Test 4");
        assertEquals("Test 4", tp1.getValue());
        assertEquals("Test 4", tv1.getText().toString());
        assertEquals("Test 4", tv2.getText().toString());
    }


    public void testDoubleBinding()
    {
        TextView tv = new TextView(getContext());

        TextProperty tp1 = new TextProperty(tv);
        TextProperty tp2 = new TextProperty(tv);

        tp1.bindTwoWay(tp2);
    }
}
