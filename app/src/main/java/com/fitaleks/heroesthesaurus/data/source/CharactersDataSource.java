package com.fitaleks.heroesthesaurus.data.source;

import android.support.annotation.NonNull;

import com.fitaleks.heroesthesaurus.data.Character;

import java.util.List;

import rx.Observable;

/**
 * Created by Alexander on 01.12.16.
 */

public interface CharactersDataSource {
    Observable<List<Character>> getCharacters();
    Observable<Character> getCharacter(@NonNull String characterId);
    Observable<List<Character>> searchForCharacters(@NonNull final String query);

    void deleteAllCharacters();
    void saveCharacter(Character character);
    void refreshCharacters();

}
