package com.example.linukey.DAL;

import android.content.Context;

import com.example.linukey.BLL.SelfPGSBLL;
import com.example.linukey.BLL.SelfTaskBLL;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.Model.Goal;
import com.example.linukey.Model.Project;
import com.example.linukey.Model.SelfTask;
import com.example.linukey.Model.Sight;

import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class LocalDateSource{
    public static List<SelfTask> selfTasks = new SelfTaskBLL().getTasks(TodoHelper.getInstance(), TodoHelper.UserId);
    public static List<Project> projects = new SelfPGSBLL().getProjects(TodoHelper.getInstance(), TodoHelper.UserId);
    public static List<Goal> goals = new SelfPGSBLL().getGoals(TodoHelper.getInstance(), TodoHelper.UserId);
    public static List<Sight> sights = new SelfPGSBLL().getSights(TodoHelper.getInstance(), TodoHelper.UserId);

    public static void updateSelfTasks(Context context, String userId) {
        selfTasks.clear();
        selfTasks = new SelfTaskBLL().getTasks(context, userId);
    }

    public static void updateProjects(Context context, String userId){
        projects.clear();
        projects = new SelfPGSBLL().getProjects(context, userId);
    }

    public static void updateGoals(Context context, String userId){
        goals.clear();
        goals = new SelfPGSBLL().getGoals(context, userId);
    }

    public static void updateSights(Context context, String userId){
        sights.clear();
        sights = new SelfPGSBLL().getSights(context, userId);
    }
}