/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.sound;

import com.xorprogramming.logging.LoggingType;
import com.xorprogramming.logging.Logger;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.SparseArray;
import java.util.LinkedList;
import java.util.Queue;

public class SoundManager
{
    private final SparseArray<Sound> resIdToSoundMap;
    private final SoundPool          pool;


    public SoundManager(int maxStreams)
    {
        resIdToSoundMap = new SparseArray<Sound>();
        pool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        pool.setOnLoadCompleteListener(new OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
            {
                if (status == 0)
                {
                    Logger.logf(LoggingType.INFO, Logger.XOR_LOG_TAG, "Sound %d successfully loaded", sampleId);
                }
                else
                {
                    Logger.logf(
                        LoggingType.ERROR,
                        Logger.XOR_LOG_TAG,
                        "Unable to load sound %d, status %d",
                        sampleId,
                        status);
                    return;
                }

                for (int i = 0; i < resIdToSoundMap.size(); i++)
                {
                    Sound cur = resIdToSoundMap.valueAt(i);
                    if (cur.soundId == sampleId)
                    {
                        cur.runQueuedRequests();
                        break;
                    }
                }
            }
        });
    }


    public boolean load(Context c, int resId)
    {
        Sound s = resIdToSoundMap.get(resId);
        if (s != null)
        {
            // sound already exists. It cannot be loaded again!
            return false;
        }

        int soundId = pool.load(c, resId, 1);
        resIdToSoundMap.put(resId, new Sound(soundId));
        return true;
    }


    public boolean play(int resId, final float vol, final int loop, final float rate)
    {
        final Sound s = resIdToSoundMap.get(resId);
        if (s == null)
        {
            return false;
        }
        s.post(new Runnable()
        {
            @Override
            public void run()
            {
                pool.play(s.soundId, vol, vol, 1, loop, rate);
            }
        });
        return true;
    }


    public boolean play(int resId, float vol, int loop)
    {
        return play(resId, vol, loop, 1);
    }


    public boolean play(int resId, float vol)
    {
        return play(resId, vol, 0);
    }


    public boolean play(int resId)
    {
        return play(resId, 1);
    }


    public void resumeAll()
    {
        pool.autoResume();
    }


    public void pauseAll()
    {
        pool.autoPause();
    }


    public boolean unload(int resId)
    {
        Sound s = resIdToSoundMap.get(resId);
        if (s == null)
        {
            return false;
        }
        boolean success = pool.unload(s.soundId);
        resIdToSoundMap.delete(resId);
        return success;
    }


    public void dispose()
    {
        resIdToSoundMap.clear();
        pool.release();
    }


    private class Sound
    {
        private final int       soundId;
        private boolean         isLoaded;
        private Queue<Runnable> requests;


        public Sound(int soundId)
        {
            this.soundId = soundId;
        }


        public void post(Runnable r)
        {
            if (isLoaded)
            {
                r.run();
            }
            else
            {
                if (requests == null)
                {
                    requests = new LinkedList<Runnable>();
                }
                requests.add(r);
            }
        }


        public void runQueuedRequests()
        {
            isLoaded = true;
            if (requests != null)
            {
                while (!requests.isEmpty())
                {
                    requests.remove().run();
                }
            }
        }
    }
}
