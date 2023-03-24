/*
 * Created by huongnd2 on 3/24/23 10:28 AM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/24/23 10:28 AM
 */

package com.android.ipchecker.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;

public class ViewUtils {


    public static void setAlphaExit(View parentView, boolean repeat) {
        if (parentView == null || !parentView.isAttachedToWindow()) {
            Log.i("ViewUtils", "View was removed from window");
            return;
        }
        parentView.setAlpha(1);
        parentView.animate()
                .setDuration(500)
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        if (repeat) {
                            setAlphaEnter(parentView, repeat);
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                }).start();
    }

    public static void setAlphaEnter(View parentView, boolean repeat) {
        if (parentView == null || !parentView.isAttachedToWindow()) {
            Log.i("ViewUtils", "View was removed from window");
            return;
        }
        parentView.setAlpha(0f);
        parentView.animate()
                .setDuration(500)
                .alpha(1f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (repeat) {
                            setAlphaExit(parentView, repeat);
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                }).start();
    }
}
