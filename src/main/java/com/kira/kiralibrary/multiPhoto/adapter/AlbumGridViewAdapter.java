package com.kira.kiralibrary.multiPhoto.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ToggleButton;

import com.kira.kiralibrary.multiPhoto.utils.BitmapCache;
import com.kira.kiralibrary.multiPhoto.utils.ImageComparator;
import com.kira.kiralibrary.multiPhoto.utils.ImageItem;
import com.kira.kiralibrary.tools.MResource;
import com.kira.kiralibrary.tools.PictureUtil;

import java.util.ArrayList;
import java.util.Collections;

public class AlbumGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private BitmapCache cache;
	private Bitmap selectBitmap, notSelectBitmap;
	private LayoutParams params;

	public AlbumGridViewAdapter(Context c, ArrayList<ImageItem> dataList) {
		mContext = c;
		cache = new BitmapCache();
		this.dataList = dataList;
		ImageComparator comparator = new ImageComparator();
		Collections.sort(dataList, comparator);
		selectBitmap = PictureUtil.readBitMap(c,
				MResource.getIdByName(c, "drawable", "choose"));
		notSelectBitmap = PictureUtil.readBitMap(c,
				MResource.getIdByName(c, "drawable", "circle"));

		WindowManager manager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth() / 3;
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.height = width;
		params.width = width;
	}

	public void dataChange(ArrayList<ImageItem> selectedDataList) {
		ImageComparator comparator = new ImageComparator();
		Collections.sort(dataList, comparator);
		notifyDataSetChanged();
	}

	public int getCount() {
		return dataList.size();
	}

	public Object getItem(int position) {
		return dataList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				}
			}
		}
	};

	/**
	 * ����б���ؼ����
	 */
	private class ViewHolder {
		public ImageView imageView, choosetoggle;
		public ToggleButton toggleButton;
		public RelativeLayout imageLayout;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					MResource.getIdByName(mContext, "layout",
							"adapter_album_gridview"), parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(MResource.getIdByName(mContext, "id",
							"image_view"));
			viewHolder.toggleButton = (ToggleButton) convertView
					.findViewById(MResource.getIdByName(mContext, "id",
							"toggle_button"));
			viewHolder.choosetoggle = (ImageView) convertView
					.findViewById(MResource.getIdByName(mContext, "id",
							"choosedbt"));
			viewHolder.imageLayout = (RelativeLayout) convertView
					.findViewById(MResource.getIdByName(mContext, "id",
							"image_layout"));
			viewHolder.imageLayout.setLayoutParams(params);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String path;
		final ImageItem imageItem = dataList.get(position);
		if (dataList != null && dataList.size() > position)
			path = imageItem.getImagePath();
		else
			path = "camera_default";
		if (path.contains("camera_default")) {
			viewHolder.imageView.setImageResource(MResource.getIdByName(
					mContext, "drawable", "plugin_camera_no_pictures"));
		} else {
			viewHolder.imageView.setTag(imageItem.getImagePath());
			cache.displayBmp(viewHolder.imageView,
					imageItem.getThumbnailPath(), imageItem.getImagePath(),
					callback);
		}
		viewHolder.toggleButton.setTag(position);
		viewHolder.choosetoggle.setTag(position);
		viewHolder.toggleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (dataList != null && mOnItemClickListener != null
						&& position < dataList.size()) {
					mOnItemClickListener.onItemClick(position,
							imageItem.isSelected());
					notifyDataSetChanged();
				}

			}
		});

		if (imageItem.isSelected() == true) {
			viewHolder.toggleButton.setChecked(true);
			viewHolder.choosetoggle.setImageBitmap(selectBitmap);
		} else {
			viewHolder.toggleButton.setChecked(false);
			viewHolder.choosetoggle.setImageBitmap(notSelectBitmap);
		}
		return convertView;
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public interface OnItemClickListener {
		public void onItemClick(int position, boolean isChecked);
	}

}
