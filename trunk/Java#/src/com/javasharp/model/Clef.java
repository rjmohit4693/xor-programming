package com.javasharp.model;

public enum Clef
{
    TREBLE("Treble", 0), BASS("Bass", 0), ALTO("Alto", 0), TENOR("Tenor", 0);
    
    private String name;
    private int offset;
    
    private Clef(String name, int offset) {
        this.name = name;
        this.offset = offset;
    }

}
