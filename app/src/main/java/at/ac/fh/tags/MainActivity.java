package at.ac.fh.tags;
//test

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private TaskManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tm = new TaskManager(this);

        ListView listView1 = (ListView) findViewById(R.id.task_list);

        ArrayList<String> Listen = new ArrayList<String>();

        Listen = tm.getLists();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.test_list_item, Listen);  //Adapter mappt zwischen dem Cursor (oder anderen Datenquelle) und der ListView

        listView1.setAdapter(arrayAdapter);

        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                Cursor c = (Cursor) parent.getItemAtPosition(i);
                int id = c.getInt(0);
                tm.removeTask(id);
                finish();
                startActivity(getIntent());
                return true;
            }
        });


        final Button btnlist = (Button) findViewById(R.id.btn_newlist);
        btnlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent newlist = new Intent(MainActivity.this, NewList.class);

                MainActivity.this.startActivity(newlist);
            }
        });


/* muss in seperaten Klassen gemacht werden
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        // prepare intent which is triggered if the
// notification is selected

        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification notification  = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher, "Call", pIntent)
                .addAction(R.drawable.ic_launcher, "More", pIntent)
                .addAction(R.drawable.ic_launcher, "And more", pIntent).build();

*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_new) {

            Intent intent = new Intent(this, ManageTaskActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startUpdateActivity(View v, int id) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("TaskId", id);
        startActivity(intent);
    }

}







