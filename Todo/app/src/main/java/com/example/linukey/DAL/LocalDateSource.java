package com.example.linukey.DAL;

import android.content.ContentResolver;
import android.content.Context;

import com.example.linukey.BLL.AddSelfPGSBLL;
import com.example.linukey.BLL.AddSelfTaskBLL;
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
    public static List<SelfTask> selfTasks = new AddSelfTaskBLL().getTasks(TodoHelper.getInstance(), TodoHelper.UserId);
    public static List<Project> projects = new AddSelfPGSBLL().getProjects(TodoHelper.getInstance(), TodoHelper.UserId);
    public static List<Goal> goals = new AddSelfPGSBLL().getGoals(TodoHelper.getInstance(), TodoHelper.UserId);
    public static List<Sight> sights = new AddSelfPGSBLL().getSights(TodoHelper.getInstance(), TodoHelper.UserId);

    public static void updateSelfTasks(Context context, String userId) {
        selfTasks.clear();
        selfTasks = new AddSelfTaskBLL().getTasks(context, userId);
    }

    public static void updateProjects(Context context, String userId){
        projects.clear();
        projects = new AddSelfPGSBLL().getProjects(context, userId);
    }

    public static void updateGoals(Context context, String userId){
        goals.clear();
        goals = new AddSelfPGSBLL().getGoals(context, userId);
    }

    public static void updateSights(Context context, String userId){
        sights.clear();
        sights = new AddSelfPGSBLL().getSights(context, userId);
    }
}