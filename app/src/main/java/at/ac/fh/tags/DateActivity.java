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
            String currentDate = "2015-02-05";
        /*Calendar cal = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.GERMAN);
        Date currentDate = null;
        try {
            currentDate = formatter.parse(cal.getTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
            Log.i("Heute", currentDate);


            Cursor c1 = tm.getMostCurrentTasks(currentDate, "heute");
            Cursor c2 = tm.getMostCurrentTasks(currentDate, "morgen");
            Cursor c3 = tm.getMostCurrentTasks(currentDate, "nächsteWoche");

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
            ListView listView3 = (ListView) findViewById(R.id.task_list3);
            listView3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
            listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    Cursor c = (Cursor) parent.getItemAtPosition(i);
                    int id = c.getInt(0);
                    startUpdateActivity(view, id);
                }
            });

            String[] columns = {"title", "points","list","date"};
            int[] views = {R.id.task_title};

            //Adapter mappt zwischen dem Cursor (oder anderen Datenquelle) und der ListView
            SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this, R.layout.task_entry, c1, columns, views);
            listView1.setAdapter(adapter1);
            SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this, R.layout.task_entry, c2, columns, views);
            listView2.setAdapter(adapter2);
            SimpleCursorAdapter adapter3 = new SimpleCursorAdapter(this, R.layout.task_entry, c3, columns, views);
            listView3.setAdapter(adapter3);

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


