package com.example.nots;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.nots.db.NoteDB;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AtyEditNote extends ListActivity {

	private OnClickListener ClickHander = new OnClickListener() {
		Intent i;
		File f;
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnAddPhoto:
				i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				f = new File(getMidiaDir(),System.currentTimeMillis()+".jpg");
				if(!f.exists())
				{
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				currPath = f.getAbsolutePath();
				
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				
				startActivityForResult(i, REQUEST_CODE_GET_PHOTO);
				
				break;
			case R.id.btnAddVideo:
				i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				f = new File(getMidiaDir(),System.currentTimeMillis()+".mp4");
				if(!f.exists())
				{
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				currPath = f.getAbsolutePath();
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				startActivityForResult(i, REQUEST_CODE_GET_VIDEO);
				break;
			case R.id.btnCancel:
				setResult(RESULT_CANCELED);
				finish();
				break;
			case R.id.btnSave:
				saveMedia(saveNote());
				setResult(RESULT_OK);
				finish();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_edit_note);

		db = new NoteDB(this);
		daRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();

		adaper = new MediaAdaper(this);
		setListAdapter(adaper);

		etName = (EditText) findViewById(R.id.etName);
		etContent = (EditText) findViewById(R.id.etContent);

		noteId = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
		if (noteId > -1) {
			etName.setText(getIntent().getStringExtra(EXTRA_NOTE_NAME));
			etContent.setText(getIntent().getStringExtra(EXTRA_NOTE_CONTENT));

			Cursor c = daRead.query(NoteDB.TABLE_NAME_MEDIA, null,
					NoteDB.COLUME_MEDIA_NOTE_ID + "=?", new String[] { noteId
							+ "" }, null, null, null);
			while (c.moveToNext()) {
				adaper.add(new MedialistCellData(c.getString(c
						.getColumnIndex(NoteDB.COLUME_MEDIA_PATH)), c.getInt(c
						.getColumnIndex(NoteDB.COLUME_NAME_ID))));
			}
			
			adaper.notifyDataSetChanged();
		}

		findViewById(R.id.btnSave).setOnClickListener(ClickHander);
		findViewById(R.id.btnCancel).setOnClickListener(ClickHander);
		findViewById(R.id.btnAddPhoto).setOnClickListener(ClickHander);
		findViewById(R.id.btnAddVideo).setOnClickListener(ClickHander);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_GET_PHOTO:
		case REQUEST_CODE_GET_VIDEO:
			if(resultCode == RESULT_OK)
			{
				adaper.add(new MedialistCellData(currPath));
				adaper.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		MedialistCellData data = adaper.getItem(position);
		Intent i;
		switch (data.type) {
		case MediaType.PHOTE:
			i = new Intent(this,AtyPhotoView.class);
			i.putExtra(AtyPhotoView.EXTRA_PATH, data.path);
			startActivity(i);
			break;
		case MediaType.VIDEO:
			i = new Intent(this,AtyVideoViewer.class);
			i.putExtra(AtyVideoViewer.EXTRA_PATH, data.path);
			startActivity(i);
			break;
		default:
			break;
		}
		
		
	}
	
	public File getMidiaDir()
	{
		File dir = new File(Environment.getExternalStorageDirectory(),"NoteMediass");
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		
		return dir;
	}
	
	public int saveNote()
	{
		ContentValues cv = new ContentValues();
		cv.put(NoteDB.COLUME_NAME_NOTE_NAME, etName.getText().toString());
		cv.put(NoteDB.COLUME_NAME_NOTE_CONTENT, etContent.getText().toString());
		cv.put(NoteDB.COLUME_NAME_NOTE_DATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		
		if(noteId > -1)
		{
			dbWrite.update(NoteDB.TABLE_NAME_NOTES, cv, NoteDB.COLUME_NAME_ID+"=?", new String[]{noteId+""});
			return noteId;
		}else
		{
			return (int) dbWrite.insert(NoteDB.TABLE_NAME_NOTES, null, cv);
		}
	}
	
	public void saveMedia(int noteId)
	{
		MedialistCellData data;
		ContentValues cv;
		for(int i = 0;i<adaper.getCount();i++)
		{
			data = adaper.getItem(i);
			if(data.id<=-1)
			{
				cv = new ContentValues();
				cv.put(NoteDB.COLUME_MEDIA_PATH, data.path);
				cv.put(NoteDB.COLUME_MEDIA_NOTE_ID, noteId);
				dbWrite.insert(NoteDB.TABLE_NAME_MEDIA, null, cv);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		daRead.close();
		dbWrite.close();
		super.onDestroy();
	}

	private int noteId = -1;
	private EditText etName, etContent;
	private MediaAdaper adaper;
	private NoteDB db;
	private SQLiteDatabase daRead;
	private SQLiteDatabase dbWrite;
	private String currPath = null;
	
	public static final int REQUEST_CODE_GET_PHOTO = 1;
	public static final int REQUEST_CODE_GET_VIDEO = 2;
	
	public static final String EXTRA_NOTE_ID = "note_id";
	public static final String EXTRA_NOTE_NAME = "note_name";
	public static final String EXTRA_NOTE_CONTENT = "note_content";

	static class MediaAdaper extends BaseAdapter {
		public MediaAdaper(Context context) {
			this.context = context;
		}
		
		public void add(MedialistCellData data) {
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
			
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.media_list_cell,null);
			}

			MedialistCellData data = getItem(position);
			ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			TextView tvPath = (TextView) convertView.findViewById(R.id.tvPath);

			ivIcon.setImageResource(data.iconId);
			tvPath.setText(data.path);
			return convertView;
		}

		private Context context;
		private List<MedialistCellData> list = new ArrayList<MedialistCellData>();

	}

	static class MedialistCellData {
		public MedialistCellData(String path) {
			this.path = path;
			if (this.path.endsWith(".jpg")) {
				iconId = R.drawable.icon_photo;
				type = MediaType.PHOTE;
			} else if (this.path.endsWith(".mp4")) {
				iconId = R.drawable.icon_video;
				type = MediaType.VIDEO;
			}
		}

		public MedialistCellData(String path, int id) {
			this(path);

			this.id = id;
		}
		int type = 0;
		int id = -1;
		String path = "";
		int iconId = R.drawable.ic_launcher;
	}
	
	static class MediaType
	{
		static final int PHOTE = 1;
		static final int VIDEO = 2;
	}
}
