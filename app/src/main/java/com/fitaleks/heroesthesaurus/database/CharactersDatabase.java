package com.fitaleks.heroesthesaurus.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by alexander on 14.01.16.
 */
@Database(version = CharactersDatabase.VERSION,
        packageName = "com.fitaleks.heroesthesaurus.provider")
public final class CharactersDatabase {
    private CharactersDatabase(){}

    public static final int VERSION = 1;

    @Table(CharacterColumns.class)
    public static final String CHARACTERS = "characters";

}
