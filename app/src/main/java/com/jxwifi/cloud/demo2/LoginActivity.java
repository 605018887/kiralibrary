package com.jxwifi.cloud.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aplus.kira.kiralibrary.tools.permission.PermissionCallback;
import com.aplus.kira.kiralibrary.tools.permission.PermissionUtil;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PermissionUtil.getInstance(getApplicationContext()).requestAllDynamicPermissons(this, new PermissionCallback.TwoMethodCallback() {
//            @Override
//            public void onSuccessCallback(String[] successPermissions) {
//
//
//            }
//
//            @Override
//            public void onDeniedCallback(String[] deniedPermissions) {
//
//            }
//        });
        PermissionUtil.getInstance(getApplicationContext()).onRequestPermissionsResult(1, new String[]{}, new int[]{});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionUtil.getInstance(getApplicationContext()).onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("spm", "LoginActivity onRequestPermissionsResult");
        PermissionUtil.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
