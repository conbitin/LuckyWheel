/*
 * Created by huongnd2 on 9/11/21 10:07 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 8/19/21 9:05 AM
 */

package com.android.ipchecker.core;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutors {

    // Number of cores to decide the number of threads
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_POOL_SIZE_NETWORK = 3;

    private static final ThreadExecutors ourInstance = new ThreadExecutors();

    public static ThreadExecutors getInstance() {
        return ourInstance;
    }

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;
    private Executor workerThread;

    private ThreadExecutors() {
        diskIO = Executors.newSingleThreadExecutor();
        networkIO = Executors.newFixedThreadPool(THREAD_POOL_SIZE_NETWORK);
        mainThread = new MainThreadExecutor();
        workerThread = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getWorkerThread() {
        return workerThread;
    }

    public static void execute(Where where, Runnable command) {
        switch (where) {
            case DISK:
                getInstance().getDiskIO().execute(command);
                break;
            case NETWORK:
                getInstance().getNetworkIO().execute(command);
                break;
            case WORKER:
                getInstance().getWorkerThread().execute(command);
                break;
            case MAIN:
                getInstance().getMainThread().execute(command);
                break;
        }
    }

    public enum Where {
        DISK, NETWORK, WORKER, MAIN
    }

    private class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
