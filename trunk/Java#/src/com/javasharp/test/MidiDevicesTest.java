package com.javasharp.test;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

public class MidiDevicesTest {

    public static void main(String[] args) {
        try {
            MidiDevice.Info[] midiDeviceInfoArray = MidiSystem.getMidiDeviceInfo();
            for ( MidiDevice.Info midiDeviceInfo : midiDeviceInfoArray ) {
                System.out.println("Next device:");
                System.out.println("  Name: " + midiDeviceInfo.getName());
                System.out.println("  Description: " + midiDeviceInfo.getDescription());
                System.out.println("  Vendor: " + midiDeviceInfo.getVendor());
                System.out.println("  toString(): " + midiDeviceInfo.toString());
                System.out.println("");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}