package com.fitaleks.heroesthesaurus.network;

import com.fitaleks.heroesthesaurus.data.Character;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by alexander on 21.08.15.
 */
public interface MarvelFetchService {

    @GET("/characters")
    Observable<List<Character>> getCharacters(@Query("orderBy") String orderBy, @Query("ts") long currentTime, @Query("hash") String hash, @Query("offset") long offset);

}
