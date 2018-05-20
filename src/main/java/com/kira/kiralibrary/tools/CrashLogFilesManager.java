package com.kira.kiralibrary.tools;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;

public class CrashLogFilesManager {
	private ArrayList<String> pathsList = new ArrayList<String>();

	public ArrayList<String> getCrashFilesPath() {
		pathsList = new ArrayList<String>();
		String dirPath = Environment.getExternalStorageDirectory().toString()
				+ "/CrashInfos";
		File dir = new File(dirPath);
		if (dir.exists() == false) {
			return pathsList;
		}
		File[] files = dir.listFiles();
		for (int i = 0, size = files.length; i < size; i++) {
			pathsList.add(files[i].getPath());
		}
		return pathsList;
	}

	public void removeFile(String path) {
		File file = new File(path);
		if (file.exists() == false) {
			return;
		}
		file.delete();
	}
}
