/*-
Copyright 2014 Xor Programming

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.xorprogramming.utils;

import java.util.Arrays;

// -------------------------------------------------------------------------
/**
 * A generic collection that minimizes the creation and garbage collection of objects through object reuse. When
 * constructed, a given number of objects can be preallocated. These objects can be retrieved from the pool,
 * initialized, and then used. If more items are taken from the pool than available, new items will be constructed by
 * {@link #createItem()}. After an item has been used, it can be returned to the pool for reuse. Note that items that
 * were not constructed by the pool can still be added to the pool.
 *
 * @param <T>
 *            The type of objects to keep in the pool
 * @author Steven Roberts
 * @version 1.1.0
 */
public abstract class MemoryPool<T>
{
    private Object[] items;
    private int      numAllocations;


    // ----------------------------------------------------------
    /**
     * Create a new MemoryPool with zero objects initially allocated.
     */
    public MemoryPool()
    {
        this(0);
    }


    // ----------------------------------------------------------
    /**
     * Create a new MemoryPool object.
     *
     * @param initAllocations
     *            The number of initial allocations in the pool
     * @throws IllegalArgumentException
     *             if the number of initial allocations is negative
     */
    public MemoryPool(int initAllocations)
    {
        if (initAllocations < 0)
        {
            throw new IllegalArgumentException("The number of initial allocations must be non-negative");
        }

        items = new Object[initAllocations];
        for (int i = 0; i < initAllocations; i++)
        {
            items[i] = createItem();
        }
        numAllocations = initAllocations;
    }


    // ----------------------------------------------------------
    /**
     * Constructs a new item of the generic type.
     *
     * @return The new item, which must be non-null
     */
    protected abstract T createItem();


    // ----------------------------------------------------------
    /**
     * Gets an item from the pool. If there are no items available, a new one will be created.
     *
     * @return The item
     */
    public final T getItem()
    {
        if (numAllocations == 0)
        {
            return createItem();
        }
        else
        {
            numAllocations--;

            @SuppressWarnings("unchecked")
            T retItem = (T)items[numAllocations];
            items[numAllocations] = null;
            return retItem;
        }
    }


    // ----------------------------------------------------------
    /**
     * Adds an item to the pool for later reuse
     *
     * @param t
     *            The item to add
     * @throws NullPointerException
     *             if the added item is null
     */
    public final void addItem(T t)
    {
        if (t == null)
        {
            throw new NullPointerException("The item must be non-null");
        }

        if (numAllocations >= items.length)
        {
            expandPool();
        }

        items[numAllocations] = t;
        numAllocations++;
    }


    // ----------------------------------------------------------
    /**
     * Empties the pool and allows all items to be garbage collected. A {@link MemoryPool} can still be used after it
     * has been emptied.
     */
    public void empty()
    {
        Arrays.fill(items, null);
        numAllocations = 0;
    }


    // ----------------------------------------------------------
    /**
     * Gets the number of allocated objects currently in the pool
     *
     * @return number of allocated objects
     */
    public int getSize()
    {
        return numAllocations;
    }


    private void expandPool()
    {
        int origLength = items.length;
        Object[] newItems = new Object[2 * origLength + 1];
        System.arraycopy(items, 0, newItems, 0, origLength);
        items = newItems;
    }
}
