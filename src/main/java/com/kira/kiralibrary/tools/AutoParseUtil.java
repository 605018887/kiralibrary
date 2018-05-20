package com.kira.kiralibrary.tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class AutoParseUtil {


    public static <E> List<E> getList(JSONArray array, Class<E> cls) throws Exception {
        List<E> list = new ArrayList<>();
        E cl = cls.newInstance();
        Field[] fs = getField(cl);
        for (int i = 0, size = array.length(); i < size; i++) {
            JSONObject oi = array.getJSONObject(i);
            Iterator<String> key = oi.keys();
            cl = cls.newInstance();
            while (key.hasNext()) {
                String keyName = key.next();
                setValue(keyName, oi, cl, fs);
            }
            list.add(cl);
        }
        return list;
    }

    public static <E> E getEntity(JSONObject object, Class<E> cls) throws Exception {
        E cl = cls.newInstance();
        return getEntity2(object, cl);
    }

    public static <E> E getEntity2(JSONObject object, E cl) throws Exception {
        Field[] fs = getField(cl);
        Iterator<String> key = object.keys();
        while (key.hasNext()) {
            String keyName = key.next();
            setValue(keyName, object, cl, fs);
        }
        return cl;
    }

    private static <E> void setValue(String keyName, JSONObject oi, E e, Field[] fs) throws Exception {
        for (int i = 0, size = fs.length; i < size; i++) {
            Field f = fs[i];
            f.setAccessible(true); //设置些属性是可以访问的
            Class<?> type = f.getType();

            if (f.getName().equals(keyName)) {
                if (type.getName().contains("int")) {
                    String methodName = "set" + toUpperCaseByFirst(keyName);
                    Method method = e.getClass().getMethod(methodName, type);
                    method.invoke(e, MJsonUtil.getInt(oi, keyName));
                } else if (type.getName().contains("String")) {
                    String methodName = "set" + toUpperCaseByFirst(keyName);
                    Method method = e.getClass().getMethod(methodName, type);
                    method.invoke(e, MJsonUtil.getString(oi, keyName));
                } else {
                    String className = "";
                    for (String iClassName : keyName.split("_")) {
                        className = className + toUpperCaseByFirst(iClassName);
                    }

                    String[] judgeS = oi.toString().split(keyName);
                    String o0 = null;
                    for (int jI = 0, sizeJ = judgeS.length; jI < sizeJ; jI++) {
                        String judge = oi.toString().split(keyName)[jI];
                        if (jI > 0) {
                            judge = judge.substring(2, 3);
                            if (judge.equals("[") || judge.equals("{")) {
                                o0 = judge;
                                break;
                            }
                        }
                    }
                    if (o0 == null) {
                        return;
                    }

                    if (o0.equals("[")) {
                        Class<?> itemClasses[] = e.getClass().getClasses();
                        JSONArray array = MJsonUtil.getJSONArray(oi, keyName);

//                        UsualTools.showPrintMsg("className:" + className);
                        for (Class<?> itemClass : itemClasses) {
//                            UsualTools.showPrintMsg("itemClass.getName():" + keyName+"-"+itemClass.getName());
                            if (itemClass.getName().contains(className)) {
//                                UsualTools.showPrintMsg("itemClass.getName():" + keyName + "-" + itemClass.getName());
                                Object newitemClass = Class.forName(itemClass.getName()).newInstance();
                                newitemClass = getList(array, newitemClass.getClass());
                                String methodName = "set" + toUpperCaseByFirst(keyName);
                                Method method = e.getClass().getMethod(methodName, type);
                                method.invoke(e, newitemClass);
                            }
                        }
                    } else {
//                        UsualTools.showPrintMsg("keyName:" + keyName);
                        Class<?> itemClasses[] = e.getClass().getClasses();
                        JSONObject itemObject = oi.getJSONObject(keyName);
//                        UsualTools.showPrintMsg("itemObject:" + itemObject);
                        for (Class<?> itemClass : itemClasses) {
//                            UsualTools.showPrintMsg("itemClass.getName():" + itemClass.getName());
                            if (itemClass.getName().contains(className)) {
//                                UsualTools.showPrintMsg("itemClass.getName():" + keyName + "-" + itemClass.getName());
                                Object newitemClass = Class.forName(itemClass.getName()).newInstance();
                                newitemClass = getEntity2(itemObject, newitemClass);
                                String methodName = "set" + toUpperCaseByFirst(keyName);
                                Method method = e.getClass().getMethod(methodName, type);
                                method.invoke(e, newitemClass);
                            }
                        }
                    }
                }

//                else if (type.getName().contains("List")) {
//
//                    UsualTools.showPrintMsg("list json:"+oi);
//
//                    Class<?> itemClasses[] = e.getClass().getClasses();
//                    JSONArray array = MJsonUtil.getJSONArray(oi, keyName);
//
//                    for (Class<?> itemClass : itemClasses) {
//                        Object newitemClass = Class.forName(itemClass.getName()).newInstance();
//                        newitemClass = getList(array, newitemClass.getClass());
//                        String methodName = "set" + toUpperCaseByFirst(keyName);
//                        Method method = e.getClass().getMethod(methodName, type);
//                        method.invoke(e, newitemClass);
//                    }
//                } else if (type.getName().contains("DataBean")) {
//                    UsualTools.showPrintMsg("object json:"+oi);
//
//                    Class<?> itemClasses[] = e.getClass().getClasses();
//                    JSONObject itemObject = oi.getJSONObject(keyName);
//                    for (Class<?> itemClass : itemClasses) {
//                        Object newitemClass = Class.forName(itemClass.getName()).newInstance();
//                        newitemClass = getEntity2(itemObject, newitemClass);
//                        String methodName = "set" + toUpperCaseByFirst(keyName);
//                        Method method = e.getClass().getMethod(methodName, type);
//                        method.invoke(e, newitemClass);
//                    }
//                }
            }
        }
    }

    private static String toUpperCaseByFirst(String name) {
        String first = name.substring(0, 1);
        return first.toUpperCase() + name.substring(1, name.length());
    }


    private static <E> Field[] getField(E e) {
        Field[] fs = e.getClass().getDeclaredFields();
        return fs;
    }

    public static <E> E getParseResult(String result, Class<E> cls) throws Exception {
        JSONObject object = new JSONObject(result);
        E parseResult = getEntity(object, cls);
        return parseResult;
    }
}
