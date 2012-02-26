/*
StorYoid: Mobile writing tool for Android
Copyright (C) 2008-2009 Werner Keil

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

import org.apache.commons.lang.ClassUtils;

import us.catmedia.storyoid.util.Constants;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Chapter extends Table {

	//private static Log logger = Log.
	private static final String TAG = Constants.APP_NAME_SHORT + "." + ClassUtils.getShortClassName(Chapter.class);
	
	public static final String TABLE_NAME = "chapter";
	public static final String COLUMN_ID = "id"; 
	public static final String COLUMN_PART_ID = "part_id";
	public static final String COLUMN_CHAPTER_NO = "chapterno";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	
	private boolean isNew;

	private int partId;
	private int chapterNo;
	private String title;
	private String description;

	public Chapter() {
		super(TABLE_NAME);
		isNew = true;
	}

	/**
	 * This method must be packaged private! It is used by
	 * {@link ScenePeer} only.
	 * @param id the id
	 */
	Chapter(int id) {
		super(TABLE_NAME);
		//this(res);
		this.id = id;
		isNew = false;
	}

	@Override
	public boolean save() throws Exception {
		try {
			String sql;
			SQLiteDatabase db = PersistenceManager.getInstance().getDb(); 
			
			if (isNew) {
				// insert
				sql = "insert into " + TABLE_NAME
						+ "(" + COLUMN_PART_ID
						+ ", " + COLUMN_CHAPTER_NO
						+ ", " + COLUMN_TITLE
						+ ", " + COLUMN_DESCRIPTION
						+ ") values(?, ?, ?, ?)";
			} else {
				// update
				sql = "update " + TABLE_NAME
						+ " set "
						+ COLUMN_PART_ID + " = ?, "
						+ COLUMN_CHAPTER_NO + " = ?, "						
						+ COLUMN_TITLE + " = ?, "
						+ COLUMN_DESCRIPTION + " = ? "
						+ "where " + COLUMN_ID + " = ?";
			}
			
			Log.d(TAG, "Trying to execute: " + sql);
						
			
			SQLiteStatement stmt = db.compileStatement(sql);			
			stmt.bindLong(1, getPartId());
			stmt.bindLong(2, getChapterNo());
			if (getTitle() != null) {
				stmt.bindString(3, getTitle());
			} else {
				stmt.bindNull(3);
			}
			if (getDescription() != null) {
				stmt.bindString(4, getDescription());
			} else {
				stmt.bindNull(4);
			}
			//Log.d(TAG, "We got a statement");
			
			if (isNew) {
				long result = stmt.executeInsert();
				if (result > 0) {
					this.id = (int)result; // FIXME try to store as long
					return true;
				}
			} else {
				stmt.bindLong(5, id);
				stmt.execute();
			}
/*			
			PreparedStatement stmt = PersistenceManager.getInstance().getConnection()
					.prepareStatement(sql);
			// insert & update
			stmt.setInt(1, getPartId());
			stmt.setInt(2, getChapterNo());
			stmt.setString(3, getTitle());
			stmt.setString(4, getDescription());
			if (!isNew) {
				// update
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
					Log.d(TAG, "save (insert): " + this);
					++count;
				}
				isNew = false;
			} else {
				Log.d(TAG, "save (update): " + this);
			}
*/						
			return false;
		//} catch (SQLException e) {
		} catch (Exception e) {
			throw e;
		}
	}

/*	
	public boolean hasScenesAssigned() {
		if (doCountScenes() > 0) {
			return true;
		}
		return false;
	}
	
	public int doCountScenes() {
		// TODO part?
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("select count (");
			buf.append(Scene.COLUMN_ID);
			buf.append(") from ");
			buf.append(Scene.TABLE_NAME);
			buf.append(" where ");
			buf.append(Scene.COLUMN_CHAPTER_ID);
			buf.append(" = ");
			buf.append(getId());
			Statement stmt = PersistenceManager.getInstance().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery(buf.toString());
			return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
*/	
	@Override
	public String getLabelText(){
		return toString();
	}

	
	public String getInfo(){
		try{
			StringBuffer buf = new StringBuffer();
			
			buf.append("<html>");
			buf.append("<b>");
			buf.append(this);
			buf.append("</b>");
			
			buf.append("<br style='padding-after: 10px'>");
			buf.append(getDescription());
/*
			for (Scene scene : ScenePeer.doSelectByChapter(this)) {
				buf.append("<br>");
				if (scene.getSceneNo() == 0) {
					buf.append("<i>");
				}
				buf.append(scene);
				buf.append(": ");
				if (scene.getTitle() == null || scene.getTitle().length() == 0) {
					buf.append(scene.getSummary(true, 35));
				} else {
					buf.append(scene.getTitle(true, 35));
				}
				if (scene.getSceneNo() == 0) {
					buf.append("</i>");
				}
			}
			
			// check whether some scenes are missing or found twice
			java.util.List<Integer> notFoundList = new ArrayList<Integer>();
			java.util.List<Integer> foundTwiceList = new ArrayList<Integer>();
			ScenePeer.checkScenceNumbers(this, notFoundList, foundTwiceList);
			if(!notFoundList.isEmpty() || !foundTwiceList.isEmpty()){
				buf.append("<p>");	
			}
			if (!notFoundList.isEmpty()) {
				buf.append(I18N.getMsg(getResources(), R.string.msg_warning_missing_scenes,
						notFoundList.toString()));
				if (!foundTwiceList.isEmpty()) {
					buf.append("<br>");
				}
			}
			if (!foundTwiceList.isEmpty()) {
				buf.append(I18N.getMsg("msg.warning.scenes.twice",
						foundTwiceList.toString()));
			}
			
			// show next scene respective chapter number
			int nextSceneNo = ScenePeer.getNextSceneNo(this);
			int nextChapterNo = ChapterPeer.getNextChapterNo();
			if(nextSceneNo > 0 || nextChapterNo > 0){
				buf.append("<p>");
			}
			if (nextSceneNo > 0) {
				buf.append(I18N.getMsg("msg.info.next.scene", nextSceneNo));
				if (nextChapterNo > 0) {
					buf.append("<br>");
				}
			}
			if (nextChapterNo > 0) {				
				buf.append(I18N.getMsg("msg.info.next.chaper", nextChapterNo));
			}
			*/
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int getPartId() {
		return partId;
	}
/*	
	public Part getPart() {
		return PartPeer.doSelectById(partId);
	}	
	
	public void setPart(Part part) {
		this.partId = part.getId();
	}
*/
	public void setPartId(int partId) {
		this.partId = partId;
	}

	public int getChapterNo() {
		return chapterNo;
	}

	public String getChapterNoStr() {
		if (chapterNo == 0) {
			return "-";
		}
		return Integer.toString(chapterNo);
	}

	public void setChapterNo(int chapterNo) {
		this.chapterNo = chapterNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return getChapterNo() + ": " + getTitle();
	}

}
