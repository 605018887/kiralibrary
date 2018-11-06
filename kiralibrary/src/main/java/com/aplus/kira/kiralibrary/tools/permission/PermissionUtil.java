package com.aplus.kira.kiralibrary.tools.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2018\5\17 0017.
 */

public class PermissionUtil {
    private Context context;
    private static PermissionUtil instance;

    public PermissionUtil(Context context) {
        this.context = context;
    }

    public static PermissionUtil getInstance(Context context) {
        if (instance == null) {
            instance = new PermissionUtil(context);
        }
        return instance;
    }

    public List<PermissionInfo> getAllPermissionInfos() {
        List<PermissionInfo> permissionInfoList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] permissions = pi.requestedPermissions;
            if (permissions != null) {
                for (String str : permissions) {
                    PermissionInfo permissionInfo = context.getPackageManager().getPermissionInfo(str, 0);
                    permissionInfoList.add(permissionInfo);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return permissionInfoList;
    }

    public boolean checkPermissionGrant(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermisson(Activity activity, String permission, PermissionCallback permissionCallback) {
        requestPermissons(activity, new String[]{permission}, permissionCallback);
    }

    public void requestPermissons(Activity activity, String[] permissions, PermissionCallback permissionCallback) {
        if (permissionCallback instanceof PermissionCallback.OneMethodCallback) {
            this.oneMethodCallback = (PermissionCallback.OneMethodCallback) permissionCallback;
        } else if (permissionCallback instanceof PermissionCallback.TwoMethodCallback) {
            this.twoMethodCallback = (PermissionCallback.TwoMethodCallback) permissionCallback;
        }
        List<String> pers = new ArrayList<>();
        for (String permission:permissions){
            if (!checkPermissionGrant(permission)){
                pers.add(permission);
            }
        }
        if (pers.size()==0){
            if (permissionCallback instanceof PermissionCallback.OneMethodCallback) {
                this.oneMethodCallback = (PermissionCallback.OneMethodCallback) permissionCallback;
                this.oneMethodCallback.onPermissionCallback(new String[0],new String[0]);
            } else if (permissionCallback instanceof PermissionCallback.TwoMethodCallback) {
                this.twoMethodCallback = (PermissionCallback.TwoMethodCallback) permissionCallback;
                this.twoMethodCallback.onSuccessCallback(new String[0]);
//                this.twoMethodCallback.onDeniedCallback(new String[0]);
            }
            return;
        }
        permissions = new String[pers.size()];
        int i=0;
        for (String permission:pers){
            permissions[i] = permission;
            i++;
        }
        ActivityCompat.requestPermissions(activity, permissions,
                0);//自定义的code
    }

    public void requestAllDynamicPermissons(Activity activity, PermissionCallback permissionCallback) {
        List<PermissionInfo> groups = PermissionUtil.getInstance(context).getAllPermissionInfos();

        for (int i = 0; i < groups.size(); i++) {
            PermissionInfo info = groups.get(i);
            boolean isGrant = PermissionUtil.getInstance(context).checkPermissionGrant(info.name);
            if (isGrant) {
                groups.remove(info);
                i--;
            }
        }
        String[] permissions = new String[groups.size()];
        int i = 0;
        for (PermissionInfo info : groups) {
            permissions[i] = info.name;
            i++;
        }
        requestPermissons(activity, permissions, permissionCallback);
    }


    private PermissionCallback.OneMethodCallback oneMethodCallback;
    private PermissionCallback.TwoMethodCallback twoMethodCallback;

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String sPermissions = "";
        String dPermissions = "";
        for (String permission : permissions) {
            if (checkPermissionGrant(permission)) {
                if (sPermissions.equals("")) {
                    sPermissions = permission;
                } else {
                    sPermissions = sPermissions + "---" + permission;
                }
            } else {
                if (dPermissions.equals("")) {
                    dPermissions = permission;
                } else {
                    dPermissions = dPermissions + "---" + permission;
                }
            }
        }
        String[] successPermissions = sPermissions.split("---");
        String[] deniedPermissions = dPermissions.split("---");
        boolean isHasDeniedPermissions = true;
        if (successPermissions[0].equals("")) {
            successPermissions = new String[0];
        }
        if (deniedPermissions[0].equals("")) {
            deniedPermissions = new String[0];
            isHasDeniedPermissions = false;
        }
        if (oneMethodCallback != null) {
            oneMethodCallback.onPermissionCallback(successPermissions, deniedPermissions);
        }
        if (twoMethodCallback != null) {
            twoMethodCallback.onSuccessCallback(successPermissions);
            if (isHasDeniedPermissions){
                twoMethodCallback.onDeniedCallback(deniedPermissions);
            }
        }
    }

    public void showToPermissionActivityDialog(final Activity activity, String message) {
        new AlertDialog.Builder(activity).setCancelable(false)
                .setMessage(message)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ToPermissionActivityUtil().toPermissionActivity(activity);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
}
