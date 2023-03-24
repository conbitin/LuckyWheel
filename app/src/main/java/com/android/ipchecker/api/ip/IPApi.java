/*
 * Created by huongnd2 on 3/23/23 12:41 AM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 12:39 AM
 */

package com.android.ipchecker.api.ip;

import android.util.Log;

import com.android.ipchecker.api.ip.response.ErrorResponse;
import com.android.ipchecker.api.ip.response.IPInfoResponse;
import com.android.ipchecker.core.ThreadExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public class IPApi {
    private static IPApi sInstance;
    private static final String HEADER_AUTHOR = "Authorization";
    private static final Map<String, String> DEFAULT_QUERY = new HashMap<>();

    private IPApi() {
    }

    private Gson parser = new GsonBuilder().setDateFormat("MMM dd, yyyy HH:mm:ss").setLenient().disableHtmlEscaping().create();

    public static IPApi getInstance() {
        if (sInstance == null) {
            synchronized (IPApi.class) {
                if (sInstance == null) {
                    sInstance = new IPApi();
                }
            }
        }
        return sInstance;
    }


    private void handleResponse(Response response, RequestListener listener) {
        if (response == null) {
            listener.failed();
            return;
        }
        if (response.isSuccessful()) {
            listener.success(response.body());
        } else {
            try {
                String errorBody = response.errorBody().string();
                Log.e("CloudApi", "handleResponse:" + errorBody);
                listener.error(parser.fromJson(errorBody, ErrorResponse.class));
            } catch (Exception e) {
                Log.e("CloudApi", "handleResponse", e);
                listener.failed();
            }
        }
    }

    public interface RequestListener<T> {
        void success(T data);

        void error(ErrorResponse error);

        void failed();
    }

    public Gson parser() {
        return parser;
    }

    public void getPublicIP(RequestListener listener) {
        ThreadExecutors.execute(ThreadExecutors.Where.NETWORK, () -> {
            GetIPRequest publicIPRequest = getWebServiceForCheckPublicIP(GetIPRequest.class);
            handleResponse(WebServiceUtils.executeApi(publicIPRequest.getPublicIP()), listener);
        });
    }

    public void getIPLocationInfo(String publicIp, RequestListener listener) {
        ThreadExecutors.execute(ThreadExecutors.Where.NETWORK, () -> {
            GetIPRequest infoIPRequest = getWebServiceForIPInfo(GetIPRequest.class);
            handleResponse(WebServiceUtils.executeApi(infoIPRequest.getIPInfo("keycdn-tools:https://" + publicIp, publicIp)), listener);
        });
    }

    private interface GetIPRequest {
        @Headers({"Content-Type: application/json"})
        @GET(Endpoint.API_GET_PUBLIC_ID_REQUEST)
        CompletableFuture<Response<String>> getPublicIP();

        @Headers({"Content-Type: application/json"})
        @GET(Endpoint.API_GET_IP_INFO_REQUEST)
        CompletableFuture<Response<IPInfoResponse>> getIPInfo(@Header("User-Agent") String userAgent, @Query("host") String ip);
    }

    private <T> T getWebServiceForCheckPublicIP(Class<T> clazz) {
        return WebServiceUtils.getWebService(clazz, Endpoint.getHostGetPublicIp());
    }

    private <T> T getWebServiceForIPInfo(Class<T> clazz) {
        return WebServiceUtils.getWebService(clazz, Endpoint.getHostGetIpInfo());
    }
}
