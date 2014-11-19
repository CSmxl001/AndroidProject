package com.example.notifity;

import android.R.color;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createNotifity();
	}
	
	private void createNotifity()
	{
		/*int icon = R.drawable.ic_launcher;
		String text = "notifity";
		long when = System.currentTimeMillis();
		
		
		Notification notifity = new Notification(icon,text,when);
		
		notifity.number++;
		notifity.defaults = Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;
		
		Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notifity.sound = ringUri;
		
		long[] vibrate = new long[]{1000,1000,1000,1000,1000};
		notifity.vibrate = vibrate;
		
		notifity.ledARGB = Color.RED;
		notifity.ledOffMS = 0;
		notifity.ledOnMS = 1;
		notifity.flags = notifity.flags|Notification.FLAG_SHOW_LIGHTS;*/
		
		Notification.Builder builder = new Notification.Builder(this);
		
		builder.setSmallIcon(R.drawable.ic_launcher)
		.setTicker("notifity")
		.setWhen(System.currentTimeMillis())
		.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
		.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
		.setVibrate(new long[]{1000,1000,1000,1000,1000})
		.setLights(Color.RED, 0, 1);
		
		Notification notifity = builder.getNotification();
		NotificationManager mangerManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mangerManager.notify(1,notifity);
	}
}
