/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008 Werner Keil

Portions StorYBook: Summary-based software for novelist and script writers.
Copyright (C) 2008 Martin Mustun

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package us.catmedia.storyoid.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
//import java.util.ResourceBundle;

import android.content.res.Resources;

/*
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
*/

public class I18N {

	private static ResourceBundle iconResourceBundle = null;
	private static ResourceBundle messageResourceBundle = null;

	public static String getCountryLanguage(Locale locale){
		return locale.getLanguage() + "_" + locale.getCountry();
	}
	
	public static DateFormat getDateFormatterWithTime() {
		DateFormat formatter;
		if (isGerman()) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else {
			formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		}
		return formatter;
	}

	public static DateFormat getDateFormatter() {
		DateFormat formatter;
		if (isGerman()) {
			formatter = new SimpleDateFormat("yyyy/MM/dd");
		} else {
			formatter = new SimpleDateFormat("dd.MM.yyyy");
		}
		return formatter;
	}
	
	public static boolean isEnglish() {
		Locale locale = Locale.getDefault();
		if (locale == Locale.ENGLISH) {
			return true;
		}
		return false;
	}

	public static boolean isGerman() {
		Locale locale = Locale.getDefault();
		if (locale == Locale.GERMAN) {
			return true;
		}
		return false;
	}

	public static final String getMsg(Resources resources, int resourceKey, Object arg) {
		Object[] args = new Object[]{arg};
		return getMsg(resources, resourceKey, args);
	}
	
	public static final String getMsg(Resources resources, int resourceKey, Object[] args) {
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(Locale.getDefault());
		//String pattern = resourceKey ;//getMessageResourceBundle().getString(resourceKey);
		String pattern = resources.getString(resourceKey);
		formatter.applyPattern(pattern);
		return formatter.format(args);
	}

/*	
	public static final void setMnemonic(JMenuItem menuItem, int englishKey){
		setMnemonic(menuItem, englishKey, englishKey);
	}
	
	public static final void setMnemonic(JMenuItem menuItem, int englishKey, int germanKey){
		if(Locale.getDefault() == Locale.GERMANY){
			menuItem.setMnemonic(germanKey);
		} else {
			menuItem.setMnemonic(englishKey);	
		}
	}
	
	public static final void setMnemonic(JMenu menu, int englishKey){
		setMnemonic(menu, englishKey, englishKey);
	}
	
	public static final void setMnemonic(JMenu menu, int englishKey, int germanKey){
		if(Locale.getDefault() == Locale.GERMANY){
			menu.setMnemonic(germanKey);
		} else {
			menu.setMnemonic(englishKey);	
		}
	}
	
	public static final void initResourceBundles(){
		// read language from preferences and set it
		String countryLanguage = PrefManager.getInstance().getStringValue(
				Constants.PREF_LANG);
		Locale locale;
		if (Constants.PREF_LANG_EN_US.equals(countryLanguage)) {
			locale = Locale.US;
		} else if (Constants.PREF_LANG_DE_DE.equals(countryLanguage)) {
			locale = Locale.GERMANY;
		} else if (Constants.PREF_LANG_ES_ES.equals(countryLanguage)) {
			locale = new Locale("es","ES");
		} else if (Constants.PREF_LANG_DA_DK.equals(countryLanguage)) {
			locale = new Locale("da","DK");
		} else if (Constants.PREF_LANG_PT_BR.equals(countryLanguage)) {
			locale = new Locale("pt","BR");
		} else if (Constants.PREF_LANG_IT_IT.equals(countryLanguage)) {
			locale = Locale.ITALY;
		} else if (Constants.PREF_LANG_FR_FR.equals(countryLanguage)) {
			locale = Locale.FRANCE;
		} else {
			locale = Locale.US;
		}
		initResourceBundles(locale);
	}
	
	public static final void initResourceBundles(Locale locale){
		messageResourceBundle = null;
		Locale.setDefault(locale);
		UIManager.getDefaults().setDefaultLocale(locale);
	}
*/
	public static final ResourceBundle getMessageResourceBundle() {
		if (messageResourceBundle == null) {
			messageResourceBundle = ResourceBundle.getBundle(
					"ch.intertec.storybook.resources.messages",
					Locale.getDefault());
		}
		return messageResourceBundle;
	}

	public static String getMsg(String resourceKey) {
		ResourceBundle rb = getMessageResourceBundle();
		return rb.getString(resourceKey);
	}

	public static String getMsgColon(String resourceKey) {
		return getMsgColon(resourceKey, false);
	}

	public static String getMsgDot(String resourceKey) {
		return getMsg(resourceKey) + "...";
	}

	public static String getMsgColon(String resourceKey, boolean required) {
		ResourceBundle rb = getMessageResourceBundle();
		StringBuffer buf = new StringBuffer();
		if (required) {
			buf.append('*');
		}
		buf.append(rb.getString(resourceKey));
		buf.append(':');
		return buf.toString();
	}
	
	public static final ResourceBundle getIconResourceBundle() {
		if (iconResourceBundle == null) {
			iconResourceBundle
				= ResourceBundle.getBundle("ch.intertec.storybook.resources.icons");
		}
		return iconResourceBundle;
	}
/*	
	public static Icon getIcon(String resourceKey) {
		ResourceBundle rb = getIconResourceBundle();
		String name = rb.getString(resourceKey);
		Icon icon = createImageIcon(
				SwingTools.getMainFrameAsJFrame(), name);
		return icon;
	}
	
	public static ImageIcon createImageIcon(Object obj, String path) {
		java.net.URL imgURL = obj.getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
*/	
}
