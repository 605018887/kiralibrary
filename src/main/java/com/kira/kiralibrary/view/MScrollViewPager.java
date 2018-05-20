package com.kira.kiralibrary.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MScrollViewPager extends ViewPager {
	private boolean isCanScroll = true;

	public MScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setScrollAble(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		try {
			if (isCanScroll) {
				return super.onTouchEvent(arg0);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		try {
			if (isCanScroll) {
				return super.onInterceptTouchEvent(arg0);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}
}
