package pe.kmh.popupmemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PopupService extends Service {

    protected boolean mRunning;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(PopupService.this);
        builder.setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(getString(R.string.app_name)).setOngoing(true)
            .setOnlyAlertOnce(true).setWhen(0)
            .setContentText(getString(R.string.notification_mes))
            .setAutoCancel(false).setOnlyAlertOnce(true);

        Intent i = new Intent(getApplicationContext(), PopupScreen.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pIntent);
        if (android.os.Build.VERSION.SDK_INT < 16) manager.notify(1, builder.getNotification());
        else manager.notify(1, builder.build());
        return START_STICKY;
    }
}
