package com.kira.kiralibrary.tools;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter {
    private int settingTime;// 设置时间


    private boolean isRun;

    private int countingTime;

    private Timer timer = new Timer();

    private TimerTask task;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (countingListener != null) {
                        countingListener.onCounting(countingTime);
                    }
                    break;
                case 2:
                    reset();
                    break;
            }
            super.handleMessage(msg);
        }
    };// 全局handler

    /**
     * 发送message
     *
     * @param what 1计时，2完成计时
     */
    private void sendMsg(int what) {
        Message message = new Message();
        message.what = what;
        mHandler.sendMessage(message);
    }

    public TimeCounter(int settingTime) {
        super();
        this.settingTime = settingTime;
    }

    public void setCountDown() {
        UsualTools.showPrintMsg("setCountDown");
        if (isRun) {
            return;
        }
        isRun = true;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                countingTime++;
                sendMsg(1);
                if (countingTime >= settingTime) {
                    sendMsg(2);
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    public void stopCountDown() {
        UsualTools.showPrintMsg("stopCountDown");
        isRun = false;
        countingTime = 0;
        if (task!=null&&timer!=null){
            task.cancel();
            timer.cancel();
        }
    }

    public void reset() {
        UsualTools.showPrintMsg("reset");
        isRun = false;
        countingTime = 0;
        if (task!=null&&timer!=null){
            task.cancel();
            timer.cancel();
        }
        if (finishListener != null) {
            finishListener.onCountDownFinish();
        }
    }

    public void updateTime(int updateTime) {
        settingTime = updateTime;
    }

    public interface CountDownFinishListener {
        void onCountDownFinish();
    }

    private CountDownFinishListener finishListener;

    public void setCountDownFinishListener(
            CountDownFinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public interface OnCountingListener {
        void onCounting(int countingTime);
    }

    private OnCountingListener countingListener;

    public void setOnCountingListener(OnCountingListener countingListener) {
        this.countingListener = countingListener;
    }
}
