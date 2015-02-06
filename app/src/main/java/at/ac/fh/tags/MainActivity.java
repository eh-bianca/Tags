package at.ac.fh.tags;
//test

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private TaskManager tm;
    private Context context = this;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private int level;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tm = new TaskManager(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.levelBar);

        level=doWork();

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {

                    level+=1;




                    handler.post(new Runnable() {
                        public void run() {

                            if(progressStatus>100){
                                level=0;
                            }
                            else{

                                level=100;

                            }

                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }

                    });
                }
            }
            }).start();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageButton btn1 =(ImageButton) findViewById(R.id.btn1);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn1.getLayoutParams();
        params.width =  width/2;
        btn1.setLayoutParams(params);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityMain(v);
            }
        });

        ImageButton btn2 =(ImageButton) findViewById(R.id.btn2);
        params = (RelativeLayout.LayoutParams) btn2.getLayoutParams();
        params.width = width/2;
        btn2.setLayoutParams(params);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityDate(v);
            }
        });

        ListView listView1 = (ListView) findViewById(R.id.task_list);

        ArrayList<String> Listen = new ArrayList<String>();

        Listen = tm.getLists();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.test_list_item, Listen);  //Adapter mappt zwischen dem Cursor (oder anderen Datenquelle) und der ListView

        listView1.setAdapter(arrayAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String list = (String) adapterView.getItemAtPosition(i);
                startTasksOfList(view, list);
            }
        });

    }

    private int doWork()
    {
        int level=tm.getSum();

       return level;



    }



        /*final Button btnlist = (Button) findViewById(R.id.btn_newlist);
        btnlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //   Intent newlist = new Intent(MainActivity.this, NewList.class);

                //  MainActivity.this.startActivity(newlist);
            }
        });  */

//test
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

    public void startTasksOfList(View v, String list) {
        Intent intent = new Intent(this, TasksOfList.class);
        intent.putExtra("List", list);
        startActivity(intent);
    }

   /* public void loadButtons(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Button btn1 =(Button) findViewById(R.id.btn1);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn1.getLayoutParams();
        params.width = width/3;
        btn1.setLayoutParams(params);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityMain(v);
            }
        });

        Button btn2 =(Button) findViewById(R.id.btn2);
        params = (RelativeLayout.LayoutParams) btn2.getLayoutParams();
        params.width = width/3;
        btn2.setLayoutParams(params);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityDate(v);
            }
        });

        Button btn3 =(Button) findViewById(R.id.btn3);
        params = (RelativeLayout.LayoutParams) btn3.getLayoutParams();
        params.width = width/3;
        btn3.setLayoutParams(params);

    } */

    public void startActivityMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startActivityDate(View v){
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
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







