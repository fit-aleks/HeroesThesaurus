package com.fitaleks.heroesthesaurus.data.source.remote

import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.data.source.CharactersDataSource
import com.fitaleks.heroesthesaurus.network.NetworkHelper
import retrofit2.Call

class CharactersRemoteDataSource private constructor(): CharactersDataSource {// Prevent direct instantiation.

    companion object {
        val instance by lazy {
            CharactersRemoteDataSource()
        }
    }

    //TODO: pass correct number of already loaded characters
    fun getCharacters(): Call<List<MarvelCharacter>> {
        val curTime = System.currentTimeMillis()
        return NetworkHelper.getRestAdapter()
                .getCharacters("name", curTime, NetworkHelper.getHash(curTime), 0)
    }

//    override fun getCharacter(characterId: String): Observable<MarvelCharacter>? {
//        return null
//    }

    fun searchForCharacters(query: String): Call<List<MarvelCharacter>> {
        val curTime = System.currentTimeMillis()
        return NetworkHelper.getRestAdapter()
                .getCharacters("name", curTime, NetworkHelper.getHash(curTime), 0, query)
    }

    override fun deleteAllCharacters() {

    }

    override fun saveCharacter(character: MarvelCharacter) {

    }

    override fun refreshCharacters() {

    }
}
