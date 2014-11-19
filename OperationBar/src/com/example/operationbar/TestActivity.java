package com.example.operationbar;

import java.util.ArrayList;

import android.R.menu;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.app.ActionBar.OnNavigationListener;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.aty_textactivity);
		
		ActionBar action = getActionBar();
		action.setHomeButtonEnabled(true);
		action.setDisplayUseLogoEnabled(false);
		action.setDisplayHomeAsUpEnabled(true);
		
		action.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		action.setDisplayShowCustomEnabled(true);
		action.setCustomView(R.layout.my_action_view);
		
		ArrayList<CharSequence> al = new ArrayList<CharSequence>();
		al.add("item 1");
		al.add("item 2");
		
		ArrayAdapter<CharSequence> adaper = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,al);
		
		ArrayAdapter dropdownAdaper = ArrayAdapter.createFromResource(this,R.array.my_dropdown_values, android.R.layout.simple_list_item_1);
		
		action.setListNavigationCallbacks(dropdownAdaper, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i = new Intent(this,MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	static final private int MENU_ITEM = Menu.FIRST;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		int groupId = 0;
		int itemId = MENU_ITEM;
		int itemOrder = Menu.NONE;
		int itemText = R.string.munu_item;
		
		MenuItem menuitem = menu.add(groupId,itemId,itemOrder,itemText);
		
		menuitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return super.onCreateOptionsMenu(menu);
	}
}
