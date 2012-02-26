/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008 Werner Keil
 */
package us.catmedia.storyoid.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;

public class Updater {

	public static boolean checkForUpdate() {
		try {
			// get version
//			URL url = new URL("http://localhost/storybook/version.txt");
			URL url = new URL(Constants.APP_URL + "/version.txt");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String inputLine = "";
			String version = "";
			int c = 0;
			while ((inputLine = in.readLine()) != null) {
				version = inputLine;
				if (c == 0) {
					// currently only the first line is read
					break;
				}
			}
			in.close();
			
			// compare version
			if (!Constants.APP_VERSION.equalsIgnoreCase((version))) {
//				String updateUrl = "http://localhost/storybook/update?g_lang="
//					+ Locale.getDefault().getLanguage();
				String updateUrl = Constants.APP_URL + "/update?g_lang="
					+ Locale.getDefault().getLanguage();
				/*BrowserDialog dlg = new BrowserDialog(
						I18N.getMsg("msg.update.title"),
						updateUrl, 500, 400); */
				//MainFrame mainFrame = SwingTools.getMainFrame();
				//SwingTools.createModalDialog(dlg, mainFrame);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
