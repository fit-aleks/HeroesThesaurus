package com.fitaleks.heroesthesaurus.database;


import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by alexander on 14.01.16.
 */
public interface CharacterColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String ID = "_id";
    @DataType(TEXT) @Unique(onConflict = ConflictResolutionType.REPLACE) @NotNull String NAME = "name";
    @DataType(TEXT) @NotNull String DESCRIPTION = "description";
    @DataType(TEXT) String LAST_MODIFIED = "modified";
    @DataType(TEXT) String IMAGE_URL = "imageUrl";

}
