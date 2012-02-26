/*
 
StorYoid: Mobile writing tool for Android
Copyright (C) 2008-2009 Werner Keil

*/
package us.catmedia.storyoid.main;

import static us.catmedia.storyoid.util.Constants.APP_NAME_SHORT;

import java.util.Locale;

import org.apache.commons.lang.time.StopWatch;

import us.catmedia.storyoid.R;
import us.catmedia.storyoid.model.Chapter;
import us.catmedia.storyoid.model.PersistenceManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final String TAG = APP_NAME_SHORT + ".Main";
	
	private EditText txtEdit;
	private Button btnScenes;	
	private Button btnSave;
	
	PersistenceManager pm;
	StopWatch sw;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Creating...");               
        //Log.i(TAG, String.valueOf(Locale.getDefault()));
        
        
        //Locale.setDefault(Locale.GERMAN);
        
        setContentView(R.layout.main);
        
        //getBaseContext();
        
        txtEdit = (EditText) findViewById(R.id.EditText01);
        
        btnScenes = (Button) findViewById(R.id.Button01);    	
        btnScenes.setOnClickListener(new ClickAdapter());  
        
        btnSave = (Button) findViewById(R.id.Button02);
        btnSave.setOnClickListener(new SaveClickAdapter());
        
        sw = new StopWatch();
        sw.start();
        Log.d(TAG, "Starting " + sw.toString());
        pm = PersistenceManager.getInstance();
        pm.init("20", getBaseContext());
        Log.d(TAG, String.valueOf(Locale.getDefault()));
        
        pm.initDbModel();        
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
        //Log.i(TAG, "Old Locale: " + String.valueOf(Locale.getDefault()));
        
        
        //Locale.setDefault(Locale.GERMAN);
 
        //Log.i(TAG, "New Locale: " + String.valueOf(Locale.getDefault()));
		Log.d(TAG, "Locale: " + String.valueOf(Locale.getDefault()));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Log.i(TAG, "Cleaning...");
		pm.cleanup();
		pm = null;
		
		if (sw != null) {		
			sw.stop();
			Log.i(TAG, sw.toString());
		} else {
			Log.w(TAG, "No Stopwatch found.");
		}
	}
	
	void saveChapter() {
        Chapter c = new Chapter();
        c.setTitle("Title 1");
        
        //this.setTitle(c.getTitle().subSequence(0, c.getTitle().length()));
        try {
        	if (c.save()) {
        		Log.d(TAG, "ID: " + c.getId());
        	}
        } catch (Exception ex) {
        	Log.e(TAG, "DB Error", ex);
        }
	}
	
    /**
     * Click Adapter
     */
    private final class ClickAdapter implements View.OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
        	Log.v(TAG, "Click [" + getIntent() + "]");
        	Log.d(TAG, txtEdit.getText().toString());
        	
        	//new NoteEditor().startActivity(getIntent());
        	setContentView(R.layout.note_editor);
            // Launch activity to view/edit the currently selected item
            //startActivity(new Intent("us.catmedia.storyoid.action.EDIT_NOTE"));
        	//startActivity(new NoteEditor().getIntent());
		}
    }
    
    /**
     * Click Adapter
     */
    private final class SaveClickAdapter implements View.OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
        	Log.v(TAG, "Clicked Save");
        	
        	Log.d(TAG + ".Click", "Saving...");
        	saveChapter();
		}
    }
}