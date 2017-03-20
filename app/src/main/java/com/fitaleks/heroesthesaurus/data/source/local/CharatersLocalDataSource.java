package com.fitaleks.heroesthesaurus.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.fitaleks.heroesthesaurus.data.Character;
import com.fitaleks.heroesthesaurus.data.source.CharactersDataSource;
import com.fitaleks.heroesthesaurus.util.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static com.fitaleks.heroesthesaurus.data.source.local.ThesaurusPersistenceContract.CharacterEntry;
import static com.fitaleks.heroesthesaurus.util.Utils.checkNotNull;

/**
 * Created by Alexander on 01.12.16.
 */

public class CharatersLocalDataSource implements CharactersDataSource {

    @Nullable
    private static CharatersLocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Func1<Cursor, Character> mTaskMapperFunction;

    public CharatersLocalDataSource(@NonNull Context context, @NonNull BaseSchedulerProvider schedulerProvider) {
        ThesaurusDbHelper dbHelper = new ThesaurusDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
        mTaskMapperFunction = this::getCharacter;
    }

    @NonNull
    private Character getCharacter(Cursor c) {
        final long itemId = c.getLong(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_ENTRY_ID));
        final String title = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_TITLE));
        final String description =
                c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_DESCRIPTION));
        final String imageUrl = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_IMAGE));
        return new Character(itemId, title, description, imageUrl);
    }

    public static CharatersLocalDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new CharatersLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Character>> getCharacters() {
        final String[] projection = {
                CharacterEntry.COLUMN_NAME_ENTRY_ID,
                CharacterEntry.COLUMN_NAME_TITLE,
                CharacterEntry.COLUMN_NAME_DESCRIPTION,
                CharacterEntry.COLUMN_NAME_IMAGE
        };
        final String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), CharacterEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(CharacterEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction);
    }

    @Override
    public Observable<Character> getCharacter(@NonNull String characterId) {
        final String[] projection = {
                CharacterEntry.COLUMN_NAME_ENTRY_ID,
                CharacterEntry.COLUMN_NAME_TITLE,
                CharacterEntry.COLUMN_NAME_DESCRIPTION,
                CharacterEntry.COLUMN_NAME_IMAGE
        };
        final String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?", TextUtils.join(",", projection), CharacterEntry.TABLE_NAME,
                CharacterEntry.COLUMN_NAME_ENTRY_ID);
        return mDatabaseHelper.createQuery(CharacterEntry.TABLE_NAME, sql, characterId)
                    .mapToOneOrDefault(mTaskMapperFunction, null);
    }

    @Override
    public void deleteAllCharacters() {
        mDatabaseHelper.delete(CharacterEntry.TABLE_NAME, null);
    }

    @Override
    public void saveCharacter(Character character) {
        checkNotNull(character);
        ContentValues values = new ContentValues();
        values.put(CharacterEntry.COLUMN_NAME_ENTRY_ID, character.marvelId);
        values.put(CharacterEntry.COLUMN_NAME_TITLE, character.name);
        values.put(CharacterEntry.COLUMN_NAME_DESCRIPTION, character.description);
        if (character.thumbnail != null) {
            character.imageUrl = character.thumbnail.path + "." + character.thumbnail.extension;
        }
        values.put(CharacterEntry.COLUMN_NAME_IMAGE, character.imageUrl);
        mDatabaseHelper.insert(CharacterEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void refreshCharacters() {

    }
}
