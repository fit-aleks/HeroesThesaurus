package com.fitaleks.heroesthesaurus.data.source

import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by Alexander on 01.12.16.
 */
interface CharactersDataSource {
//    fun getCharacters(): Observable<List<MarvelCharacter>>
//    fun getCharacter(characterId: String): Observable<MarvelCharacter>?
//    fun searchForCharacters(query: String): Observable<List<MarvelCharacter>>

    fun deleteAllCharacters()
    fun saveCharacter(character: MarvelCharacter)
    fun refreshCharacters()
}
