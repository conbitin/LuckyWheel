package com.android.ipchecker.api.ip.response;

/*
 * Created by huongnd2 on 9/15/21 11:24 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 9/15/21 11:24 PM
 */

import androidx.annotation.NonNull;

import com.android.ipchecker.api.ip.IPApi;

public class BaseResponse {

    @NonNull
    @Override
    public String toString() {
        return IPApi.getInstance().parser().toJson(this);
    }

    public <T extends BaseResponse> T copy(Class<T> tClass) {
        return IPApi.getInstance().parser().fromJson(this.toString(), tClass);
    }
}
