/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008-2009 Werner Keil

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

public class Constants {
	
	/**
	 * The application name.
	 */
	// FIXME move most resources into Android RES
	public static final String COPYRIGHT_YEAR = "2009";
	public static final String COPYRIGHT_AUTHOR = "Werner Keil, Martin Mustun";
	
	public static final String APP_NAME = "StorYoid";
	static final String APP_NAME_LOCAL = "StorYBook";
	public static final String APP_NAME_SHORT = "SY";
	public static final String APP_SLOGAN = APP_NAME_LOCAL + " - enjoy writing again";
	public static final String APP_RELEASE_DATE = "2009-05-25";
	public static final String APP_VERSION = "0.1.5";
	public static final String APP_URL = "http://storyoid.catmedia.us";
	public static final String APP_SLOGAN_AND_URL = APP_SLOGAN + " - " + APP_URL;
	public static final String DB_MODEL_VERSION = "0.9.5";
	
	// project sub-directories
	public static final String PROJECT_DIR = "projects";
	public static final String BACKUP_DIR = "backups";
	public static final String USER_DICTS_DIR = "dicts";
	
	// program sub-directories
	public static final String DICTS_DIR = "dict";
		
	// panel names
	public static final String PANEL_NAME_HEADER_PANEL = "headerPanel";	
	public static final String PANEL_NAME_MAIN_PANEL = "mainPanel";
	public static final String PANEL_NAME_FOOTER_PANEL = "footerPanel";
	public static final String PANEL_NAME_DATE_STRAND_PANEL = "dateStrandPanel";
	public static final String PANEL_NAME_SPACE_PANEL = "spacePanel";

	// project settings
	
	// view
	public static final String PROJECT_VIEW = "view";
	public static final String PROJECT_VIEW_BOOK = "bookview";
	public static final String PROJECT_VIEW_CHRONO = "chronoview";
	public static final String PROJECT_VIEW_MANAGE = "manageview";
	public static final String PROJECT_VIEW_SIZE = "viewsize";
	
	// part
	public static final String PROJECT_PART_ID = "partid";	

	
	// preference keys
	
	// language
	public static final String PREF_LANG = "language";
	public static final String PREF_LANG_EN_US = "en_US";
	public static final String PREF_LANG_DE_DE = "de_DE";
	public static final String PREF_LANG_ES_ES = "es_ES";
	public static final String PREF_LANG_DA_DK = "da_DK";
	public static final String PREF_LANG_PT_BR = "pt_BR";
	public static final String PREF_LANG_IT_IT = "it_IT";
	public static final String PREF_LANG_FR_FR = "fr_FR";
	
	// spelling
	public static final String PREF_SPELLING = "spelling";
	public static final String PREF_SPELLING_NO = "no";
	public static final String PREF_SPELLING_EN_US = "en_US";
	public static final String PREF_SPELLING_DE_DE = "de_DE";
	public static final String PREF_SPELLING_ES_ES = "es_ES";
	public static final String PREF_SPELLING_IT_IT = "it_IT";
	public static final String PREF_SPELLING_FR_FR = "fr_FR";
	
	// look and feel
	public static final String PREF_LAF = "lookandfeel";
	public static final String PREF_LAF_CROSS = "cross";
	public static final String PREF_LAF_SYSTEM = "system";
	public static final String PREF_LAF_MOTIF = "motif";
	public static final String PREF_LAF_TINY = "tiny";
	public static final String PREF_LAF_TONIC = "tonic";
	public static final String PREF_LAF_SUBSTANCE = "substance";
	
	// start options
	public static final String PREF_START = "start";
	public static final String PREF_START_SHOW_WELCOME = "welcome";
	public static final String PREF_START_OPEN_PROJECT = "openproject";
	public static final String PREF_START_DO_NOTHING = "donothing";
	public static final String PREF_LAST_PROJECT = "lastproject";
		
	// confirm exit
	public static final String PREF_CONFIRM_EXIT = "confirmexit";
	
	// check for updates
	public static final String PREF_CHECK_UPDATES = "checkupdates";

	// google map
	public static final String PREF_GOOGLE_MAP_URL = "googlemapurl";
	public static final String PREF_GOOGLE_MAP_DEFAULT_URL = "http://maps.google.com";
	
	// window size and position
	public static final String PREF_WINDOW_WIDTH = "windowwidth";
	public static final String PREF_WINDOW_HEIGHT = "windowheight";
	public static final String PREF_WINDOW_X = "windowx";
	public static final String PREF_WINDOW_Y = "windowy";
	public static final String PREF_WINDOW_MAXIMIZE = "windowmaximize";
	
	// default font
	public static final String PREF_FONT_DEFAULT_NAME = "fontdefaultname";
	public static final String PREF_FONT_DEFAULT_STYLE = "fontdefaultstyle";
	public static final String PREF_FONT_DEFAULT_SIZE = "fontdefaultsize";
	public static String getAppNameAndVersion() {
		StringBuffer buf = new StringBuffer();
		buf.append(APP_SLOGAN);
		buf.append(" - Version ");
		buf.append(APP_VERSION);
		return buf.toString();
	}
}
