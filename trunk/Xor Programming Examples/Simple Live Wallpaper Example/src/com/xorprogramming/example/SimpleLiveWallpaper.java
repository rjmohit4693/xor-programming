package com.xorprogramming.example;

import android.view.MotionEvent;
import com.xorprogramming.wallpaper.SimpleWallpaperService;

// -------------------------------------------------------------------------
/**
 * A very simple example of how to use the {@code SimpleWallpaperService} provided in the Xor Programming API
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class SimpleLiveWallpaper
    extends SimpleWallpaperService
{

    @Override
    public Engine onCreateEngine()
    {
        return new SimpleLiveWallpaperEngine();
    }


    // -------------------------------------------------------------------------
    /**
     * A very simple example of how to use the {@code SimpleEngine} provided in the Xor Programming API
     *
     * @author Steven Roberts
     * @version 1.0.0
     */
    public class SimpleLiveWallpaperEngine
        extends SimpleEngine<Scene>
    {

        // ----------------------------------------------------------
        /**
         * Create a new SimpleLiveWallpaperEngine object.
         */
        public SimpleLiveWallpaperEngine()
        {
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
