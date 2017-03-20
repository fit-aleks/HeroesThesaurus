package com.fitaleks.heroesthesaurus.data.source.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 * Created by Alexander on 01.12.16.
 */
public final class ThesaurusPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ThesaurusPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CharacterEntry implements BaseColumns {
        public static final String TABLE_NAME = "marvel_character";
        public static final String COLUMN_NAME_ENTRY_ID = "marvel_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}
