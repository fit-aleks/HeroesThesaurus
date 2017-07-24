package com.fitaleks.heroesthesaurus.network

import com.fitaleks.heroesthesaurus.data.MtgCard
import com.fitaleks.heroesthesaurus.data.MtgSet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alexander on 21.08.15.
 */
interface MtgFetchService {

    @GET("sets")
    fun getSets(): Call<List<MtgSet>>

    @GET("cards")
    fun getCards(@Query("set") setCode: String): Call<List<MtgCard>>

//    @GET
//    fun getMtgSet(@Url pathToCharacterDetails: String): Call<MtgCard>

//    @GET("characters/{characterId}/comics")
//    fun getComicsByCharacter(@Path("characterId") characterId: Long): Call<List<MarvelComics>>

//    @GET("characters")
//    fun getCards(@Query("orderBy") orderBy: String,
//                      @Query("offset") offset: Long,
//                      @Query("nameStartsWith") charName: String): Call<List<MtgCard>>
}
