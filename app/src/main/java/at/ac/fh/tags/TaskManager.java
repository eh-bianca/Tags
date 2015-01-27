package at.ac.fh.tags;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            db.execSQL("CREATE TABLE task (_id INTEGER PRIMARY KEY, title CHAR, priority INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }

    public void addTask (String title, int priority) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        //= Behälter mit dem man Schlüssel-Wert Paare angibt, die beim insert in Datenbank geschrieben werden
        cv.put("title", title);
        cv.put("priority", priority);

        db.insert("task", null, cv);

    }
    public Cursor getTasks() {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM task ORDER BY priority DESC", null);
        return c;
    }
    public void removeTask (int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("DELETE FROM task WHERE _id = " + id);
    }

    public void updateTask (int id, String text, int priority) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("UPDATE task SET title = '" + text + "', priority = " + priority + " WHERE _id = " + id);
    }
    public Cursor getTask(int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM task WHERE _id = " + id, null);
        return c;
    }
    public Cursor getPriorityTasks(int priority) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM task WHERE priority = " + priority + " ORDER BY title ASC", null);
        return c;
    }
}
