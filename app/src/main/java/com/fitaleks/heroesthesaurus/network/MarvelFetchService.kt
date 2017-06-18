package com.fitaleks.heroesthesaurus.network

import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alexander on 21.08.15.
 */
interface MarvelFetchService {

    @GET("characters")
    fun getCharacters(@Query("orderBy") orderBy: String,
                      @Query("offset") offset: Long): Call<List<MarvelCharacter>>

    @GET("characters")
    fun getCharacters(@Query("orderBy") orderBy: String,
                      @Query("offset") offset: Long,
                      @Query("nameStartsWith") charName: String): Call<List<MarvelCharacter>>
}
