package com.devnull.radio.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Replacement for the deprecated {@link android.os.AsyncTask}, keeping the subset of its API
 * used in this app.
 * <p>
 * Like {@code AsyncTask.execute()}, tasks run one at a time on a shared background thread, so
 * the order in which tasks are started is the order in which they run. {@link #onPreExecute()}
 * runs synchronously in {@link #execute()}, {@link #onPostExecute(Object)} runs on the main
 * thread and is skipped if the task was cancelled. An exception thrown by
 * {@link #doInBackground()} of a task that was not cancelled is rethrown on the main thread.
 */
public abstract class BackgroundTask<Result> {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "BackgroundTask");
        thread.setDaemon(true);
        return thread;
    });
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private volatile Future<?> future;

    @MainThread
    protected void onPreExecute() {
    }

    @WorkerThread
    protected abstract Result doInBackground();

    @MainThread
    protected void onPostExecute(Result result) {
    }

    /**
     * Starts the task. Must be called on the main thread and at most once per instance.
     */
    @MainThread
    public final BackgroundTask<Result> execute() {
        if (!started.compareAndSet(false, true)) {
            throw new IllegalStateException("Cannot execute task: the task has already been executed");
        }

        onPreExecute();

        future = EXECUTOR.submit(() -> {
            if (cancelled.get()) {
                return;
            }
            final Result result;
            try {
                result = doInBackground();
            } catch (Throwable t) {
                // Same as AsyncTask: an error in a task that was not cancelled crashes the app
                // instead of being silently swallowed by the Future.
                if (!cancelled.get()) {
                    MAIN_HANDLER.post(() -> {
                        throw new RuntimeException("An error occurred while executing doInBackground()", t);
                    });
                }
                return;
            }
            MAIN_HANDLER.post(() -> {
                if (!cancelled.get()) {
                    onPostExecute(result);
                }
            });
        });
        return this;
    }

    /**
     * Prevents {@link #onPostExecute(Object)} from being called. If the task is running and
     * {@code mayInterruptIfRunning} is true, its thread is interrupted.
     *
     * @return false if the task was already cancelled
     */
    public final boolean cancel(boolean mayInterruptIfRunning) {
        if (!cancelled.compareAndSet(false, true)) {
            return false;
        }
        Future<?> f = future;
        if (f != null) {
            f.cancel(mayInterruptIfRunning);
        }
        return true;
    }

    public final boolean isCancelled() {
        return cancelled.get();
    }
}
