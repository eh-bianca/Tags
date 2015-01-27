package at.ac.fh.tags;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;


public class UpdateActivity extends Activity {
    private TaskManager tm;
    private int priority = 1;
    private int id;
    private TaskManager.TaskSQLHelper sqlHelper;
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("TaskId");
        Log.i("Id =", " " + id);

        tm = new TaskManager(this);
        Cursor c = tm.getTask(id);
        EditText text = (EditText) findViewById(R.id.text);

        while (c.moveToNext()) {
            text.setText(c.getString(1));
            priority = c.getInt(2);
        }
        switch (priority) {
            case 1:
                rb = (RadioButton) findViewById(R.id.radio1);
                rb.setChecked(true);
                break;
            case 2:
                rb = (RadioButton) findViewById(R.id.radio2);
                rb.setChecked(true);
                break;
            case 3:
                rb = (RadioButton) findViewById(R.id.radio3);
                rb.setChecked(true);
                break;
        }
    }

    public void onPriorityChanged(View v) {

        RadioButton rb = (RadioButton) v;
        boolean checked = rb.isChecked();
        switch (rb.getId()) {
            case R.id.radio1:
                priority = 1;
                break;
            case R.id.radio2:
                priority = 2;
                break;
            case R.id.radio3:
                priority = 3;
                break;
        }
    }
    public void onUpdate(View v) {
        EditText text = (EditText) findViewById(R.id.text);
        tm.updateTask(id,text.getText().toString(),priority);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
