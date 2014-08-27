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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.xorprogramming.R;

// -------------------------------------------------------------------------
/**
 * A FrameLayout that maintains an aspect ratio while maximizing the parent's space.
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public class AspectFrame
    extends FrameLayout
{
    private static final int DEFAULT_WIDTH  = 3;
    private static final int DEFAULT_HEIGHT = 4;
    
    private float            widthRatio;
    private float            heightRatio;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AspectFrame object.
     *
     * @param context
     */
    public AspectFrame(Context context)
    {
        super(context);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AspectFrame object.
     *
     * @param context
     * @param attrs
     */
    public AspectFrame(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getAttributes(context, attrs);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AspectFrame object.
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AspectFrame(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        getAttributes(context, attrs);
    }
    
    
    private void getAttributes(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectFrame);
        
        widthRatio = a.getFloat(R.styleable.AspectFrame_widthRatio, DEFAULT_WIDTH);
        heightRatio = a.getFloat(R.styleable.AspectFrame_heightRatio, DEFAULT_HEIGHT);
        
        a.recycle();
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int finalWidth;
        int finalHeight;
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize =
            widthMode == MeasureSpec.UNSPECIFIED ? Integer.MAX_VALUE : MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize =
            heightMode == MeasureSpec.UNSPECIFIED ? Integer.MAX_VALUE : MeasureSpec.getSize(heightMeasureSpec);
        
        if (heightMode == MeasureSpec.EXACTLY && widthMode == MeasureSpec.EXACTLY)
        {
            finalWidth = widthSize;
            finalHeight = heightSize;
            
        }
        else if (heightMode == MeasureSpec.EXACTLY)
        {
            finalWidth = (int)Math.min(widthSize, heightSize * widthRatio / heightRatio);
            finalHeight = (int)(finalWidth * heightRatio / widthRatio);
            
        }
        else if (widthMode == MeasureSpec.EXACTLY)
        {
            finalHeight = (int)Math.min(heightSize, widthSize * heightRatio / widthRatio);
            finalWidth = (int)(finalHeight * widthRatio / heightRatio);
            
        }
        else
        {
            if (widthSize > heightSize * widthRatio / heightRatio)
            {
                finalHeight = heightSize;
                finalWidth = (int)(finalHeight * widthRatio / heightRatio);
            }
            else
            {
                finalWidth = widthSize;
                finalHeight = (int)(finalWidth * widthRatio / heightRatio);
            }
            
        }
        
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}
