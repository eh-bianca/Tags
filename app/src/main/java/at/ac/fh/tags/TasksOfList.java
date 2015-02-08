package at.ac.fh.tags;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by Bianca on 06.02.2015.
 */

public class TasksOfList extends Activity {

    private TaskManager tm;
    private Context context = this;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView tvPunkte;
    private TextView tvLevel;
    private Handler handler = new Handler();
    private int punktestand;
    private int source = 2;
    private String list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_of_list);

        tm = new TaskManager(this);

        Bundle extras = getIntent().getExtras();
        list = extras.getString("List");
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

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvPunkte = (TextView) findViewById(R.id.levelBar);
        tvLevel = (TextView) findViewById(R.id.level);

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
    }

    public void startUpdateActivity(View v, int id) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("TaskId", id);
        intent.putExtra("Source", source);
        intent.putExtra("List", list);
        startActivity(intent);
    }

    public void startActivityMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
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
        int id = item.getItemId();
        if (id == R.id.action_new) {

            //Intents wurden geändert, damit die aufgerufene Activity weiß, wovon sie aufgerufen wurde
            Intent intent = new Intent(this, ManageTaskActivity.class);
            intent.putExtra("Source", source);
            intent.putExtra("List", list);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
