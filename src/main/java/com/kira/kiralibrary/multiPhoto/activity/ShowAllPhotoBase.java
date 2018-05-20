package com.kira.kiralibrary.multiPhoto.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.kira.kiralibrary.multiPhoto.adapter.AlbumGridViewAdapter;
import com.kira.kiralibrary.multiPhoto.mInterface.OnSelectListener;
import com.kira.kiralibrary.multiPhoto.utils.ImageItem;

import java.util.ArrayList;

public abstract class ShowAllPhotoBase extends Activity {
	private GridView gridView;
	private AlbumGridViewAdapter gridImageAdapter;
	public static ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();
	private ArrayList<ImageItem> selectList;
	private int maxSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		maxSize = onSetMaxSize();
		selectList = onSetSelectList();
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList);
		gridView = onSetGridview();
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

	public abstract int onSetMaxSize();

	public abstract ArrayList<ImageItem> onSetSelectList();

	public abstract GridView onSetGridview();

}
