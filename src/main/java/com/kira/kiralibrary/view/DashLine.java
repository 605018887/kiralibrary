package com.kira.kiralibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 虚线
 * 
 * @author Administrator
 * 
 */
public class DashLine extends View {

	public DashLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#a9a9a9"));

		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(480, 10);
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paint.setAntiAlias(true);
		paint.setPathEffect(effects);
		paint.setStrokeWidth(4);
		canvas.drawPath(path, paint);
	}
}
