/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008 Werner Keil

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

import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import android.database.sqlite.SQLiteStatement;

import us.catmedia.storyoid.util.Logger;
//import org.apache.log4j.Logger;
import us.catmedia.storyoid.util.DbTools;

public class Scene extends Table {

	private static Logger logger = Logger.getLogger(Scene.class);

	public static final String TABLE_NAME = "scene";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CHAPTER_ID = "chapter_id";
//	public static final String COLUMN_PART_ID = "part_id";
	public static final String COLUMN_STRAND_ID = "strand_id";
	public static final String COLUMN_DATE = "date"; 
	public static final String COLUMN_SCENE_NO = "sceneno";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_SUMMARY = "summary";
	public static final String COLUMN_STATUS = "status";
	
	public static final int STATUS_NONE = 0;
	public static final int STATUS_OUTLINE = 1;
	public static final int STATUS_DRAFT = 2;
	public static final int STATUS_1ST_EDIT = 3;
	public static final int STATUS_2ND_EDIT = 4;
	public static final int STATUS_DONE = 5;
	
	private boolean isNew;
	
	private int chapterId;
//	private int partId;
	private int strandId;
	private Date date;
	private int sceneNo;
	private String summary;
	private int status;
	private String title;

	public Scene() {
		super(TABLE_NAME);
		isNew = true;
	}

	/**
	 * This method must be packaged private! It is used by
	 * {@link ScenePeer} only.
	 * @param id the id
	 */
	Scene(int id) {
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
				sql = "insert into " + TABLE_NAME
						+ "(" + COLUMN_CHAPTER_ID
						+ ", " + COLUMN_STRAND_ID
						+ ", " + COLUMN_DATE
						+ ", " + COLUMN_SCENE_NO
						+ ", " + COLUMN_TITLE
						+ ", " + COLUMN_SUMMARY
						+ ", " + COLUMN_STATUS
						+ ") values(?, ?, ?, ?, ?, ?, ?)";
			} else {
				// update
				sql = "update " + TABLE_NAME
						+ " set "
						+ COLUMN_CHAPTER_ID + " = ?, "						
						+ COLUMN_STRAND_ID + " = ?, "
						+ COLUMN_DATE + " = ?, "
						+ COLUMN_SCENE_NO + " = ?, "
						+ COLUMN_TITLE + " = ?, "
						+ COLUMN_SUMMARY + " = ?, "
						+ COLUMN_STATUS + " = ? "
						+ "where " + COLUMN_ID + " = ?";
			}
			SQLiteStatement stmt = PersistenceManager.getInstance().getDb()
					.compileStatement(sql);
			// insert & update
			stmt.bindLong(1, getChapterId());
			stmt.bindLong(2, getStrandId());
			stmt.bindString(3, getDate().toString());
			stmt.bindLong(4, getSceneNo());
			stmt.bindString(5, getTitle());
			stmt.bindString(6, getSummary());
			stmt.bindLong(7, getStatus());
			if (!isNew) {
				// update
				stmt.bindLong(8, getId());
			}
			if (stmt.executeInsert() != 1) {
				throw new SQLException(isNew ? "insert" : "update" + " failed");
			}
			if (isNew) {
				this.id = stmt.getUniqueId();
				/*
				int count = 0;
				while (rs.next()) {
					if (count > 0) {
						throw new SQLException("error: got more than one id");
					}
					this.id = rs.getInt(1);
					logger.debug("save (insert): " + this);
					++count;
				}
				*/
				isNew = false;
			} else {
				logger.debug("save (update): " + this);
			}
			return true;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public String getLabelText(){
		return toString();
	}
	
	public String getChapterAndSceneNumber() {
		StringBuffer buf = new StringBuffer();
		if (getChapter() != null) {
			buf.append(getChapter().getChapterNoStr());
		} else {
			buf.append("x");
		}
		buf.append(".");
		if (getSceneNo() > 0) {			
			buf.append(getSceneNoStr());
		} else {
			buf.append("x");
		}
		return buf.toString();
	}
	
	public String toString() {
		if (getSceneNo() == 0) {
			return "x";
		}
		return getSceneNoStr();
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSummary() {
		return getSummary(false, 0);
	}

	public String getSummary(boolean truncate, int length) {
		if (!truncate) {
			return summary;
		}
		return truncate(summary, length);
	}

	public String getShortSummary() {
		if (summary== null || summary.length()==0) {
			return "";
		}
		return StringUtils.substring(summary, 0, 64);
	}

	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setSceneNo(int sceneNo) {
		this.sceneNo = sceneNo;
	}

	public void setSceneNoStr(String sceneNoStr) throws NumberFormatException {
		setSceneNo(Integer.parseInt(sceneNoStr));
	}
	
	public int getSceneNo() {
		return sceneNo;
	}
	
	public String getSceneNoStr(){
		return new Integer(sceneNo).toString();
	}

	public int getStrandId() {
		return strandId;
	}

/*	
	public Strand getStrand() {
		return StrandPeer.doSelectById(strandId);
	}

	public void setStrand(Strand strand) {
		this.strandId = strand.getId();
	}
	
	public void setStrandId(int strandId) {
		this.strandId = strandId;
	}
*/
	public Date getDate() {
		return date;
	}

	public String getDateStr() {
		return date.toString();
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(Calendar cal){
		setDate(DbTools.calendar2SQLDate(cal));
	}

	public int getChapterId() {
		return chapterId;
	}
	
	public Chapter getChapter() {
		//return ChapterPeer.doSelectById(chapterId);
		// FIXME add ChapterPeer
		return null;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	
	public void setChapter(Chapter chapter) {
		chapterId = chapter.getId();
	}

	public String getTitle() {
		return getTitle(false, 0);
	}
	
	public String getTitle(boolean truncate, int length){
		if (truncate == false) {
			return title;
		}
		return truncate(title, length);
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	private String truncate(String str, int length) {
		if (str.length() == 0) {
			return "";
		}
		String substr = StringUtils.substring(str, 0, length);
		if (str.length() > 35) {
			return substr + "...";
		}
		return substr;
	}
}
