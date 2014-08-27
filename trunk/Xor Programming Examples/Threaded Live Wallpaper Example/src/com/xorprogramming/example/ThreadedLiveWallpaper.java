package com.xorprogramming.example;

import android.view.MotionEvent;
import com.xorprogramming.wallpaper.ThreadedWallpaperService;

// -------------------------------------------------------------------------
/**
 * A very simple example of how to use the {@code ThreadedWallpaperService} provided in the Xor Programming API
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class ThreadedLiveWallpaper
    extends ThreadedWallpaperService
{

    @Override
    public Engine onCreateEngine()
    {
        return new ThreadedLiveWallpaperEngine();
    }


    // -------------------------------------------------------------------------
    /**
     * A very simple example of how to use the {@code ThreadedEngine} provided in the Xor Programming API
     *
     * @author Steven Roberts
     * @version 1.0.0
     */
    public class ThreadedLiveWallpaperEngine
        extends ThreadedEngine<Scene>
    {

        // ----------------------------------------------------------
        /**
         * Create a new ThreadedLiveWallpaperEngine object.
         */
        public ThreadedLiveWallpaperEngine()
        {
            // Set the scene and run at 16 fps
            super(new Scene(), 16);
        }


        @Override
        public void onTouchEvent(MotionEvent event)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    getWallpaperScene().onTouch();
                    break;
                case MotionEvent.ACTION_UP:
                    getWallpaperScene().onRelease();
                    break;
                default:
                    super.onTouchEvent(event);
                    break;
            }
        }

    }

}
