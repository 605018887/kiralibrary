package com.kira.kiralibrary.multiPhoto.utils;

import java.util.Comparator;

/**
 * ͼƬ�Ƚ�������������ǰ�ķ���ǰ��
 * @author Administrator
 *
 */
public class ImageComparator implements Comparator<ImageItem>{

	@Override
	public int compare(ImageItem lhs, ImageItem rhs) {
		if (lhs.getDate() < rhs.getDate()) {
			return 1;
		} else if (lhs.getDate() > rhs.getDate()) {
			return -1;
		}
		return 0;
	}

}
