package com.kira.kiralibrary.multiPhoto.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageItem implements Serializable {
	private String imageId;
	private String thumbnailPath;
	private String imagePath;
	private String imageUrl;
	private long date;
	private Bitmap bitmap;
	private boolean isSelected = false;
	private int degree;
	/**
	 * 上传来源，0本地，1网络
	 */
	private int source;

	private int imageType;
	private String strExa;
	/**
	 * 1添加图片
	 */
	private int clickType;

	/**
	 *
	 * @return 1添加图片
	 */
	public int getClickType() {
		return clickType;
	}

	/**
	 *
	 * @param clickType 1添加图片
	 */
	public void setClickType(int clickType) {
		this.clickType = clickType;
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public String getStrExa() {
		return strExa;
	}

	public void setStrExa(String strExa) {
		this.strExa = strExa;
	}

	/**
	 * 上传来源
	 * 
	 * @return 0本地，1网络
	 */
	public int getSource() {
		return source;
	}

	/**
	 * 设置上传来源
	 * 
	 * @param source
	 *            0本地，1网络
	 */
	public void setSource(int source) {
		this.source = source;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Bitmap getBitmap() {
		if (bitmap == null) {
			try {
				bitmap = revitionImageSize(imagePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	private Bitmap revitionImageSize(String path) {
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(new File(path)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;
			Bitmap bitmap = null;
			while (true) {
				if ((options.outWidth >> i <= 1000)
						&& (options.outHeight >> i <= 1000)) {
					in = new BufferedInputStream(new FileInputStream(new File(
							path)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
			return bitmap;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
	}

}
