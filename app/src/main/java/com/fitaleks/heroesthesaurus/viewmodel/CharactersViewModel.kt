package com.fitaleks.heroesthesaurus.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository

/**
 * Created by Alexander on 04.06.17.
 */
class CharactersViewModel : ViewModel() {
//    private val observableHeroes: LiveData<List<MarvelCharacter>>

    fun getCharactersData(): LiveData<List<MarvelCharacter>> = CharactersRepository.getCharacters()
    fun searchForCharacters(query: String): LiveData<List<MarvelCharacter>> = CharactersRepository.searchForCharacters(query)
}