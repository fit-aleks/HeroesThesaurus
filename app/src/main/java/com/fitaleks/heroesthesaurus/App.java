package com.fitaleks.heroesthesaurus;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by alexanderkulikovskiy on 24.08.15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
