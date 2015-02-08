package at.ac.fh.tags;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class ScheduleService extends Service {


    public class ServiceBinder extends Binder {
        ScheduleService getService() {
            return ScheduleService.this;
        }
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ScheduleService", "Received start id " + startId + ": " + intent);


        return START_STICKY;
    }


    public ScheduleService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // service object Erhalt von Daten über den Service
         private final IBinder mBinder = new ServiceBinder();

    //Notificationauslöser
    public void setAlarm(Calendar c) {
        // setzt die Notification in der AlarmTask Klasse
        new AlarmTask(this, c).run();
    }
}



