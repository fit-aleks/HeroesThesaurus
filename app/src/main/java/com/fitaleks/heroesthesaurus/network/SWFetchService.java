package com.fitaleks.heroesthesaurus.network;

import com.fitaleks.heroesthesaurus.data.Character;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Alexander on 01.12.16.
 */
public interface SWFetchService {

    @GET("/people")
    Observable<List<Character>> getCharacters(@Query("orderBy") String orderBy, @Query("ts") long currentTime, @Query("hash") String hash, @Query("offset") long offset);
}
