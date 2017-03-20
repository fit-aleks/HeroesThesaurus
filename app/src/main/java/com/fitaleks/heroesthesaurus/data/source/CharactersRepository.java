package com.fitaleks.heroesthesaurus.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.fitaleks.heroesthesaurus.data.Character;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.fitaleks.heroesthesaurus.util.Utils.checkNotNull;

/**
 * Created by Alexander on 01.12.16.
 */

public class CharactersRepository implements CharactersDataSource {
    @Nullable
    private static CharactersRepository INSTANCE = null;

    @NonNull
    private final CharactersDataSource mTasksRemoteDataSource;

    @NonNull
    private final CharactersDataSource mTasksLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    Map<Long, Character> mCachedTasks;
    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean mCacheIsDirty = false;

    private CharactersRepository(@NonNull CharactersDataSource mTasksRemoteDataSource,
                                @NonNull CharactersDataSource mTasksLocalDataSource) {
        this.mTasksRemoteDataSource = mTasksRemoteDataSource;
        this.mTasksLocalDataSource = mTasksLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param charactersRemoteDataSource the backend data source
     * @param charactersLocalDataSource the device storage data source
     * @return the {@link CharactersRepository} instance
     */
    public static CharactersRepository getInstance(@NonNull CharactersDataSource charactersRemoteDataSource,
                                                   @NonNull CharactersDataSource charactersLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CharactersRepository(charactersRemoteDataSource, charactersLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(CharactersDataSource, CharactersDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Character>> getCharacters() {
        if (mCachedTasks != null && !mCacheIsDirty) {
            return Observable.from(mCachedTasks.values()).toList();
        } else if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        Observable<List<Character>> remoteCharacters = getAndSaveRemoteCharacters();
        if (mCacheIsDirty) {
            return remoteCharacters;
        } else {
            Observable<List<Character>> localCharacters = getAndCacheLocalCharacters();
            return Observable.concat(localCharacters, remoteCharacters)
                    .first();
        }
    }

    private Observable<List<Character>> getAndCacheLocalCharacters() {
        return mTasksLocalDataSource.getCharacters()
                .flatMap(characters -> Observable.from(characters).doOnNext(character -> mCachedTasks.put(character.marvelId, character)).toList());
    }

    private Observable<List<Character>> getAndSaveRemoteCharacters() {
        return mTasksRemoteDataSource.getCharacters()
                .flatMap(characters -> Observable.from(characters)
                        .doOnNext(character -> {
                            mTasksLocalDataSource.saveCharacter(character);
                            mCachedTasks.put(character.marvelId, character);
                        }).toList());
    }

    @Override
    public Observable<Character> getCharacter(@NonNull String characterId) {
        return null;
    }

    @Override
    public void deleteAllCharacters() {
        mTasksRemoteDataSource.deleteAllCharacters();
        mTasksLocalDataSource.deleteAllCharacters();
    }

    @Override
    public void saveCharacter(Character character) {
        checkNotNull(character);

        mTasksRemoteDataSource.saveCharacter(character);
        mTasksLocalDataSource.saveCharacter(character);
    }

    @Override
    public void refreshCharacters() {
        mCacheIsDirty = true;
    }
}
