/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008 Werner Keil

StorYBook: Summary-based software for novelist and authors.
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

package us.catmedia.storyoid.model;

/*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
*/
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;

//import org.apache.log4j.Logger;
//import org.h2.jdbc.JdbcSQLException;

//import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import us.catmedia.storyoid.util.Constants;

public class InternalPeer {

	public static final String KEY_DB_MODEL_VERSION = "dbversion";
	
	//private static Logger logger = Logger.getLogger(InternalPeer.class);
	private static final String TAG = InternalPeer.class.getName().substring(
			InternalPeer.class.getName().lastIndexOf(".")+1);
	
	public static void create() {
		try {
			createTable();
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e(TAG, "Error", e);
		}
	}

	public static void createTable() throws Exception {
		String sql;
		SQLiteDatabase db;

		// create
		Log.d(TAG, "create table " + Internal.TABLE_NAME);
		sql = "create table IF NOT EXISTS "
			+ Internal.TABLE_NAME
			+ " ("
			+ Internal.Columns.id.name() + " identity primary key,"
			+ Internal.Columns.key.name() + " varchar(64),"
			+ Internal.Columns.string_value.name() + " varchar(64),"
			+ Internal.Columns.integer_value.name() + " int,"
			+ Internal.Columns.boolean_value.name() + " bool"
			+ ")";

		db= PersistenceManager.getInstance().getDb();
		db.execSQL(sql);
	}

/*	
	public static List<Internal> doSelectAll() {
		try {
			if(!PersistenceManager.getInstance().isConnectionOpen()){
				return new ArrayList<Internal>();
			}
			
			List<Internal> list = new ArrayList<Internal>();
			StringBuffer sql = new StringBuffer("select * from " + Internal.TABLE_NAME);
			sql.append(" order by " + Internal.Columns.key.name());

			SQLiteDatabase db = PersistenceManager.getInstance().getDb();
			Cursor rs = db.rawQuery(sql.toString(), null);
			if (rs.getCount()>0) {
				while (!rs.isAfterLast()) {
					Internal internal = makeInternal(rs);
					Log.d(TAG, "doSelectAll: " + internal);
					list.add(internal);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Internal doSelectById(int id) throws Exception {
		String sql = "select * from " + Internal.TABLE_NAME + " where "
				+ Internal.Columns.id.name() + " = ?";
		PreparedStatement stmt = PersistenceManager.getInstance().getConnection().prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Internal internal = null;
		int c = 0;
		while (rs.next() && c < 2) {
			internal = makeInternal(rs);
			++c;
		}
		if (c == 0) {
			return null;
		}
		if (c > 1) {
			throw new Exception("more than one record found");
		}
		logger.debug("doSelectById: " + internal);
		return internal;
	}
	*/
	
	public static Internal doSelectByKey(String key) throws Exception {
		String sql = "select * "
			+ "from " + Internal.TABLE_NAME
			+ " where " + Internal.Columns.key.name() + " = ?";
		//SQLiteStatement stmt = PersistenceManager.getInstance().getDb().compileStatement(sql);
		//stmt.bindString(1, key);
		//stmt.
		//ResultSet rs = stmt.executeQuery();
		//stmt.
		String[] keys = { key };
		Cursor cs = PersistenceManager.getInstance().getDb().rawQuery(sql, keys);
		Internal internal = null;
		int c = 0;
		while (!cs.isAfterLast() && c < 2) {
			internal = makeInternal(cs);
			++c;
		}
		if (c == 0) {
			return null;
		}
		if (c > 1) {
			throw new Exception("more than one record found");
		}
		Log.d(TAG, "doSelectByKey: " + internal);
		return internal;
	}

	public static long doCount() {
		try {
			/*
			String sql = "select count(" + Internal.COLUMN_ID + ") from "
					+ Internal.TABLE_NAME;
			Statement stmt = PersistenceManager.getInstance().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getInt(1);
			*/
			SQLiteDatabase db = PersistenceManager.getInstance().getDb();
			return DatabaseUtils.queryNumEntries(db, Internal.TABLE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static Internal makeInternal(Cursor rs) throws SQLException {
		Internal internal = new Internal(rs.getInt(Internal.Columns.id.ordinal()));
		internal.setKey(rs.getString(Internal.Columns.key.ordinal()));
		internal.setStringValue(rs.getString(Internal.Columns.string_value.ordinal()));
		internal.setIntegerValue(rs.getInt(Internal.Columns.integer_value.ordinal()));
		internal.setBooleanValue(Boolean.valueOf(rs.getString(Internal.Columns.boolean_value.ordinal())));
		return internal;
	}
	
/*
	public static boolean doDelete(Internal internal) throws Exception {
		if (internal == null) {
			return false;
		}
		Log.d(TAG, "doDelete: " + internal);
		String sql = "delete from " + Internal.TABLE_NAME
			+ " where " + Internal.COLUMN_ID + " = " + internal.getId();
		Statement stmt = PersistenceManager.getInstance().getConnection().createStatement();
		stmt.execute(sql);
		return true;
	}		
*/
	public static String getDbModelVersion() {
		try{
			return doSelectByKey(KEY_DB_MODEL_VERSION).getStringValue();
		/*} catch(JdbcSQLException e){
			try{
				// try to get the value from an old DB model				
				String sql = "select * "
					+ "from " + Internal.TABLE_NAME
					+ " where " + Internal.COLUMN_KEY + " = '" + KEY_DB_MODEL_VERSION + "'";
				Statement stmt = PersistenceManager.getInstance().getDb().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				return rs.getString(Internal.COLUMN_OLD_VALUE);
			} catch(SQLException se){
				se.printStackTrace();
				return null;
			} */
		} catch(Exception e){
			Log.e(TAG, "Error", e);
			return null;
		}
	}
	
	public static void setDbModelVersion() {
		setDbModelVersion(Constants.DB_MODEL_VERSION);
	}
	
	public static void setDbModelVersion(String version) {
		try {
			Internal internal = doSelectByKey(KEY_DB_MODEL_VERSION);
			if (internal == null) {
				internal = new Internal();
				internal.setKey(KEY_DB_MODEL_VERSION);
			}
			internal.setStringValue(version);
			internal.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
