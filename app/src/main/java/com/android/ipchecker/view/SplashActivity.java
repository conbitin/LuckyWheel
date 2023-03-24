/*
 * Created by huongnd2 on 3/23/23 5:17 PM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 5:16 PM
 */

package com.android.ipchecker.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.ipchecker.R;
import com.android.ipchecker.api.ip.IPApi;
import com.android.ipchecker.api.ip.response.ErrorResponse;
import com.android.ipchecker.api.ip.response.IPInfoResponse;
import com.android.ipchecker.core.App;
import com.android.ipchecker.repository.AppRepository;
import com.android.ipchecker.utils.NetworkUtils;
import com.android.ipchecker.utils.Settings;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";

    @Inject
    AppRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.self().getAppComponent().inject(this);
        setContentView(R.layout.splash_activity);
        if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "No network connected", Toast.LENGTH_LONG).show();
            findViewById(R.id.app_icon).postDelayed(() -> {
                if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                    navigateToThirdActivity();
                    finish();
                } else {
                    checkIPInfo();
                }
            }, 2000);

        } else {
            checkIPInfo();
        }
    }

    private void checkIPInfo() {
        IPApi.getInstance().getPublicIP(new IPApi.RequestListener<String>() {
            @Override
            public void success(String data) {
                Log.i(TAG, data);
                if (TextUtils.isEmpty(data)) {
                    repository.updateYourIP(data);
                }
                int entryAppCount = Settings.getIntSettings(Settings.KEY_ENTRY_APP_COUNT, 0);
                Settings.setSettings(Settings.KEY_ENTRY_APP_COUNT, entryAppCount + 1);

                IPApi.getInstance().getIPLocationInfo(data, new IPApi.RequestListener<IPInfoResponse>() {
                    @Override
                    public void success(IPInfoResponse data) {
                        Log.i(TAG, "IPInfoResponse: " + data);
                        repository.updateYourCountry(data.getData().getGeo().getCountryName());
                        repository.updateYourCountryCode(data.getData().getGeo().getCountryCode());
                        String countryCode = (data.getData().getGeo().getCountryCode() + "").toUpperCase();
                        if (!TextUtils.isEmpty(countryCode)) {
                            Settings.setSettings(Settings.KEY_COUNTRY_CODE, countryCode);
                        }

                        if (!TextUtils.isEmpty(data.getData().getGeo().getCountryName())) {
                            Settings.setSettings(Settings.KEY_COUNTRY, data.getData().getGeo().getCountryName());
                        }

                        if (TextUtils.equals("VN", countryCode) || TextUtils.equals("IN", countryCode)) {
                            runOnUiThread(() -> {
                                navigateToLuckyWheel();
                                finish();
                            });
                        } else {
                            runOnUiThread(() -> {
                                navigateToThirdActivity();
                                finish();
                            });
                        }
                    }

                    @Override
                    public void error(ErrorResponse error) {
                        runOnUiThread(() -> {
                            navigateToThirdActivity();
                            finish();
                        });
                    }

                    @Override
                    public void failed() {
                        runOnUiThread(() -> {
                            navigateToThirdActivity();
                            finish();
                        });
                    }
                });
            }


            @Override
            public void error(ErrorResponse error) {
                runOnUiThread(() -> {
                    navigateToThirdActivity();
                    finish();
                });
            }

            @Override
            public void failed() {
                runOnUiThread(() -> {
                    navigateToThirdActivity();
                    finish();
                });
            }
        });
    }

    private void navigateToLuckyWheel() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void navigateToThirdActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
