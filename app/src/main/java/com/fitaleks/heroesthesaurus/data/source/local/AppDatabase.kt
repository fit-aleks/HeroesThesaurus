package com.fitaleks.heroesthesaurus.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by Alexander on 04.06.17.
 */
@Database(entities = arrayOf(MarvelCharacter::class), version = 1) abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharacterDao
}