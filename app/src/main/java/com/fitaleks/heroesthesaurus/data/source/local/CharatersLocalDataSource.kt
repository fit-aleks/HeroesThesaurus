package com.fitaleks.heroesthesaurus.data.source.local

import android.content.Context

/**
 * Created by Alexander on 01.12.16.
 */
class CharatersLocalDataSource(context: Context)  {

//    private val mDatabaseHelper: BriteDatabase
//
//    private val mTaskMapperFunction: Func1<Cursor, MarvelCharacter>
//
//    init {
//        val dbHelper = ThesaurusDbHelper(context)
//        val sqlBrite = SqlBrite.create()
//        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io())
//        mTaskMapperFunction = Func1<Cursor, MarvelCharacter> { this.getCharacter(it) }
//    }
/*
    private fun getCharacter(c: Cursor): MarvelCharacter {
        val itemId = c.getLong(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_ENTRY_ID))
        val title = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_TITLE))
        val description = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_DESCRIPTION))
        val imageUrl = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_IMAGE))
        return MarvelCharacter(itemId, title, description, imageUrl)
    }

    override fun getCharacters(): Observable<List<MarvelCharacter>> {
        val projection = arrayOf(CharacterEntry.COLUMN_NAME_ENTRY_ID, CharacterEntry.COLUMN_NAME_TITLE, CharacterEntry.COLUMN_NAME_DESCRIPTION, CharacterEntry.COLUMN_NAME_IMAGE)
        val sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), CharacterEntry.TABLE_NAME)
        return mDatabaseHelper.createQuery(CharacterEntry.TABLE_NAME, sql)
                .mapToList(mTaskMapperFunction)
    }

    override fun getCharacter(characterId: String): Observable<MarvelCharacter> {
        val projection = arrayOf(CharacterEntry.COLUMN_NAME_ENTRY_ID, CharacterEntry.COLUMN_NAME_TITLE, CharacterEntry.COLUMN_NAME_DESCRIPTION, CharacterEntry.COLUMN_NAME_IMAGE)
        val sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?", TextUtils.join(",", projection), CharacterEntry.TABLE_NAME,
                CharacterEntry.COLUMN_NAME_ENTRY_ID)
        return mDatabaseHelper.createQuery(CharacterEntry.TABLE_NAME, sql, characterId)
                .mapToOneOrDefault(mTaskMapperFunction, null)
    }

    override fun deleteAllCharacters() {
        mDatabaseHelper.delete(CharacterEntry.TABLE_NAME, null)
    }

    override fun saveCharacter(character: MarvelCharacter) {
        checkNotNull(character)
        val values = ContentValues()
        values.put(CharacterEntry.COLUMN_NAME_ENTRY_ID, character.marvelId)
        values.put(CharacterEntry.COLUMN_NAME_TITLE, character.name)
        values.put(CharacterEntry.COLUMN_NAME_DESCRIPTION, character.description)
        if (character.thumbnail != null) {
            character.imageUrl = character.thumbnail!!.path + "." + character.thumbnail!!.extension
        }
        values.put(CharacterEntry.COLUMN_NAME_IMAGE, character.imageUrl)
        mDatabaseHelper.insert(CharacterEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    override fun refreshCharacters() {

    }

    override fun searchForCharacters(query: String): Observable<List<MarvelCharacter>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    //    companion object {
//
//        private var INSTANCE: CharatersLocalDataSource? = null
//
//        fun getInstance(
//                context: Context,
//                schedulerProvider: BaseSchedulerProvider): CharatersLocalDataSource {
//            if (INSTANCE == null) {
//                INSTANCE = CharatersLocalDataSource(context, schedulerProvider)
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
}
