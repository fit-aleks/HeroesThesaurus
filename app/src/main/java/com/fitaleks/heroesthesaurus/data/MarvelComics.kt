package com.fitaleks.heroesthesaurus.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 02.07.17.
 */
class MarvelComics(@SerializedName("id") val marvelId : Long,
                   val title: String,
                   thumbnail: Thumbnail): BaseMarvelEntity(thumbnail = thumbnail)