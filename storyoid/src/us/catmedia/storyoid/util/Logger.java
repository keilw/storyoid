/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008-2009 Werner Keil
 */
package us.catmedia.storyoid.util;

import static us.catmedia.storyoid.util.Constants.APP_NAME;

import android.util.Log;

/**
 * @author Werner Keil
 *
 */
public class Logger {
	private static final String DEFAULT_TAG = APP_NAME + ".Logger";
	
	private static final String DEFAULT_MSG_ERR = "Error";
	
	private Class<?> clazz;
	
	private Logger(Class<?> c) {
		this.clazz = c;
	}
	
	public static final Logger getLogger(Class<?> c) {
		return new Logger(c);
	}
	
	public void debug(String msg) {
		if (clazz != null) {
			Log.d(clazz.getName(), msg);
		} else {
			Log.d(DEFAULT_TAG, msg);
		}
	}
	
	public void error(String msg) {
		if (clazz != null) {
			Log.e(clazz.getName(), msg);
		} else {
			Log.e(DEFAULT_TAG, msg);
		}
	}
	
	public void error(Throwable t) {
		if (clazz != null) {
			Log.e(clazz.getName(), DEFAULT_MSG_ERR, t);
		} else {
			Log.e(DEFAULT_TAG, DEFAULT_MSG_ERR, t);
		}
	}
}
