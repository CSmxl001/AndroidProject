package com.example.media;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class SurfaceViewVideoViewActivity extends Activity implements SurfaceHolder.Callback {
	
	static final String TAG = "SurfaceViewVidemoViewActivity";
	
	private MediaPlayer mediaPlayer;
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mediaPlayer.setDisplay(holder);
			Uri uri = Uri.parse("android.resource://" +getPackageName() +"/"+ R.raw.test);
			mediaPlayer.setDataSource(SurfaceViewVideoViewActivity.this, uri);
			mediaPlayer.prepare();
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.surfaceviewmediaviewer);
		
		mediaPlayer = new MediaPlayer();
		
		final SurfaceView surface = (SurfaceView) findViewById(R.id.surfaceview);
		surface.setKeepScreenOn(true);
		SurfaceHolder holder = surface.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.setFixedSize(400, 300);
		
		findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.start();
			}
		});
		
		findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.pause();
			}
		});
		
		findViewById(R.id.buttonSkip).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.seekTo(mediaPlayer.getDuration()/2);
			}
		});
		
		MediaController controll = new MediaController(this);
		controll.setMediaPlayer(new MediaPlayerControl() {
			@Override
			public void start() {
				mediaPlayer.start();
			}
			
			@Override
			public void seekTo(int pos) {
				mediaPlayer.seekTo(pos);
			}
			
			@Override
			public void pause() {
				mediaPlayer.pause();
			}
			
			@Override
			public boolean isPlaying() {
				return mediaPlayer.isPlaying();
			}
			
			@Override
			public int getDuration() {
				// TODO Auto-generated method stub
				return mediaPlayer.getDuration();
			}
			
			@Override
			public int getCurrentPosition() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getBufferPercentage() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getAudioSessionId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean canSeekForward() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean canSeekBackward() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean canPause() {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
//		controll.setAnchorView(surface);
		controll.show();
	}
}
