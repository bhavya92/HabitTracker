package com.example.root.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.habittracker.Data.HabitContract.HabitEntry;
import com.example.root.habittracker.Data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    HabitDbHelper habitDbHelper;
    EditText taskNameEditText;
    EditText taskTimeEditText;
    TextView detailsTextView;
    Button saveButton;
    long habitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskNameEditText = findViewById(R.id.name_edit_text);
        taskTimeEditText = findViewById(R.id.time_edit_text);
        detailsTextView = findViewById(R.id.details_text_view);
        saveButton = findViewById(R.id.button);
        habitDbHelper = new HabitDbHelper(this);
        displayDataInfo();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertHabitData();
            }
        });

    }

    private void insertHabitData() {
        String taskName = taskNameEditText.getText().toString().trim();
        String taskTime = taskTimeEditText.getText().toString().trim();
        SQLiteDatabase sqLiteDatabase = habitDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HabitEntry.COLUMN_TASK_NAME, taskName);
        contentValues.put(HabitEntry.COLUMN_TASK_TIME, taskTime);

        habitId = sqLiteDatabase.insert(HabitEntry.TABLE_NAME, null, contentValues);

        if (habitId == -1) {
            Toast.makeText(this, "Error Saving Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data Saved with Id : " + habitId, Toast.LENGTH_SHORT).show();
            detailsTextView.append("\n" + habitId + ":" + taskName + ":" + taskTime);
        }
    }


    private void displayDataInfo() {
        SQLiteDatabase sqLiteDatabase = habitDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + HabitEntry.TABLE_NAME, null);

        try {

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_TASK_NAME);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_TASK_TIME);
            detailsTextView.append("\n" + HabitEntry._ID + " : " + HabitEntry.COLUMN_TASK_NAME + " : " + HabitEntry.COLUMN_TASK_TIME);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);

                String TaskName = cursor.getString(nameColumnIndex);
                String TaskTime = cursor.getString(timeColumnIndex);
                detailsTextView.append("\n" + id + " : " + TaskName + " : " + TaskTime);
            }
        } finally {
            cursor.close();
        }

    }

}
