package com.example.linukey.BLL;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by linukey on 11/29/16.
 */

public class TodoHelper extends Application {

    public static Map<String, String> TaskState = new HashMap<String, String>(){{
        put("complete", "已完成");
        put("noComplete", "未完成");
        put("outOfDate", "过期");
    }};

    public static Map<String, String> TaskGroup = new HashMap<String, String>(){{
        put("today", "今日待办");
        put("tomorrow", "明日待办");
        put("next", "下一步行动");
    }};

    public static Map<String, String> UserGroup = new HashMap<String, String>(){{
        put("normal", "normal");
        put("administrator", "administrator");
    }};

    public static Map<String, String> PGS_State = new HashMap<String, String>(){{
        put("complete", "已完成");
        put("noComplete", "未完成");
    }};

    public static String UserName = "linukey";
    public static String PassWord = ".linukey.1";
    public static String UserId = "1";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }

    private static TodoHelper instance;

    public static TodoHelper getInstance() {
        return instance;
    }
}
