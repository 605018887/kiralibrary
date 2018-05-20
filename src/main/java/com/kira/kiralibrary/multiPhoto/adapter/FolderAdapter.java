package com.kira.kiralibrary.multiPhoto.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kira.kiralibrary.multiPhoto.activity.ShowAllPhotoBase;
import com.kira.kiralibrary.multiPhoto.utils.BitmapCache;
import com.kira.kiralibrary.multiPhoto.utils.ImageBucket;
import com.kira.kiralibrary.multiPhoto.utils.ImageItem;
import com.kira.kiralibrary.tools.MResource;

import java.util.ArrayList;

public class FolderAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<ImageBucket> list;

	BitmapCache cache;
	private LayoutInflater inflater;

	public FolderAdapter(Activity context, ArrayList<ImageBucket> list) {
		super();
		this.context = context;
		this.list = list;
		cache = new BitmapCache();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
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

	private class ViewHolder {
		// ����
		public ImageView imageView;
		public ImageView choose_back;
		// �ļ�������
		public TextView folderName;
		// �ļ��������ͼƬ����
		public TextView fileNum;
	}

	ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(
					MResource.getIdByName(context, "layout", "adapter_folder"),
					null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(MResource
					.getIdByName(context, "id", "file_image"));
			holder.choose_back = (ImageView) convertView.findViewById(MResource
					.getIdByName(context, "id", "choose_back"));
			holder.folderName = (TextView) convertView.findViewById(MResource
					.getIdByName(context, "id", "name"));
			holder.fileNum = (TextView) convertView.findViewById(MResource
					.getIdByName(context, "id", "filenum"));
			holder.imageView.setAdjustViewBounds(true);
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		String path;
		if (list.get(position).getImageList() != null) {

			path = list.get(position).getImageList().get(0).getImagePath();

			holder.folderName.setText(list.get(position).getBucketName());

			holder.fileNum.setText("" + list.get(position).getCount());

		} else
			path = "android_hybrid_camera_default";
		if (path.contains("android_hybrid_camera_default")) {

		}

		else {

			final ImageItem item = list.get(position).getImageList().get(0);
			holder.imageView.setTag(item.getImagePath());
			cache.displayBmp(holder.imageView, item.getThumbnailPath(),
					item.getImagePath(), callback);
		}

		// holder.imageView.setOnClickListener(new ImageViewClickListener(
		// position, holder.choose_back));

		return convertView;
	}

	private class ImageViewClickListener implements OnClickListener {
		private int position;
		// private Intent intent;
		private ImageView choose_back;

		public ImageViewClickListener(int position, ImageView choose_back) {
			this.position = position;
			// this.intent = intent;
			this.choose_back = choose_back;
		}

		public void onClick(View v) {
			ShowAllPhotoBase.dataList = (ArrayList<ImageItem>) list.get(
					position).getImageList();
			int allId = context.getIntent().getIntExtra("allId", 0);
			int galleryId = context.getIntent().getIntExtra("galleryId", 0);

			Intent intent = new Intent();
			String folderName = list.get(position).getBucketName();
			intent.putExtra("folderName", folderName);
			intent.putExtra("allId", allId);
			intent.putExtra("galleryId", galleryId);

			intent.setClass(context, ShowAllPhotoBase.class);
			context.startActivityForResult(intent, 0);
			choose_back.setVisibility(v.VISIBLE);
		}
	}

	// public int dipToPx(int dip) {
	// return (int) (dip * dm.density + 0.5f);
	// }

}