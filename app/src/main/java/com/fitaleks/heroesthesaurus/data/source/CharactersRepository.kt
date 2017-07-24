package com.fitaleks.heroesthesaurus.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.fitaleks.heroesthesaurus.data.MtgCard
import com.fitaleks.heroesthesaurus.data.MtgSet
import com.fitaleks.heroesthesaurus.network.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alexander on 01.12.16.
 */
object CharactersRepository {
    private val LOG_TAG: String = CharactersRepository::class.java.simpleName
    // temporary hardcoded
//    private val maxAvailableNumOfCharacters = 1465

    fun getSets() : LiveData<List<MtgSet>> {
        val mutableLiveData:MutableLiveData<List<MtgSet>> = MutableLiveData()
        NetworkHelper.restAdapter
                .getSets()
                .enqueue(object : Callback<List<MtgSet>> {
                    override fun onResponse(call: Call<List<MtgSet>>?, response: Response<List<MtgSet>>?) {
                        mutableLiveData.value = response?.body()
                    }

                    override fun onFailure(call: Call<List<MtgSet>>?, t: Throwable?) {

                    }
                })
        return mutableLiveData
    }

//    var listOfCharacters: LiveData<List<MtgCard>>? = null
    fun getCharacters(setCode: String): LiveData<List<MtgCard>>? {
//        listOfCharacters?.let { return listOfCharacters }

        val mutableLiveData: MutableLiveData<List<MtgCard>> = MutableLiveData()
//        listOfCharacters = mutableLiveData
        NetworkHelper.restAdapter
                .getCards(setCode)
                .enqueue(object : Callback<List<MtgCard>> {
                    override fun onFailure(call: Call<List<MtgCard>>?, t: Throwable?) {
                        Log.e(LOG_TAG, "fail - ${t?.localizedMessage}", t)
//                        call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<List<MtgCard>>?, response: Response<List<MtgCard>>) {
                        mutableLiveData.value = response.body()
                    }
                }
                )
        return mutableLiveData
    }

//    fun searchForCharacters(query: String): LiveData<List<MtgCard>> {
//        val mutableLiveData: MutableLiveData<List<MtgCard>> = MutableLiveData()
//        NetworkHelper.restAdapter
//                .getCards("name", 0)
//                .enqueue(object : Callback<List<MtgCard>> {
//                    override fun onFailure(call: Call<List<MtgCard>>?, t: Throwable?) {
//                    }
//
//                    override fun onResponse(call: Call<List<MtgCard>>?, response: Response<List<MtgCard>>) {
//                        mutableLiveData.value = response.body()
//                    }
//                }
//                )
//        return mutableLiveData
//    }

//    fun getMtgSet(pathToDetailedChar: String): LiveData<MtgCard> {
//        val mutableLiveData: MutableLiveData<MtgCard> = MutableLiveData()
//        NetworkHelper.restAdapter
//                .getMtgSet(pathToDetailedChar)
//                .enqueue(object : Callback<MtgCard> {
//                    override fun onResponse(call: Call<MtgCard>?, response: Response<MtgCard>?) {
//                        response?.body()?.let { mutableLiveData.value = it }
//                    }
//
//                    override fun onFailure(call: Call<MtgCard>?, t: Throwable?) {
//                    }
//                })
//        return mutableLiveData
//    }

//    fun getComicsByCharacter(characterId: Long): LiveData<List<MarvelComics>> {
//        val mutableLiveData: MutableLiveData<List<MarvelComics>> = MutableLiveData()
//        NetworkHelper.restAdapter
//                .getComicsByCharacter(characterId)
//                .enqueue(object : Callback<List<MarvelComics>> {
//                    override fun onFailure(call: Call<List<MarvelComics>>?, t: Throwable?) {
//                        Log.d(LOG_TAG, "Retrying: ${call?.toString()}")
//                        call?.clone()?.enqueue(this)
//                    }
//
//                    override fun onResponse(call: Call<List<MarvelComics>>?, response: Response<List<MarvelComics>>) {
//                        mutableLiveData.value = response.body()
//                    }
//                })
//        return mutableLiveData
//    }

}
