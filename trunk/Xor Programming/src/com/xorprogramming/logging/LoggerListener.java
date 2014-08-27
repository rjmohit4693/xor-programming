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

package com.xorprogramming.logging;

// -------------------------------------------------------------------------
/**
 * A listener interface for responding to logging event from the {@link Logger}
 *
 * @author Steven Roberts
 * @version 1.0.0
 */
public interface LoggerListener
{
    // ----------------------------------------------------------
    /**
     * Called when a message is logged to output
     *
     * @param type
     *            The logging type
     * @param tag
     *            The logging tag
     * @param message
     *            The message that was logged
     */
    void onLog(LoggingType type, String tag, String message);
}
