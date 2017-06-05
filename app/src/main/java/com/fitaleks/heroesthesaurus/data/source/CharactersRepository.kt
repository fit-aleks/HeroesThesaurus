package com.fitaleks.heroesthesaurus.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.data.source.remote.CharactersRemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Alexander on 01.12.16.
 */
object CharactersRepository {

    fun getCharacters(): LiveData<List<MarvelCharacter>> {
        val mutableLiveData: MutableLiveData<List<MarvelCharacter>> = MutableLiveData()
        CharactersRemoteDataSource.instance.getCharacters().enqueue(object : Callback<List<MarvelCharacter>> {
            override fun onFailure(call: Call<List<MarvelCharacter>>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<List<MarvelCharacter>>?, response: Response<List<MarvelCharacter>>) {
                    mutableLiveData.value = response.body()
                }
            }

        )
        return mutableLiveData

    }

    override fun searchForCharacters(query: String): Observable<List<MarvelCharacter>> {
        return mTasksRemoteDataSource.searchForCharacters(query)
    }

    private val andCacheLocalCharacters: Observable<List<MarvelCharacter>>
        get() = mTasksLocalDataSource.getCharacters().flatMap { characters ->
            Observable.from(characters)
                    .doOnNext { character ->
                        mCachedTasks!!.put(character.marvelId, character)
                    }.toList()
        } ?: Observable.empty<List<MarvelCharacter>>()

    private val andSaveRemoteCharacters: Observable<List<MarvelCharacter>>
        get() = mTasksRemoteDataSource.getCharacters().flatMap { characters ->
            Observable.from(characters)
                    .doOnNext { character ->
                        mTasksLocalDataSource.saveCharacter(character)
                        mCachedTasks!!.put(character.marvelId, character)
                    }.toList()
        } ?: Observable.empty<List<MarvelCharacter>>()

    override fun getCharacter(characterId: String): Observable<MarvelCharacter>? {
        return null
    }

    override fun deleteAllCharacters() {
        mTasksRemoteDataSource.deleteAllCharacters()
        mTasksLocalDataSource.deleteAllCharacters()
    }

    override fun saveCharacter(character: MarvelCharacter) {
        mTasksRemoteDataSource.saveCharacter(character)
        mTasksLocalDataSource.saveCharacter(character)
    }

    override fun refreshCharacters() {
        mCacheIsDirty = true
    }

}
