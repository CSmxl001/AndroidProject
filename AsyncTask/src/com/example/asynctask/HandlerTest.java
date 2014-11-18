package com.example.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HandlerTest extends Activity {
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.aty_handler);
		
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle b = msg.getData();
				String s = b.getString("value:");
				Log.d("myOutPut",s);
			}
		};
		
		MyThread m = new MyThread();
		new Thread(m).start();
	}
	
	class MyThread implements Runnable
	{
		@Override
		public void run() {
			try
			{
				Thread.sleep(3000);
				Message ms = new Message();
				Bundle b = new Bundle();
				b.putString("value:", "zixianc");
				ms.setData(b);
				HandlerTest.this.handler.sendMessage(ms);
			}catch(InterruptedException e){};
		}
		
	}
}
