package com.kira.kiralibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.kira.kiralibrary.tools.SystemTools;

public class OpenCameraActivity extends FragmentActivity {
	public static final int TAKE_PICTURE_CODE = 1001;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		if (SystemTools.TAKE_PICTURE == true) {
			SystemTools.TAKE_PICTURE = false;
			SystemTools.takePicture(this, System.currentTimeMillis() + "");
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg1 == RESULT_OK) {
			switch (arg0) {
			case SystemTools.TAKE_PICTURE_CODE:
				setResult(RESULT_OK);
				finish();
				break;

			default:
				break;
			}
		} else {
			finish();
		}
		super.onActivityResult(arg0, arg1, arg2);
	}
}
