/*
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

import us.catmedia.storyoid.util.Constants;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class ChapterPeer {
	
	//private static Logger logger = Logger.getLogger(ChapterPeer.class);
	private static final String TAG = Constants.APP_NAME_SHORT + "." + ChapterPeer.class.getSimpleName();
	
	/**
	 * Has to be package private!
	 * @throws Exception
	 */
	static void createTable() throws Exception {
		String sql;
		SQLiteDatabase db;
		//Statement stmt;
		
		/*Log.d(TAG, "createTable: drop table "
				+ Chapter.TABLE_NAME
				+ " if exists");
		sql = "drop table " + Chapter.TABLE_NAME + " if exists"; */
//		stmt = PersistenceManager
//			.getInstance().getConnection().createStatement();
//		stmt.execute(sql);

		Log.d(TAG, "create table " + Chapter.TABLE_NAME);
		sql = "create table IF NOT EXISTS " + Chapter.TABLE_NAME + " ("
				+ Chapter.COLUMN_ID + " identity primary key,"
				+ Chapter.COLUMN_PART_ID + " int,"
				+ Chapter.COLUMN_CHAPTER_NO + " int,"
				+ Chapter.COLUMN_TITLE + " varchar(64),"
				+ Chapter.COLUMN_DESCRIPTION + " varchar(2048))";
//		stmt = PersistenceManager
//			.getInstance().getConnection().createStatement();
//		stmt.execute(sql);
		
		db= PersistenceManager.getInstance().getDb();
		db.execSQL(sql);
	}

/*
	public static List<Chapter> doSelectAll() {
		return doSelectAll(false);
	}
	
	public static List<Chapter> doSelectAll(boolean partDepending) {
		try{
			List<Chapter> list = new ArrayList<Chapter>();
			StringBuffer sql = new StringBuffer("select * from ");
			sql.append(Chapter.TABLE_NAME);
			if (partDepending) {
				sql.append(" where part_id = ");
				sql.append(SwingTools.getActivePartId());
			}			
			sql.append(" order by ");
			sql.append(Chapter.COLUMN_CHAPTER_NO);
			Statement stmt = PersistenceManager.getInstance().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				Chapter ch = makeChapter(rs);
				list.add(ch);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Chapter>();
	}

	public static List<Chapter> doSelectByPart(Part part) {
		try{
			List<Chapter> list = new ArrayList<Chapter>();
			StringBuffer sql = new StringBuffer("select * from ");
			sql.append(Chapter.TABLE_NAME);
			sql.append(" where part_id = ");
			sql.append(part.getId());
			sql.append(" order by ");
			sql.append(Chapter.COLUMN_CHAPTER_NO);
			Statement stmt = PersistenceManager.getInstance().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				Chapter ch = makeChapter(rs);
				list.add(ch);
			}
			return list;
		} catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Chapter>();
	}

	public static Chapter doSelectById(int id) {
		try{
			String sql = "select * from " + Chapter.TABLE_NAME
					+ " where " + Chapter.COLUMN_ID + " = ?";
			PreparedStatement stmt = PersistenceManager.getInstance().getConnection()
					.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Chapter ch = null;
			int c = 0;
			while (rs.next() && c < 2) {
				ch = makeChapter(rs);
				++c;
			}
			if (c == 0) {
				return null;
			}
			if (c > 1) {
				throw new Exception("more than one record found");
			}
			Log.d("doSelectById: " + ch);
			return ch;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Integer> doSelectByChapter(int chapterNo) throws Exception {
		List<Integer> list = new ArrayList<Integer>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + Chapter.TABLE_NAME);
		sql.append(" where " + Chapter.COLUMN_CHAPTER_NO + " = ?");
		sql.append(" order by " + Chapter.COLUMN_CHAPTER_NO);
		PreparedStatement stmt = PersistenceManager.getInstance().getConnection()
				.prepareStatement(sql.toString());
		stmt.setInt(1, chapterNo);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			list.add(new Integer(rs.getInt(Chapter.COLUMN_CHAPTER_NO)));
		}
		return list;
	}

	private static Chapter makeChapter(ResultSet rs) throws SQLException {
		Chapter ch = new Chapter(rs.getInt(Chapter.COLUMN_ID));
		ch.setPartId(rs.getInt(Chapter.COLUMN_PART_ID));
		ch.setChapterNo(rs.getInt(Chapter.COLUMN_CHAPTER_NO));
		ch.setTitle(rs.getString(Chapter.COLUMN_TITLE));
		ch.setDescription(rs.getString(Chapter.COLUMN_DESCRIPTION));
		return ch;
	}	
	
	public static Chapter makeOrUpdateChapter(ChapterDialog dlg, boolean edit)
			throws Exception {
		
		Chapter chapter;
		if (edit) {
			chapter = dlg.getChapter();
		} else {
			chapter = new Chapter();
		}
		
		// get selected strand from combo box
		Part part = (Part) dlg.getPartCoB().getSelectedItem();
		
		chapter.setChapterNo(Integer.parseInt(dlg.getChapterNoTF().getText()));
		chapter.setPart(part);
		chapter.setTitle(dlg.getTitleTF().getText());
		chapter.setDescription(dlg.getDescriptionTA().getText());
		chapter.save();
		return chapter;
	}

	public static boolean doDelete(Chapter chapter) throws Exception {
		if (chapter == null) {
			return false;
		}
		
		// in affected scenes set the chapter id to 0
		for (Scene scene : ScenePeer.doSelectAll()) {
			if (scene.getChapterId() == chapter.getId()) {
				scene.setChapterId(-1);
				scene.save();
			}
		}
		
		Statement stmt;
		String sql = "delete from " + Chapter.TABLE_NAME
			+ " where " + Chapter.COLUMN_ID + " = " + chapter.getId();
		stmt = PersistenceManager.getInstance().getConnection().createStatement();
		stmt.execute(sql);
		return true;		
	}	

	public static List<Integer> getChapterNumbersAsIntegerList() {
		List<Integer> list = new ArrayList<Integer>();
		for (Chapter chapter : doSelectAll()) {
			list.add(chapter.getChapterNo());
		}
		return list;
	}
	
	public static void checkChapterNumbers(List<Integer> notFoundList,
			List<Integer> foundTwiceList) {
		try {
			List<Integer> chapterNumberList = getChapterNumbersAsIntegerList();
			if (chapterNumberList.isEmpty()) {
				return;
			}
			Integer min = Collections.min(chapterNumberList);
			Integer max = Collections.max(chapterNumberList);			
			for (int i = min.intValue(); i < max.intValue() + 1; ++i) {
				List<Integer> list = doSelectByChapter(i);
				if (list.isEmpty()) {
					// chapter not found
					notFoundList.add(new Integer(i));
				}
				if (list.size() > 1) {
					// chapter found twice or more
					foundTwiceList.add(new Integer(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getNextChapterNo() {
		int max = 0;
		for (Chapter chapter : doSelectAll()) {
			if (max < chapter.getChapterNo()) {
				max = chapter.getChapterNo();
			}
		}
		if (max > 0) {
			return max + 1;
		}
		return 1;
	}

	public static Date getLastDate(Chapter chapter) {
		List<Date> list = ScenePeer.doSelectDistinctDateByChapter(chapter);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	public static void renumberScenes(Chapter chapter) {
		try {
			int counter = 1;
			for (Scene scene : ScenePeer.doSelectByChapter(chapter)) {
				scene.setSceneNo(counter);
				scene.save();
				++counter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateChapters(int number) {
		try {
			int start = getNextChapterNo();
			for (int i = start; i < start + number; ++i) {
				Chapter chapter = new Chapter();
				chapter.setPartId(SwingTools.getActivePartId());
				chapter.setChapterNo(i);
				chapter.setTitle(I18N.getMsg("msg.common.chapter") + " " + i);
				chapter.setDescription("");
				chapter.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  */
}
