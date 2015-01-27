package at.ac.fh.tags;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class ManageTaskActivity extends Activity {

    private TaskManager tm;
    private int priority = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        tm = new TaskManager(this);

    }
    public void onPriorityChanged(View v) {

        RadioButton rb = (RadioButton) v;
        boolean checked = rb.isChecked(); // rb.isChecked() = true wenn RadioButton ausgewählt ist

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

            default:
                priority = 1;
        }
    }
    public void onSave(View v) {
        EditText text = (EditText) findViewById(R.id.title);
        tm.addTask(text.getText().toString(),priority);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
