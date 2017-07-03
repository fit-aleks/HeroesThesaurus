package com.fitaleks.heroesthesaurus.data

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
@Entity
class MarvelCharacter(@SerializedName("id") val marvelId: Long,
                      val name: String,
                      val description: String,
                      @SerializedName("modified") val lastModifiedDate: String,
                      thumbnail: Thumbnail) : BaseMarvelEntity(thumbnail = thumbnail), Comparable<MarvelCharacter>, Parcelable {

    constructor(parcel: Parcel) : this(
            marvelId = parcel.readLong(),
            name = parcel.readString(),
            description = parcel.readString(),
            lastModifiedDate = parcel.readString(),
            thumbnail = Thumbnail(parcel.readString(), parcel.readString()))

    override fun compareTo(other: MarvelCharacter): Int {
        return this.name.compareTo(other.name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other == null || other::class.java != MarvelCharacter::class.java) {
            return false
        }

        val character = other as MarvelCharacter
        return marvelId == character.marvelId
    }

    override fun hashCode(): Int {
        var result = marvelId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + lastModifiedDate.hashCode()
        result = 31 * result + thumbnail.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(marvelId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(lastModifiedDate)
        parcel.writeString(thumbnail.path)
        parcel.writeString(thumbnail.extension)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarvelCharacter> {
        override fun createFromParcel(parcel: Parcel): MarvelCharacter {
            return MarvelCharacter(parcel)
        }

        override fun newArray(size: Int): Array<MarvelCharacter?> {
            return arrayOfNulls(size)
        }
    }

}