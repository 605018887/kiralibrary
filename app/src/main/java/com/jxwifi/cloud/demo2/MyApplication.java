package com.jxwifi.cloud.demo2;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.aplus.kira.kiralibrary.tools.UsualTools;

import com.squareup.leakcanary.LeakCanary;



public class MyApplication extends Application {

    private static MyApplication application;

    public static MyApplication getApplication() {
        return application;
    }



    @Override
    public void onCreate() {
        application = this;
        UsualTools.isShowPrintMsg = true;
        initLeakCanary();

        super.onCreate();
    }

    /**
     * 初始化内存泄漏检测
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }



}
