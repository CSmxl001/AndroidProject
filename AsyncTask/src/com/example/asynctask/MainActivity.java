package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView tv;
	ProgressBar ps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.text);
		ps = (ProgressBar) findViewById(R.id.progressBar);
		
		
		String input = "redrum...redrumredrumredrumredrumredrumr";
		new MyAsyncTask().execute(input);
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params) {
			String result = "";
			int myProgress = 0;
			
			int inputLength = params[0].length();
			
			for(int i = 1;i<=inputLength;i++)
			{
				myProgress = i;
				result = result + params[0].charAt(inputLength-i);
				try
				{
					Thread.sleep(100);
				}catch(InterruptedException e)
				{
					
				}
				
				publishProgress(myProgress);
			}
			
			return result;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			ps.setProgress(values[0]);
			Log.d("pro:", values[0]+"");
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tv.setText(result);
		}
	}
}
