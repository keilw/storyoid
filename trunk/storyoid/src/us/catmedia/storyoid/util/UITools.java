package us.catmedia.storyoid.util;

public class UITools {
	public static String shortenString(String string, int newLength) {
		if (string == null) return null;
		return string.substring(0, newLength);
	}
}
