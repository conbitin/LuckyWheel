/*
 * Created by huongnd2 on 3/23/23 12:41 AM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 12:39 AM
 */

package com.android.ipchecker.api.ip.request;

import androidx.annotation.NonNull;

import com.android.ipchecker.api.ip.IPApi;


public class BaseRequest {

    @NonNull
    @Override
    public String toString() {
        return IPApi.getInstance().parser().toJson(this);
    }
}
