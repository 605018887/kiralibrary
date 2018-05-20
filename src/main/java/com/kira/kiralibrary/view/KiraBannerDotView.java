package com.kira.kiralibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.kira.kiralibrary.tools.UsualTools;

/**
 * Created by kirawu on 2018/1/17.
 */

public class KiraBannerDotView extends View {
    private int layout_width;
    private int layout_height;
    private int r;
    private int space = 20;
    private int notSelectColor;
    private int selectColor;
    private int num = 5;
    private int currentPosition;

    public KiraBannerDotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[]{android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height // 3
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        layout_width = ta.getLayoutDimension(2,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layout_height = ta.getLayoutDimension(3,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();
        r = layout_height / 3;
        notSelectColor = Color.parseColor("#ffffff");
        selectColor = Color.parseColor("#6e9d5a");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layout_width = getWidth();
    }

    public void setNum(int num) {
        this.num = num;
        invalidate();
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int dotLength = r * 2 * num + space * (num - 1);
        int startX = (layout_width - dotLength) / 2;

        Paint paint = new Paint();


        for (int i = 0; i < num; i++) {
            if (i == currentPosition) {
                paint.setColor(selectColor);
            } else {
                paint.setColor(notSelectColor);
            }
            canvas.drawCircle(startX + r + i * (r * 2 + space), r, r, paint);
        }
        super.onDraw(canvas);
    }

    private void spm(String log) {
        UsualTools.showPrintMsg(log);
    }
}
