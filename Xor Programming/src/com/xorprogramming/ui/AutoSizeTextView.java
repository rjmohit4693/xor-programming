/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.xorprogramming.R;

// -------------------------------------------------------------------------
/**
 * A view containing text that automatically sizes to maximize the given space.
 * 
 * @author Steven Roberts
 * @version 1.1.0
 */
public class AutoSizeTextView
    extends TextView
{
    private static final int   MAX_FONT_SIZE  = 256;
    private static final int   MIN_FONT_SIZE  = 2;
    private static final float THRESHOLD      = .5f;
    
    private static final int   JUSTIFY_LEFT   = 0;
    private static final int   JUSTIFY_CENTER = 1;
    private static final int   JUSTIFY_RIGHT  = 2;
    
    private final Paint        TEXT_PAINT     = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int                justification;
    private float              widthRatio;
    private float              heightRatio;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AutoSizeTextView object.
     * 
     * @param context
     */
    public AutoSizeTextView(Context context)
    {
        super(context);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AutoSizeTextView object.
     * 
     * @param context
     * @param attrs
     */
    public AutoSizeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getAttributes(context, attrs);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AutoSizeTextView object.
     * 
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AutoSizeTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        getAttributes(context, attrs);
    }
    
    
    private void getAttributes(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoSizeTextView);
        widthRatio = a.getFloat(R.styleable.AutoSizeTextView_widthRatio, -1);
        heightRatio = a.getFloat(R.styleable.AutoSizeTextView_heightRatio, -1);
        justification = a.getInt(R.styleable.AutoSizeTextView_justification, JUSTIFY_CENTER);
        a.recycle();
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (widthRatio < 0 || heightRatio < 0)
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        
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
    
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        String text = getText().toString();
        if (text == null)
        {
            return;
        }
        
        syncPaint();
        
        int availWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        float textSize = getOptimalFontSize(text, availWidth, availHeight, MAX_FONT_SIZE, MIN_FONT_SIZE);
        TEXT_PAINT.setTextSize(textSize);
        float totalWidth = TEXT_PAINT.measureText(text);
        float totalHeight = TEXT_PAINT.descent() - TEXT_PAINT.ascent();
        switch (justification)
        {
            case JUSTIFY_LEFT:
                canvas.drawText(text, getPaddingLeft(), (availHeight + totalHeight) / 2 - TEXT_PAINT.descent()
                    + getPaddingTop(), TEXT_PAINT);
                break;
            case JUSTIFY_RIGHT:
                canvas.drawText(text, availWidth + getPaddingLeft() - totalWidth, (availHeight + totalHeight) / 2
                    - TEXT_PAINT.descent() + getPaddingTop(), TEXT_PAINT);
                break;
            default:
                canvas.drawText(text, (availWidth - totalWidth) / 2 + getPaddingLeft(), (availHeight + totalHeight) / 2
                    - TEXT_PAINT.descent() + getPaddingTop(), TEXT_PAINT);
                break;
        }
        
    }
    
    
    private void syncPaint()
    {
        TEXT_PAINT.setTypeface(getTypeface());
        TEXT_PAINT.setColor(getCurrentTextColor());
    }
    
    
    private float getOptimalFontSize(String text, int availWidth, int availHeight, float max, float min)
    {
        float middle = (max + min) / 2;
        TEXT_PAINT.setTextSize(middle);
        float totalWidth = TEXT_PAINT.measureText(text);
        float totalHeight = TEXT_PAINT.descent() - TEXT_PAINT.ascent();
        if (max - min <= THRESHOLD)
        {
            // All done
            return min;
        }
        else if (totalWidth > availWidth || totalHeight > availHeight)
        {
            // Too big
            return getOptimalFontSize(text, availWidth, availHeight, middle, min);
        }
        else
        {
            // Too small
            return getOptimalFontSize(text, availWidth, availHeight, max, middle);
        }
    }
    
}
