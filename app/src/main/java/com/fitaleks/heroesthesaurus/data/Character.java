package com.fitaleks.heroesthesaurus.data;

import android.content.ContentProviderOperation;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
public final class Character implements Comparable<Character> {
    @SerializedName("id")
    public long marvelId;
    public String name;
    public String description;
    public String lastModifiedDate;
    public String imageUrl;

    @SerializedName("thumbnail")
    public Thumbnail thumbnail;

    public Character(long marvelId, String name, String description, String imageUrl) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.marvelId = marvelId;
        this.description = description;
    }

    /*public ContentProviderOperation saveModel() {

        if (this.thumbnail != null) {
            this.imageUrl = thumbnail.path + "." + thumbnail.extension;
        }
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                CharactersProvider.Characters.CONTENT_URI);
        builder.withValue(CharacterColumns.NAME, name);
        builder.withValue(CharacterColumns.DESCRIPTION, description);
        builder.withValue(CharacterColumns.LAST_MODIFIED, lastModifiedDate);
        builder.withValue(CharacterColumns.IMAGE_URL, imageUrl);

        return builder.build();

    }*/

    @Override
    public int compareTo(@NonNull Character another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;
        return marvelId == character.marvelId;

    }
}
