package com.android.ipchecker.api.ip;

/*
 * Created by huongnd2 on 9/14/21 11:11 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 9/14/21 11:11 PM
 */


public class Endpoint {


    public static final String HOST_GET_PUBLIC_IP           = "http://ifconfig.me/";
    public static final String API_GET_PUBLIC_ID_REQUEST    = "ip";


    public static final String HOST_GET_IP_INFO             = "https://tools.keycdn.com/";
    public static final String API_GET_IP_INFO_REQUEST      = "geo.json";


    public static String getHostGetPublicIp() {
        return HOST_GET_PUBLIC_IP;
    }

    public static String getHostGetIpInfo() {
        return HOST_GET_IP_INFO;
    }
}
