package com.kira.kiralibrary.multiPhoto.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.kira.kiralibrary.multiPhoto.adapter.AlbumGridViewAdapter;
import com.kira.kiralibrary.multiPhoto.mInterface.OnSelectListener;
import com.kira.kiralibrary.multiPhoto.utils.AlbumHelper;
import com.kira.kiralibrary.multiPhoto.utils.ImageBucket;
import com.kira.kiralibrary.multiPhoto.utils.ImageItem;

import java.util.ArrayList;

public abstract class AlbumBase extends Activity {
	private GridView gridView;
	private AlbumGridViewAdapter gridImageAdapter;
	private ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();
	private AlbumHelper helper;
	public static ArrayList<ImageBucket> contentList;
	private ArrayList<ImageItem> selectList;
	private int maxSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		maxSize = onSetMaxSize();
		gridView = onSetGridview();
		selectList = onSetSelectList();
		initData();
	}

	public abstract int onSetMaxSize();

	public abstract GridView onSetGridview();

	public abstract ArrayList<ImageItem> onSetSelectList();

	public void initData() {

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		contentList = helper.getImagesBucketList(selectList);
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).getImageList());
		}

		gridImageAdapter = new AlbumGridViewAdapter(this, dataList);
		gridView.setAdapter(gridImageAdapter);
		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(int position, boolean isChecked) {
						ImageItem imageItem = dataList.get(position);

						if (isChecked) {
							imageItem.setSelected(false);
							for (int i = 0, size = selectList.size(); i < size; i++) {
								if (imageItem.getImagePath().equals(
										selectList.get(i).getImagePath())) {
									selectList.remove(i);
									break;
								}
							}

						} else {
							if (selectList.size() >= maxSize) {
								Toast.makeText(getApplicationContext(),
										"超出可选图片张数", Toast.LENGTH_SHORT).show();
								return;
							}
							imageItem.setSelected(true);
							selectList.add(imageItem);
						}

						if (selectListener != null) {
							selectListener.onSelect(selectList.size());
						}

					}
				});
	}

	private OnSelectListener selectListener;

	public void setOnSelectListener(OnSelectListener selectListener) {
		this.selectListener = selectListener;
	}

	public void dataChange(ArrayList<ImageItem> selectList) {
		this.selectList = selectList;
		for (int i = 0, size = dataList.size(); i < size; i++) {
			dataList.get(i).setSelected(false);
		}
		for (int i = 0, size = selectList.size(); i < size; i++) {
			ImageItem item = selectList.get(i);
			for (int j = 0, size2 = dataList.size(); j < size2; j++) {
				ImageItem item2 = dataList.get(j);
				if (item.getImagePath().equals(item2.getImagePath())) {
					item2.setSelected(true);
				}
			}
		}
		gridImageAdapter.dataChange(dataList);
	}
}
