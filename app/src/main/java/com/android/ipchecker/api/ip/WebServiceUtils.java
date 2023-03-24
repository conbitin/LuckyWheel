package com.android.ipchecker.api.ip;

/*
 * Created by huongnd2 on 9/15/21 12:08 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 9/13/21 4:26 PM
 */

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceUtils {

    public static boolean IS_DEBUG = true;

    public static <T> T getWebService(Class<T> clazz, @NonNull String domain) {
        OkHttpClient okHttpClient = createOkHttpClient(null);
        return createWebService(clazz, okHttpClient, domain);
    }

    public static <T> T executeApi(CompletableFuture<T> call) {
        try {
            return call.get();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T executeApiIncludeEx(CompletableFuture<T> call) throws Exception {
        return call.get();
    }

    private static <T> T createWebService(Class<T> clazz, OkHttpClient okHttpClient, String domain) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(domain)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(IPApi.getInstance().parser()))
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    private static OkHttpClient createOkHttpClient(@Nullable Authenticator authenticator) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (authenticator != null) {
            clientBuilder.authenticator(authenticator);
        }
        addSSLSocketFactory(clientBuilder);
        addDevInterceptor(clientBuilder, IS_DEBUG);
        setConnectionSpecs(clientBuilder)
                .connectTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(20L, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true);
        return clientBuilder.build();
    }

    private static OkHttpClient.Builder addSSLSocketFactory(OkHttpClient.Builder builder) {
        TrustManager trustAllCert = new X509TrustManager() {
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = trustAllCert;
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException | NullPointerException e) {
            e.printStackTrace();
        }

        builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        return builder;
    }

    private static OkHttpClient.Builder addDevInterceptor(OkHttpClient.Builder builder, boolean isDebug) {
        if (isDebug) {
            HttpLoggingInterceptor httpLogInterceptor = new HttpLoggingInterceptor();
            httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLogInterceptor);
        }
        return builder;
    }

    private static OkHttpClient.Builder setConnectionSpecs(OkHttpClient.Builder builder) {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .allEnabledTlsVersions()
                .allEnabledCipherSuites()
                .build();
        builder.connectionSpecs(Arrays.asList(spec, ConnectionSpec.CLEARTEXT));
        return builder;
    }

}
