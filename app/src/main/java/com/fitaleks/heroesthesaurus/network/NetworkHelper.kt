package com.fitaleks.heroesthesaurus.network

import android.util.Log
import com.fitaleks.heroesthesaurus.BuildConfig
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

/**
 * Created by alexander on 21.08.15.
 */
object NetworkHelper {
    private val LOG_TAG = NetworkHelper::class.java.simpleName
    private val MARVEL_ENDPOINT = "http://gateway.marvel.com/v1/public/"

    val restAdapter: MarvelFetchService
        get() {
            val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapterFactory(MarvelTypeAdapterFactory())
                    .create()

            val requestInterceptor = Interceptor { chain ->
                val curTime = System.currentTimeMillis()
                var request = chain.request()
                val httpUrl = request.url().newBuilder()
                        .addQueryParameter("ts", curTime.toString())
                        .addQueryParameter("hash", NetworkHelper.getHash(curTime))
                        .addQueryParameter("apikey", BuildConfig.MARVEL_PUB_API_KEY)
                        .build()
                request = request.newBuilder().url(httpUrl).build()
                chain.proceed(request)
            }

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build()

            val restAdapter = Retrofit.Builder()
                    .baseUrl(MARVEL_ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return restAdapter.create(MarvelFetchService::class.java)
        }

    private class MarvelTypeAdapterFactory : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
            val delegate = gson.getDelegateAdapter(this, type)
            val elementAdapter = gson.getAdapter(JsonElement::class.java)

            return object : TypeAdapter<T>() {
                @Throws(IOException::class)
                override fun write(out: JsonWriter, value: T) {
                    delegate.write(out, value)
                }

                @Throws(IOException::class)
                override fun read(`in`: JsonReader): T {
                    var jsonElement = elementAdapter.read(`in`)
                    if (jsonElement.isJsonObject) {
                        val jsonObject = jsonElement.asJsonObject
                        if (jsonObject.has("data")) {
                            val jsonData = jsonObject.getAsJsonObject("data")
                            if (jsonData.has("results")) {
                                jsonElement = jsonData.get("results")
                            }
                        }
                    }

                    return delegate.fromJsonTree(jsonElement)
                }
            }.nullSafe()
        }
    }

    fun getHash(curTime: Long): String {
        val toHash = curTime.toString() + BuildConfig.MARVEL_SECRET_API_KEY + BuildConfig.MARVEL_PUB_API_KEY
        val hashString: String
        try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(toHash.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            println(hexString.toString())
            hashString = hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            Log.e(LOG_TAG, "No such algorithm exception")
            return ""
        }

        return hashString
    }

}
