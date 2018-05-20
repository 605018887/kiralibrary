package com.kira.kiralibrary.multiPhoto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.kira.kiralibrary.multiPhoto.utils.ImageItem;
import com.kira.kiralibrary.multiPhoto.zoom.PhotoView;
import com.kira.kiralibrary.multiPhoto.zoom.ViewPagerFixed;
import com.kira.kiralibrary.tools.MResource;

import java.util.ArrayList;


public abstract class GalleryBase extends Activity {

	private int location = 0;

	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	private ArrayList<ImageItem> selectList;

	private ArrayList<ImageItem> delNetList = new ArrayList<ImageItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		selectList = onSetSelectList();
		location = onSetLocation();
		pager = onSetViewPagerFixed();
		initData();
	}

	public abstract ArrayList<ImageItem> onSetSelectList();

	public abstract int onSetLocation();

	public abstract ViewPagerFixed onSetViewPagerFixed();

	public void initData() {
		for (int i = 0, size = selectList.size(); i < size; i++) {
			if (selectList.get(i).getBitmap() == null
					&& selectList.get(i).getImageUrl() == null) {
				selectList.remove(i);
				break;
			}
		}
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < selectList.size(); i++) {
			initListViews(selectList.get(i));
		}

		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin(getResources().getDimensionPixelOffset(
				(MResource.getIdByName(this, "dimen", "ui_10_dip"))));
		// pager.setPageMargin((int) getResources().getDimensionPixelOffset(
		// R.dimen.ui_10_dip));
		pager.setCurrentItem(location);
	}

	private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private void initListViews(ImageItem item) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		if (item.getBitmap() != null) {
			img.setImageBitmap(item.getBitmap());
		} else if (item.getImageUrl() != null) {
			displayNetImg(img, item.getImageUrl());
		}
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}

	public abstract void displayNetImg(PhotoView img, String url);

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;

		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	public void removeImage() {
		if (selectList.size() == 1) {
			addDelNetList(0);
			selectList.clear();
			selectList.add(new ImageItem());
			onRemoveAllImage();
		} else {
			if (selectList.size() == 0) {
				return;
			}
			addDelNetList(location);
			selectList.remove(location);
			pager.removeAllViews();
			listViews.remove(location);
			adapter.setListViews(listViews);
			adapter.notifyDataSetChanged();
		}
	}

	private void addDelNetList(int location) {

		ImageItem imageItem = selectList.get(location);
		if (imageItem.getImageId() != null && imageItem.getImageUrl() != null
				&& imageItem.getSource() == 0) {
			delNetList.add(imageItem);
		}
	}

	public ArrayList<ImageItem> getDelNetList() {
		return delNetList;
	}

	public abstract void onRemoveAllImage();

}
