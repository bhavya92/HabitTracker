package com.example.root.habittracker.Data;

/**
 * Created by root on 3/15/18.
 */

import android.provider.BaseColumns;

public class HabitContract {
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "Habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TASK_NAME = "Taks_To_do";
        public static final String COLUMN_TASK_TIME = "Time";
    }
}