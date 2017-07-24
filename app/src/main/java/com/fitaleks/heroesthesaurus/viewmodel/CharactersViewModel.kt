package com.fitaleks.heroesthesaurus.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fitaleks.heroesthesaurus.data.MtgCard
import com.fitaleks.heroesthesaurus.data.MtgSet
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository

/**
 * Created by Alexander on 04.06.17.
 */
class CharactersViewModel : ViewModel() {
    fun getSets(): LiveData<List<MtgSet>> = CharactersRepository.getSets()
    fun getCharactersData(setCode: String): LiveData<List<MtgCard>>? = CharactersRepository.getCharacters(setCode)
//    fun searchForCharacters(query: String): LiveData<List<MtgCard>> = CharactersRepository.searchForCharacters(query)
//    fun getMtgSet(characterId: Long): LiveData<MtgCard> = CharactersRepository.getMtgSet(characterId)
//    fun getComicsByCharacter(characterId: Long): LiveData<List<MarvelComics>> = CharactersRepository.getComicsByCharacter(characterId)
}