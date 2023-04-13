package com.mandalorian.replybot.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService: Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopNotificationService()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    fun startNotificationService() {
        Intent(this, NotificationService::class.java).also {
            it.action = "android.service.notification.NotificationListenerService"
            startService(it)
        }
    }

    fun stopNotificationService() {
        val intent = Intent(this, NotificationService::class.java).also {
            stopService(it)
        }
    }
}