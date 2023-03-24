package com.android.ipchecker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ipchecker.R;
import com.android.ipchecker.api.ip.IPApi;
import com.android.ipchecker.api.ip.response.ErrorResponse;
import com.android.ipchecker.api.ip.response.IPInfoResponse;
import com.android.ipchecker.core.App;
import com.android.ipchecker.core.ThreadExecutors;
import com.android.ipchecker.repository.AppRepository;
import com.android.ipchecker.utils.Settings;
import com.android.ipchecker.utils.ViewUtils;
import com.android.ipchecker.view.internalblur.InternalRealtimeBlurView;
import com.android.ipchecker.view.luckyview.LuckyWheelView;
import com.android.ipchecker.view.luckyview.model.LuckyItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.model.ReviewErrorCode;
import com.google.android.play.core.review.testing.FakeReviewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    List<LuckyItem> data = new ArrayList<>();
    private int coin;

    public static final int INTERVAL = 1;
    public static final int LUCKY_ITEM_SIZE = 10;
    public static int[] INDEX = new int[LUCKY_ITEM_SIZE];

    int digital1 = -1, digital2 = -1;

    static {
        for (int i = 0; i < LUCKY_ITEM_SIZE; i++) {
            INDEX[i] = i + 1;
        }
    }

    @Inject
    AppRepository appRepository;

    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable mRateUsRunnable = new Runnable() {
        @Override
        public void run() {
            Dialog dialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_Dialog);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.self().getAppComponent().inject(this);

        Log.i(TAG, "randomLetter: " + appRepository.getRandomLetter());

        ImageView imageView = findViewById(R.id.imageView11);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final LuckyWheelView luckyWheelView = findViewById(R.id.luckyWheel);
        findViewById(R.id.spinButton).setEnabled(true);
        findViewById(R.id.spinButton).setAlpha(1f);

        findViewById(R.id.resetResult).setOnClickListener(v -> {
            digital1 = -1;
            digital2 = -1;
            ((TextView) findViewById(R.id.digital1)).setText("-");
            ((TextView) findViewById(R.id.digital2)).setText("-");
        });
        buildData();

        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        findViewById(R.id.spinButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (digital2 != -1) {
                    findViewById(R.id.resetResult).performClick();
                }



                int index = getRandomIndex();
                luckyWheelView.startLuckyWheelWithTargetIndex(index);

                findViewById(R.id.spinButton).setEnabled(false);
                findViewById(R.id.spinButton).setAlpha(.5f);
            }
        });



        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                int value = (index - 1) * INTERVAL;
                if (value < 0) value = 0;

                if (digital1 == -1) {
                    digital1 = value;
                    ((TextView) findViewById(R.id.digital1)).setText(digital1 + "");
                } else {
                    digital2 = value;
                    ((TextView) findViewById(R.id.digital2)).setText(digital2 + "");
                }

                findViewById(R.id.spinButton).setEnabled(true);
                findViewById(R.id.spinButton).setAlpha(1f);


            }
        });

        String country = appRepository.getCountry().getValue();
        if (!TextUtils.isEmpty(country)) {
            ((TextView) findViewById(R.id.countryText)).setText("Your Country: " + country);
        } else {
            ((TextView) findViewById(R.id.countryText)).setText("Your Country: " + "Checking...");
        }

        appRepository.getCountry().observe(this, value -> {
            if (!TextUtils.isEmpty(value)) {
                ((TextView) findViewById(R.id.countryText)).setText("Your Country: " + value);
            } else {
                ((TextView) findViewById(R.id.countryText)).setText("Your Country: " + "Checking...");
            }
        });

/*
        IPApi.getInstance().getPublicIP(new IPApi.RequestListener<String>() {
            @Override
            public void success(String data) {
                Log.i("MainActivity", data);

                IPApi.getInstance().getIPLocationInfo(data, new IPApi.RequestListener<IPInfoResponse>() {
                    @Override
                    public void success(IPInfoResponse data) {
                        Log.i("MainActivity", "IPInfoResponse: " + data);

                        runOnUiThread(() -> {
                            ((TextView) findViewById(R.id.countryText)).setText("Your Country: " + data.getData().getGeo().getCountryName());
                        });
                    }

                    @Override
                    public void error(ErrorResponse error) {

                    }

                    @Override
                    public void failed() {

                    }
                });
            }

            @Override
            public void error(ErrorResponse error) {

            }

            @Override
            public void failed() {

            }
        });*/
        if (!Settings.getBooleanSettings(Settings.KEY_RATE_US) && Settings.getIntSettings(Settings.KEY_ENTRY_APP_COUNT, 0) >=2) {
            mHandler.postDelayed(mRateUsRunnable, 2000);
        }
    }

    private int getRandomIndex() {
       return new Random().nextInt(LUCKY_ITEM_SIZE);
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

    private void buildData() {
        data.clear();
        for (int i = 0; i < LUCKY_ITEM_SIZE; i++) {
            LuckyItem luckyItem1 = new LuckyItem();
            luckyItem1.text = String.valueOf(INTERVAL * i);
            luckyItem1.color = Color.parseColor(getColorFromIndex(i % 4));
            data.add(luckyItem1);
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRateUsRunnable);
        super.onDestroy();
    }

    public String getColorFromIndex(int index) {
        switch (index) {
            case 1:
                return "#8E84FF";
            case 2:
                return "#752BEF";
            case 3:
                return "#EBBEBDFC";
            default:
                return "#8574F1";
        }
    }

    private void rateUs() {
//        ReviewManager manager = ReviewManagerFactory.create(this);
        ReviewManager manager = new FakeReviewManager(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Log.i(TAG, "ReviewInfo success:" + reviewInfo.toString());
                manager.launchReviewFlow(MainActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(command -> {
                    Toast.makeText(MainActivity.this, "Review failed, Thank You!" + command.toString(), Toast.LENGTH_SHORT).show();
                });
            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode int reviewErrorCode = ((ReviewException) task.getException()).getErrorCode();
                Log.i(TAG, "ReviewInfo failed:" + reviewErrorCode);
            }
        });

    }
}
