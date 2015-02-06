package at.ac.fh.tags;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class TaskManager {
    public final String DB_NAME = "TODO_DB";
    private TaskSQLHelper sqlHelper;
    public TaskManager(Context context) {
        sqlHelper = new TaskSQLHelper(context);
    }

    public class TaskSQLHelper extends SQLiteOpenHelper {

        TaskSQLHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //DATETIME - format: YYYY-MM-DD HH:MI:SS
            db.execSQL("CREATE TABLE task (_id INTEGER PRIMARY KEY, title CHAR, points INTEGER, list CHAR, date DATETIME)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }

    public void addTask (String title, int points, String list, String date) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        //= Behälter mit dem man Schlüssel-Wert Paare angibt, die beim insert in Datenbank geschrieben werden
        cv.put("title", title);
        cv.put("points", points);
        cv.put("list", list);
        cv.put("date", date);

        db.insert("task", null, cv);

    }
    public Cursor getTasksOfList(String list) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM task WHERE list = '" + list + "' ORDER BY date", null);
        return c;
    }
    public void removeTask (int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("DELETE FROM task WHERE _id = " + id);
    }

    public void updateTask (int id, String text, int points, String list, String date) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("UPDATE task SET title = '" + text + "', points = " + points + ", list = '" + list + "', date = '" + date + "' WHERE _id = " + id);
    }
    public Cursor getTask(int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM task WHERE _id = " + id, null);
        return c;
    }

    public Cursor getMostCurrentTasks(String date, String whatDate) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();


        if(whatDate == "heute"){
            //Cursor c = db.rawQuery("SELECT * FROM task where date = CONVERT(VARCHAR(12), " + date + ", 110)" , null);
            Cursor c = null;
            return c;
        }
        else if(whatDate == "morgen"){

            Cursor c = db.rawQuery("SELECT * FROM task WHERE date = date()", null);
            //Cursor c = null;
            return c;
        }
        else{
            Cursor c = db.rawQuery("SELECT * FROM task ORDER BY date ASC",null);
            //Cursor c = null;
            return c;
        }
    }
    public ArrayList<String> getLists() {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT distinct list FROM task", null);
        ArrayList<String> Listen = new ArrayList<String>();

        while(c.moveToNext()){
            Listen.add(c.getString(0));
        }
        return Listen;
    }
}
