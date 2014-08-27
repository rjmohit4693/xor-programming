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

package com.xorprogramming.game.achievement;

import java.util.EnumSet;
import java.util.Set;

// -------------------------------------------------------------------------
/**
 * An abstract class representing a game achievement. An achievement starts out locked and can be unlocked if an
 * achievement check succeeds. Once unlocked, it cannot be relocked. An achievement also has a set of events that
 * trigger an achievement check. Instances of this class should be managed with an {@link AchievementManager}.
 *
 * @param <E>
 *            An enum that contains the various game actions that could trigger an achievement check
 * @param <T>
 *            The type of object to check to see if the achievement is unlocked
 * @see AchievementManager
 * @author Steven Roberts
 * @version 1.0.0
 */
public abstract class Achievement<E extends Enum<E>, T>
{
    private final Set<E> checkActions;
    private final String name;
    private final String description;
    private final int    saveId;
    private Long         achievementUnlockedTime;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new Achievement object.
     *
     * @param saveId
     *            A unique identifier used by a {@link AchievementManager} for saving
     * @param name
     *            The name
     * @param description
     *            Explains the conditions under which the achievement can be unlocked
     * @param firstCheckAction
     *            The first enum item representing an action that triggers an achievement check
     * @param otherCheckActions
     *            The remaining enum items
     */
    public Achievement(int saveId, String name, String description, E firstCheckAction, E... otherCheckActions)
    {
        if (name == null)
        {
            throw new NullPointerException("The name must be non-null");
        }
        else if (description == null)
        {
            throw new NullPointerException("The description must be non-null");
        }
        this.checkActions = EnumSet.of(firstCheckAction, otherCheckActions);
        this.saveId = saveId;
        this.name = name;
        this.description = description;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Restores the achievement unlock time
     *
     * @param restoreAchievementUnlockedTime
     *            The achievement unlock time
     */
    final void restore(Long restoreAchievementUnlockedTime)
    {
        this.achievementUnlockedTime = restoreAchievementUnlockedTime;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the unique identifier for saving
     *
     * @return The id
     */
    final int getSaveId()
    {
        return saveId;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the set of actions that trigger an achievement check
     *
     * @return The set of actions
     */
    final Set<E> getCheckActions()
    {
        return checkActions;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Checks prerequisites then calls {@link #checkAchievement(Enum, Object)} to see if the achievement is unlocked. If
     * the achievement is unlocked, the time is recorded.
     *
     * @param action
     *            The action that triggered the check
     * @param checkObject
     *            The object to check to see if the achievement is unlocked
     * @return true if unlocked, false otherwise
     */
    final boolean check(E action, T checkObject)
    {
        if (!isUnlocked() && checkAchievement(action, checkObject))
        {
            achievementUnlockedTime = System.currentTimeMillis();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Checks if the achievement is unlocked by examining the generic {@code checkObject}
     *
     * @param action
     *            The action that triggered the check
     * @param checkObject
     *            The object to check to see if the achievement is unlocked
     * @return true if unlocked, false otherwise
     */
    protected abstract boolean checkAchievement(E action, T checkObject);
    
    
    // ----------------------------------------------------------
    /**
     * Checks if the achievement is unlocked.
     *
     * @return true if unlocked, false otherwise
     */
    public final boolean isUnlocked()
    {
        return achievementUnlockedTime != null;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the time at which the achievement was unlocked.
     *
     * @return The time in milliseconds since the epoch, or null if not yet unlocked
     */
    public final Long getAcievementUnlockTime()
    {
        return achievementUnlockedTime;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the achievement description
     *
     * @return The achievement description
     */
    public final String getDescription()
    {
        return description;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Returns the name of the achievement.
     */
    @Override
    public final String toString()
    {
        return name;
    }
}
