/*-
Copyright 2014 Xor Programming

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.xorprogramming.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

// -------------------------------------------------------------------------
/**
 * A FrameLayout that maintains a square shape, while maximizing the parent's space.
 *
 * @author Steven Roberts
 * @version 1.0.0
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
