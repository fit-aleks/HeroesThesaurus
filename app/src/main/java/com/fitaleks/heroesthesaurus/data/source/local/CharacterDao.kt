package com.fitaleks.heroesthesaurus.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by Alexander on 04.06.17.
 */
@Dao
interface CharacterDao {
    @Query("select * from marvel_character")
    fun loadCharacters(): LiveData<List<MarvelCharacter>>
    @Query("delete from marvel_character")
    fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMarverlCharacter(marvelCharacter: MarvelCharacter)
}