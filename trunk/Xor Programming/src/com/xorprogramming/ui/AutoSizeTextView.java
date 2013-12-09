package com.xorprogramming.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.xorprogramming.R;

// -------------------------------------------------------------------------
/**
 * A view containing text that automatically sizes to maximize the given space.
 * 
 * @author Steven Roberts
 * @version 1.1.0
 */
public class AutoSizeTextView
    extends View
{
    private static final int   MAX_FONT_SIZE  = 256;
    private static final int   MIN_FONT_SIZE  = 2;
    private static final float THRESHOLD      = .01f;
    
    private static final int   JUSTIFY_LEFT   = 0;
    private static final int   JUSTIFY_CENTER = 1;
    private static final int   JUSTIFY_RIGHT  = 2;
    
    private final Paint        TEXT_PAINT     = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String             text;
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
        
        TEXT_PAINT.setColor(a.getColor(R.styleable.AutoSizeTextView_android_textColor, Color.BLACK));
        text = a.getString(R.styleable.AutoSizeTextView_android_text);
        justification = a.getInt(R.styleable.AutoSizeTextView_justification, JUSTIFY_CENTER);
        widthRatio = a.getFloat(R.styleable.AutoSizeTextView_widthRatio, -1);
        heightRatio = a.getFloat(R.styleable.AutoSizeTextView_heightRatio, -1);
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
    
    
    // ----------------------------------------------------------
    /**
     * Sets the typeface of the text
     * 
     * @param typeface
     *            The new typeface
     */
    public void setTypeface(Typeface typeface)
    {
        TEXT_PAINT.setTypeface(typeface);
        invalidate();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Sets the text to be displayed
     * 
     * @param text
     *            The new text
     */
    public void setText(String text)
    {
        this.text = text;
        invalidate();
    }
    
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        if (isInEditMode() || text == null)
        {
            return;
        }
        int availWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        float textSize = getOptimalFontSize(availWidth, availHeight, MAX_FONT_SIZE, MIN_FONT_SIZE);
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
                canvas.drawText(text, availWidth - getPaddingRight() - totalWidth, (availHeight + totalHeight) / 2
                    - TEXT_PAINT.descent() + getPaddingTop(), TEXT_PAINT);
                break;
            default:
                canvas.drawText(text, (availWidth - totalWidth) / 2 + getPaddingLeft(), (availHeight + totalHeight) / 2
                    - TEXT_PAINT.descent() + getPaddingTop(), TEXT_PAINT);
                break;
        }
        
    }
    
    
    private float getOptimalFontSize(int availWidth, int availHeight, float max, float min)
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
            return getOptimalFontSize(availWidth, availHeight, middle, min);
        }
        else
        {
            // Too small
            return getOptimalFontSize(availWidth, availHeight, max, middle);
        }
    }
    
}
