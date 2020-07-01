package com.example.version1.Util;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;



import androidx.core.app.NotificationCompat;

import com.example.version1.Activity.MainActivity;
import com.example.version1.MyApplication;
import com.example.version1.R;
import com.example.version1.manager.ActivityManager;

import java.io.File;

public class DownloadService extends Service {

    private DownloadTask downloadTask;
    private NotificationManager notificationManager;
    private String downloadUrl;
    public static final String TAG= "DownloadService";
    public DownloadListener listener = new DownloadListener() {

        @Override
        public void onProgress(int progress) {
            notificationManager.notify(1, getNotification("正在下载",
                    progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            // 下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            notificationManager.notify(1, getNotification("下载完成",
                    -1));
            Toast.makeText(DownloadService.this, "下载完成",
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFailed() {
            downloadTask = null;
            // 下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            notificationManager.notify(1, getNotification("下载失败",
                    -1));
            Toast.makeText(DownloadService.this, "下载失败",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, "已暂停", Toast.LENGTH_SHORT).
                    show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "已取消", Toast.LENGTH_SHORT).show();
        }

    };



    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

     public class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);

                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("正在下载...", 0));
                Toast.makeText(DownloadService.this, "正在下载...", Toast.
                        LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    // 取消下载时需将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.
                            lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    notificationManager.cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);    //设置通知
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建NotificationChannel
        //在Android8以后需要指定NotificationChannel，immportance选LOW避免前台通知持续弹出
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("channelId","channelName", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("channelId");
        }

        if (progress >= 0) {
            // 当progress大于或等于0时才需显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "5s onDestroy");
        Toast.makeText(this, "this service destroy", Toast.LENGTH_SHORT).show();
        stopForeground(true);
    }

}