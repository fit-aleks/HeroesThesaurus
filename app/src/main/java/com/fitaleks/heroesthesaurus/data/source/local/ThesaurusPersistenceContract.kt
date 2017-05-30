package com.fitaleks.heroesthesaurus.data.source.local

import android.provider.BaseColumns

/**
 * The contract used for the db to save the tasks locally.
 * Created by Alexander on 01.12.16.
 */
object ThesaurusPersistenceContract {

    /* Defines the table contents */
    abstract class CharacterEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "marvel_character"
            val COLUMN_NAME_ENTRY_ID = "marvel_id"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_DESCRIPTION = "description"
            val COLUMN_NAME_IMAGE = "image"
        }

    }
}
