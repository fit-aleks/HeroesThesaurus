package com.fitaleks.heroesthesaurus.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by Alexander on 01.12.16.
 */
class ThesaurusDbHelper(context: Context) : SQLiteOpenHelper(context, ThesaurusDbHelper.DATABASE_NAME, null, ThesaurusDbHelper.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Not required as at version 1
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Not required as at version 1
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Tasks.db"
        private val TEXT_TYPE = " TEXT"
        private val INTEGER_TYPE = " INTEGER"
        private val COMMA_SEP = ","

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ThesaurusPersistenceContract.CharacterEntry.TABLE_NAME + " (" +
                        BaseColumns._ID + INTEGER_TYPE + " PRIMARY KEY," +
                        ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        ThesaurusPersistenceContract.CharacterEntry.COLUMN_NAME_IMAGE + TEXT_TYPE +
                        " )"
    }
}
