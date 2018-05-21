package com.aplus.kira.kiralibrary.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class ServiceTools {

	/**
	 * 启动服务
	 * 
	 * @param context
	 * @param serviceName
	 *            服务名称
	 * @param packageName
	 *            服务包名
	 * @param serviceCls
	 *            服务类
	 */
	public void startService(Context context, String serviceName,
			String packageName, Class<?> serviceCls) {
		Intent intent = new Intent(context, serviceCls);
		intent.putExtra("serviceName", serviceName);
		intent.putExtra("packageName", packageName);
		context.startService(intent);
	}

	/**
	 * 结束服务
	 * 
	 * @param context
	 * @param serviceName
	 *            服务名称
	 * @param packageName
	 *            服务包名
	 * @param serviceCls
	 *            服务类
	 */
	public void stopService(Context context, String serviceName,
			String packageName, Class<?> serviceCls) {
		Intent intent = new Intent(context, serviceCls);
		intent.putExtra("serviceName", serviceName);
		intent.putExtra("packageName", packageName);
		context.stopService(intent);
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param mContext
	 * @param serviceName
	 *            服务名称
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(80);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}
}
