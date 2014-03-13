package com.xorprogramming.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.xorprogramming.io.LowMemoryBitmapFactory;
import com.xorprogramming.wallpaper.WallpaperScene;

// -------------------------------------------------------------------------
/**
 * A very simple example of how to use the {@code WallpaperScene} provided in the Xor Programming API. This
 * implementation has a background that fades to different colors and displays the Xor Programming logo in the center of
 * the screen.
 *
 * @author Steven Roberts
 * @version Mar 13, 2014
 */
public class Scene
    implements WallpaperScene
{
    private static final float ICON_RELATIVE_SIZE = .6f;        // size relative to the smallest screen dimenstion
    private static final float ANGULAR_VELOCITY   = 15f;        // in deg/s
    private final float[]      hsv                = { 0, 1, 1 }; // the background color is hsv
    private final Paint        bitmapPaint        = new Paint();
    private Bitmap             icon;


    @Override
    public void update(float deltaT, int width, int height)
    {
        hsv[0] += ANGULAR_VELOCITY * deltaT;
        hsv[0] %= 360; // make sure the angle is between 0 and 360 degrees
    }


    @Override
    public void initialize(Context c)
    {
        // Nothing needed
    }


    @Override
    public void onSizeChange(Context c, int newWidth, int newHeight)
    {
        dispose();
        int size = (int)(ICON_RELATIVE_SIZE * Math.min(newWidth, newHeight));
        icon = LowMemoryBitmapFactory.getScaledImage(c.getResources(), R.drawable.ic_launcher, size, size, true);
    }


    @Override
    public void render(Canvas c, int width, int height)
    {
        int color = Color.HSVToColor(hsv);
        c.drawColor(color);

        // draw the icon centered on the screen
        int left = (width - icon.getWidth()) / 2;
        int top = (height - icon.getHeight()) / 2;
        c.drawBitmap(icon, left, top, bitmapPaint);
    }


    @Override
    public void dispose()
    {
        if (icon != null)
        {
            icon.recycle();
        }
    }


    // ----------------------------------------------------------
    /**
     * Called when the user touches the screen. Makes the bitmap slightly translucent.
     */
    public void onTouch()
    {
        bitmapPaint.setAlpha(200);
    }


    // ----------------------------------------------------------
    /**
     * Called when the user release the finger from the screen. Makes the bitmap opaque
     */
    public void onRelease()
    {
        bitmapPaint.setAlpha(255);
    }

}
