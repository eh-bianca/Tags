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
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
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

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bianca on 30.01.2015.
 */
public class MainActivity extends Activity {

    private TaskManager tm;
    private Context context = this;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView tvPunkte;
    private TextView tvLevel;
    private Handler handler = new Handler();
    private int punktestand;
    private int source = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tm = new TaskManager(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        tvPunkte = (TextView) findViewById(R.id.levelBar1);
        tvLevel = (TextView) findViewById(R.id.level1);

        punktestand=tm.getSum();

        int barStand=punktestand%100;
        int level = (punktestand - barStand)/100;
        tvLevel.setText("Level " + level);
        Log.i("Level: ", "" + level);
        Log.i("Punktestand: ", "" + punktestand);


        if(barStand == 0){
            tvPunkte.setText(progressStatus + "/" + progressBar.getMax());
        }
        else {
            while (progressStatus < barStand) {
                progressStatus += 1;


                progressBar.setProgress(progressStatus);

                tvPunkte.setText(progressStatus + "/" + progressBar.getMax());
            }
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageButton btn1 =(ImageButton) findViewById(R.id.btn1);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn1.getLayoutParams();
        params.width =  width/2-(width/16);
        btn1.setLayoutParams(params);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityMain(v);
            }
        });

        ImageButton btn2 =(ImageButton) findViewById(R.id.btn2);
        params = (RelativeLayout.LayoutParams) btn2.getLayoutParams();
        params.width = width/2-(width/16);
        btn2.setLayoutParams(params);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityDate(v);
            }
        });
        btn2.setBackgroundColor(Color.parseColor("#ff54d4ff"));

        params = (RelativeLayout.LayoutParams) tvLevel.getLayoutParams();
        params.width = width / 4;
        tvLevel.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
        params.width = (width / 2);
        ListView listView1 = (ListView) findViewById(R.id.task_list);

        ArrayList<String> Listen = new ArrayList<String>();

        Listen = tm.getLists();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Listen);  //Adapter mappt zwischen dem Cursor (oder anderen Datenquelle) und der ListView

        listView1.setAdapter(arrayAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String list = (String) adapterView.getItemAtPosition(i);
                startTasksOfList(view, list);
            }
        });

    }






    public void startTasksOfList(View v, String list) {
        Intent intent = new Intent(this, TasksOfList.class);
        intent.putExtra("List", list);
        startActivity(intent);
    }



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

            //Intents wurden geändert, damit die aufgerufene Activity weiß, wovon sie aufgerufen wurde
            Intent intent = new Intent(this, ManageTaskActivity.class);
            intent.putExtra("Source", 0);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startUpdateActivity(View v, int id) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("TaskId", id);
        intent.putExtra("Source", source);
        startActivity(intent);
    }



}







