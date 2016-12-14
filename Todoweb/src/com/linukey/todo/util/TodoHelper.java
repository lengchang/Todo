package com.linukey.todo.util;

import java.util.HashMap;
import java.util.Map;

public class TodoHelper {
    public static Map<String, String> UserGroup = new HashMap<String, String>(){{
        put("normal", "normal");
        put("root", "root");
    }};
}
