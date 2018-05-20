package com.kira.kiralibrary.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BroadcastUtil {

	/**
	 * 注册广播
	 * @param context
	 * @param receiver 广播
	 * @param priority 优先级
	 * @param action 标识
	 */
	public void registerBroadcast(Context context, BroadcastReceiver receiver,
			int priority, String action) {
		IntentFilter filter = new IntentFilter();
		filter.setPriority(priority);
		filter.addAction(action);
		context.registerReceiver(receiver, filter);
	}

	/**
	 * 发送广播
	 * @param context
	 * @param intent
	 * @param action 标识
	 */
	public void sendBroadcast(Context context, Intent intent, String action) {
		intent.setAction(action);
		context.sendBroadcast(intent);
	}
}
