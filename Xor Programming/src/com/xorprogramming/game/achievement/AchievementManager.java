package com.xorprogramming.game.achievement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AchievementManager<E extends Enum<E>, T>
{
    private final Map<E, List<Achievement<E, T>>> achievementActionMap;
    private final List<Achievement<E, T>>         achievementList;
    private final List<AchievementListener>       listeners;


    public AchievementManager()
    {
        achievementActionMap = new TreeMap<E, List<Achievement<E, T>>>(new Comparator<E>()
        {
            public int compare(E lhs, E rhs)
            {
                return lhs.compareTo(rhs);
            }
        });
        achievementList = new ArrayList<Achievement<E, T>>();
        listeners = new ArrayList<AchievementListener>();
    }


    public void addAchievementListener(AchievementListener listener)
    {
        listeners.add(listener);
    }


    public void addAchievement(Achievement<E, T> achievement)
    {
        achievementList.add(achievement);
        for (E key : achievement.getCheckActions())
        {
            List<Achievement<E, T>> value = achievementActionMap.get(key);
            if (value == null)
            {
                value = new ArrayList<Achievement<E, T>>();
                value.add(achievement);
                achievementActionMap.put(key, value);
            }
            else
            {
                value.add(achievement);
            }
        }
    }


    public void checkAchievements(E action, T t)
    {
        List<Achievement<E, T>> value = achievementActionMap.get(action);
        for (int i = 0; i < value.size(); i++)
        {
            Achievement<E, T> achievement = value.get(i);
            if (achievement.checkAchievement(action, t))
            {
                updateListeners(achievement);
            }
        }
    }


    private void updateListeners(Achievement<?, ?> achievement)
    {
        for (int i = 0; i < listeners.size(); i++)
        {
            listeners.get(i).onAchievementGet(achievement);
        }
    }


    public int getAchievementCount()
    {
        return achievementList.size();
    }


    public int getAchievementsGetsCount()
    {
        int achievementGets = 0;
        for (int i = 0; i < achievementList.size(); i++)
        {
            if (achievementList.get(i).hasAchievement())
            {
                achievementGets++;
            }
        }
        return achievementGets;
    }
}
