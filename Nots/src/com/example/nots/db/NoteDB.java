package com.example.nots.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDB extends SQLiteOpenHelper {

	public NoteDB(Context context) {
		super(context, "notes", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME_NOTES + "(" + COLUME_NAME_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUME_NAME_NOTE_NAME
				+ " TEXT NOT NULL DEFAULT \"\"," + COLUME_NAME_NOTE_CONTENT
				+ " TEXT NOT NULL DEFAULT \"\"," + COLUME_NAME_NOTE_DATE
				+ " TEXT NOT NULL DEFAULT \"\")");

		db.execSQL("CREATE TABLE " + TABLE_NAME_MEDIA + "("
				+ COLUME_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUME_MEDIA_PATH + " TEXT NOT NULL DEFAULT \"\","
				+ COLUME_MEDIA_NOTE_ID + " INTEGRE NOT NULL DEFAULT 0)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public static final String TABLE_NAME_NOTES = "notes";
	public static final String TABLE_NAME_MEDIA = "media";
	
	public static final String COLUME_NAME_ID = "_id";
	public static final String COLUME_NAME_NOTE_NAME = "name";
	public static final String COLUME_NAME_NOTE_CONTENT = "content";
	public static final String COLUME_NAME_NOTE_DATE = "date";

	public static final String COLUME_MEDIA_PATH = "path";
	public static final String COLUME_MEDIA_NOTE_ID = "note_id";
}
