package com.javasharp.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public final class MidiUtils
{
    private MidiUtils()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }


    private static Collection<Instrument> getInstrments()
        throws MusicException
    {
        try (Synthesizer synth = MidiSystem.getSynthesizer())
        {
            synth.open();
            Collection<Instrument> instruments = Arrays.asList(synth.getAvailableInstruments());
            return Collections.unmodifiableCollection(instruments);
        }
        catch (MidiUnavailableException e)
        {
            throw new MusicException("Unable to get MIDI instruments", e);
        }
    }
}
