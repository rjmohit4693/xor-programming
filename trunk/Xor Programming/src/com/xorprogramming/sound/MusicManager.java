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
