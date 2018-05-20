package com.kira.kiralibrary.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;

/**
 * 短信信息接收工具类，注意添加权限，sms-read和sms-receive
 * 
 * @author estronger_kira
 * 
 */
public class SmsReceiverTool {
	private Context context;

	public SmsReceiverTool(Context context) {
		super();
		this.context = context;
	}

	public void setMessageReceiverCallback(MessageReceiveCallBack callBack) {
		this.callBack = callBack;
		SMSMessageReceiver receiver = new SMSMessageReceiver();
		IntentFilter filter = new IntentFilter(SMS_ACTION);
		context.registerReceiver(receiver, filter);
	}

	private String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	public class SMSMessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(SMS_ACTION)) {
				Object[] messages = (Object[]) intent
						.getSerializableExtra("pdus");
				byte[][] pduObjs = new byte[messages.length][];
				for (int i = 0, size = pduObjs.length; i < size; i++) {
					pduObjs[i] = (byte[]) messages[i];
				}
				byte[][] pdus = new byte[pduObjs.length][];
				int pduCount = pdus.length;
				SmsMessage[] msgs = new SmsMessage[pduCount];
				for (int i = 0; i < pduCount; i++) {
					pdus[i] = pduObjs[i];
					msgs[i] = SmsMessage.createFromPdu(pdus[i]);
				}
				for (SmsMessage message : msgs) {
					callBack.onReceiveMessage(
							message.getDisplayOriginatingAddress(),
							message.getDisplayMessageBody());
				}

			}

		}
	}

	public interface MessageReceiveCallBack {
		void onReceiveMessage(String from, String body);
	}

	private MessageReceiveCallBack callBack;

}
