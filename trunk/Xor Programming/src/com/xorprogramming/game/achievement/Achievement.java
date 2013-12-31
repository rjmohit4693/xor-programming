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
    private boolean      hasAchievement;
    private long         achievementGetTime;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new Achievement object.
     * 
     * @param name
     * @param description
     * @param firstCheckAction
     * @param otherCheckActions
     */
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
    
    
    final void restore(boolean restoreHasAchievement, long restoreAchievementGetTime)
    {
        this.hasAchievement = restoreHasAchievement;
        this.achievementGetTime = restoreAchievementGetTime;
    }
    
    
    final Set<E> getCheckActions()
    {
        return checkActions;
    }
    
    
    final boolean checkAchievement(E action, T t)
    {
        if (!hasAchievement && abstractCheckAchievement(action, t))
        {
            achievementGetTime = System.currentTimeMillis();
            return hasAchievement = true;
        }
        else
        {
            return false;
        }
    }
    
    
    protected abstract boolean abstractCheckAchievement(E action, T t);
    
    
    protected abstract int getID();
    
    
    public final boolean hasAchievement()
    {
        return hasAchievement;
    }
    
    
    public final long getAcievementGetTime()
    {
        return achievementGetTime;
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
