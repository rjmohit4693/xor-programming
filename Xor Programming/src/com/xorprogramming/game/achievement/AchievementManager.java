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

import com.xorprogramming.io.utils.IOUtils;
import com.xorprogramming.io.utils.Savable;
import com.xorprogramming.logging.Logger;
import com.xorprogramming.logging.LoggingType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// -------------------------------------------------------------------------
/**
 * This class manages a collection of {@link Achievement} instances. It handles the checking of all achievements that
 * correspond to an event and allows {@link AchievementListener}s to respond to unlocked achievements. The achievements
 * stored in an {@code AchievementManager} can be saved and restored using {@link IOUtils}.
 *
 * @param <E>
 *            An enum that contains the various game actions that could trigger an achievement check
 * @param <T>
 *            The type of object to check to see if the achievement is unlocked
 * @author Steven Roberts
 * @version 1.0.0
 */
public final class AchievementManager<E extends Enum<E>, T>
    implements Iterable<Achievement<?, ?>>, Savable
{
    private final Map<E, List<Achievement<E, T>>> achievementActionMap;
    private final List<Achievement<?, ?>>         achievementList;
    private final List<AchievementListener>       listeners;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new AchievementManager object.
     *
     * @param enumClass
     *            The class object of the generic enum type
     */
    public AchievementManager(Class<E> enumClass)
    {
        achievementActionMap = new EnumMap<E, List<Achievement<E, T>>>(enumClass);
        achievementList = new ArrayList<Achievement<?, ?>>();
        listeners = new ArrayList<AchievementListener>();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Adds an {@link AchievementListener} to be notified of an unlocked {@link Achievement}.
     *
     * @param listener
     *            The listener to add
     */
    public void addAchievementListener(AchievementListener listener)
    {
        listeners.add(listener);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Adds an {@link Achievement}
     *
     * @param achievement
     *            The {@link Achievement} to add
     */
    public void addAchievement(Achievement<E, T> achievement)
    {
        if (getAchievementById(achievement.getSaveId()) != null) // duplicate save id!
        {
            throw new IllegalArgumentException(String.format(
                "An achievement with the id %d has already been added",
                achievement.getSaveId()));
        }
        
        achievementList.add(achievement);
        for (E key : achievement.getCheckActions())
        {
            List<Achievement<E, T>> value = achievementActionMap.get(key);
            if (value == null)
            {
                value = new ArrayList<Achievement<E, T>>();
                achievementActionMap.put(key, value);
            }
            value.add(achievement);
        }
    }
    
    
    private Achievement<?, ?> getAchievementById(int id)
    {
        for (Achievement<?, ?> a : achievementList)
        {
            if (a.getSaveId() == id)
            {
                return a;
            }
        }
        return null;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Checks all achievements that correspond to the given action.
     *
     * @param action
     *            The achievement description
     * @param checkObject
     *            The object to check to see if the achievement is unlocked
     */
    public void checkAchievements(E action, T checkObject)
    {
        List<Achievement<E, T>> value = achievementActionMap.get(action);
        for (int i = 0; i < value.size(); i++)
        {
            Achievement<E, T> achievement = value.get(i);
            if (achievement.check(action, checkObject))
            {
                updateListeners(achievement);
            }
        }
    }
    
    
    private void updateListeners(Achievement<?, ?> achievement)
    {
        for (int i = 0; i < listeners.size(); i++)
        {
            listeners.get(i).onAchievementUnlock(achievement);
        }
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the {@link Achievement} at the given index
     *
     * @param index
     *            The index to get
     * @return The {@link Achievement} at the given index
     * @throws IndexOutOfBoundsException
     *             if {@code index < 0} or {@code index >= getAchievementCount()}
     */
    public Achievement<?, ?> getAchievement(int index)
    {
        return achievementList.get(index);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the total number of achievements
     *
     * @return total number of achievements
     */
    public int getAchievementCount()
    {
        return achievementList.size();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Gets the number of achievements that have been unlocked
     *
     * @return the number of unlocked achievements
     */
    public int getUnlockedAchievementsCount()
    {
        int achievementGets = 0;
        for (int i = 0; i < achievementList.size(); i++)
        {
            if (achievementList.get(i).isUnlocked())
            {
                achievementGets++;
            }
        }
        return achievementGets;
    }
    
    
    @Override
    public Iterator<Achievement<?, ?>> iterator()
    {
        return new IteratorDecorator<Achievement<?, ?>>(achievementList.iterator());
    }
    
    
    @Override
    public void restore(ObjectInputStream ois)
        throws IOException,
        ClassNotFoundException
    {
        while (ois.available() > 0)
        {
            int id = ois.readInt();
            Long achievementUnlockedTime = (Long)ois.readObject();
            Achievement<?, ?> a = getAchievementById(id);
            if (a == null)
            {
                Logger.log(LoggingType.WARNING, Logger.XOR_LOG_TAG, "Unable to restore achievement with id " + id);
            }
            else
            {
                a.restore(achievementUnlockedTime);
            }
        }
    }
    
    
    @Override
    public void save(ObjectOutputStream oos)
        throws IOException
    {
        for (Achievement<?, ?> a : this)
        {
            oos.writeInt(a.getSaveId());
            oos.writeObject(a.getAcievementUnlockTime());
        }
    }
    
    
    private static class IteratorDecorator<T>
        implements Iterator<T>
    {
        private final Iterator<T> i;
        
        
        public IteratorDecorator(Iterator<T> i)
        {
            this.i = i;
        }
        
        
        @Override
        public boolean hasNext()
        {
            return i.hasNext();
        }
        
        
        @Override
        public T next()
        {
            return i.next();
        }
        
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Cannot remove an element from this Iterator");
        }
    }
}
