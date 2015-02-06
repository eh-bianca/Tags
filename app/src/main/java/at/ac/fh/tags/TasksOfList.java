package at.ac.fh.tags;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class TasksOfList extends Activity {

    private TaskManager tm;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_of_list);

        tm = new TaskManager(this);

        Bundle extras = getIntent().getExtras();
        String list = extras.getString("List");
        Log.i("List", list);

        final Cursor c = tm.getTasksOfList(list);

        TextView text = (TextView) findViewById(R.id.listtitle);
        text.setText(list);

        ListView tasks = (ListView) findViewById(R.id.task_view);

        String[] columns = {"title", "points"};
        int[] views = {R.id.task_title, R.id.task_points};

        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this, R.layout.task_entry, c, columns, views);
        tasks.setAdapter(adapter1);
        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Cursor c = (Cursor) parent.getItemAtPosition(i);
                int id = c.getInt(0);
                startUpdateActivity(view, id);
            }
        });
        tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Did you complete the task?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int points = cursor.getInt(2);
                        Log.i("punkte vorher", "" + tm.getSum());
                        tm.updateSum(points + tm.getSum());
                        Log.i("punkte nachher", "" + tm.getSum());
                        tm.removeTask(cursor.getInt(0));
                        finish();
                        startActivity(getIntent());
                    }
                });

                alert.setNegativeButton("No, but delete task", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        tm.removeTask(cursor.getInt(0));
                        finish();
                        startActivity(getIntent());
                    }
                });
                alert.show();
            return true;
            }
        });
    }
    public void startUpdateActivity(View v, int id) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("TaskId", id);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tasks_of_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
