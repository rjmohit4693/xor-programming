package com.xorprogramming.io.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Savable
{
    void restore(ObjectInputStream ois)
        throws IOException,
        ClassNotFoundException;
    
    
    void save(ObjectOutputStream oos)
        throws IOException;
}
