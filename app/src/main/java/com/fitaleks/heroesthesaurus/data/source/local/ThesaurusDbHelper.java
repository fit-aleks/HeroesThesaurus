package com.fitaleks.heroesthesaurus.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexander on 01.12.16.
 */
public class ThesaurusDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Tasks.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ThesaurusPersistenceContract.CharacterEntry.TABLE_NAME + " (" +
                    ThesaurusPersistenceContract.CharacterEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_IMAGE + TEXT_TYPE +
                    " )";

    public ThesaurusDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
