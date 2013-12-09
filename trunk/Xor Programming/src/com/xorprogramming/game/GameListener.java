package com.xorprogramming.game;

public interface GameListener<T>
{
    void onGameEvent(T t, GameEngine<T> engine);
    
}
