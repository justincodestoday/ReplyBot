package com.mandalorian.replybot.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationManagerCompat
import com.mandalorian.replybot.utils.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotificationService: NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)

        val wNotification = NotificationUtils.getWearableNotification(sbn) ?: return
        val title = wNotification.bundle?.getString("android.title") ?: "Empty"
        val msg = wNotification.bundle?.getString("android.text") ?: "Empty"

//        if (!title.contains(Regex("caaron|lo|nathalie|joel|justin|yan|xiang|vikram"))) {
//            RegexOption.IGNORE_CASE
//            return
//        }

        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val bundle = Bundle()

//        val replyText = "Hello, I'm implementing a reply bot"

//        if (msg.contains(Regex("hi|hello")))
//
//            bundle.putCharSequence(wNotification.remoteInputs[0].resultKey, replyText)

        RemoteInput.addResultsToIntent(
            wNotification.remoteInputs.toTypedArray(), intent, bundle
        )

        CoroutineScope(Dispatchers.Main).launch {
            try {
                wNotification.pendingIntent?.let {
                    NotificationManagerCompat.from(this@NotificationService).cancelAll()
                    cancelNotification(sbn?.key)
                    delay(500)
                    it.send(this@NotificationService, 0, intent)
//                if (sbn?.id != null) {
//                    NotificationManagerCompat.from(this).cancel(sbn.id)
//                } else {
//                    cancelNotification(sbn?.key.toString())
//                }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}