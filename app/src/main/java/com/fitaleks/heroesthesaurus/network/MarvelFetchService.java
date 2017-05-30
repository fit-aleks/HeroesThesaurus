package com.fitaleks.heroesthesaurus.network;

import com.fitaleks.heroesthesaurus.data.MarvelCharacter;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by alexander on 21.08.15.
 */
public interface MarvelFetchService {

    @GET("/characters")
    Observable<List<MarvelCharacter>> getCharacters(@Query("orderBy") String orderBy,
                                                    @Query("ts") long currentTime,
                                                    @Query("hash") String hash,
                                                    @Query("offset") long offset);

    @GET("/characters")
    Observable<List<MarvelCharacter>> getCharacters(@Query("orderBy") String orderBy,
                                                    @Query("ts") long currentTime,
                                                    @Query("hash") String hash,
                                                    @Query("offset") long offset,
                                                    @Query("nameStartsWith") String charName);
}
