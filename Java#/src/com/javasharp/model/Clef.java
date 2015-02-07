package com.javasharp.model;

public enum Clef
{
    TREBLE("Treble", 0),
    BASS("Bass", 0),
    ALTO("Alto", 0),
    TENOR("Tenor", 0);

    private final String name;
    private final int    offset;


    private Clef(String name, int offset)
    {
        this.name = name;
        this.offset = offset;
    }


    @Override
    public String toString()
    {
        return name;
    }
}
