package com.jxwifi.cloud.demo2;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aplus.kira.kiralibrary.Mdialog;
import com.aplus.kira.kiralibrary.tools.permission.PermissionCallback;
import com.aplus.kira.kiralibrary.tools.permission.PermissionUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.getInstance(getApplicationContext()).requestAllDynamicPermissons(this, new PermissionCallback.OneMethodCallback() {
            @Override
            public void onPermissionCallback(String[] successPermissions, String[] deniedPermissions) {
                Log.d("spm", "MainActivity toLoginActivity");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                },2000);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionUtil.getInstance(getApplicationContext()).onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("spm","MainActivity onRequestPermissionsResult");
        PermissionUtil.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
