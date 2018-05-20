package com.kira.kiralibrary.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MJsonUtil {

	public static String getString(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getString(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return "";
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return "";
		}
	}

	public static String getString(JSONObject object, String key,
			String defaultValue) {
		if (object.has(key)) {
			try {
				return object.getString(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return defaultValue;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return defaultValue;
		}
	}

	public static int getInt(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getInt(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return -1;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return -1;
		}
	}

	public static int getInt(JSONObject object, String key, int defaultValue) {
		if (object.has(key)) {
			try {
				return object.getInt(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return defaultValue;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return defaultValue;
		}
	}

	public static long getLong(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getLong(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return -1;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return -1;
		}
	}

	public static double getDouble(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getDouble(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return -1;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return -1;
		}
	}

	public static double getDouble(JSONObject object, String key,
			double defaultValue) {
		if (object.has(key)) {
			try {
				return object.getDouble(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return defaultValue;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return defaultValue;
		}
	}

	public static boolean getBoolean(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getBoolean(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return false;
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return false;
		}
	}

	public static JSONArray getJSONArray(JSONObject object, String key) {
		if (object.has(key)) {
			try {
				return object.getJSONArray(key);
			} catch (JSONException e) {
				UsualTools.showPrintMsg("errorKey=" + key);
				return new JSONArray();
			}
		} else {
			UsualTools.showPrintMsg("no exist key=" + key);
			return new JSONArray();
		}
	}
}
