/*
 * Copyright (C) 2014 Xor Programming Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.xorprogramming.game.achievement;

import java.util.EnumSet;
import java.util.Set;

// -------------------------------------------------------------------------
/**
 * An abstract class representing a game achievement
 *
 * @param <E>
 *            An enum that contains the various game actions that could trigger an achievement get check
 * @param <T>
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


    final void restore(Long restoreAchievementUnlockedTime)
    {
        this.achievementUnlockedTime = restoreAchievementUnlockedTime;
    }


    final int getSaveId()
    {
        return saveId;
    }


    final Set<E> getCheckActions()
    {
        return checkActions;
    }


    final boolean check(E action, T t)
    {
        if (!hasAchievement() && checkAchievement(action, t))
        {
            achievementUnlockedTime = System.currentTimeMillis();
            return true;
        }
        else
        {
            return false;
        }
    }


    protected abstract boolean checkAchievement(E action, T t);


    public final boolean hasAchievement()
    {
        return achievementUnlockedTime != null;
    }


    public final Long getAcievementGetTime()
    {
        return achievementUnlockedTime;
    }


    public final String getDescription()
    {
        return description;
    }


    @Override
    public final String toString()
    {
        return name;
    }
}
