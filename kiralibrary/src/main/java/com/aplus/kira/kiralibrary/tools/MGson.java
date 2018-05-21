package com.aplus.kira.kiralibrary.tools;


import com.google.gson.Gson;

/**
 * Created by kirawu on 2017/9/29.
 */

public class MGson {
    public static <T> T fromJson(String result, Class<T> classOfT) throws Exception {
        T parseResult = new Gson().fromJson(result, classOfT);
        return parseResult;
    }

}
