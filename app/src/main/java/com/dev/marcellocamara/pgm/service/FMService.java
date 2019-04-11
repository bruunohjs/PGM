package com.dev.marcellocamara.pgm.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.dev.marcellocamara.pgm.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/***
    marcellocamara@id.uff.br
            2019
***/

public class FMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(getClass().getSimpleName(), "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(getClass().getSimpleName(), "Text: " + title + " Body: " + body);
            showNotification(title, body);
        }

    }

    private void showNotification(String title, String body) {

        String default_notification_channel_id = getResources().getString(R.string.default_notification_channel_id);
        Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_credit_card)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setSound(uriSound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    default_notification_channel_id,
                    "Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());

    }

    @Override
    public void onNewToken(String token) {

        Log.d(getClass().getSimpleName(), "Refreshed token: " + token);

    }

}