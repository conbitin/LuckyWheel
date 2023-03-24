/*
 * Created by huongnd2 on 1/5/22 11:44 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 1/5/22 11:44 AM
 */

package com.android.ipchecker.repository;


import androidx.lifecycle.MutableLiveData;

public interface AppRepository {

    MutableLiveData<String> getCountry();

    MutableLiveData<String> getYourIP();

    MutableLiveData<String> getYourCountryCode();

    void updateYourIP(String value);

    void updateYourCountry(String value);

    void updateYourCountryCode(String value);

    String getRandomLetter();
}
