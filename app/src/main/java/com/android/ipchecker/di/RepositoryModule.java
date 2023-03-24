/*
 * Created by huongnd2 on 1/5/22 11:43 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 10/7/21 11:47 AM
 */

package com.android.ipchecker.di;

import com.android.ipchecker.repository.AppRepository;
import com.android.ipchecker.repository.AppRepositoryImp;

import dagger.Binds;
import dagger.Module;

@Module
public interface RepositoryModule {
    @Binds
    public AppRepository provideAppRepository(AppRepositoryImp appRepositoryImp);
}
