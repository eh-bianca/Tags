package at.ac.fh.tags;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

/**
 * Created by christophguttmann on 07.02.15.
 */
public class ScheduleClient {


    private ScheduleService mBoundService;

    private Context mContext;

    private boolean mIsBound;

    public ScheduleClient(Context context) {
        mContext = context;
    }


    public void doBindService() {
        // Verbindung mit dem Service
        mContext.bindService(new Intent(mContext, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            //service object zum interagieren mit dem Service
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };


    public void setAlarmForNotification(Calendar c){
        mBoundService.setAlarm(c);

    }

   //Beenden der Verbindung
    public void doUnbindService() {
        if (mIsBound) {

            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }
}

