package com.javasharp.model;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiEvent;

public class PartContext
{
    private final ScoreContext scoreContext;
    private final Instrument   instrument;
    private final int          channel;
    
    private int                volume;
    private KeySignature       keySignature;
    private Clef               clef;
    
    
    public PartContext(ScoreContext scoreContext, Instrument instrument, int channel)
    {
        this.scoreContext = scoreContext;
        this.instrument = instrument;
        this.channel = channel;
    }
    
    
    public ScoreContext getScoreContext()
    {
        return scoreContext;
    }
    
    
    public int getVolume()
    {
        return volume;
    }
    
    
    public void playNote()
    {
        track
            .add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, pitch, 100), offset + locationInMeasure));
    }
}
