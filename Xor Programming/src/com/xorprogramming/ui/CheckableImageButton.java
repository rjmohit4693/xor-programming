/*
 * Copyright (C) 2014 Xor Programming
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xorprogramming.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageButton;
import com.xorprogramming.R;

// -------------------------------------------------------------------------
/**
 * An ImageButton that adds the ability to be checked or not. In a layout, the is_checked property can be used to check
 * or uncheck the button.
 *
 * @author Steven
 * @version 1.1.0
 */
public class CheckableImageButton
    extends ImageButton
    implements Checkable
{
    private static final int[] CHECKED_STATE_SET = { R.attr.checked };

    private boolean            checked;


    // ----------------------------------------------------------
    /**
     * Create a new CheckableImageButton object.
     *
     * @param context
     */
    public CheckableImageButton(Context context)
    {
        this(context, null);
    }


    // ----------------------------------------------------------
    /**
     * Create a new CheckableImageButton object.
     *
     * @param context
     * @param attrs
     */
    public CheckableImageButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckableImageButton);
        boolean c = a.getBoolean(R.styleable.CheckableImageButton_checked, false);
        setChecked(c);
        a.recycle();
    }


    public boolean isChecked()
    {
        return checked;
    }


    public void setChecked(boolean c)
    {
        if (checked != c)
        {
            checked = c;
            refreshDrawableState();
        }
    }


    public void toggle()
    {
        setChecked(!checked);
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace)
    {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
        {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }


    @Override
    protected void drawableStateChanged()
    {
        super.drawableStateChanged();
        invalidate();
    }
}
