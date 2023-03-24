/*
 * Created by huongnd2 on 3/23/23 12:54 AM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 3/23/23 12:48 AM
 */

package com.android.ipchecker.api.ip.response;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class IPInfoResponse extends BaseResponse {
    private String status;
    private String description;
    private Data data;

    @SerializedName("status")
    public String getStatus() {
        return status;
    }

    @SerializedName("status")
    public void setStatus(String value) {
        this.status = value;
    }

    @SerializedName("description")
    public String getDescription() {
        return description;
    }

    @SerializedName("description")
    public void setDescription(String value) {
        this.description = value;
    }

    @SerializedName("data")
    public Data getData() {
        return data;
    }

    @SerializedName("data")
    public void setData(Data value) {
        this.data = value;
    }

    public static class Data extends BaseResponse {
        private Geo geo;

        @SerializedName("geo")
        public Geo getGeo() {
            return geo;
        }

        @SerializedName("geo")
        public void setGeo(Geo value) {
            this.geo = value;
        }
    }


    public static class Geo extends BaseResponse {
        private String host;
        private String ip;
        private String rdns;
        private long asn;
        private String isp;
        private String country_name;
        private String country_code;
        private String region_name;
        private String region_code;
        private String city;
        private JSONObject postal_code;
        private String continent_name;
        private String continent_code;
        private double latitude;
        private double longitude;
        private JSONObject metro_code;
        private String timezone;
        private String datetime;

        @SerializedName("host")
        public String getHost() {
            return host;
        }

        @SerializedName("host")
        public void setHost(String value) {
            this.host = value;
        }

        @SerializedName("ip")
        public String getIP() {
            return ip;
        }

        @SerializedName("ip")
        public void setIP(String value) {
            this.ip = value;
        }

        @SerializedName("rdns")
        public String getRdns() {
            return rdns;
        }

        @SerializedName("rdns")
        public void setRdns(String value) {
            this.rdns = value;
        }

        @SerializedName("asn")
        public long getAsn() {
            return asn;
        }

        @SerializedName("asn")
        public void setAsn(long value) {
            this.asn = value;
        }

        @SerializedName("isp")
        public String getISP() {
            return isp;
        }

        @SerializedName("isp")
        public void setISP(String value) {
            this.isp = value;
        }

        @SerializedName("country_name")
        public String getCountryName() {
            return country_name;
        }


        public void setCountryName(String value) {
            this.country_name = value;
        }


        public String getCountryCode() {
            return country_code;
        }


        public void setCountryCode(String value) {
            this.country_code = value;
        }

        public String getRegionName() {
            return region_name;
        }

        public void setRegionName(String value) {
            this.region_name = value;
        }

        @SerializedName("region_code")
        public String getRegionCode() {
            return region_code;
        }

        @SerializedName("region_code")
        public void setRegionCode(String value) {
            this.region_code = value;
        }

        @SerializedName("city")
        public String getCity() {
            return city;
        }

        @SerializedName("city")
        public void setCity(String value) {
            this.city = value;
        }

        public JSONObject getPostalCode() {
            return postal_code;
        }

        public void setPostalCode(JSONObject value) {
            this.postal_code = value;
        }

        @SerializedName("continent_name")
        public String getContinentName() {
            return continent_name;
        }

        @SerializedName("continent_name")
        public void setContinentName(String value) {
            this.continent_name = value;
        }

        @SerializedName("continent_code")
        public String getContinentCode() {
            return continent_code;
        }

        @SerializedName("continent_code")
        public void setContinentCode(String value) {
            this.continent_code = value;
        }

        @SerializedName("latitude")
        public double getLatitude() {
            return latitude;
        }

        @SerializedName("latitude")
        public void setLatitude(double value) {
            this.latitude = value;
        }

        @SerializedName("longitude")
        public double getLongitude() {
            return longitude;
        }

        @SerializedName("longitude")
        public void setLongitude(double value) {
            this.longitude = value;
        }

        @SerializedName("metro_code")
        public JSONObject getMetroCode() {
            return metro_code;
        }

        @SerializedName("metro_code")
        public void setMetroCode(JSONObject value) {
            this.metro_code = value;
        }

        @SerializedName("timezone")
        public String getTimezone() {
            return timezone;
        }

        @SerializedName("timezone")
        public void setTimezone(String value) {
            this.timezone = value;
        }

        @SerializedName("datetime")
        public String getDatetime() {
            return datetime;
        }

        @SerializedName("datetime")
        public void setDatetime(String value) {
            this.datetime = value;
        }
    }
}



