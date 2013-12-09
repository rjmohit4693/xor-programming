package com.xorprogramming.game.achievement;

import java.util.EnumSet;
import java.util.Set;

public abstract class Achievement<E extends Enum<E>, T>
{
    private final Set<E> checkActions;
    private final String name;
    private final String description;
    private boolean      hasAchievement;


    public Achievement(String name, String description, E firstCheckAction, E... otherCheckActions)
    {
        this(name, description, false, firstCheckAction, otherCheckActions);
    }


    public Achievement(
        String name,
        String description,
        boolean hasAchievement,
        E firstCheckAction,
        E... otherCheckActions)
    {
        this.name = name;
        this.description = description;
        this.hasAchievement = hasAchievement;
        this.checkActions = EnumSet.of(firstCheckAction, otherCheckActions);
    }


    final Set<E> getCheckActions()
    {
        return checkActions;
    }


    final boolean checkAchievement(E action, T t)
    {
        if (!hasAchievement)
        {
            return hasAchievement = abstractCheckAchievement(action, t);
        }
        else
        {
            return false;
        }
    }


    protected abstract boolean abstractCheckAchievement(E action, T t);


    public final boolean hasAchievement()
    {
        return hasAchievement;
    }


    public String getDescription()
    {
        return description;
    }


    @Override
    public String toString()
    {
        return name;
    }
}
