package me.submission.app.moviecatalogapi.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.activity.MainActivity;

public class DailyReceiver extends BroadcastReceiver {

    public static final int DAILY_REQUEST_CODE = 100;
    public static final int RELEASE_REQUEST_CODE = 101;
    public static final String DAILY_RECEIVER = "notify_service";

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotificationDaily(context, context.getString(R.string.daily_notification_desc), 100);
    }

    private void showNotificationDaily(Context context, String description, int id) {
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager managerCompat = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                "100").setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.daily_notification_title))
                .setContentText(description)
                .setSound(ringtone)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        if (managerCompat != null) {
            managerCompat.notify(id, builder.build());
        }
        Log.d("tag", "dailyreceiver");
    }
}
