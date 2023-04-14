package com.mandalorian.replybot.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.model.WearableNotification
import com.mandalorian.replybot.repository.FireStoreMessageRepository
import com.mandalorian.replybot.utils.NotificationUtils
import kotlinx.coroutines.*
import javax.inject.Inject

class NotificationService: NotificationListenerService() {
    @Inject
    lateinit var messageRepo: FireStoreMessageRepository
    private var msgReceived: String = ""
    private var replyText: String = ""

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
    }

    private fun checkMsg(wNotification: WearableNotification, callback: () -> Unit) {
        msgReceived = wNotification.bundle?.getString("android.text") ?: "Empty"

//        wNotification.bundle?.keySet()?.forEach {
//            Log.d(DEBUG, "keySet: $it \n content: ${wNotification.bundle?.getString(it)}")
//        }
//        Log.d(DEBUG, wNotification.bundle?.keySet().toString())
//        Log.d(DEBUG, "Message: $msg")
        val messages = getMessages()

        for (i in messages) {
            if (msgReceived.contains(Regex(i.receipt, RegexOption.IGNORE_CASE))) {
                replyText = i.replyMsg
            } else {
                return
            }

//            val notifName = wNotification.name
//            if ((i.whatsapp || i.facebook || i.slack) && (hasAppName(
//                    notifName,
//                    "com.whatsapp"
//                ) || hasAppName(notifName, "com.facebook"))
//            ) {
//                callback()
//            }
        }
        callback()
    }

    private fun wNotificationPendingIntent(sbn: StatusBarNotification?, wNotification: WearableNotification, intent: Intent) {
        try {
            wNotification.pendingIntent?.let {
                CoroutineScope(Dispatchers.Default).launch {
//                    isRunning = false
                    cancelNotification(sbn?.key)

                    it.send(this@NotificationService, 0, intent)
                    delay(500)
//                    isRunning = true
                }
            }
        } catch (e: Exception) {
//            isRunning = true
            e.printStackTrace()
        }
    }

    private fun getMessages(): MutableList<Message> {
        val messages: MutableList<Message> = mutableListOf()

        val job = CoroutineScope(Dispatchers.Default).launch {
            val res = messageRepo.getAllMessages()
            res.let {
                it.forEach { message ->
                    messages.add(message)
                }
            }
        }
        runBlocking {
            job.join()
        }
        return messages
    }
}