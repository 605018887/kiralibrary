package com.aplus.kira.kiralibrary.tools;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirawu on 2017/9/29.
 */

public class MGson {
    public static <T> T fromJson(String result, Class<T> classOfT) throws Exception {
        T parseResult = new Gson().fromJson(result, classOfT);
        return parseResult;
    }

    public static <T> List<T> fromJsonArray(String result, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(result, type);

        List<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
