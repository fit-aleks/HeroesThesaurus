package com.fitaleks.heroesthesaurus.data

/**
 * Created by Alexander on 02.07.17.
 */
open class BaseMarvelEntity(val thumbnail: Thumbnail) {
    fun getStandardImagePath(): String = "${thumbnail.path}.${thumbnail.extension}"
}