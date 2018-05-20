package com.kira.kiralibrary.tools;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kirawu on 2017/9/29.
 */

public class MGson {
    public static <T> T fromJson(String result, Class<T> classOfT) throws Exception {
        T parseResult = new Gson().fromJson(result, classOfT);
        return parseResult;
    }

}
