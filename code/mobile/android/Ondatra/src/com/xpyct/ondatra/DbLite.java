package com.xpyct.ondatra;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

//import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DbLite {
    private Context context;

	protected String _appFolder;
	protected String _dbName;
    public    String _dbPath;
    protected SQLiteDatabase _db = null;
	
	public DbLite(Context context) throws IOException {
        this.context = context;
		String _appFolder = "Ondatra";
		String _dbName    = "test.db";
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();  // sdcard
		InitDbLite(extStorageDirectory + '/' + _appFolder + '/' + _dbName);
	}

	public DbLite(String pathname) throws IOException {
		this._dbName = pathname;
	}

    private void InitDbLite(String path) {
        this._dbPath = path;
        this._db = context.openOrCreateDatabase(this._dbPath, Context.MODE_PRIVATE, null);
    }
	
	public void create() {
        //this._db.execSQL("create table IF NOT EXISTS notes (id      integer primary key autoincrement, "
        //        + " created date    default CURRENT_DATE, "
        //        + " text    varchar, "
        //        + " field   INT(3));");
        this._db.execSQL("create table IF NOT EXISTS notes2 (id      integer primary key autoincrement, "
                       + " created date    default CURRENT_DATE, "
                       + " text    varchar, "
                       + " field   INT(3));");
        //this._db.execSQL("create table IF NOT EXISTS config (id      integer primary key autoincrement, "
        //               + " created date    default CURRENT_DATE, "
        //               + " text    varchar, "
        //               + " field   INT(3));");
        //this._db.execSQL("INSERT INTO config (created, text) VALUES (datetime(), \"localhost\");");
        //System.out.println("record created");
	}

    public void insert(String text) {
        //if( !this._db.isReadOnly() ) {
        //    this._db.execSQL("INSERT INTO notes (created, text) VALUES (datetime(), \"" + text + "\");");
        //}
        String aa = null;
        this._db.beginTransaction();
        try{
            //this._db.execSQL("INSERT INTO notes (created, text) VALUES (datetime(), \"" + text + "\");");
            this._db.execSQL("INSERT INTO notes2 (created, text) VALUES (datetime(), \"" + text + "\");");
        }
        catch (SQLException ex){
            //return ex.toString();
            aa = ex.toString();
        }
        this._db.setTransactionSuccessful();
        this._db.endTransaction();
        aa = null;
        //return null;
    }

    public String getText() {
        Cursor c = this._db.rawQuery("SELECT * FROM config WHERE id=1", null);
        c.moveToFirst(); //c.moveToNext();
        String txt = c.getString( c.getColumnIndex("text"));
        System.out.println("localhost is: " + txt );
        return txt;
    }

    public void open() {
        ;
    }

	public void execute() {
		;
	}

	public void select() {
		;
	}

	public void close() {
		;
	}

	public void delete() {
		;
	}
}
