package com.android.ipchecker.core;

import android.app.Application;

public class App extends Application {

    private static App sInstance = null;
    ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        appComponent = DaggerApplicationComponent.builder().build();
    }

    public static App self() {
        return sInstance;
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
