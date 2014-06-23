package com.xorprogramming.tests;

import android.test.AndroidTestCase;
import com.xorprogramming.io.utils.IOUtils;
import java.util.Scanner;

public class IOUtilsTest
    extends AndroidTestCase
{
    public void testCloseStream()
    {
        assertFalse(IOUtils.closeStream(null));

        Scanner s = new Scanner("Test\nXor");
        assertEquals("Test", s.nextLine());
        assertTrue(IOUtils.closeStream(s));

        Exception ex = null;
        try
        {
            s.nextLine();
        }
        catch (IllegalStateException ise)
        {
            ex = ise;
        }

        assertTrue(ex instanceof IllegalStateException);

        assertFalse(IOUtils.closeStream(s));
    }
}
