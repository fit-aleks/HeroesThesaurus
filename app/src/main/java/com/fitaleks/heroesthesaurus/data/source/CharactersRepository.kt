package com.fitaleks.heroesthesaurus.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.network.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alexander on 01.12.16.
 */
object CharactersRepository {

    fun getCharacters(): LiveData<List<MarvelCharacter>> {
        val mutableLiveData: MutableLiveData<List<MarvelCharacter>> = MutableLiveData()
        NetworkHelper.getRestAdapter()
                .getCharacters("name", 0)
                .enqueue(object : Callback<List<MarvelCharacter>> {
                    override fun onFailure(call: Call<List<MarvelCharacter>>?, t: Throwable?) {
                    }

                    override fun onResponse(call: Call<List<MarvelCharacter>>?, response: Response<List<MarvelCharacter>>) {
                        mutableLiveData.value = response.body()
                    }
                }
                )
        return mutableLiveData
    }

    fun searchForCharacters(query: String): LiveData<List<MarvelCharacter>> {
        val mutableLiveData: MutableLiveData<List<MarvelCharacter>> = MutableLiveData()
        NetworkHelper.getRestAdapter()
                .getCharacters("name", 0, query)
                .enqueue(object : Callback<List<MarvelCharacter>> {
                    override fun onFailure(call: Call<List<MarvelCharacter>>?, t: Throwable?) {
                    }

                    override fun onResponse(call: Call<List<MarvelCharacter>>?, response: Response<List<MarvelCharacter>>) {
                        mutableLiveData.value = response.body()
                    }
                }
                )
        return mutableLiveData
    }

}
