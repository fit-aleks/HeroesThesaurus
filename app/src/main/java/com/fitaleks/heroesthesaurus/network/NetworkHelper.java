package com.fitaleks.heroesthesaurus.network;

import android.util.Log;

import com.fitaleks.heroesthesaurus.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexander on 21.08.15.
 */
public class NetworkHelper {
    private static final String LOG_TAG = NetworkHelper.class.getSimpleName();
    private static final String MARVEL_ENDPOINT = "http://gateway.marvel.com/v1/public/";
    private static final String TAG = NetworkHelper.class.getSimpleName();

    public static MarvelFetchService getRestAdapter() {

        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(new MarvelTypeAdapterFactory())
                .create();

        final Interceptor requestInterceptor =
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        final HttpUrl httpUrl = request.url().newBuilder()
                                .addQueryParameter("apikey", BuildConfig.MARVEL_PUB_API_KEY)
                                .build();
                        request = request.newBuilder().url(httpUrl).build();
                        return chain.proceed(request);
                    }
                };

        final HttpLoggingInterceptor loggingInterceptor1 = new HttpLoggingInterceptor();
        loggingInterceptor1.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor1)
                .build();

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(MARVEL_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return restAdapter.create(MarvelFetchService.class);
    }

    private static class MarvelTypeAdapterFactory implements TypeAdapterFactory {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                @Override
                public T read(JsonReader in) throws IOException {
                    JsonElement jsonElement = elementAdapter.read(in);
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.has("data")) {
                            JsonObject jsonData = jsonObject.getAsJsonObject("data");
                            if (jsonData.has("results")) {
                                jsonElement = jsonData.get("results");
                            }
                        }
                    }

                    return delegate.fromJsonTree(jsonElement);
                }
            }.nullSafe();
        }
    }

    public static String getHash(final long curTime) {
        final String toHash = curTime + BuildConfig.MARVEL_SECRET_API_KEY + BuildConfig.MARVEL_PUB_API_KEY;
        String hashString;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(toHash.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            System.out.println(hexString.toString());
            hashString = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, "No such algorithm exception");
            return "";
        }
        return hashString;
    }

}
