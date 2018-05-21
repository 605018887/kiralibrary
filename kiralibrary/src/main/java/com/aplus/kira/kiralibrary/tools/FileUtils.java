package com.aplus.kira.kiralibrary.tools;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	public static String sdDirS = Environment.getExternalStorageDirectory()
			.toString();
	public static String SDPATH = sdDirS + "/Photo_LJ/";


	public static String saveBitmap(Bitmap bm, String picName) {
		String picPath = "";
		try {
			File firstDir = new File(sdDirS + "/Photo_LJ");
			if (!firstDir.exists()) {
				firstDir.mkdirs();
			}
			File f = new File(SDPATH, picName + ".jpeg");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			picPath = SDPATH + picName + ".jpeg";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picPath;
	}

	/**
	 * �����ļ���
	 * 
	 * @param dirName
	 *            �ļ�������
	 * @return �ļ���
	 * @throws IOException
	 */
	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	/**
	 * �ļ��Ƿ����
	 * 
	 * @param fileName
	 * @return ����ֵ��trueΪ���ڣ�falseΪ������
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param fileName
	 *            �ļ�·��
	 */
	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	/**
	 * ɾ���ļ���
	 */
	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete();
			else if (file.isDirectory())
				deleteDir();
		}
		dir.delete();
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @throws Exception
	 */
	public static void copyFile(String oldPath, String newPath)
			throws Exception {
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = new FileInputStream(oldPath); // 读入原文件
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.close();
		}

	}
}
