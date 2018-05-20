package com.kira.kiralibrary.style;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kira.kiralibrary.tools.MResource;
import com.kira.kiralibrary.tools.UsualTools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 
 * @author eStronger Kira
 * @since Mar 20,2015
 * @自定义dialog类
 * 
 */
public class Mdialog {

	/**
	 * 提示对话框
	 * 
	 * @param activity
	 * @param message
	 *            提示信息
	 * @return AlertDialog
	 */
	public static AlertDialog showAlertDialog(Activity activity, String message) {
		Builder builder = new Builder(activity);
		builder.setMessage(message);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		return alertDialog;
	}

	/**
	 * 进程对话框
	 * 
	 * @param activity
	 * @return ProgressDialog
	 */
	public static ProgressDialog createProgressDialog(Activity activity) {
		ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("数据加载ing...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		return progressDialog;

	}

	/**
	 * 进程对话框
	 * 
	 * @param activity
	 * @param message
	 *            提示信息
	 * @return ProgressDialog
	 */
	public static ProgressDialog showProgressDialog(Activity activity,
			String message) {
		ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage(message);
		progressDialog.show();
		return progressDialog;
	}

	/**
	 * 显示一个选择日期的对话框
	 * 
	 * @param activity
	 * @param textView
	 *            选择日期后，把日期返回给该textview
	 */
	public static void showDatePickDialog(Activity activity,
			final TextView textView) {
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		// 获取一个日历对象
		final Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

		// 当点击DatePickerDialog控件的设置按钮时，调用该方法
		DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// 修改日历控件的年，月，日
				// 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
				dateAndTime.set(Calendar.YEAR, year);
				dateAndTime.set(Calendar.MONTH, monthOfYear);
				dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				textView.setText(formatter.format(dateAndTime.getTime()));
			}
		};
		// 生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
		new DatePickerDialog(activity, d, dateAndTime.get(Calendar.YEAR),
				dateAndTime.get(Calendar.MONTH),
				dateAndTime.get(Calendar.DAY_OF_MONTH)).show();

	}

	/**
	 * 显示一个选择时间的对话框
	 * 
	 * @param activity
	 * @param textView
	 *            选择时间后，把时间返回给该textview
	 */
	public static void showTimePickDialog(Activity activity,
			final TextView textView) {
		final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		// 获取一个日历对象
		final Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

		// 当点击DatePickerDialog控件的设置按钮时，调用该方法
		OnTimeSetListener t = new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
				dateAndTime.set(Calendar.MINUTE, minute);
				textView.setText(formatter.format(dateAndTime.getTime()));
			}
		};
		new TimePickerDialog(activity, t,
				dateAndTime.get(Calendar.HOUR_OF_DAY),
				dateAndTime.get(Calendar.MINUTE), true).show();

	}

	/**
	 * 显示一个布尔选项的对话框
	 * 
	 * @param activity
	 * @param message
	 *            提示信息
	 * @param sureListener
	 *            实现确定事件的监听
	 * @param cancelListener
	 *            实现取消事件的监听
	 */
	public static void showBooleanDialog(Activity activity, String message,
			OnClickListener sureListener, OnClickListener cancelListener) {
		Builder builder = new Builder(activity);
		builder.setMessage(message);

		builder.setTitle("提示");

		builder.setPositiveButton("确认", sureListener);

		builder.setNegativeButton("取消", cancelListener);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	public static AlertDialog createBooleanDialog(Activity activity,
			String message, OnClickListener sureListener,
			OnClickListener cancelListener) {
		Builder builder = new Builder(activity);
		builder.setMessage(message);

		builder.setTitle("提示");

		builder.setPositiveButton("确认", sureListener);

		builder.setNegativeButton("取消", cancelListener);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	/**
	 * 创建一个自定义view的对话框
	 * 
	 * @param activity
	 * @param view
	 * @return AlertDialog
	 */
	public static AlertDialog createMyViewDialog(Activity activity, View view) {
		Builder builder = new Builder(activity);
		builder.setView(view);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	/**
	 * 创建一个自定义view和style的对话框
	 * 
	 * @param activity
	 * @param style
	 * @param view
	 * @return Dialog
	 */
	public static Dialog createMyViewAndStyleDialog(Activity activity,
			int style, View view) {
		Dialog dialog = new Dialog(activity, style);
		dialog.setContentView(view);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;

	}

	/**
	 * 遮层对话框
	 * 
	 * @param activity
	 * @return Dialog
	 */
	public static Dialog createCoverDialog(Activity activity) {
		Dialog progressDialog = new Dialog(activity, MResource.getIdByName(
				activity, "style", "dialog"));
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		return progressDialog;

	}

	/**
	 * 更新对话框（不可取消）
	 * 
	 * @param context
	 * @param message
	 *            提示信息
	 * @param sureListener
	 *            实现更新的操作监听
	 */
	public static void showUpdateDialog(Context context, String message,
			OnClickListener sureListener) {
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", sureListener);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 先是一个拨打电话的对话框
	 * 
	 * @param context
	 * @param tel
	 *            电话号码
	 */
	public static void showPhoneDialog(final Context context, final String tel) {
		Mdialog.showBooleanDialog((Activity) context, "你确定要拨打" + tel + "吗？",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						UsualTools.telNumber(context, tel);

					}
				}, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
	}

}
