package com.xorprogramming.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Savable
{
    void save(ObjectOutputStream out)
        throws IOException;
    
    
    void restore(ObjectInputStream in)
        throws IOException;
}
