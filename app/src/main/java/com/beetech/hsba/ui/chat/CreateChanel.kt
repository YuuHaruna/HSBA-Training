package com.beetech.hsba.ui.chat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun createChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            "hsba_channel_id",
            "HSBA Notification chanel",
            importance
        ).apply {
            description = "dong mo ta nay that vo dung"
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(listOf(channel))
}