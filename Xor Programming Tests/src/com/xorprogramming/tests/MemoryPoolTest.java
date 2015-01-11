package com.xorprogramming.tests;

import android.test.AndroidTestCase;
import com.xorprogramming.utils.MemoryPool;

public class MemoryPoolTest
    extends AndroidTestCase
{
    public void testConstructors()
    {
        assertEquals(0, new ItemPool().getSize());
        assertEquals(100, new ItemPool(100).getSize());

        Exception ex = null;
        try
        {
            new ItemPool(-1);
        }
        catch (IllegalArgumentException iae)
        {
            ex = iae;
        }

        assertTrue(ex instanceof IllegalArgumentException);
    }


    public void testGetItem()
    {
        MemoryPool<String> pool = new MemoryPool<String>()
        {
            @Override
            protected String createItem()
            {
                return "hi";
            }
        };

        assertEquals("hi", pool.getItem());
        assertEquals("hi", pool.getItem());

        pool.addItem("123");
        assertEquals("123", pool.getItem());

        assertEquals("hi", pool.getItem());
        assertEquals("hi", pool.getItem());
    }


    public void testAddItem()
    {
        MemoryPool<Item> pool = new ItemPool();

        for (int i = 0; i < 10; i++)
        {
            pool.addItem(new Item("test"));
        }

        assertEquals(10, pool.getSize());

        for (int i = 0; i < 10; i++)
        {
            assertEquals(new Item("test"), pool.getItem());
        }

        assertEquals(0, pool.getSize());

        assertEquals(new Item("name"), pool.getItem());

        Exception ex = null;
        try
        {
            pool.addItem(null);
        }
        catch (NullPointerException npe)
        {
            ex = npe;
        }

        assertTrue(ex instanceof NullPointerException);
    }


    public void testEmpty()
    {
        MemoryPool<Item> pool = new ItemPool(10);
        assertEquals(10, pool.getSize());

        pool.empty();
        assertEquals(0, pool.getSize());
        pool.empty();
        assertEquals(0, pool.getSize());

        for (int i = 0; i < 20; i++)
        {
            pool.addItem(new Item("empty"));
        }
        assertEquals(20, pool.getSize());

        pool.empty();
        assertEquals(0, pool.getSize());
    }


    public class ItemPool
        extends MemoryPool<Item>
    {
        public ItemPool()
        {
            super();
        }


        public ItemPool(int initAllocations)
        {
            super(initAllocations);
        }


        @Override
        protected Item createItem()
        {
            return new Item("name");
        }
    }


    private class Item
    {
        private String name;


        public Item(String name)
        {
            this.name = name;
        }


        @Override
        public boolean equals(Object o)
        {
            return o instanceof Item && ((Item)o).name.equals(name);
        }
    }
}
