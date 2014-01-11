package com.xorprogramming.example.updaterthread;

public enum UpdaterThreadState
{
    NOT_YET_STARTED("Not yet started"),
    RUNNING("Running"),
    STOPPING("Stopping"),
    STOPPED("Stopped");

    private String name;

    private UpdaterThreadState(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
