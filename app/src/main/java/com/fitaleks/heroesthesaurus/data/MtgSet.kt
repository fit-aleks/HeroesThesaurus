package com.fitaleks.heroesthesaurus.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Alexander on 09.07.17.
 */
data class MtgSet(val code: String,
                  val oldCode: String?,
                  val name: String,
                  val releaseDate: Date,
                  val block: String?,
                  val type: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            Date(parcel.readLong()),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(oldCode)
        parcel.writeString(name)
        parcel.writeLong(releaseDate.time)
        parcel.writeString(block)
        parcel.writeString(type)
    }

    fun imageName() = "set_icon_$code.png"

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MtgSet> {
        override fun createFromParcel(parcel: Parcel): MtgSet {
            return MtgSet(parcel)
        }

        override fun newArray(size: Int): Array<MtgSet?> {
            return arrayOfNulls(size)
        }
    }

}