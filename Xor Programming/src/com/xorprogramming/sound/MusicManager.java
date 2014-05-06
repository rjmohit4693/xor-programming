/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.sound;

import android.content.Context;
import android.media.MediaPlayer;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;

public class MusicManager
{
    private MediaPlayer player;
    
    
    public boolean play(Context context, int resourceID)
    {
        return play(context, resourceID, false);
    }
    
    
    public boolean play(Context context, int resourceID, boolean loop)
    {
        if (player == null)
        {
            player = MediaPlayer.create(context, resourceID);
            if (player == null)
            {
                return false;
            }
            player.setLooping(loop);
            player.start();
            return true;
        }
        else
        {
            Logger.log(LoggingType.WARNING, "Music manager cannot play audio while another is already playing");
            return false;
        }
    }
    
    
    public boolean pause()
    {
        if (player == null)
        {
            Logger.log(LoggingType.WARNING, "Music manager cannot pause when no audio is playing");
            return false;
        }
        else
        {
            player.pause();
            return true;
        }
    }
    
    
    public boolean resume()
    {
        if (player == null)
        {
            Logger.log(LoggingType.WARNING, "Music manager cannot resume when no audio is paused");
            return false;
        }
        else
        {
            player.start();
            return true;
        }
    }
    
    
    public void dispose()
    {
        if (player != null)
        {
            player.release();
            player = null;
        }
    }
}
