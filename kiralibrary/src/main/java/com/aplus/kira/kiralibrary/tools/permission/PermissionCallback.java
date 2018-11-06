package com.aplus.kira.kiralibrary.tools.permission;

/**
 * Created by Administrator on 2018\5\17 0017.
 */

public interface PermissionCallback {

    interface OneMethodCallback extends PermissionCallback {
        void onPermissionCallback(String[] successPermissions, String[] deniedPermissions);
    }

    interface TwoMethodCallback extends PermissionCallback {
        void onSuccessCallback(String[] successPermissions);
        void onDeniedCallback(String[] deniedPermissions);
    }


}
