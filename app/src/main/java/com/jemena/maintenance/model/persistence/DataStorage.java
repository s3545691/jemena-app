package com.jemena.maintenance.model.persistence;

import android.provider.BaseColumns;

public final class DataStorage {

    private DataStorage() {}

    public static class FormEntry implements BaseColumns {
        public static final String TABLE_NAME = "Forms";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_JSON = "JSON";
    }

    public static final String SQL_CREATE_FORMS_TABLE =
            "CREATE TABLE " + FormEntry.TABLE_NAME + " (" +
                    FormEntry._ID + " INTEGER PRIMARY KEY," +
                    FormEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FormEntry.COLUMN_NAME_JSON + " TEXT)";


    public static class FilledFormEntry implements BaseColumns {
        public static final String TABLE_NAME = "filled-forms";
        public static final String COLUMN_NAME_RESPONSES = "responses";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COlUMN_NAME_DATE = "date";
    }

    public static final String SQL_CREATE_FILLED_FORMS_TABLE =
            "CREATE TABLE " + FilledFormEntry.TABLE_NAME + " (" +
                    FilledFormEntry._ID + " INTEGER PRIMARY KEY," +
                    FilledFormEntry.COLUMN_NAME_RESPONSES + " TEXT," +
                    FilledFormEntry.COLUMN_NAME_TYPE + " TEXT," +
                    " FOREIGN KEY ("+FilledFormEntry.COLUMN_NAME_TYPE+") REFERENCES "+
                    FilledFormEntry.COlUMN_NAME_DATE + " TEXT)";


    public static final String SQL_DELETE_FORMS_TABLE =
            "DROP TABLE IF EXISTS " + FormEntry.TABLE_NAME;

    public static final String SQL_DELETE_FILLED_FORMS_TABLE =
            "DROP TABLE IF EXISTS " + FilledFormEntry.TABLE_NAME;
}
