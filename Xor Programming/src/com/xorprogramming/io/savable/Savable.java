package com.xorprogramming.io.savable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Savable
{
    void save(ObjectOutputStream out)
        throws IOException, SaveException;


    void restore(ObjectInputStream in)
        throws IOException, SaveException;
}
