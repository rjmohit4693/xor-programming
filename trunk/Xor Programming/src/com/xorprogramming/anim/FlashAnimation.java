/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.anim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

// -------------------------------------------------------------------------
/**
 * An animation that grows then shrinks. If a percent rest time is given, the animation will rest at the original size
 * until for that percent of the animation duration after the growing and shrinking has completed.
 * 
 * @author Steven Roberts
 * @version 1.0.0
 */
public class FlashAnimation
    extends Animation
{
    private final float percentGrowth;
    private final float percentRestTime;
    private float       viewWidth;
    private float       viewHeight;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new FlashAnimation object no a 0 percent rest timer
     * 
     * @param percentGrowth
     *            The percent growth of the animated object
     */
    public FlashAnimation(float percentGrowth)
    {
        this(percentGrowth, 1);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Create a new FlashAnimation object.
     * 
     * @param percentGrowth
     *            The percent growth of the animated object
     * @param percentRestTime
     *            The percent of the animation duration where the animated object with stay at the original size after
     *            the growing and shrinking has completed
     */
    public FlashAnimation(float percentGrowth, float percentRestTime)
    {
        if (Float.isInfinite(percentGrowth) || Float.isNaN(percentGrowth))
        {
            throw new IllegalArgumentException("Invalid percent growth for flash animation: " + percentGrowth);
        }
        else if (percentRestTime >= 0 && percentRestTime <= 1)
        {
            this.percentGrowth = percentGrowth;
            this.percentRestTime = percentRestTime;
        }
        else
        {
            throw new IllegalArgumentException("Invalid percent growth for flash animation: " + percentGrowth);
        }
    }
    
    
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight)
    {
        viewWidth = width;
        viewHeight = height;
        super.initialize(width, height, parentWidth, parentHeight);
    }
    
    
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        if (interpolatedTime <= percentRestTime)
        {
            float scale = (float)(1 + percentGrowth * Math.sin(Math.PI * interpolatedTime / percentRestTime));
            t.getMatrix().setScale(scale, scale, viewWidth / 2, viewHeight / 2);
        }
        else
        {
            t.getMatrix().setScale(1, 1);
        }
    }
}
