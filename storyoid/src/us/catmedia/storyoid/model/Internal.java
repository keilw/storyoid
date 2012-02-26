/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008-2009 Werner Keil, Creative Arts & Technologies

Portions StorYBook: Summary-based software for novelist and authors.
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
import java.sql.SQLException;
*/

//import org.apache.log4j.Logger;

import org.apache.commons.lang.ClassUtils;

//import android.content.res.Resources;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Internal extends Table {
	
	//private static Logger logger = Logger.getLogger(Internal.class);
	//private static final String TAG = "Internal";
	private static final String TAG = ClassUtils.getShortClassName(Internal.class);
	
	static final String TABLE_NAME = "internal";
	static enum Columns {
		id, key, string_value, integer_value, boolean_value
	}
	//public static final int COLUMN_KEY = 2;
	//public static final int COLUMN_STRING_VALUE = 3;
	//public static final int COLUMN_INTEGER_VALUE = 4;
	//public static final int COLUMN_BOOLEAN_VALUE = 5;
	
	//public static final String COLUMN_OLD_VALUE = "value";
	
	private String key = null;
	private String stringValue = null;
	private Integer integerValue = null;
	private Boolean booleanValue = null;
	
	public Internal() {
		super(TABLE_NAME);
		isNew = true;
	}

	/**
	 * This method must be packaged private! It is used
	 * by {@link InternalPeer} only.
	 * 
	 * @param id
	 *            the id
	 */
	Internal(int id) {
		super(TABLE_NAME);
		this.id = id;
		isNew = false;
	}
	
	@Override
	public boolean save() throws Exception {
		try {
			String sql;
			if (isNew) {
				// insert
				sql = "insert into "
					+ TABLE_NAME
					+ "(" + Columns.key.name()
					+ ", " + Columns.string_value.name()
					+ ", " + Columns.integer_value.name()
					+ ", " + Columns.boolean_value.name()
					+ ") values(?, ?, ?, ?)";
			} else {
				// update
				sql = "update " + TABLE_NAME
					+ " set "
					+ Columns.key.name() + " = ?, "
					+ Columns.string_value.name() + " = ?, "
					+ Columns.integer_value.name() + " = ?, "
					+ Columns.boolean_value.name() + " = ? "
					+ "where " + Columns.id.name() + " = ?";
			}
			
			
			Log.d(TAG, "Trying to execute: " + sql);
			
			SQLiteDatabase db = PersistenceManager.getInstance().getDb();
			// Since SQL doesn't allow inserting a completely empty row, the second parameter of db.insert defines the column that will receive NULL if cv is empty
ContentValues cv=new ContentValues();
cv.put("YourColumnName", "YourColumnValue");
db.insert("MyTableName", "YourColumnName", cv);

//ContentValues cv=new ContentValues();
cv.put("YourColumnName", "YourColumnValue");
db.update("MyTableName", cv, "_id=?", new String[]{"1"});


			//PreparedStatement stmt = PersistenceManager.getInstance().getConnection().prepareStatement(sql);
			// sets for insert & update
	/*
			stmt.setString(1, getKey());
			stmt.setString(2, getStringValue() == null ? "" : getStringValue());
			stmt.setInt(3, getIntegerValue() == null ? Integer.MIN_VALUE
					: getIntegerValue());
			stmt.setBoolean(4, getBooleanValue() == null ? false
					: getBooleanValue());
			if (!isNew) {
				// sets for update only
				stmt.setInt(5, getId());
			}
			if (stmt.executeUpdate() != 1) {
				throw new SQLException(isNew ? "insert" : "update" + " failed");
			}
			if (isNew) {
				ResultSet rs = stmt.getGeneratedKeys();
				int count = 0;
				while (rs.next()) {
					if (count > 0) {
						throw new SQLException("error: got more than one id");
					}
					this.id = rs.getInt(1);
					logger.debug("save (insert): " + this);
					++count;
				}
				isNew = false;
			} else {
				logger.debug("save (update): " + this);
			}
			return true;
		} catch (SQLException e) { */
			return true;
		}	catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public String getLabelText(){
		return toString();
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("ID=" + getId());
		buf.append(", key=" + getKey());
		if(getStringValue() != null){
			buf.append(", stringValue=" + getStringValue());
		}
		if(getIntegerValue() != null){
			buf.append(", integerValue=" + getIntegerValue());
		}
		if(getBooleanValue() != null){
			buf.append(", booleanValue=" + getBooleanValue());
		}
		return buf.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
}
