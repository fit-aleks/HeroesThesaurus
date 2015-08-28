package com.fitaleks.heroesthesaurus.data;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
@Table(name = "Characters", id = BaseColumns._ID)
public class Character extends Model implements Comparable<Character> {
    @SerializedName("id")
    @Column(name = "marvel_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long marvelId;
    @Column(name = "name")
    public String name;
    @Column(name = "description")
    public String description;
    @Column(name = "modified")
    public String lastModifiedDate;
    @Column(name = "imageUrl")
    public String imageUrl;

    @SerializedName("thumbnail")
    public Thumbnail thumbnail;

    public static long getCharactersCount() {
        return new Select().from(Character.class).count();
    }

    public void saveModel() {
        if (this.thumbnail != null) {
            this.imageUrl = thumbnail.path + "." + thumbnail.extension;
        }
        this.save();
    }

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
