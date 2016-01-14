package com.fitaleks.heroesthesaurus.database;

import android.content.ContentValues;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.NotifyInsert;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by alexander on 14.01.16.
 */
@ContentProvider(authority = CharactersProvider.AUTHORITY,
        database = CharactersDatabase.class,
        packageName = "com.fitaleks.heroesthesaurus.provider")
public class CharactersProvider {
    public static final String AUTHORITY = "com.fitaleks.heroesthesaurus.database.CharactersProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String CHARACTERS = "characters";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = CharactersDatabase.CHARACTERS)
    public static class Characters {
        @ContentUri(
                path = Path.CHARACTERS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = CharacterColumns.NAME + " ASC"
        )
        public static final Uri CONTENT_URI = buildUri(Path.CHARACTERS);

        @InexactContentUri(
                name = "CHARACTER_ID",
                path = Path.CHARACTERS + "/#",
                type = "vnd.android.cursor.dir/list",
                whereColumn = CharacterColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.CHARACTERS, Long.toString(id));
        }

        @NotifyInsert(paths = Path.CHARACTERS)
        public static Uri[] onInsert(ContentValues values) {
            return new Uri[]{};
        }


    }

}
