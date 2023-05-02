package com.mandalorian.replybot.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.model.WearableNotification
import com.mandalorian.replybot.repository.FireStoreMessageRepository
import com.mandalorian.replybot.utils.Constants
import com.mandalorian.replybot.utils.NotificationUtils
import kotlinx.coroutines.*
import javax.inject.Inject

class NotificationService : NotificationListenerService() {
    @Inject
    lateinit var messageRepo: FireStoreMessageRepository
    lateinit var intent: Intent
    private var msgReceived: String = ""
    private var replyText: String = ""

    override fun onCreate() {
        super.onCreate()
        start()
        messageRepo = FireStoreMessageRepository(Firebase.firestore.collection("messages"))
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)

        if (!isRunning) return

        val wNotification = NotificationUtils.getWearableNotification(sbn) ?: return
        Log.d(Constants.DEBUG, wNotification.name) // package name like Facebook

        checkMsg(sbn, wNotification) {
            createIntentBundle(wNotification)
            wNotificationPendingIntent(sbn, wNotification)
        }
    }

    private fun createIntentBundle(wNotification: WearableNotification) {
        intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val bundle = Bundle()
        bundle.putCharSequence(wNotification.remoteInputs[0].resultKey, replyText)

        RemoteInput.addResultsToIntent(wNotification.remoteInputs.toTypedArray(), intent, bundle)
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

    private fun checkMsg(
        sbn: StatusBarNotification?,
        wNotification: WearableNotification,
        callback: () -> Unit
    ) {
        msgReceived = wNotification.bundle?.getString("android.text") ?: "Empty"
        val messages = getMessages()
        Log.d(Constants.DEBUG, messages.toString())
        for (i in messages) {
            if (i.isActivated && msgReceived.contains(Regex(i.incomingMsg, RegexOption.IGNORE_CASE))) {
                Log.d(Constants.DEBUG, "$i")
                replyText = i.replyMsg
                cancelNotification(sbn?.key)
            } else {
                continue
            }

            val notifName = wNotification.name
            if (hasAppName(notifName, "com.facebook.orca") || hasAppName(
                    notifName,
                    "com.whatsapp"
                )
            ) {
                callback()
            }
        }
//        if (!messageFound) return
//        callback()
    }

    private fun wNotificationPendingIntent(
        sbn: StatusBarNotification?,
        wNotification: WearableNotification
    ) {
        wNotification.pendingIntent?.let {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    Log.d(Constants.DEBUG, "what is the intent?")
                    isRunning = false
                    cancelNotification(sbn?.key)

                    it.send(this@NotificationService, 0, intent)
                    delay(1000)
                    isRunning = true
                } catch (e: Exception) {
                    isRunning = true
                    e.printStackTrace()
                }
            }
        }
    }

    private fun hasAppName(notifName: String, appName: String): Boolean {
        return notifName.contains(Regex(appName, RegexOption.IGNORE_CASE))
    }

    // static object, no object is needed to access the properties inside companion object
    companion object {
        private var isRunning = false
        fun start() {
            isRunning = true
        }

        fun stop() {
            isRunning = false
        }
    }
}