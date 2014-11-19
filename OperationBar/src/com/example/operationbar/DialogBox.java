package com.example.operationbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DialogBox extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialogbox_xml);
		
//		createDialog();
//		createAlertDialog();
//		datePickerDialog();
		createTimeDialog();
	}
	
	private void createDialog()
	{
		Dialog d = new Dialog(this);
		d.setTitle("title");
		d.setContentView(R.layout.dialog_view);
		TextView tv = (TextView) d.findViewById(R.id.dialog_text_view);
		tv.setText("this is a dialog");
		d.show();
		
	}
	
	private void createAlertDialog()
	{
		Context context = this;
		String title = "it is pitch";
		String message = "aaaaaaa";
		String b_s1 ="back";
		String b_s2 ="forward";
		
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		ad.setTitle(title);
		ad.setMessage(message);
		
		ad.setPositiveButton(b_s1, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		ad.setNegativeButton(b_s2, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		ad.show();
	}
	
	private void datePickerDialog()
	{
		DatePickerDialog dlog = new DatePickerDialog(this, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				
			}
		}, 1978, 6, 9);
		
		dlog.show();
	}
	
	private void createTimeDialog()
	{
		DialogFragment d = MyDialogFragment.newInstance("1987");
		d.show(getFragmentManager(), "1");
	}
}
