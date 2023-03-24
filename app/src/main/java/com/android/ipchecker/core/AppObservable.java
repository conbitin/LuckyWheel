/*
 * Created by huongnd2 on 9/11/21 10:07 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 8/19/21 9:05 AM
 */
package com.android.ipchecker.core;

import java.util.Observable;

public class AppObservable extends Observable {
    private static AppObservable sInstance;

    private AppObservable() {
    }

    public static AppObservable getInstance() {
        if (sInstance == null) {
            synchronized (AppObservable.class) {
                if (sInstance == null) {
                    sInstance = new AppObservable();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
