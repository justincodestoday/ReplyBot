package com.mandalorian.replybot.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.service.notification.StatusBarNotification
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mandalorian.replybot.R
import com.mandalorian.replybot.model.WearableNotification
import com.mandalorian.replybot.utils.Constants.NOTIFICATION_ID
import com.mandalorian.replybot.utils.Constants.NOTIFICATION_NAME

object NotificationUtils {
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

//    fun createNotification(context: Context, title: String, msg: String) {
//        val notification = getNotificationBuilder(context, title, msg)
//            .build()
//        if (ActivityCompat.checkSelfPermission
//                (context, Manifest.permission.POST_NOTIFICATIONS)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        NotificationManagerCompat.from(context).notify(0, notification)
//    }

//    private fun getNotificationBuilder(context: Context, title: String, msg: String) =
//        NotificationCompat.Builder(context, NOTIFICATION_ID)
//            .setContentTitle(title)
//            .setContentText(msg)
////            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setSmallIcon(R.drawable.ic_notifications)

    fun getWearableNotification(sbn: StatusBarNotification?): WearableNotification? {
        return sbn?.notification?.let { notification ->
            val actions = notification.actions ?: return null
            val remoteInputs = ArrayList<android.app.RemoteInput>(actions.size)
            var pendingIntent: PendingIntent? = null

            for(action in actions) {
                action?.remoteInputs?.let { remoteInps ->
                    remoteInps.forEach {
                        remoteInputs.add(it as android.app.RemoteInput)
                        pendingIntent = action.actionIntent
                    }
                }
            }

            WearableNotification(
                sbn.tag,
                sbn.packageName,
                pendingIntent,
                remoteInputs,
                notification.extras
            )
        }
    }
}