package com.example.nots;

import java.util.ArrayList;
import java.util.List;

import com.example.nots.db.NoteDB;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AtyEditNote extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_edit_note);
		
		db = new NoteDB(this);
		daRead = db.getReadableDatabase();
		
		
		adaper = new MediaAdaper(this);
		setListAdapter(adaper);
		
		etName = (EditText) findViewById(R.id.etName);
		etContent = (EditText) findViewById(R.id.etContent);
		
		
		noteId = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
		if(noteId > -1)
		{
			etName.setText(getIntent().getStringExtra(EXTRA_NOTE_NAME));
			etContent.setText(getIntent().getStringExtra(EXTRA_NOTE_CONTENT));
			
			Cursor c = daRead.query(NoteDB.TABLE_NAME_MEDIA, null, NoteDB.COLUME_MEDIA_NOTE_ID+"=?", new String[]{noteId+""},null,null,null);
			while(c.moveToNext())
			{
				adaper.add(new MedialistCellData(c.getString(c.getColumnIndex(NoteDB.COLUME_MEDIA_PATH)),c.getInt(c.getColumnIndex(NoteDB.COLUME_NAME_ID))));
				
			}
		}
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		daRead.close();
		super.onDestroy();
	}
	
	
	private int noteId = -1;
	private EditText etName,etContent;
	private MediaAdaper adaper;
	private NoteDB db;
	private SQLiteDatabase daRead;
	
	public static final String EXTRA_NOTE_ID = "note_id";
	public static final String EXTRA_NOTE_NAME = "note_name";
	public static final String EXTRA_NOTE_CONTENT = "note_content";
	
	static class MediaAdaper extends BaseAdapter
	{
		public MediaAdaper(Context context)
		{
			this.context = context;
		}
		
		public void add(MedialistCellData data)
		{
			list.add(data);
		}
		
		@Override
		public int getCount() {
			
			return list.size();
		}

		@Override
		public MedialistCellData getItem(int position) {
			
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.media_list_cell, null);
			}
			
			MedialistCellData data = getItem(position);
			ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			TextView  tvPath = (TextView) convertView.findViewById(R.id.tvPath);
			
			ivIcon.setImageResource(data.iconId);
			tvPath.setText(data.path);
			return null;
		}
		
		private Context context;
		private List<MedialistCellData> list = new ArrayList<MedialistCellData>();
		
	}
	
	static class MedialistCellData
	{
		public MedialistCellData(String path)
		{
			this.path = path;
			if(this.path.endsWith(".jpg"))
			{
				iconId = R.drawable.icon_photo;
			}else if(this.path.endsWith(".mp4"))
			{
				iconId = R.drawable.icon_video;
			}
		}
		
		public MedialistCellData(String path,int id)
		{
			this(path);
			
			this.id =id;
		}
		
		int id  = -1;
		String path = "";
		int iconId = R.drawable.ic_launcher;
	}
}
