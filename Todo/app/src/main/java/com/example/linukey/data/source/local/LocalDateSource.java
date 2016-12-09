package com.example.linukey.data.source.local;

import android.content.Context;

import com.example.linukey.data.model.Goal;
import com.example.linukey.data.model.Project;
import com.example.linukey.data.model.SelfTask;
import com.example.linukey.data.model.Sight;
import com.example.linukey.data.model.TeamTask;

import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class LocalDateSource{
    public static List<SelfTask> selfTasks = null;
    public static List<Project> projects = null;
    public static List<Goal> goals = null;
    public static List<Sight> sights = null;
    public static List<TeamTask> teamTasks = null;

    public static void updateSelfTasks(Context context) {
        selfTasks = SelfTask.getTasks(context);
    }

    public static void updateProjects(Context context){
        projects = Project.getProjects(context);
    }

    public static void updateGoals(Context context){
        goals = Goal.getGoals(context);
    }

    public static void updateSights(Context context){
        sights = Sight.getSights(context);
    }

    public static void updateTeamTasks(Context context){
        teamTasks = TeamTask.getTeamTasks(context);
    }
}