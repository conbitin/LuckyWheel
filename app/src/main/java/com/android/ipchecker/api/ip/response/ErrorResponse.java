/*
 * Created by huongnd2 on 3/23/23 12:47 AM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 12:39 AM
 */

package com.android.ipchecker.api.ip.response;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse extends BaseResponse {
    @SerializedName("code")
    public int code;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.status = code;
        this.message = message;
    }

}
