package com.kira.kiralibrary.tools;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Countdown {
	private int settingTime;// 设置时间
	private int time;// 倒计时的整个时间
	private Handler mHandler = new Handler();// 全局handler
	private Button messageBtn;
	private String btnText;
	private Thread thread;
	private boolean isRun;
	private String countDownWordFont = "";
	private String countDownWordBack = "";

	/**
	 * 执行倒计时操作
	 * 
	 * @param settingTime
	 *            设置倒计时时间
	 * @param messageBtn
	 *            倒计时按钮
	 * @param btnText
	 *            倒计时按钮文字描述
	 * @param rListener
	 *            重置倒计时的监听
	 */
	public Countdown(int settingTime, Button messageBtn, String btnText,
			ResetListener rListener) {
		super();
		this.btnText = btnText;
		this.settingTime = settingTime;
		this.messageBtn = messageBtn;
		this.rListener = rListener;
		time = settingTime;
		messageBtn.setText(btnText);
	}

	public void setCountDown() {
		isRun = true;
		thread = new Thread(new ClassCut());
		thread.start();
	}

	public void reset() {
		isRun = false;
		time = settingTime;// 修改倒计时剩余时间变量为设置时间
		thread = null;
		messageBtn.setText(btnText);
		messageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rListener.reset();
			}
		});
		if (finishListener != null) {
			finishListener.onCountDownFinish();
		}
	}

	public interface CountDownFinishListener {
		void onCountDownFinish();
	}

	private CountDownFinishListener finishListener;

	public void setCountDownFinishListener(
			CountDownFinishListener finishListener) {
		this.finishListener = finishListener;
	}

	public void setCountDownWord(String countDownWordFont,
			String countDownWordBack) {
		this.countDownWordFont = countDownWordFont;
		this.countDownWordBack = countDownWordBack;
	}

	public interface ResetListener {
		void reset();
	}

	private ResetListener rListener;

	class ClassCut implements Runnable {

		@Override
		public void run() {

			messageBtn.setOnClickListener(null);

			while (time > 0 && isRun) {// 整个倒计时执行的循环

				time--;

				mHandler.post(new Runnable() {// 通过它在UI主线程中修改显示的剩余时间

					@Override
					public void run() {

						// TODO Auto-generated method stub

						messageBtn.setText(countDownWordFont + time
								+ countDownWordBack);// 显示剩余时间

					}

				});

				try {

					Thread.sleep(1000);// 线程休眠1秒钟 这个就是倒计时的间隔时间

				} catch (InterruptedException e) {

					e.printStackTrace();

				}

			}

			// 下面是倒计时结束，处理的逻辑

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					reset();
				}

			});

		}

	}
}
