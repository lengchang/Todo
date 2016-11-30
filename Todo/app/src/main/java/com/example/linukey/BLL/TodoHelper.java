package com.example.linukey.BLL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by linukey on 11/29/16.
 */

public class TodoHelper {

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
}
