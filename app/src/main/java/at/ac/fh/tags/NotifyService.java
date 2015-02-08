package at.ac.fh.tags;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class NotifyService extends Service {
    public NotifyService() {
    }


    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    private static final int NOTIFICATION = 123;

    public static final String INTENT_NOTIFY = "service.INTENT_NOTIFY";

    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);


        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification();


        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private final IBinder mBinder = new ServiceBinder();

    /**
     * Erstellt die Notification und zeigt sie in der OS-Bar an.
     */
    private void showNotification() {
        // Titel der Notification
        CharSequence title = "TAGS";
        // Icon der Notification
        int icon = R.drawable.ic_launcher;
        //Textfeld der Notification
        CharSequence text = "You have todos open.";
        //Zeitpunkt der Notification
        long time = System.currentTimeMillis();

        Notification notification = new Notification(icon, text, time);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DateActivity.class), 0);


        notification.setLatestEventInfo(this, title, text, contentIntent);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Senden der Notification ans System
        mNM.notify(NOTIFICATION, notification);

        // Service wird gestoppt nach dem Versenden der Notification
        stopSelf();
    }
}



