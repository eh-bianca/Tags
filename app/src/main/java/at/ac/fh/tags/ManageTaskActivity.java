package at.ac.fh.tags;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ManageTaskActivity extends Activity {

    private TaskManager tm;
    private int points = 10;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        tm = new TaskManager(this);

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        ArrayList<String> Listen = tm.getLists();

        Calendar calendar =Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Listen);
        s.setAdapter(adapter);
    }
    public void onPointsChanged(View v) {

        RadioButton rb = (RadioButton) v;

        switch (rb.getId()) {
            case R.id .radio10:
                points = 10;
                break;
            case R.id.radio30:
                points = 30;
                break;
            case R.id.radio50:
                points = 50;
                break;
            default:
                points = 10;

        }
    }
    public void newList(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("New List");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Spinner s = (Spinner) findViewById(R.id.spinner);
                ArrayList<String> Listen = tm.getLists();

                String newList = input.getText().toString();
                Listen.add(newList);

                ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, Listen);
                s.setAdapter(adapter);

                if (!newList.equals(null)) {
                    int spinnerPostion = adapter.getPosition(newList);
                    s.setSelection(spinnerPostion);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void onSave(View v) {
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        Spinner s = (Spinner) findViewById(R.id.spinner);
        EditText text = (EditText) findViewById(R.id.title);

        String Date = dp.getYear() + "-" + dp.getMonth() + "-" + dp.getDayOfMonth() + " " + tp.getCurrentHour() + ":" + tp.getCurrentMinute() + ":00";
        String List = s.getSelectedItem().toString();

        tm.addTask(text.getText().toString(),points, List, Date);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
