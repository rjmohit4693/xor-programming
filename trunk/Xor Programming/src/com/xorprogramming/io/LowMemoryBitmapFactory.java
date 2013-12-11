package com.xorprogramming.io;

import com.xorprogramming.logging.LoggingType;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.xorprogramming.logging.Logger;

// -------------------------------------------------------------------------
/**
 * A factory used to efficiently load bitmaps.
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class LowMemoryBitmapFactory
{
    private static final int TEMP_STORAGE_SIZE = 16384;
    
    
    // ----------------------------------------------------------
    /**
     * Create a scaled bitmap of the given resource
     * 
     * @param res
     *            Resource used for retrieving the resource
     * @param id
     *            The resource id of the images
     * @param width
     *            Required width of the bitmap
     * @param height
     *            Required height of the bitmap
     * @param higherQuality
     *            If true, the bitmap will be 32-bit and dithered. If false, the bitmap will be 16 bit and not dithered
     * @return The scaled image
     */
    public static Bitmap getScaledImage(Resources res, int id, int width, int height, boolean higherQuality)
    {
        if (res == null)
        {
            throw new IllegalArgumentException("The resources must be non-null");
        }
        else if (width <= 0)
        {
            throw new IllegalArgumentException("The bitmap width must be positive");
        }
        else if (height <= 0)
        {
            throw new IllegalArgumentException("The bitmap height must be positive");
        }
        try
        {
            // Decode image size
            BitmapFactory.Options bitmapSizeOptions = new BitmapFactory.Options();
            bitmapSizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, bitmapSizeOptions);
            
            // load image using inSampleSize adapted to required image size
            BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
            bitmapDecodeOptions.inTempStorage = new byte[TEMP_STORAGE_SIZE];
            bitmapDecodeOptions.inSampleSize = computeInSampleSize(bitmapSizeOptions, width, height);
            bitmapDecodeOptions.inPurgeable = true;
            bitmapDecodeOptions.inDither = higherQuality;
            bitmapDecodeOptions.inPreferredConfig = higherQuality ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            
            Bitmap decodedBitmap = BitmapFactory.decodeResource(res, id, bitmapDecodeOptions);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(decodedBitmap, width, height, higherQuality);
            decodedBitmap.recycle();
            decodedBitmap = null;
            return scaledBitmap;
        }
        catch (Exception ex)
        {
            Logger.log(LoggingType.ERROR, ex.getMessage());
            return null;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a bitmap of the given resource. The bitmap will be the same size as the image.
     * 
     * @param res
     *            Resource used for retrieving the resource
     * @param id
     *            The resource id of the images
     * @param width
     *            Used to determine the sampling size of the resource. This is not the width of the returned bitmap
     * @param height
     *            Used to determine the sampling size of the resource. This is not the height of the returned bitmap
     * @param higherQuality
     *            If true, the bitmap will be 32-bit and dithered. If false, the bitmap will be 16 bit and not dithered
     * @return The unscaled image
     */
    public static Bitmap getUnscaledImage(Resources res, int id, int width, int height, boolean higherQuality)
    {
        if (res == null)
        {
            throw new IllegalArgumentException("The resources must be non-null");
        }
        else if (width <= 0)
        {
            throw new IllegalArgumentException("The bitmap width must be positive");
        }
        else if (height <= 0)
        {
            throw new IllegalArgumentException("The bitmap height must be positive");
        }
        try
        {
            // Decode image size
            BitmapFactory.Options bitmapSizeOptions = new BitmapFactory.Options();
            bitmapSizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, bitmapSizeOptions);
            
            // load image using inSampleSize adapted to required image size
            BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
            bitmapDecodeOptions.inTempStorage = new byte[TEMP_STORAGE_SIZE];
            bitmapDecodeOptions.inSampleSize = computeInSampleSize(bitmapSizeOptions, width, height);
            bitmapDecodeOptions.inPurgeable = true;
            bitmapDecodeOptions.inDither = higherQuality;
            bitmapDecodeOptions.inPreferredConfig = higherQuality ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            return BitmapFactory.decodeResource(res, id, bitmapDecodeOptions);
        }
        catch (Exception ex)
        {
            Logger.log(LoggingType.ERROR, ex.getMessage());
            return null;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a bitmap of the given resource. The bitmap will be the same size as the image.
     * 
     * @param res
     *            Resource used for retrieving the resource
     * @param id
     *            The resource id of the images
     * @param higherQuality
     *            If true, the bitmap will be 32-bit and dithered. If false, the bitmap will be 16 bit and not dithered
     * @return The unscaled image
     */
    public static Bitmap getUnscaledImage(Resources res, int id, boolean higherQuality)
    {
        if (res == null)
        {
            throw new IllegalArgumentException("The resources must be non-null");
        }
        try
        {
            // load image using inSampleSize adapted to required image size
            BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
            bitmapDecodeOptions.inTempStorage = new byte[TEMP_STORAGE_SIZE];
            bitmapDecodeOptions.inPurgeable = true;
            bitmapDecodeOptions.inDither = higherQuality;
            bitmapDecodeOptions.inPreferredConfig = higherQuality ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            
            return BitmapFactory.decodeResource(res, id, bitmapDecodeOptions);
        }
        catch (Exception ex)
        {
            Logger.log(LoggingType.ERROR, ex.getMessage());
            return null;
        }
    }
    
    
    private static int computeInSampleSize(BitmapFactory.Options options, int dstWidth, int dstHeight)
    {
        final int srcHeight = options.outHeight;
        final int srcWidth = options.outWidth;
        
        final int heightRatio = Math.round((float)srcHeight / dstHeight);
        final int widthRatio = Math.round((float)srcWidth / dstWidth);
        return Math.min(heightRatio, widthRatio);
    }
    
}
