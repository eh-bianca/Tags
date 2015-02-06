package at.ac.fh.tags;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vincent McCawk on 06.02.2015.
 */


    public class DateActivity extends Activity {

        private TaskManager tm;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_date);

            tm = new TaskManager(this);
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            Log.i("heuteSWAG", currentDate.toString());

            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();

            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date nextWeek = calendar.getTime();


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String todayAsString = dateFormat.format(currentDate);
            String tomorrowAsString = dateFormat.format(tomorrow);
            String nextWeekAsString = dateFormat.format(nextWeek);




            /*
        try {
            currentDate = formatter.parse(cal.getTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/



            Cursor c1 = tm.getMostCurrentTasks(todayAsString, tomorrowAsString,nextWeekAsString, "heute");
            Cursor c2 = tm.getMostCurrentTasks(todayAsString, tomorrowAsString,nextWeekAsString, "dieseWoche");

            ListView listView1 = (ListView) findViewById(R.id.task_list1);
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
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    Cursor c = (Cursor) parent.getItemAtPosition(i);
                    int id = c.getInt(0);
                    startUpdateActivity(view, id);
                }
            });
            ListView listView2 = (ListView) findViewById(R.id.task_list2);
            listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    Cursor c = (Cursor) parent.getItemAtPosition(i);
                    int id = c.getInt(0);
                    startUpdateActivity(view, id);
                }
            });


            String[] columns = {"title", "date"};
            int[] views = {R.id.task_title, R.id.task_date};

            //Adapter mappt zwischen dem Cursor (oder anderen Datenquelle) und der ListView
            SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this, R.layout.task_entry, c1, columns, views);
            listView1.setAdapter(adapter1);
            SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this, R.layout.task_entry, c2, columns, views);
            listView2.setAdapter(adapter2);

            loadButtons();

        }


        public void loadButtons(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

            ImageButton btn1 =(ImageButton) findViewById(R.id.btn1);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn1.getLayoutParams();
            params.width = width/2;
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
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivityDate(v);
                }
            });

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


