package com.xorprogramming.game;

public interface GameController<T>
{
    void addListener(GameListener<T> listener);
    
    
    void removeListener(GameListener<T> listener);
    
    
    void updateListeners(T t);
    
}
