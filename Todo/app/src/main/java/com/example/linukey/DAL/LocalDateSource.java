package com.example.linukey.DAL;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.example.linukey.BLL.AddSelfBLL;
import com.example.linukey.BLL.TodoHelper;
import com.example.linukey.Model.SelfTask;
import com.example.linukey.Model.SelfTaskView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linukey on 12/2/16.
 */

public class LocalDateSource{
    public static List<SelfTask> selfTasks = new AddSelfBLL().getTasks(TodoHelper.getInstance(), TodoHelper.UserId);

    public static void updateSelfTasks(Context context, String userId) {
        selfTasks.clear();
        selfTasks = new AddSelfBLL().getTasks(context, userId);
    }
}