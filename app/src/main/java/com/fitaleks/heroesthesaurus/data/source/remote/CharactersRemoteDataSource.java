package com.fitaleks.heroesthesaurus.data.source.remote;

import android.support.annotation.NonNull;

import com.fitaleks.heroesthesaurus.data.Character;
import com.fitaleks.heroesthesaurus.data.source.CharactersDataSource;
import com.fitaleks.heroesthesaurus.network.NetworkHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;

public class CharactersRemoteDataSource implements CharactersDataSource {
    private static CharactersRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private CharactersRemoteDataSource() {}

    public static CharactersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CharactersRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Character>> getCharacters() {
        final long curTime = System.currentTimeMillis();
        return NetworkHelper.getRestAdapter()
                //TODO: pass correct number of already loaded characters
                .getCharacters("name", curTime, NetworkHelper.getHash(curTime), 0)
                .timeout(15, TimeUnit.SECONDS)
                .retry(3)
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Character> getCharacter(@NonNull String characterId) {
        return null;
    }

    @Override
    public void deleteAllCharacters() {

    }

    @Override
    public void saveCharacter(Character character) {

    }

    @Override
    public void refreshCharacters() {

    }
}
