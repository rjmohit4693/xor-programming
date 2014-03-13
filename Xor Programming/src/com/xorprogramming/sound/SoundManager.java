/*
 * Copyright (C) 2014 Xor Programming
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xorprogramming.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.SparseIntArray;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;

public class SoundManager
{
    private static final int     MAX_SIMULTANEOUS_STREAMS = 5;
    private static final int     STREAM_TYPE              = AudioManager.STREAM_MUSIC;

    private final SparseIntArray soundPoolIDs;
    private final SparseIntArray loadingSoundPoolIDs;

    private SoundPool            soundPool;
    private AudioManager         manager;


    public SoundManager()
    {
        soundPoolIDs = new SparseIntArray();
        loadingSoundPoolIDs = new SparseIntArray();
    }


    public void loadSound(Context context, int resourceID)
    {
        if (soundPool == null)
        {
            soundPool = new SoundPool(MAX_SIMULTANEOUS_STREAMS, STREAM_TYPE, 0);
            manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
            {
                public void onLoadComplete(SoundPool pool, int sampleId, int status)
                {
                    if (status == 0 && soundPool != null)
                    {
                        int index = loadingSoundPoolIDs.indexOfKey(sampleId);
                        if (index >= 0)
                        {
                            soundPoolIDs.put(loadingSoundPoolIDs.valueAt(index), sampleId);
                            soundPoolIDs.removeAt(index);
                        }
                    }
                }
            });
        }
        loadingSoundPoolIDs.put(soundPool.load(context, resourceID, 1), resourceID);
    }


    public boolean playSound(int resourceID)
    {
        return playSound(resourceID, 0);
    }


    public boolean playSound(int resourceID, int loop)
    {
        if (soundPool == null)
        {
            Logger.log(LoggingType.WARNING, "Sound manager cannot play audio when it has not been loaded");
            return false;
        }
        int index = soundPoolIDs.indexOfKey(resourceID);
        if (index >= 0)
        {
            float volume = getVolume();
            return soundPool.play(soundPoolIDs.get(resourceID), volume, volume, 1, loop, 1) != 0;
        }
        else
        {
            Logger.log(LoggingType.WARNING, "Sound manager cannot play audio when it has not been loaded");
            return false;
        }
    }


    private float getVolume()
    {
        float streamVolumeCurrent = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return streamVolumeCurrent / streamVolumeMax;
    }


    public boolean pauseAll()
    {
        if (soundPool != null)
        {
            soundPool.autoPause();
            return true;
        }
        else
        {
            Logger.log(LoggingType.WARNING, "Sound manager cannot pause audio when it has not been loaded");
            return false;
        }
    }


    public boolean resumeAll()
    {
        if (soundPool != null)
        {
            soundPool.autoResume();
            return true;
        }
        else
        {
            Logger.log(LoggingType.WARNING, "Sound manager cannot resume audio when it has not been loaded");
            return false;
        }
    }


    public boolean unloadSound(int resourceID)
    {
        if (soundPool == null)
        {
            Logger.log(LoggingType.WARNING, "Sound manager cannot unload audio when it has not been loaded");
            return false;
        }
        int index = soundPoolIDs.indexOfKey(resourceID);
        if (index >= 0)
        {
            int soundID = soundPoolIDs.valueAt(index);
            soundPoolIDs.removeAt(index);
            return soundPool.unload(soundID);
        }
        else
        {
            Logger.log(LoggingType.WARNING, "Sound manager cannot unload audio " + resourceID);
            return false;
        }
    }


    public void dispose()
    {
        if (soundPool != null)
        {
            for (int i = 0; i < soundPoolIDs.size(); i++)
            {
                soundPool.unload(soundPoolIDs.valueAt(i));
            }
            soundPool.release();
        }
        soundPoolIDs.clear();
        loadingSoundPoolIDs.clear();
        soundPool = null;
        manager = null;
    }
}
