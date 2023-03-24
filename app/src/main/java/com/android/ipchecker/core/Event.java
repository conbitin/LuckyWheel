package com.android.ipchecker.core;

/*
 * Created by huongnd2 on 9/11/21 10:07 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 8/19/21 9:05 AM
 */

import android.os.Bundle;

public class Event {
    int eventCode;
    Bundle data;

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    private Event(int eventCode) {
        this.eventCode = eventCode;
    }

    private Event(int eventCode, Bundle data) {
        this.eventCode = eventCode;
        this.data = data;
    }


    public static Event of(int eventCode) {
        return new Event(eventCode);
    }

    public static Event of(int eventCode, Bundle data) {
        return new Event(eventCode, data);
    }


}
