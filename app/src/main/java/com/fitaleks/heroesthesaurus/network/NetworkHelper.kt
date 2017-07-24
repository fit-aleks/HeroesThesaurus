package com.fitaleks.heroesthesaurus.network

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
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by alexander on 21.08.15.
 */
object NetworkHelper {
    private val LOG_TAG = NetworkHelper::class.java.simpleName
    private val COMICVINE_ENDPOINT = "http://api.comicvine.com"
    private val MTG_ENDPOINT = "https://api.magicthegathering.io/v1/"

    private val TAG_RESULTS = "results"
    private val TAG_CARDS = "cards"
    private val TAG_SETS = "sets"

    val restAdapter: MtgFetchService
        get() {
            val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .registerTypeAdapterFactory(MTGTypeAdapterFactory())
                    .setDateFormat("yyyy-MM-dd")
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .create()

            val requestInterceptor = Interceptor { chain ->
                var request = chain.request()
                val httpUrl = request.url().newBuilder()
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
                    .baseUrl(MTG_ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return restAdapter.create(MtgFetchService::class.java)
        }

    private class DateDeserializer : JsonDeserializer<Date> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Date {
            val dateString = json.asString

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return simpleDateFormat.parse(dateString)
        }
    }

    private class MTGTypeAdapterFactory : TypeAdapterFactory {
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
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
                        if (jsonObject.has(TAG_CARDS)) {
                            jsonElement = jsonObject.get(TAG_CARDS)
                        } else if (jsonObject.has(TAG_SETS)) {
                            jsonElement = jsonObject.get(TAG_SETS)
                        }
                    }

                    return delegate.fromJsonTree(jsonElement)
                }
            }.nullSafe()
        }
    }

}
