package com.fitaleks.heroesthesaurus.data.source

import android.support.annotation.VisibleForTesting
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import rx.Observable
import java.util.*

/**
 * Created by Alexander on 01.12.16.
 */
class CharactersRepository private constructor(var mTasksRemoteDataSource: CharactersDataSource,
                                               var mTasksLocalDataSource: CharactersDataSource) : CharactersDataSource {

    companion object {
        fun instance(tasksRemoteDataSource: CharactersDataSource,
                     tasksLocalDataSource: CharactersDataSource) : CharactersRepository {
            return CharactersRepository(tasksRemoteDataSource, tasksLocalDataSource)
        }
    }

//    private var mTasksRemoteDataSource: CharactersDataSource? = null
//    private var mTasksLocalDataSource: CharactersDataSource? = null

    /**
     * Returns the single instance of this class, creating it if necessary.

     * @param charactersRemoteDataSource the backend data source
     * *
     * @param charactersLocalDataSource the device storage data source
     * *
     * @return the [CharactersRepository] instance
     */
//    fun init(charactersRemoteDataSource: CharactersDataSource, charactersLocalDataSource: CharactersDataSource) {
//        mTasksRemoteDataSource = charactersRemoteDataSource
//        mTasksLocalDataSource = charactersLocalDataSource
//    }

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    internal var mCachedTasks: MutableMap<Long, MarvelCharacter>? = null
    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    internal var mCacheIsDirty = false

    override fun getCharacters(): Observable<List<MarvelCharacter>> {
        if (mCachedTasks != null && !mCacheIsDirty) {
            return Observable.from(mCachedTasks!!.values).toList()
        } else if (mCachedTasks == null) {
            mCachedTasks = LinkedHashMap<Long, MarvelCharacter>()
        }
        val remoteCharacters = andSaveRemoteCharacters
        if (mCacheIsDirty) {
            return remoteCharacters
        } else {
            val localCharacters = andCacheLocalCharacters
            return Observable.concat(localCharacters, remoteCharacters)
                    .first()
        }
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
