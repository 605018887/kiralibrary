package com.kira.kiralibrary.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kira.kiralibrary.tools.UsualTools;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kirawu on 2017/9/20.
 */

public class KiraBannerView extends ViewGroup {
    private Context context;
    private int totalWidth;
    private int delay = 0;
    private int speed = 5000;
    private int space = 1;
    private ArrayList<Integer> nodes = new ArrayList<>();
    private Timer timer;
    private TimerTask task;
    /**
     * 0按下，1按起，2滑动
     */
    private int currentAction = 1;
    private boolean isScrolling;
    private boolean isBack;
    private boolean isHasLeft;
    private int currentPosition;

    public KiraBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        startScroll();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        totalWidth = 0;
        nodes = new ArrayList<>();
        for (int i = 0, size = getChildCount(); i < size; i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            childView.layout(totalWidth, 0, totalWidth + width, height);
            totalWidth = totalWidth + width;
            nodes.add(width);
        }
//        DotView dotView = new DotView(context);
//        addView(dotView);
    }

    private void startScroll() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(task, delay, speed);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (currentAction == 0 || currentAction == 2) {
                return;
            }
            if (msg.what == 1) {
                if (nodes.size() == 0 || getChildCount() < 2 || isScrolling) {
                    return;
                }
                int node = nodes.get(0);
                scrollTo(0, 0);
                smoothScrollTo(0, node);
            } else if (msg.what == 2) {
                int toX = (int) msg.obj;
                scrollTo(toX, 0);
            } else if (msg.what == 3) {
                if (!isBack) {
                    int node = nodes.get(0);
                    View view0 = getChildAt(0);
                    removeViewAt(0);
                    addView(view0);
                    nodes.remove(0);
                    nodes.add(node);
                }
                scrollTo(0, 0);

//                spm("currentPosition:"+currentPosition);
//                spm("isBack:"+isBack);

                if (isBack) {
                    currentPosition--;
                } else {
                    currentPosition++;
                }
                if (currentPosition == -1) {
                    currentPosition = getChildCount() - 1;
                } else if (currentPosition == getChildCount()) {
                    currentPosition = 0;
                }
                if (currentPositionListener != null) {
                    currentPositionListener.onCurrentPosition(currentPosition);
                }

//                spm("currentPosition:"+currentPosition);

                isScrolling = false;
                isHasLeft = false;
                isBack = false;



            }
            super.handleMessage(msg);
        }
    };

    private int scrollCount = 0;
    private int scrollTotal = 0;

    private void smoothScrollTo(final int scrollX, final int node) {
//        spm("node:"+node);
        scrollCount = 0;
        scrollTotal = 0;
        final int totalCount = 20;
        if (task != null) {
            task.cancel();
            timer.cancel();
            task = null;
            timer = null;
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;
                if (!isBack) {
                    if (scrollCount == 0) {
                        int x = node / totalCount;
                        message.obj = scrollX + x;
                        scrollTotal = scrollTotal + x;
                    } else if (scrollCount == totalCount - 1) {
                        int x = node - scrollTotal;
                        message.obj = getScrollX() + x;
                        scrollTotal = scrollTotal + x;
                        isHasLeft = true;
                    } else {
                        int x = node / totalCount;
                        message.obj = getScrollX() + x;
                        scrollTotal = scrollTotal + x;
                        isHasLeft = true;
                    }
                } else {
                    if (scrollCount == 0) {
                        int x = node / totalCount;
                        message.obj = scrollX - x;
                        scrollTotal = node;
                    } else if (scrollCount == totalCount - 1) {
                        message.obj = 0;
                        scrollTotal = -node;
                        isHasLeft = false;
                    } else {
                        int x = node / totalCount;
                        message.obj = getScrollX() - x;
                        scrollTotal = scrollTotal - x;
                        isHasLeft = false;
                    }
                }
                handler.sendMessage(message);

                scrollCount++;
                if (scrollCount == totalCount) {
                    task.cancel();
                    timer.cancel();
                    task = null;
                    timer = null;

                    Message message3 = new Message();
                    message3.what = 3;
                    handler.sendMessage(message3);
                }
            }
        };
        timer.schedule(task, 0, 50);
        isScrolling = true;
    }

    private float downX;
    private int scrollX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (nodes.size() == 0 || getChildCount() < 2) {
            return true;
        }
        requestDisallowInterceptTouchEvent(true);
        currentAction = event.getAction();
        if (actionChangeListener != null) {
            actionChangeListener.onActionChange(currentAction);
        }
        switch (currentAction) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                scrollX = getScrollX();
                if (task != null) {
                    task.cancel();
                    timer.cancel();
                    task = null;
                    timer = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float dis = downX - x;
                if (dis >= 0) {
                    isBack = false;
                    if (isHasLeft) {
                        int node = nodes.get(0);
                        View view0 = getChildAt(0);
                        removeViewAt(0);
                        addView(view0);
                        nodes.remove(0);
                        nodes.add(node);
                        isHasLeft = false;
                    }
                    scrollX = (int) (downX - x);
                    scrollTo(scrollX, 0);
                } else {
                    isBack = true;
                    if (!isHasLeft) {
                        View view = getChildAt(getChildCount() - 1);
                        removeView(view);
                        addView(view, 0);
                        int node = nodes.get(nodes.size() - 1);
                        nodes.add(0, node);
                        nodes.remove(nodes.size() - 1);
//                        scrollTo((int) (node+downX - x), 0);
                        scrollTotal = node;
                        isHasLeft = true;
                    }
                    scrollX = (int) (scrollTotal + downX - x);
                    scrollTo(scrollX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int node = nodes.get(0);
                if (!isBack) {
                    smoothScrollTo(scrollX, node - scrollX);
                } else {
                    smoothScrollTo(scrollX, scrollX);
                }
                break;
        }
        return true;
    }

    public interface OnActionChangeListener {
        /***
         *
         * @param action 0按下，1按起，2滑动
         */
        void onActionChange(int action);
    }

    private OnActionChangeListener actionChangeListener;

    public void setOnActionChangeListener(OnActionChangeListener actionChangeListener) {
        this.actionChangeListener = actionChangeListener;
    }

    public interface OnCurrentPositionListener {
        void onCurrentPosition(int position);
    }

    private OnCurrentPositionListener currentPositionListener;

    public void setOnCurrentPositionListener(OnCurrentPositionListener currentPositionListener) {
        this.currentPositionListener = currentPositionListener;
    }

    private void spm(String log) {
        UsualTools.showPrintMsg(log);
    }
}
