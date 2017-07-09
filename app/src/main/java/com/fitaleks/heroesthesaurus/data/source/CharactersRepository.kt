package com.fitaleks.heroesthesaurus.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.data.MarvelComics
import com.fitaleks.heroesthesaurus.network.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Alexander on 01.12.16.
 */
object CharactersRepository {
    private val LOG_TAG: String = CharactersRepository::class.java.simpleName
    // temporary hardcoded
    private val maxAvailableNumOfCharacters = 1465

    var listOfCharacters: LiveData<List<MarvelCharacter>>? = null
    fun getCharacters(): LiveData<List<MarvelCharacter>>? {
        listOfCharacters?.let { return listOfCharacters }

        val mutableLiveData: MutableLiveData<List<MarvelCharacter>> = MutableLiveData()
        listOfCharacters = mutableLiveData
        NetworkHelper.restAdapter
                .getCharacters("name", Random().nextInt(maxAvailableNumOfCharacters).toLong())
                .enqueue(object : Callback<List<MarvelCharacter>> {
                    override fun onFailure(call: Call<List<MarvelCharacter>>?, t: Throwable?) {
                        call?.clone()?.enqueue(this)
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
        NetworkHelper.restAdapter
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

    fun getCharacter(characterId: Long): LiveData<MarvelCharacter> {
        val mutableLiveData: MutableLiveData<MarvelCharacter> = MutableLiveData()
        NetworkHelper.restAdapter
                .getCharacter(characterId)
                .enqueue(object : Callback<List<MarvelCharacter>> {
                    override fun onResponse(call: Call<List<MarvelCharacter>>?, response: Response<List<MarvelCharacter>>?) {
                        response?.body()?.let { mutableLiveData.value = it[0] }
                    }

                    override fun onFailure(call: Call<List<MarvelCharacter>>?, t: Throwable?) {
                    }
                })
        return mutableLiveData
    }

    fun getComicsByCharacter(characterId: Long): LiveData<List<MarvelComics>> {
        val mutableLiveData: MutableLiveData<List<MarvelComics>> = MutableLiveData()
        NetworkHelper.restAdapter
                .getComicsByCharacter(characterId)
                .enqueue(object : Callback<List<MarvelComics>> {
                    override fun onFailure(call: Call<List<MarvelComics>>?, t: Throwable?) {
                        Log.d(LOG_TAG, "Retrying: ${call?.toString()}")
                        call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<List<MarvelComics>>?, response: Response<List<MarvelComics>>) {
                        mutableLiveData.value = response.body()
                    }
                })
        return mutableLiveData
    }

}
