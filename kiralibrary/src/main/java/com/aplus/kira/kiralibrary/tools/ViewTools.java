package com.aplus.kira.kiralibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputFilter;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewTools {

	public static TextView setStringToTextView(Activity activity, int textId,
			CharSequence text) {
		TextView textView = (TextView) activity.findViewById(textId);
		textView.setText(text);
		return textView;

	}

	public static TextView setStringToTextView(View view, int textId,
			CharSequence text) {
		TextView textView = (TextView) view.findViewById(textId);
		textView.setText(text);
		return textView;

	}

	public static Button setStringToButton(View view, int textId,
			CharSequence text) {
		Button button = (Button) view.findViewById(textId);
		button.setText(text);
		return button;
	}

	public static Button setStringToButton(Activity activity, int textId,
			CharSequence text) {
		Button button = (Button) activity.findViewById(textId);
		button.setText(text);
		return button;
	}

	public static void setVisible(Activity activity, int id) {
		activity.findViewById(id).setVisibility(View.VISIBLE);
	}

	public static void setVisible(View view, int id) {
		view.findViewById(id).setVisibility(View.VISIBLE);
	}

	public static void setInvisible(Activity activity, int id) {
		activity.findViewById(id).setVisibility(View.INVISIBLE);
	}

	public static void setInvisible(View view, int id) {
		view.findViewById(id).setVisibility(View.INVISIBLE);
	}

	public static void setGone(Activity activity, int id) {
		activity.findViewById(id).setVisibility(View.GONE);
	}

	public static void setGone(View view, int id) {
		view.findViewById(id).setVisibility(View.GONE);
	}

	public static boolean isShow(Activity activity, int id) {
		return activity.findViewById(id).isShown();
	}

	public static boolean isShow(View view, int id) {
		return view.findViewById(id).isShown();
	}

	public static TextView setTextViewBackground(Activity activity, int textId,
			int resid) {
		TextView textView = (TextView) activity.findViewById(textId);
		textView.setBackgroundResource(resid);
		return textView;

	}

	public static TextView setTextViewBackground(View view, int textId,
			int resid) {
		TextView textView = (TextView) view.findViewById(textId);
		textView.setBackgroundResource(resid);
		return textView;

	}

	public static EditText setStringToEditText(Activity activity, int editId,
			CharSequence text) {
		EditText editText = (EditText) activity.findViewById(editId);
		editText.setText(text);
		return editText;

	}

	public static EditText setStringToEditText(View view, int editId,
			CharSequence text) {
		EditText editText = (EditText) view.findViewById(editId);
		editText.setText(text);
		return editText;

	}

	public static EditText setEditTextEnable(View view, int editId,
			boolean enabled) {
		EditText editText = (EditText) view.findViewById(editId);
		editText.setEnabled(enabled);
		return editText;

	}

	public static String getStringFromTextView(Activity activity, int textId) {
		TextView textView = (TextView) activity.findViewById(textId);
		String content = textView.getText().toString().trim();
		return content;

	}

	public static String getStringFromTextView(View view, int textId) {
		TextView textView = (TextView) view.findViewById(textId);
		String content = textView.getText().toString().trim();
		return content;

	}

	public static String getStringFromButton(Activity activity, int textId) {
		Button textView = (Button) activity.findViewById(textId);
		String content = textView.getText().toString().trim();
		return content;

	}

	public static String getStringFromEdittext(Activity activity, int editId) {
		EditText editText = (EditText) activity.findViewById(editId);
		String content = editText.getText().toString().trim();
		return content;

	}

	public static String getStringFromEdittext(View view, int editId) {
		EditText editText = (EditText) view.findViewById(editId);
		String content = editText.getText().toString().trim();
		return content;

	}

	public static RelativeLayout setRelativeLayoutListener(Activity activity,
			int textId, OnClickListener listener) {
		RelativeLayout relativeLayout = (RelativeLayout) activity
				.findViewById(textId);
		relativeLayout.setOnClickListener(listener);
		return relativeLayout;
	}

	public static RelativeLayout setRelativeLayoutListener(View parentView,
			int textId, OnClickListener listener) {
		RelativeLayout relativeLayout = (RelativeLayout) parentView
				.findViewById(textId);
		relativeLayout.setOnClickListener(listener);
		return relativeLayout;
	}

	public static TextView setTextViewListener(Activity activity, int textId,
			OnClickListener listener) {
		TextView textView = (TextView) activity.findViewById(textId);
		textView.setOnClickListener(listener);
		return textView;
	}

	public static ImageView setImageViewListener(Activity activity,
			int imageId, OnClickListener listener) {
		ImageView imageView = (ImageView) activity.findViewById(imageId);
		imageView.setOnClickListener(listener);
		return imageView;
	}

	public static ImageView setImageViewListener(View view, int imageId,
			OnClickListener listener) {
		ImageView imageView = (ImageView) view.findViewById(imageId);
		imageView.setOnClickListener(listener);
		return imageView;
	}

	public static View setViewClickListener(Activity activity, int viewId,
			OnClickListener listener) {
		View view = activity.findViewById(viewId);
		view.setOnClickListener(listener);
		return view;
	}

	public static View setViewClickListener(View view, int viewId,
			OnClickListener listener) {
		View myView = view.findViewById(viewId);
		myView.setOnClickListener(listener);
		return myView;
	}

	public static ImageView setImageViewBackround(Activity activity,
			int imageId, int resid) {
		ImageView imageView = (ImageView) activity.findViewById(imageId);
		imageView.setImageBitmap(PictureUtil.readBitMap(activity, resid));
		return imageView;
	}

	public static ImageView setImageViewBackround(Context context, View view,
			int imageId, int resid) {
		ImageView imageView = (ImageView) view.findViewById(imageId);
		imageView.setImageBitmap(PictureUtil.readBitMap(context, resid));
		return imageView;
	}

	public static TextView setTextViewListener(View parentView, int textId,
			OnClickListener listener) {
		TextView textView = (TextView) parentView.findViewById(textId);
		textView.setOnClickListener(listener);
		return textView;
	}

	public static TextView setTextViewTextColor(Activity activity, int textId,
			String color) {
		TextView textView = (TextView) activity.findViewById(textId);
		textView.setTextColor(Color.parseColor(color));
		return textView;
	}

	public static TextView setTextViewTextColor(Activity activity, int textId,
			int color) {
		TextView textView = (TextView) activity.findViewById(textId);
		textView.setTextColor(color);
		return textView;
	}

	public static TextView setTextViewTextColor(View view, int textId,
			String color) {
		TextView textView = (TextView) view.findViewById(textId);
		textView.setTextColor(Color.parseColor(color));
		return textView;
	}

	public static TextView setTextViewTextColor(View view, int textId, int color) {
		TextView textView = (TextView) view.findViewById(textId);
		textView.setTextColor(color);
		return textView;
	}

	public static Button setButtonListener(Activity activity, int btnId,
			OnClickListener listener) {
		Button button = (Button) activity.findViewById(btnId);
		button.setOnClickListener(listener);
		return button;
	}

	public static Button setButtonListener(View view, int btnId,
			OnClickListener listener) {
		Button button = (Button) view.findViewById(btnId);
		button.setOnClickListener(listener);
		return button;
	}

	public static void addListViewBottomView(Context context, int id,
			ListView listView) {
		View view = LayoutInflater.from(context).inflate(id, null);
		listView.addFooterView(view);
	}

	/**
	 * 设置下划线
	 * 
	 * @param textView
	 * @return
	 */
	public static TextView setBelowLine(TextView textView) {
		textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		return textView;
	}

	/**
	 * 加粗
	 * 
	 * @param textview
	 */
	public static void setTextCuTi(TextView textview) {
		TextPaint tp = textview.getPaint();
		tp.setFakeBoldText(true);// 加粗
	}

	/**
	 * 设置webview
	 * 
	 * @param webView
	 * @param url
	 */
	public static void setWebView(WebView webView, String url) {
		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
//		settings.setTextSize(WebSettings.TextSize.LARGEST);
		settings.setJavaScriptEnabled(true);// 设置可以浏览脚1094.65本为javaScript的网页
		webView.loadUrl(url);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条样式
		webView.setFocusable(true);// 获取焦点
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.requestFocus();
		webView.setFocusableInTouchMode(true);
	}

	public static void setWebView(WebView webView, String url,
			WebViewClient client) {
		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		webView.loadUrl(url);
		webView.getSettings().setJavaScriptEnabled(true);// 设置可以浏览脚本为javaScript的网页
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条样式
		webView.setFocusable(true);// 获取焦点
		webView.setWebViewClient(client);
		webView.requestFocus();
		webView.setFocusableInTouchMode(true);
	}

	public static void setVideoWebView(WebView webView, String url,
								  WebViewClient client) {
		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		settings.setJavaScriptEnabled(true);// 设置可以浏览脚本为javaScript的网页
		settings.setSavePassword(true);
		settings.setSaveFormData(true);// 保存表单数据
		settings.setDomStorageEnabled(true);
		settings.setSupportMultipleWindows(true);// 新加


		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条样式
		webView.setFocusable(true);// 获取焦点
		webView.setFocusableInTouchMode(true);
		webView.requestFocus();
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(client);
		webView.loadUrl(url);
	}

	public static void setBackgroundResource(Activity activity, int id,
			int drawId) {
		activity.findViewById(id).setBackgroundResource(drawId);
	}

	public static void setBackgroundResource(View view, int id, int drawId) {
		view.findViewById(id).setBackgroundResource(drawId);
	}

	public static void setBackgroundColor(Activity activity, int id, int colorId) {
		activity.findViewById(id).setBackgroundColor(colorId);
	}

	public static void setBackgroundColor(View view, int id, int colorId) {
		view.findViewById(id).setBackgroundColor(colorId);
	}

	public static void setEditTextFocusChangeListener(Activity activity,
			int id, OnFocusChangeListener l) {
		EditText editText = (EditText) activity.findViewById(id);
		editText.setOnFocusChangeListener(l);
	}

	public static void setImageViewBitmap(Activity activity, int id,
			Bitmap bitmap) {
		ImageView imageView = (ImageView) activity.findViewById(id);
		imageView.setImageBitmap(bitmap);
	}

	public static void setImageViewBitmap(View view, int id, Bitmap bitmap) {
		ImageView imageView = (ImageView) view.findViewById(id);
		imageView.setImageBitmap(bitmap);
	}

	public static void setEditTextInputLength(EditText editText, int length) {
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				length) });
	}
}
