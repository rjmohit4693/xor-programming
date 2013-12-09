package com.xorprogramming.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

// -------------------------------------------------------------------------
/**
 * A FrameLayout that maintains a square shape, while maximizing the parent's space.
 * 
 * @author Steven Roberts
 * @version Jul 11, 2013
 */
public class SquareFrame
    extends FrameLayout
{
    private static final int DEFAULT_SIZE = 100;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SquareFrame object.
     * 
     * @param context
     */
    public SquareFrame(Context context)
    {
        super(context);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SquareFrame object.
     * 
     * @param context
     * @param attrs
     */
    public SquareFrame(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SquareFrame object.
     * 
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SquareFrame(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int size = 0;
        if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED)
        {
            size = DEFAULT_SIZE;
        }
        else if (widthMode == MeasureSpec.UNSPECIFIED ^ heightMode == MeasureSpec.UNSPECIFIED)
        {
            size = Math.max(width, height);
        }
        else
        {
            size = Math.min(width, height);
        }
        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }
}
