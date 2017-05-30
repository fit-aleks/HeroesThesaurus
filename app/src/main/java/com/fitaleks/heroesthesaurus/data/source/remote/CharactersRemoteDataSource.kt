package com.fitaleks.heroesthesaurus.data.source.remote

import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.data.source.CharactersDataSource
import com.fitaleks.heroesthesaurus.network.NetworkHelper
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CharactersRemoteDataSource private constructor(): CharactersDataSource {// Prevent direct instantiation.

    companion object {
        val instance by lazy {
            CharactersRemoteDataSource()
        }
    }

    //TODO: pass correct number of already loaded characters
    override fun getCharacters(): Observable<List<MarvelCharacter>> {
        val curTime = System.currentTimeMillis()
        return NetworkHelper.getRestAdapter()
                .getCharacters("name", curTime, NetworkHelper.getHash(curTime), 0)
                .timeout(15, TimeUnit.SECONDS)
                .retry(3)
                .onErrorResumeNext(Observable.empty<List<MarvelCharacter>>())
                .subscribeOn(Schedulers.io())
    }

    override fun getCharacter(characterId: String): Observable<MarvelCharacter>? {
        return null
    }

    override fun searchForCharacters(query: String): Observable<List<MarvelCharacter>> {
        val curTime = System.currentTimeMillis()
        return NetworkHelper.getRestAdapter()
                .getCharacters("name", curTime, NetworkHelper.getHash(curTime), 0, query)
                .timeout(15, TimeUnit.SECONDS)
                .retry(3)
                .onErrorResumeNext(Observable.empty<List<MarvelCharacter>>())
                .subscribeOn(Schedulers.io())
    }

    override fun deleteAllCharacters() {

    }

    override fun saveCharacter(character: MarvelCharacter) {

    }

    override fun refreshCharacters() {

    }
}
