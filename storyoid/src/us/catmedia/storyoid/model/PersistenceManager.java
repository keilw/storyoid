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

import static android.content.Context.MODE_PRIVATE;

import us.catmedia.storyoid.util.Constants;
//import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

//import javax.swing.JOptionPane;

//import org.apache.log4j.Logger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//import us.catmedia.storyoid.util.I18N;
//import ch.intertec.storybook.util.ProjectTools;
//import ch.intertec.storybook.util.SwingTools;

public class PersistenceManager {
	/*
    class Row extends Object {
        public String name;
        public String address;
        public String mobile;
        public String home;
        public long rowId;
    }
    */
    
	//private static Logger logger = Logger.getLogger(PersistenceManager.class);
    private static final String TAG = Constants.APP_NAME_SHORT + "." + PersistenceManager.class.getSimpleName();
    
	private static PersistenceManager thePersistenceManager;
	private String databaseName;
	private boolean init;
	private boolean openOnlyIfExists;

	private Connection connection;

    //private static final String DATABASE_NAME = "ContactDB";

    //private static final String DATABASE_TABLE = "mycontact";

    private static final float DATABASE_VERSION = 0.95f;
    
    private static final String DB_EXT = ".db";
    
    private SQLiteDatabase db;
    private Context context;
    
	private PersistenceManager() {
		// make the constructor private
		init = false;
		//connection = null;
		databaseName = null;
	}

	public void init(String databaseName, Context ctx) {
		init(databaseName, ctx, false);
	}

	private void init(String databaseName, Context ctx, boolean onlyOpenIfExists) {
		
		this.databaseName = checkDbName(databaseName);
		this.context = ctx;
			//ProjectTools.getProjectDir() + File.separator + databaseName;
		//this.openOnlyIfExists = onlyOpenIfExists;
		//this.connection = null;
		this.db = ctx.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
		
		this.init = true;
		
		thePersistenceManager = this;
		
		try {
			//getConnection();
			getDb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i(TAG, "init db, databaseName=" + this.databaseName);
	}

	
	private final String checkDbName(String dbName) {
		if (dbName != null && dbName.endsWith(DB_EXT)) {
			return dbName;
		} else {
			dbName += DB_EXT;
		}
		return dbName;
	}
	
	public void initDbModel() {
		Log.d(TAG, "Setting DB Model for: " + this.databaseName + "...");
		
		try {
			
			//  create tables
			InternalPeer.createTable();
/*			
			PartPeer.createTable();
			StrandPeer.createTable();
			ScenePeer.createTable();
			SceneLinkStrandPeer.createTable();
			CharacterPeer.createTable();
			SceneLinkCharacterPeer.createTable();
			LocationPeer.createTable();
			SceneLinkLocationPeer.createTable();
			*/
			ChapterPeer.createTable();			
			/*
			// save DB model version
			InternalPeer.setDbModelVersion();

			// create default strand
			Strand strand = new Strand();
			strand.setName(I18N.getMsg("db.init.strand.name"));
			strand.setAbbreviation(I18N.getMsg("db.init.strand.abbr"));
			strand.setColor(SwingTools.getNiceBlue());
			strand.save();
			
			// create default part
			Part part = new Part();
			part.setNumber(1);
			part.setName(I18N.getMsg("db.init.part"));
			part.save();
*/			
		} catch (Exception e) {
			//e.printStackTrace();
			Log.w(TAG, "Error", e);
		}

	}

	public void saveTables(){
		//if (!isConnectionOpen()) {
			return;
		//}
		// currently not needed
	}

	public static PersistenceManager getInstance() {
		if (thePersistenceManager == null) {
			thePersistenceManager = new PersistenceManager();
		}
		return thePersistenceManager;
	}

	public Connection getConnection() {
		if (!init) {
			return null;
		}
		if (connection == null) {
			String connectionStr = "jdbc:h2:" + databaseName;
			if (openOnlyIfExists) {
				connectionStr = connectionStr + ";IFEXISTS=TRUE";
			}
			Log.i(TAG, "connect to: " + connectionStr);
			try {
				Class.forName("org.h2.Driver");
				connection = DriverManager.getConnection(
						connectionStr, "sa", "");
			} catch (Exception e) {
//				if (SwingTools.getMainFrameAsJFrame() != null) {
//					JOptionPane.showMessageDialog(
//							SwingTools.getMainFrameAsJFrame(),
//							e.getMessage(),
//							I18N.getMsg("msg.error.connection.failed"),
//							JOptionPane.ERROR_MESSAGE);
//				} else {
					Log.e(TAG, "Error",e);
					//e.printStackTrace();
				//}
			}
		}
		return connection;
	}

	public void closeConnection() {
		if (!isConnectionOpen()) {
			return;
		}
		try {
			this.connection.close();
			this.init = false;
			this.connection = null;
			this.databaseName = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @return the db
	 */
	public SQLiteDatabase getDb() {
		return db;
	}

	/**
	 * @param db the db to set
	 *
	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}
	*/
	// currently not needed

	public boolean isConnectionOpen() {
		//return connection != null;
		return db != null;
	}
	
	public boolean hasContext() {
		return context != null;
	}
	
	public void cleanup() {
		if (isConnectionOpen()) {
			db.close();
		}
	}
}
