package com.kira.kiralibrary.multiPhoto.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.kira.kiralibrary.multiPhoto.adapter.FolderAdapter;
import com.kira.kiralibrary.multiPhoto.utils.ImageBucket;
import com.kira.kiralibrary.multiPhoto.utils.ImageItem;

import java.util.ArrayList;

public abstract class ImageFileBase extends Activity {
	private FolderAdapter folderAdapter;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gridView = onSetGridview();
		folderAdapter = new FolderAdapter(this, AlbumBase.contentList);
		gridView.setAdapter(folderAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageBucket bucket = AlbumBase.contentList.get(position);
				ShowAllPhotoBase.dataList = (ArrayList<ImageItem>) bucket
						.getImageList();
				String folderName = bucket.getBucketName();
				onJumpActivity(folderName);
			}
		});
	}

	public abstract GridView onSetGridview();

	public abstract void onJumpActivity(String folderName);

}