package com.beetech.hsba.ui.chat

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.beetech.hsba.MainActivity
import com.beetech.hsba.R

class UploadFileService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createChannel(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                notificationIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                PendingIntent.getActivity(
                    this, 2007, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }
        val notification: Notification = Notification.Builder(this, "hsba_channel_id")
            .setContentTitle("Beetech")
            .setContentText("HSBA demo service uploading file")
            .setSmallIcon(R.drawable.ic_logo_medda_login)
            .setContentIntent(pendingIntent)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) setForegroundServiceBehavior(
                    Notification.FOREGROUND_SERVICE_IMMEDIATE
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) setOngoing(true)
            }
            .build()


        val finishNotification = Notification.Builder(this, "hsba_channel_id")
            .setContentTitle("Beetech")
            .setContentText("HSBA demo service upload file success")
            .setSmallIcon(R.drawable.ic_logo_medda_login)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Notification ID cannot be 0.
        startForeground(2023, notification)

        Looper.getMainLooper()?.let {
            Handler(it).postDelayed({
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        with(NotificationManagerCompat.from(this)) {
                            notify(1010, finishNotification.build())
                        }
                    }
                } else {
                    with(NotificationManagerCompat.from(this)) {
                        notify(1010, finishNotification.build())
                    }
                }
                stopSelf()
            }, 30_000)
        }

        return START_NOT_STICKY
    }
}