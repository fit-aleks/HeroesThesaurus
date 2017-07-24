package com.fitaleks.heroesthesaurus.data

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
@Entity
class MtgCard(@SerializedName("id") val marvelId: String,
              val name: String,
              val text: String,
              @SerializedName("imageUrl") val imageUrl: String) : Comparable<MtgCard>, Parcelable {

    constructor(parcel: Parcel) : this(
            marvelId = parcel.readString(),
            name = parcel.readString(),
            text = parcel.readString(),
            imageUrl = parcel.readString())

    override fun compareTo(other: MtgCard): Int {
        return this.name.compareTo(other.name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other == null || other::class.java != MtgCard::class.java) {
            return false
        }

        val character = other as MtgCard
        return marvelId == character.marvelId
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(marvelId)
        parcel.writeString(name)
        parcel.writeString(text)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MtgCard> {
        override fun createFromParcel(parcel: Parcel): MtgCard {
            return MtgCard(parcel)
        }

        override fun newArray(size: Int): Array<MtgCard?> {
            return arrayOfNulls(size)
        }
    }

}