package com.javasharp.test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SequencerTest
{
    private static final int TICKS_PER_BEAT = 16;
    
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException 
    {
        Sequencer sequencer = MidiSystem.getSequencer();
        Sequence seq = new Sequence(Sequence.PPQ, TICKS_PER_BEAT);
        Track track = seq.createTrack();
        
        //Length of a midi sequence
        //seq.getMicrosecondLength()
        
        //Start playback from an arbirary position
        //sequencer.setTickPosition(tick);
        
        //Set tempo of playback
        sequencer.setTempoInBPM(120);
        
        //Set repeat start point
        //sequencer.setLoopStartPoint(tick);
        
      if (sequencer == null) {
         // TODO Error -- sequencer device is not supported.
         // Inform user and return...
     } else {
         sequencer.open();
     }
        
        Thread.sleep(100);
        //set instrument
        int channel = 0;
        int tick = 0;
        int instrument = 1;
        track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument, 0), tick));
        
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 100), 0));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 100), 4));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 100), 8));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 100), 12));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 100), 16));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 100), 20));
        
        //add note
        int pitch = 60; //middle C
        int offset = 0;
        int volume = 100;
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, pitch, volume), offset));
        
        sequencer.setSequence(seq);
        
        sequencer.start();
        Thread.sleep(2000);
        sequencer.close();
    }
}
