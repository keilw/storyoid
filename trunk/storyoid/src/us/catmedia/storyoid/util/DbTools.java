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

import java.sql.Date;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.List;

import android.database.sqlite.SQLiteStatement;

import us.catmedia.storyoid.util.Logger;

import us.catmedia.storyoid.model.Chapter;
import us.catmedia.storyoid.model.InternalPeer;
//import us.catmedia.storyoid.model.Location;
//import us.catmedia.storyoid.model.LocationPeer;
import us.catmedia.storyoid.model.PersistenceManager;
import us.catmedia.storyoid.model.Scene;

public class DbTools {
	
	private static Logger logger = Logger.getLogger(DbTools.class);
	
	public static boolean checkAndAlterModel(){
		String prjVersion = InternalPeer.getDbModelVersion();
		String currentVersion = Constants.DB_MODEL_VERSION;

		if (prjVersion.equals(currentVersion)) {
			// model matches, nothing to do
			return true;
		}
		
		// alter models
/*		
		// version 0 -> 0.4
		if (prjVersion.equals("0")) {
			boolean ret = false;
			ret = alterVersionFrom0to0_1();
			if (ret) { ret = alterVersionFrom0_1to0_2(); }
			if (ret) { ret = alterVersionFrom0_2to0_3(); }
			if (ret) { ret = alterVersionFrom0_3to0_4(); }
			if (ret) { ret = alterVersionFrom0_4to0_5(); }
			if (ret) { ret = alterVersionFrom0_5to0_6(); }
			if (ret) { ret = alterVersionFrom0_6to0_7(); }
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}
		
		// version 0.1 -> 0.4
		if (prjVersion.equals("0.1")) {
			boolean ret = false;
			ret = alterVersionFrom0_1to0_2();
			if (ret) { ret = alterVersionFrom0_2to0_3(); }
			if (ret) { ret = alterVersionFrom0_3to0_4(); }
			if (ret) { ret = alterVersionFrom0_4to0_5(); }
			if (ret) { ret = alterVersionFrom0_5to0_6(); }
			if (ret) { ret = alterVersionFrom0_6to0_7(); }
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}
		
		// version 0.2 -> 0.4
		if (prjVersion.equals("0.2")) {
			boolean ret = false;
			ret = alterVersionFrom0_2to0_3();
			if (ret) { ret = alterVersionFrom0_3to0_4(); }
			if (ret) { ret = alterVersionFrom0_4to0_5(); }
			if (ret) { ret = alterVersionFrom0_5to0_6(); }
			if (ret) { ret = alterVersionFrom0_6to0_7(); }
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}

		// version 0.3 -> 0.4
		if (prjVersion.equals("0.3")) {
			boolean ret = false;
			ret = alterVersionFrom0_3to0_4();
			if (ret) { ret = alterVersionFrom0_4to0_5(); }
			if (ret) { ret = alterVersionFrom0_5to0_6(); }
			if (ret) { ret = alterVersionFrom0_6to0_7(); }
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}
		
		// version 0.4 -> 0.5
		if (prjVersion.equals("0.4")) {
			boolean ret = false;
			ret = alterVersionFrom0_4to0_5();
			if (ret) { ret = alterVersionFrom0_5to0_6(); }
			if (ret) { ret = alterVersionFrom0_6to0_7(); }
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}
		
		// version 0.5 -> 0.6
		if (prjVersion.equals("0.5")) {
			boolean ret = false;
			ret = alterVersionFrom0_5to0_6();
			if (ret) { ret = alterVersionFrom0_6to0_7(); }
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}

		// version 0.6 -> 0.7
		if (prjVersion.equals("0.6")) {
			boolean ret = false;
			ret = alterVersionFrom0_6to0_7();
			if (ret) { ret = alterVersionFrom0_7to0_8(); }
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}
		
		// version 0.7 -> 0.8
		if (prjVersion.equals("0.7")) {
			boolean ret = false;
			ret = alterVersionFrom0_7to0_8();
			if (ret) { ret = alterVersionFrom0_8to0_9(); }
			return ret;
		}
*/
		// version 0.8 -> 0.9
		if (prjVersion.equals("0.8")) {
			boolean ret = false;
			ret = alterVersionFrom0_8to0_9();
			return ret;
		}

		// unknown version
		/*JOptionPane.showMessageDialog(
				SwingTools.getMainFrame(),
				I18N.getMsg("msg.error.wrong.version", prjVersion),
				I18N.getMsg("msg.common.error"),
				JOptionPane.ERROR_MESSAGE); */
		// TODO Android Message Dialog
		return false;
	}
	
	private static boolean alterVersionFrom0to0_1(){
		// from a beta version, just set it to "0.1"
		InternalPeer.setDbModelVersion("0.1");
		return true;
	}

/*	
	private static boolean alterVersionFrom0_1to0_2(){
		try {
			SQLiteStatement stmt;

			logger.debug("alter db model from version 0.1 to 0.2");			
			
			logger.debug("alter table chapter");
			String sql ="alter table chapter alter column summary varchar(8192)";
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();

			logger.debug("alter table person");
			sql ="alter table person alter column description varchar(8192)";
			stmt.
			stmt.execute(sql);

			logger.debug("alter table location");
			sql ="alter table location alter column description varchar(8192)";				
			stmt.execute(sql);
			
			InternalPeer.setDbModelVersion("0.2");			
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		}
	}

	private static boolean alterVersionFrom0_2to0_3(){
		try {
			Statement stmt;
			
			logger.debug("alter db model from version 0.2 to 0.3");
			stmt = PersistenceManager.getInstance().getConnection().createStatement();

			logger.debug("alter table chapter");
			String sql ="alter table chapter add todo boolean";
			stmt.execute(sql);
			
			InternalPeer.setDbModelVersion("0.3");
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		}
	}

	private static boolean alterVersionFrom0_3to0_4(){
		try {
			Statement stmt;
			
			logger.debug("alter db model from version 0.3 to 0.4");
			stmt = PersistenceManager.getInstance().getConnection().createStatement();

			logger.debug("alter table person");
			String sql ="alter table person add color int";
			stmt.execute(sql);
			
			InternalPeer.setDbModelVersion("0.4");
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		}
	}

	private static boolean alterVersionFrom0_4to0_5(){
		try {
			Statement stmt;
			
			logger.debug("alter db model from version 0.4 to 0.5");
			stmt = PersistenceManager.getInstance().getConnection().createStatement();

			logger.debug("alter person.firstname");
			String sql ="alter table person alter column firstname varchar(64)";
			stmt.execute(sql);

			logger.debug("alter person.lastname");
			sql ="alter table person alter column lastname varchar(64)";
			stmt.execute(sql);
			
			logger.debug("alter person.occupation");
			sql ="alter table person alter column occupation varchar(64)";
			stmt.execute(sql);			
			
			InternalPeer.setDbModelVersion("0.5");
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		}
	}
	
	private static boolean alterVersionFrom0_5to0_6(){
		try {
			Statement stmt;
			
			logger.debug("alter db model from version 0.5 to 0.6");
			stmt = PersistenceManager.getInstance().getConnection().createStatement();

			// add address field
			String sql ="alter table location add address varchar(64)";
			logger.debug(sql);
			stmt.execute(sql);
			// fill it with empty strings
			List<Location> locationList = LocationPeer.doSelectAll();
			for (Location location : locationList) {
				location.setAddress("");
				location.save();
			}
			
			sql ="alter table chapter rename to scene";
			logger.debug(sql);
			stmt.execute(sql);
			
			sql ="alter table scene alter column chapterno rename to sceneno";
			logger.debug(sql);
			stmt.execute(sql);
			
			sql ="alter table chapter_person rename to scene_person";
			logger.debug(sql);
			stmt.execute(sql);

			sql ="alter table scene_person alter column chapter_id rename to scene_id";
			logger.debug(sql);
			stmt.execute(sql);

			sql ="alter table chapter_location rename to scene_location";
			logger.debug(sql);
			stmt.execute(sql);

			sql ="alter table scene_location alter column chapter_id rename to scene_id";
			logger.debug(sql);
			stmt.execute(sql);

			sql ="alter table chapter_strand rename to scene_strand";
			logger.debug(sql);
			stmt.execute(sql);

			sql ="alter table scene_strand alter column chapter_id rename to scene_id";
			logger.debug(sql);
			stmt.execute(sql);
			
			InternalPeer.setDbModelVersion("0.6");
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		} catch(Exception e){
			logger.error(e);
			//SwingTools.showException(e);
			return false;			
		}
	}

	private static boolean alterVersionFrom0_6to0_7(){
		try {
			Statement stmt;
			
			logger.debug("alter db model from version 0.6 to 0.7");
			stmt = PersistenceManager.getInstance().getConnection().createStatement();

			String sql ="alter table internal alter column value rename to string_value";
			logger.debug(sql);
			stmt.execute(sql);

			sql = "alter table internal add integer_value int";
			logger.debug(sql);
			stmt.execute(sql);

			sql = "alter table internal add boolean_value bool";
			logger.debug(sql);
			stmt.execute(sql);

			InternalPeer.setDbModelVersion("0.7");
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		}
	}

	private static boolean alterVersionFrom0_7to0_8(){
		try {
			Statement stmt;
			
			logger.debug("alter db model from version 0.7 to 0.8");
			stmt = PersistenceManager.getInstance().getConnection().createStatement();

			// alter table scene
			String sql = "alter table scene add chapter_id int";
			logger.debug(sql);
			stmt.execute(sql);
			
			// create table chapter
			sql = "create table chapter ("
				+ "id identity primary key, "
				+ "chapterno int, "
				+ "title varchar(64), "
				+ "description varchar(2048))";			
			logger.debug(sql);
			stmt.execute(sql);
			
			// make chapter 1
			logger.debug("make chapter 1");
			Chapter chapter = new Chapter();
			chapter.setChapterNo(1);
			chapter.setTitle("Chapter 1");
			chapter.setDescription("");
			chapter.save();
			
			// assign all scenes to chapter 1
			logger.debug("assign all scenes to chapter 1");
			sql = "update scene "
				+ "set chapter_id = "
				+ chapter.getId();			
			logger.debug(sql);
			stmt.execute(sql);
			
			InternalPeer.setDbModelVersion("0.8");
			return true;
		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
		} catch (Exception e){
			logger.error(e);
			//SwingTools.showException(e);
			return false;			
		}
	}
*/
	
	private static boolean alterVersionFrom0_8to0_9(){
		try {
			SQLiteStatement stmt;
			
			logger.debug("alter db model from version 0.8 to 0.9");			

			String sql = "alter table scene add title varchar(256)";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();
			
			sql = "alter table scene alter column todo int";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();
			
			sql = "alter table scene alter column todo rename to status";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();
			
			// change status 0 to 5 (=done)
			sql = "update scene set "
					+ Scene.COLUMN_STATUS + "=" + "'" + Scene.STATUS_DONE
					+ "' where " + Scene.COLUMN_STATUS + "='0'";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();

			// move part link to chapter
			sql = "alter table scene drop column part_id";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();
			sql = "alter table chapter add column part_id int";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();
			
			// assign all chapters to part 1
			sql = "update chapter set " + Chapter.COLUMN_PART_ID + " = 1";
			logger.debug(sql);
			stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
			stmt.execute();			
			
			InternalPeer.setDbModelVersion("0.9");
			return true;
/*		} catch (SQLException e) {
			logger.error(e);
			//SwingTools.showException(e);
			return false;
*/			
		} catch(Exception e){
			logger.error(e);
			//SwingTools.showException(e);
			return false;			
		}
	}
	
	public static java.sql.Date dateStrToSqlDate(String dateStr)
			throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = df.parse(dateStr);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public static Date calendar2SQLDate(Calendar cal) {
		return new java.sql.Date(cal.getTimeInMillis());
	}	
}
