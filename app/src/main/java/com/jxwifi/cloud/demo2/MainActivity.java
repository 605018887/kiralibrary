package com.jxwifi.cloud.demo2;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aplus.kira.kiralibrary.Mdialog;
import com.aplus.kira.kiralibrary.tools.permission.PermissionCallback;
import com.aplus.kira.kiralibrary.tools.permission.PermissionUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.getInstance(getApplicationContext()).requestAllDynamicPermissons(this, new PermissionCallback.TwoMethodCallback() {
            @Override
            public void onSuccessCallback(String[] successPermissions) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                },2000);

            }

            @Override
            public void onDeniedCallback(String[] deniedPermissions) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
