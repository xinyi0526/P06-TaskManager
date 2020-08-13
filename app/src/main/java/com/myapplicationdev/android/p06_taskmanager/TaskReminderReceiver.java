package com.myapplicationdev.android.p06_taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;

	@Override
	public void onReceive(Context context, Intent i) {

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new
					NotificationChannel("default", "Default Channel",
					NotificationManager.IMPORTANCE_DEFAULT);

			channel.setDescription("This is for default notification");
			notificationManager.createNotificationChannel(channel);
		}

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, notifReqCode,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.Action action = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher_round,
				"Launch Task Manager",
				pIntent).build();

		Intent intentreply = new Intent(context,
				ReplyActivity.class);
		PendingIntent pendingIntentReply = PendingIntent.getActivity
				(context, 0, intentreply,
						PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput ri = new RemoteInput.Builder("status")
				.setLabel("Status report")
				.setChoices(new String [] {"Completed"})
				.build();

		NotificationCompat.Action action2 = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"Reply",
				pendingIntentReply)
				.addRemoteInput(ri)
				.build();




		NotificationCompat.WearableExtender extender = new
				NotificationCompat.WearableExtender();
		extender.addAction(action);
		extender.addAction(action2);



		// build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
		builder.setContentTitle(name);
		builder.setContentText(desc);
		builder.setSmallIcon(R.mipmap.ic_launcher_round);
		builder.setColor(Color.GREEN);
		builder.setColorized(true);
		builder.setContentIntent(pIntent);
		builder.setAutoCancel(true);

		// Attach the action for Wear notification created above
		builder.extend(extender);


		Notification n = builder.build();

		notificationManager.notify(notifReqCode, n);
	}

}
