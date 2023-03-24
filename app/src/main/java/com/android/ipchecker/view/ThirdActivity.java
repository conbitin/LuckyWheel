/*
 * Created by huongnd2 on 3/23/23 7:20 PM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 5:44 PM
 */

package com.android.ipchecker.view;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.ipchecker.R;
import com.android.ipchecker.utils.Settings;
import com.android.ipchecker.utils.ViewUtils;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        findViewById(R.id.gotoTwitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.gotoGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(browserIntent);
            }
        });
        if (!Settings.getBooleanSettings(Settings.KEY_RATE_US) && Settings.getIntSettings(Settings.KEY_ENTRY_APP_COUNT, 0) >=2) {
            mHandler.postDelayed(mRateUsRunnable, 2000);
        }
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable mRateUsRunnable = new Runnable() {
        @Override
        public void run() {
            Dialog dialog = new Dialog(ThirdActivity.this, R.style.Theme_AppCompat_Dialog);
            dialog.setContentView(R.layout.rate_us_dialog);
            dialog.findViewById(R.id.close_dialog).setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.findViewById(R.id.rateUs).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Settings.setSettings(Settings.KEY_RATE_US, true);
                    Uri uri = Uri.parse("market://details?id=com.android.ipchecker");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.android.ipchecker")));
                    }
                    //rateUs();
                }
            });
            dialog.setOnShowListener(dialog1 -> {
                ViewUtils.setAlphaEnter(dialog.findViewById(R.id.thank_view), true);
            });
            dialog.show();
        }
    };

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRateUsRunnable);
        super.onDestroy();
    }
}
