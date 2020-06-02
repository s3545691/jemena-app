package com.jemena.maintenance.model.persistence;

import android.provider.BaseColumns;

public final class DataStorage {

    private DataStorage() {}

    public static class FormEntry implements BaseColumns {
        public static final String TABLE_NAME = "Forms";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_JSON = "JSON";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FormEntry.TABLE_NAME + " (" +
                    FormEntry._ID + " INTEGER PRIMARY KEY," +
                    FormEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FormEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FormEntry.COLUMN_NAME_JSON + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FormEntry.TABLE_NAME;
}
