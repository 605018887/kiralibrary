package com.kira.kiralibrary.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by kirawu on 2017/8/23.
 */

public class PermissionUtil {
    private Activity activity;
    public final static int WRITE_COARSE_LOCATION_REQUEST_CODE = 1;
    public final static int CAMERA_REQUEST_CODE = 2;
    public final static int BLUETOOTH_REQUEST_CODE = 3;
    public final static int PHONE_CODE = 4;
    public final static int WRITE_EXTERNAL_STORAGE_CODE = 5;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    public boolean isHasLocationPermisson() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermisson() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
    }

    public boolean isHasCameraPermisson() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermisson() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                CAMERA_REQUEST_CODE);//自定义的code
    }

    public boolean isHasBluetoothPermisson() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestBluetoothPermisson() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                BLUETOOTH_REQUEST_CODE);//自定义的code
    }

    public void showBluetoothPermissonToast(){
        //判断是否需要 向用户解释，为什么要申请该权限
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            UsualTools.showShortToast(activity, "获取蓝牙权限需要允许定位权限");
        }
    }

    public boolean isHasPhonePermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPhonePermisson() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                PHONE_CODE);//自定义的code
    }

    public boolean isHasWriteExternalPermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestWriteExternalPermisson() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_CODE);//自定义的code
    }
}
