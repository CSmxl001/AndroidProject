package com.example.operationbar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		setContentView(R.layout.activity_main);
		
		ActionBar action = getActionBar();
//		action.hide();
		action.setDisplayShowTitleEnabled(false);
		action.setDisplayUseLogoEnabled(false);
		action.setDisplayShowHomeEnabled(true);
		action.setHomeButtonEnabled(true);
		
		action.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
//		action.setDisplayHomeAsUpEnabled(true);
		
		action.setTitle("Tile");
		action.setSubtitle("subtitle");
		
		/*Resources r = getResources();
		Drawable  d = r.getDrawable(R.drawable.ic_launcher);
		action.setBackgroundDrawable(d);*/
		
		Tab tab = action.newTab();
		tab.setText("first tab").setIcon(R.drawable.ic_launcher).setContentDescription("TAB THE FIRST").
		setTabListener(new TabListener<MyFragment>(this,R.id.fragmentContainer, MyFragment.class));
		
		action.addTab(tab);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home:
				Intent i = new Intent(this,TestActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public static class TabListener<T extends Fragment> implements android.app.ActionBar.TabListener
	{
		private MyFragment fragment;
		private Activity activity;
		private Class<T> fragmentClass;
		private int fragmentContainer;
		
		public TabListener(Activity ac,int fragmentContainer,Class<T> fragmentClass)
		{
			this.activity = ac;
			this.fragmentContainer = fragmentContainer;
			this.fragmentClass = fragmentClass;
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Log.d("myOutPut", "------");
			if(fragment == null)
			{
				String fragmentName = fragmentClass.getName();
				fragment = (MyFragment) Fragment.instantiate(activity, fragmentName);
				ft.add(fragmentContainer,fragment,null);
				fragment.setFragmentText(tab.getText());
			}else
			{
				ft.attach(fragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if(fragment !=null)
			{
				ft.detach(fragment);
			}
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}
	}
}
