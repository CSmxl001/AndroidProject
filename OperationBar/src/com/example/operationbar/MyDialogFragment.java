package com.example.operationbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyDialogFragment extends DialogFragment {

	private static String CURRENT_TIME = "CURRENT_TIME";
	
	public static MyDialogFragment newInstance(String currentTime)
	{
		MyDialogFragment fragment = new MyDialogFragment();
		Bundle b = new Bundle();
		b.putString(CURRENT_TIME, currentTime);
		fragment.setArguments(b);
		
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder timeDilog = new AlertDialog.Builder(getActivity());
		
		timeDilog.setTitle("title");
		timeDilog.setMessage(getArguments().getString(CURRENT_TIME));
		
		return timeDilog.create();
	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}*/
}
