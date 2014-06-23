/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.game;

import android.content.res.Resources;
import android.graphics.Canvas;

public interface GameRenderer<T extends GameEngine<?>>
{
    
    void initialize(Resources res, GameExceptionController controller);
    
    
    void render(T engine, Canvas c, int width, int height, GameExceptionController controller);
    
    
    void dispose(GameExceptionController controller);
}
