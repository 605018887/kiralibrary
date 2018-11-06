package com.aplus.kira.kiralibrary.tools.permission;

import android.Manifest;

/**
 * Created by Administrator on 2018\5\18 0018.
 */

public class PermissionString {
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String WRITE_SETTINGS = Manifest.permission.WRITE_SETTINGS;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static PermissionString instance;

    public static PermissionString getInstance() {
        if (instance == null) {
            instance = new PermissionString();
        }
        return instance;
    }

    private String getPermissionExplain(String permission) {
        switch (permission) {
            case READ_CALENDAR:
                return "读写日历权限";
            case WRITE_CALENDAR:
                return "读写日历权限";
            case CAMERA:
                return "摄像头权限";
            case READ_CONTACTS:
                return "读写联系人权限";
            case WRITE_CONTACTS:
                return "读写联系人权限";
            case GET_ACCOUNTS:
                return "获取用户信息权限";
            case ACCESS_FINE_LOCATION:
                return "获取定位权限";
            case ACCESS_COARSE_LOCATION:
                return "获取定位权限";
            case RECORD_AUDIO:
                return "录像权限";
            case READ_PHONE_STATE:
                return "读取手机状态权限";
            case CALL_PHONE:
                return "打电话权限";
            case ADD_VOICEMAIL:
                return "允许应用程序添加系统中的语音邮件权限";
            case USE_SIP:
                return "允许程序使用SIP视频服务权限";
            case PROCESS_OUTGOING_CALLS:
                return "允许应用程序监视、修改、忽略拨出的电话权限";
            case SEND_SMS:
                return "发送短信权限";
            case RECEIVE_SMS:
                return "接收短信权限";
            case READ_SMS:
                return "读取短信权限";
            case RECEIVE_WAP_PUSH:
                return "允许一个应用程序监测接受的WAP-PSUH消息权限";
            case RECEIVE_MMS:
                return "接收彩信权限";
            case WRITE_EXTERNAL_STORAGE:
                return "允许应用程序写数据到外部存储设备权限";
            case WRITE_SETTINGS:
                return "允许一个应用程序读写系统设置";
            case READ_EXTERNAL_STORAGE:
                return "允许应用程序读取外部存储设备数据权限";
        }
        return "未知权限";
    }

    public String getPermissionsTip(String[] permissons) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = permissons.length; i < size; i++) {
            if (i == 0) {
                builder.append(getPermissionExplain(permissons[i]));
            } else {
                builder.append("，" + getPermissionExplain(permissons[i]));
            }
        }
        return "请打开" + builder.toString();
    }
}
