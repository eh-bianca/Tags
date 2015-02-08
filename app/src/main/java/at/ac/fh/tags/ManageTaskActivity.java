package at.ac.fh.tags;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bianca on 30.01.2015.
 */

public class ManageTaskActivity extends Activity {

    private TaskManager tm;
    private int points = 10;
    private int source;
    Context context = this;
    private ScheduleClient scheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        tm = new TaskManager(this);

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();


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

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_manageTask);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view);
                return false;
            }
        });

    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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


        Object month;
        Object dayOfMonth;


        if (dp.getMonth() + 1 < 10) {
            int temp = dp.getMonth() + 1;
            month = (String) "0" + temp;
        } else {
            month = (int) dp.getMonth() + 1;
        }
        if (dp.getDayOfMonth() < 10) {
            dayOfMonth = (String) "0" + dp.getDayOfMonth();
        } else {
            dayOfMonth = dp.getDayOfMonth();
        }
        String Date = dp.getYear() + "-" + month + "-" + dayOfMonth + " " + tp.getCurrentHour() + ":" + tp.getCurrentMinute() + ":00";
        String list = "";
        try {
            list = s.getSelectedItem().toString();
        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence noList = "List is missing";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, noList, duration);
            toast.show();
            list = "null";
        }
        if (list != "null") {
            tm.addTask(text.getText().toString(), points, list, Date);
            Bundle extras = getIntent().getExtras();
            source = extras.getInt("Source");
            //1 steht für DateActivity 0 für MainActivity
            if (source == 1) {
                Intent intent = new Intent(this, DateActivity.class);
                startActivity(intent);
            }
            else if(source == 2){
                String liste = extras.getString("List");
                Intent intent = new Intent(this, TasksOfList.class);
                intent.putExtra("List", liste);
                startActivity(intent);
            } else{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }

        int day = dp.getDayOfMonth();
        int monat = dp.getMonth();
        int year = dp.getYear();
        int hour=tp.getCurrentHour();
        int min=tp.getCurrentMinute();
        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        c.set(year, monat, day,hour,min);

        Log.i("", "" + c);
        scheduleClient.setAlarmForNotification(c);
      }
    }
