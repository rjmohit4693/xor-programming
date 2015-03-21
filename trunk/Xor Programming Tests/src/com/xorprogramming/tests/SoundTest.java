package com.xorprogramming.tests;

import android.content.res.Resources.NotFoundException;
import android.test.AndroidTestCase;
import com.xorprogramming.xor2d.sound.Sound;
import com.xorprogramming.xor2d.sound.SoundManager;

public class SoundTest
    extends AndroidTestCase
{
    private SoundManager manager;


    @Override
    protected void setUp()
    {
        manager = new SoundManager(5);
    }


    public void testInvalidFile()
    {
        Exception ex = null;

        try
        {
            manager.loadSound(getContext(), R.string.app_name);
        }
        catch (NotFoundException nfe)
        {
            ex = nfe;
        }
        assertTrue(ex instanceof NotFoundException);

        // manager.loadSound(getContext(), R.raw.not_a_sound);
    }


    public void testDuplicateLoad() throws InterruptedException
    {
        Sound s1 = manager.loadSound(getContext(), R.raw.sound1);
        Sound s2 = manager.loadSound(getContext(), R.raw.sound1);

        assertFalse(s1.equals(s2));
    }
}
