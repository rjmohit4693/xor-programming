package com.xorprogramming.game.achievement;

import com.xorprogramming.io.savable.SaveException;
import com.xorprogramming.io.savable.Savable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class AchievementManager<E extends Enum<E>, T>
    implements Savable
{
    private final Map<E, List<Achievement<E, T>>> achievementActionMap;
    private final List<Achievement<E, T>>         achievementList;
    private final List<AchievementListener>       listeners;


    public AchievementManager(Class<E> enumClass)
    {
        achievementActionMap = new EnumMap<E, List<Achievement<E, T>>>(enumClass);
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
            if (achievement.check(action, t))
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


    public Achievement<?, ?> getAchievement(int index)
    {
        return achievementList.get(index);
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


    public void save(ObjectOutputStream out)
        throws IOException,
        SaveException
    {
        for (Achievement<E, T> a : achievementList)
        {
            out.writeInt(a.getID());
            out.writeBoolean(a.hasAchievement());
            out.writeLong(a.getAcievementGetTime());
        }
    }


    public void restore(ObjectInputStream in)
        throws IOException,
        SaveException
    {
        while (in.available() > 0)
        {
            int id = in.readInt();
            boolean hasAchievement = in.readBoolean();
            long achievementGetTime = in.readLong();
            for (Achievement<E, T> a : achievementList)
            {
                if (a.getID() == id)
                {
                    a.restore(hasAchievement, achievementGetTime);
                }
            }
        }
    }
}
