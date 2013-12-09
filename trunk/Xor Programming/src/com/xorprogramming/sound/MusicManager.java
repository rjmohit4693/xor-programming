package com.xorprogramming.sound;

import android.content.Context;
import android.media.MediaPlayer;

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
            return false;
        }
    }
    
    
    public boolean pause()
    {
        if (player == null)
        {
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
