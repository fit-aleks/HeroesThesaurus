package com.fitaleks.heroesthesaurus.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by Alexander on 04.06.17.
 */
class CharactersViewModel : ViewModel() {
    private val observableHeroes: LiveData<List<MarvelCharacter>>
    init {

    }

    fun getCharactersData(): LiveData<List<MarvelCharacter>> {
        return observableHeroes ?:
    }
}