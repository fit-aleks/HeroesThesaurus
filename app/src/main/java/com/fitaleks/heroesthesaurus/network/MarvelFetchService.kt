package com.fitaleks.heroesthesaurus.network

import com.fitaleks.heroesthesaurus.data.MarvelCharacter

import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

/**
 * Created by alexander on 21.08.15.
 */
interface MarvelFetchService {

    @GET("/characters")
    fun getCharacters(@Query("orderBy") orderBy: String,
                      @Query("ts") currentTime: Long,
                      @Query("hash") hash: String,
                      @Query("offset") offset: Long): Observable<List<MarvelCharacter>>

    @GET("/characters")
    fun getCharacters(@Query("orderBy") orderBy: String,
                      @Query("ts") currentTime: Long,
                      @Query("hash") hash: String,
                      @Query("offset") offset: Long,
                      @Query("nameStartsWith") charName: String): Observable<List<MarvelCharacter>>
}
