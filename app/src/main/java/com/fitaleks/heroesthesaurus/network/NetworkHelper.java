package com.fitaleks.heroesthesaurus.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alexander on 21.08.15.
 */
public class NetworkHelper {
    private static final String LOG_TAG = NetworkHelper.class.getSimpleName();
    private static final String MARVEL_ENDPOINT = "http://gateway.marvel.com/v1/public";
    public static final String MARVEL_PUBLIC_KEY = "your_public_key";
    public static final String MARVEL_PRIVATE_KEY = "your_private_key";

    public static MarvelFetchService getRestAdapter() {
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(new MarvelTypeAdapterFactory())
                .create();

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MARVEL_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(MarvelFetchService.class);
    }

    public static boolean isConnected(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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
        final String toHash = curTime + NetworkHelper.MARVEL_PRIVATE_KEY + NetworkHelper.MARVEL_PUBLIC_KEY;
        String hashString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(toHash.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
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
