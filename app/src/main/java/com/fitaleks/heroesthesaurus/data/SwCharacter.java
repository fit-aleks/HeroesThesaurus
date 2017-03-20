package com.fitaleks.heroesthesaurus.data;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
public class SwCharacter implements Comparable<SwCharacter> {
//    @SerializedName("id")
//    public long marvelId;
    public String name;
    public String birthYear;
    public String edited;
//    public String imageUrl;

    @SerializedName("thumbnail")
    public Thumbnail thumbnail;

    /*public ContentProviderOperation saveModel() {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                CharactersProvider.Characters.CONTENT_URI);
        builder.withValue(SwCharacterColumns.NAME, name);
        builder.withValue(SwCharacterColumns.BIRTH_YEAR, birthYear);
        builder.withValue(CharacterColumns.LAST_MODIFIED, edited);
        return builder.build();
    }*/

    @Override
    public int compareTo(@NonNull SwCharacter another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SwCharacter character = (SwCharacter) o;
        return name == character.name;

    }
}
