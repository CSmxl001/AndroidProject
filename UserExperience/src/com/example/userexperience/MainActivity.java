package com.example.userexperience;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
//		createAnimator();
		
	}

	private void createAnimator() {
		TextView tt = (TextView) findViewById(R.id.txt);

		Animator anim = AnimatorInflater.loadAnimator(this,
				R.animator.animatortest);
		anim.setTarget(tt);
		anim.setInterpolator(new AnticipateOvershootInterpolator());
		anim.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
//			Log.d("OUT:", "down");
			return true;
		case MotionEvent.ACTION_MOVE:
//			Log.d("OUT:", "move");
			return true;
		case MotionEvent.ACTION_UP:
//			Log.d("OUT:", "up");
			return true;
		case MotionEvent.ACTION_CANCEL:
//			Log.d("OUT:", "cancel");
			return true;
		case MotionEvent.ACTION_OUTSIDE:
//			Log.d("OUT:", "outsize");
			return true;
		default:
			return super.onTouchEvent(event);
		}
	}
	
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		float v = event.getY();
		float h = event.getX();
		
		Log.d("OUT:", v+"");
		return false;
	}
}
