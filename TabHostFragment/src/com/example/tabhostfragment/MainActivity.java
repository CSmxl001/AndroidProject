package com.example.tabhostfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.fragment.Fragment1;
import com.example.fragment.Fragment2;
import com.example.fragment.Fragment3;
import com.example.fragment.Fragment4;
import com.example.fragment.Fragment5;

public class MainActivity extends FragmentActivity {

	private FragmentTabHost f_tabhost;
	private LayoutInflater inflater;
	
	private Class fragmentArr[] = {Fragment1.class,Fragment2.class,Fragment3.class,Fragment4.class,Fragment5.class};
	
	private int imageArr[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_more_btn,R.drawable.tab_selfinfo_btn,R.drawable.tab_square_btn};
	
	private String txtArr[] = {"首页","消息","好友","搜索","更多"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	private void initView() {
		inflater = LayoutInflater.from(this);
		
		f_tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		f_tabhost.setup(this, getSupportFragmentManager(),R.id.realtabcontent);
		
		int count = fragmentArr.length;
		for(int i = 0;i<count;i++)
		{
			TabSpec ts = f_tabhost.newTabSpec(txtArr[i])
					.setIndicator(getTabItemView(i));
			
			f_tabhost.addTab(ts,fragmentArr[i],null);
			f_tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
	}
	
	private View getTabItemView(int index)
	{
		View v =  inflater.inflate(R.layout.tab_item_view, null);
		ImageView iv = (ImageView) v.findViewById(R.id.imageview);
		iv.setImageResource(imageArr[index]);
		TextView tv = (TextView) v.findViewById(R.id.textview);
		tv.setText(txtArr[index]);
		
		return v;
	}
}
