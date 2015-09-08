package androidhive.info.materialdesign.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import androidhive.info.materialdesign.R;

/**
 * Created by baratheraja on 5/9/15.
 */
public class Receiver extends BroadcastReceiver {

    private static int nid =1;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
       // Toast.makeText(context, intent.getStringExtra("param"), Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(intent.getStringExtra("title"))
                        .setContentText(intent.getStringExtra("detail"))
                        .setDefaults(Notification.DEFAULT_SOUND);
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(nid, mBuilder.build());

    }
}
