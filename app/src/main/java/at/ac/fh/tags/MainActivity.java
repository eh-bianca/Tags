package at.ac.fh.tags;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

    private TaskManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tm = new TaskManager(this);
        Cursor c1 = tm.getPriorityTasks(1);
        Cursor c2 = tm.getPriorityTasks(2);
        Cursor c3 = tm.getPriorityTasks(3);

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

        String[] columns = {"title", "priority"};
        int[] views = {R.id.task_title};

        //Adapter mappt zwischen dem Cursor (oder anderen Datenquelle) und der ListView
        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this, R.layout.task_entry, c1, columns, views);
        listView1.setAdapter(adapter1);
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this, R.layout.task_entry, c2, columns, views);
        listView2.setAdapter(adapter2);
        SimpleCursorAdapter adapter3 = new SimpleCursorAdapter(this, R.layout.task_entry, c3, columns, views);
        listView3.setAdapter(adapter3);

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
