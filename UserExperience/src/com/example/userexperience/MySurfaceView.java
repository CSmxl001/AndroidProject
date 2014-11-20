package com.example.userexperience;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder holder;
	private MySurfaceViewThread myThread;
	private boolean hasSurface;
	
	public MySurfaceView(Context context) {
		super(context);
		init();
	}

	private void init()
	{
		holder = getHolder();
		holder.addCallback(this);
		hasSurface = false;
	}
	
	public void resume()
	{
		if(myThread == null)
		{
			myThread = new MySurfaceViewThread();
			if(hasSurface == true)
			{
				myThread.start();
			}
		}
	}
	
	public void pause()
	{
		if(myThread != null)
		{
			myThread.requestExitAndWait();
			myThread = null;
		}
	}
	
	
	 
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		hasSurface = true;
		if(myThread != null)
		{
			myThread.start();
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
		pause();
		
	}
	class MySurfaceViewThread extends Thread
	{
		private boolean done;
		MySurfaceViewThread()
		{
			super();
			done = false;
		}
		
		@Override
		public void run() {
			super.run();
			SurfaceHolder surholder = holder;
			
			while(!done)
			{
				Canvas canvas = surholder.lockCanvas();
				
				surholder.unlockCanvasAndPost(canvas);
			}
		}
		
		public void requestExitAndWait()
		{
			done = true;
			try {
				join();
			} catch (Exception e) {
				
			}
		}
		
		public void onWindowResize(int w ,int h)
		{
			
		}
	}
}
