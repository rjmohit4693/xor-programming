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

package com.xorprogramming.thread;

// -------------------------------------------------------------------------
/**
 * The various states in which an {@code UpdaterThread} can be.
 *
 * @author Steven Roberts
 * @version 1.1.0
 */
public enum UpdaterThreadState
{
    /**
     * When an {@code UpdaterThread} is created and before an {@code start} is called
     */
    NOT_YET_STARTED("Not yet started"),
    /**
     * When the thread is running and the an {@code Updatable} is being updated
     */
    RUNNING("Running"),
    /**
     * When a stop request was sent, but before the thread is actually stopped
     */
    STOPPING("Stopping"),
    /**
     * When the thread is completely stopped
     */
    STOPPED("Stopped");
    
    private String name;
    
    
    private UpdaterThreadState(String name)
    {
        this.name = name;
    }
    
    
    @Override
    public String toString()
    {
        return name;
    }
}
