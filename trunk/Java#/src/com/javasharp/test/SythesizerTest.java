package com.javasharp.test;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class SythesizerTest
{
    public static void main(String[] args)
    {
        try
        {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel[] channels = synthesizer.getChannels();
            
            channels[0].noteOn(60, 60); // C
            channels[0].noteOn(64, 60); // E
            channels[0].noteOn(67, 60); // G
            Thread.sleep(2000);
            channels[0].allNotesOff();
            Thread.sleep(500);
            
            synthesizer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
