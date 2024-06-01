package com.ucompensar.crudandroid;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class TodoListDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo_db";
    private static final String TABLE_TODO = "todo";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "task";

    public TodoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void addTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK, task);
        db.insert(TABLE_TODO, null, values);
        db.close();
    }

    public List<String> getAllTasks() {
        List<String> tasksList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tasksList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasksList;
    }

    public void updateTask(String task, String newTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK, newTask);
        db.update(TABLE_TODO, values, KEY_TASK + " = ?", new String[]{String.valueOf(task)});
        db.close();
    }

    public void deleteTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_TASK + " = ?", new String[]{String.valueOf(task)});
        db.close();
    }
}