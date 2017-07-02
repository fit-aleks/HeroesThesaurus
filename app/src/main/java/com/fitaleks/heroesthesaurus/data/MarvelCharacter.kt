package com.fitaleks.heroesthesaurus.data

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
@Entity
class MarvelCharacter(@SerializedName("id") val marvelId: Long,
                      val name: String,
                      val description: String,
                      var imageUrl: String,
                      val lastModifiedDate: String = "",
                      @SerializedName("comics") val comics: ComicsOfCharacter) : Comparable<MarvelCharacter> {

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null

    override fun compareTo(other: MarvelCharacter): Int {
        return this.name.compareTo(other.name)
    }

    fun getStandardImagePath(): String = "${thumbnail?.path}.${thumbnail?.extension}"

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
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + lastModifiedDate.hashCode()
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        return result
    }

}

data class Thumbnail(val path: String, val extension: String)
data class ComicsOfCharacter(val available: Int, @SerializedName("collectionURI") val collectionUri: String, val items: List<ComicsReferenceItem>)
data class ComicsReferenceItem(@SerializedName("name") val title: String, @SerializedName("resourceURI") val resourceUri: String)