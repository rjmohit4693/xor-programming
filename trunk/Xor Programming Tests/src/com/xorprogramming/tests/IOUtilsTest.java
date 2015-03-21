package com.xorprogramming.tests;

import android.test.AndroidTestCase;
import com.xorprogramming.xor2d.io.utils.IOUtils;
import java.io.Closeable;
import java.util.Scanner;

public class IOUtilsTest
    extends AndroidTestCase
{
    public void testCloseNull()
    {
        assertFalse(IOUtils.closeStream((Closeable)null));
        assertFalse(IOUtils.closeStream((AutoCloseable)null));
    }


    public void testCloseStream()
    {
        Scanner scan = new Scanner("Test\nXor");
        assertEquals("Test", scan.nextLine());
        assertTrue(IOUtils.closeStream(scan));
        assertNull(scan.ioException());

        Exception ex = null;
        try
        {
            scan.nextLine();
        }
        catch (IllegalStateException ise)
        {
            ex = ise;
        }

        assertTrue(ex instanceof IllegalStateException);

        assertTrue(IOUtils.closeStream(scan));
    }


    public void testAutoCloseStream()
    {
        TestAutoCloser tac = new TestAutoCloser();
        assertFalse(IOUtils.closeStream(tac));
    }


    private class TestAutoCloser
        implements AutoCloseable
    {

        @Override
        public void close()
            throws Exception
        {
            throw new IllegalStateException("Test");
        }

    }
}
