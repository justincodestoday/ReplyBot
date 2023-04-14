package com.mandalorian.replybot.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.mandalorian.replybot.R
import com.mandalorian.replybot.ui.MainActivity
import com.mandalorian.replybot.utils.Constants

class MyService: Service() {
    override fun onCreate() {
        super.onCreate()
        startNotificationService()
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopNotificationService()
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_STICKY
    }

    fun startForegroundService() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val notification = NotificationCompat.Builder(this, Constants.NOTIFICATION_ID)
            .setContentTitle("Auto Reply BOT")
            .setContentText("Notification listener is running")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .build()

        startForeground(1, notification)
        startNotificationService()
    }

    fun startNotificationService() {
        Intent(this, NotificationService::class.java).also {
            it.action = "android.service.notification.NotificationListenerService"
            startService(it)
        }
    }

    fun stopNotificationService() {
        Intent(this, NotificationService::class.java).also {
            stopService(it)
        }
    }
}