/*
 * Created by huongnd2 on 1/5/22 11:41 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 1/5/22 11:33 AM
 */

package com.android.ipchecker.repository;


import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppRepositoryImp implements AppRepository {
    public static final String TAG = "AppRepositoryImp";

    private MutableLiveData<String> ipLiveData;
    private MutableLiveData<String> countryLiveData;
    private MutableLiveData<String> countryCodeLiveData;

    @Inject
    public AppRepositoryImp() {
        ipLiveData = new MutableLiveData<>();
        ipLiveData.setValue("");
        countryLiveData = new MutableLiveData<>();
        countryLiveData.setValue("");
        countryCodeLiveData = new MutableLiveData<>();
        countryCodeLiveData.setValue("");
    }

    @Override
    public MutableLiveData<String> getCountry() {
        return countryLiveData;
    }

    @Override
    public MutableLiveData<String> getYourIP() {
        return ipLiveData;
    }

    @Override
    public MutableLiveData<String> getYourCountryCode() {
        return countryCodeLiveData;
    }

    @Override
    public void updateYourIP(String value) {
        ipLiveData.postValue(value);
    }

    @Override
    public void updateYourCountry(String value) {
        countryLiveData.postValue(value);
    }

    @Override
    public void updateYourCountryCode(String value) {
        countryCodeLiveData.postValue(value);
    }

    @Override
    public String getRandomLetter() {
        String text = "Kanchanaburi";
        return text.charAt(new Random().nextInt(text.length())) + "";
    }

}
