package com.xorprogramming.game;

public interface GameEngine<T>
{
    void update(float deltaT, GameController<T> controller);
}
