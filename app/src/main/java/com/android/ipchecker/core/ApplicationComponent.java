/*
 * Created by huongnd2 on 9/11/21 10:07 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 8/19/21 9:05 AM
 */

package com.android.ipchecker.core;

import com.android.ipchecker.view.MainActivity;
import com.android.ipchecker.di.RepositoryModule;
import com.android.ipchecker.view.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        RepositoryModule.class,
})
public interface ApplicationComponent {
    /*Common*/
    void inject(MainActivity activity);

    void inject(SplashActivity activity);
}
