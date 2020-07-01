package com.example.version1.Util;

public interface DownloadListener {

    void onProgress(int progress);
    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();

}