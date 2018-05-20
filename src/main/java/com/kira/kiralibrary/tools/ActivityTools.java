package com.kira.kiralibrary.tools;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

public class ActivityTools {

	public boolean isRunningForeground(Context context) {
		String packageName = getPackageName(context);
		String topActivityPackageName = getTopActivityPackageName(context);
		if (packageName != null && topActivityPackageName != null
				&& topActivityPackageName.equals(packageName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前activity的名称
	 * 
	 * @param context
	 * @return
	 */
	public String getTopActivityPackageName(Context context) {
		String topActivityPackageName = null;
		ActivityManager activityManager = (ActivityManager) (context
				.getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningTaskInfo> runningTaskInfos = activityManager
				.getRunningTasks(1);
		if (runningTaskInfos != null) {
			ComponentName f = runningTaskInfos.get(0).topActivity;
			topActivityPackageName = f.getPackageName();
		}
		return topActivityPackageName;
	}

	/**
	 * 获取包名
	 * 
	 * @param context
	 * @return
	 */
	public String getPackageName(Context context) {
		String packageName = context.getPackageName();
		return packageName;
	}
}
