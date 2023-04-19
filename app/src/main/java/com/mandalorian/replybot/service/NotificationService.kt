package com.mandalorian.replybot.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.common.collect.ComparisonChain.start
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

    private lateinit var messageRepo: FireStoreMessageRepository
    private lateinit var intent: Intent
    private var msgReceived: String = ""
    private var replyText: String = ""
    private lateinit var title: String
    private lateinit var wNotification: WearableNotification

    override fun onCreate() {
        super.onCreate()
        messageRepo = FireStoreMessageRepository(Firebase.firestore.collection("messages"))
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)

        wNotification = NotificationUtils.getWearableNotification(sbn) ?: return
        title = wNotification.bundle?.getString("android.title") ?: "Empty"
        val msg = wNotification.bundle?.getString("android.text") ?: "Empty"

        Log.d(Constants.DEBUG, "Found a notification")
        Log.d(Constants.DEBUG, title)
        Log.d(Constants.DEBUG, msg)
        Log.d(Constants.DEBUG, checkTitle().toString())

        if (!isRunning) return
        if (!checkTitle()) return

        checkMsg {
            Log.d(Constants.DEBUG, "check msg")
            createIntentBundle()
            wNotificationPendingIntent(sbn)
        }
        
    }

    private fun checkTitle(): Boolean {
        if (title.contains(
                Regex(
                    "0086|6877|caaron|ching|justin|yan|xiang|vikram|khayrul|601606|joel|quan|Joel|nathalie",
                    RegexOption.IGNORE_CASE
                )
            )
        ) {
            return true
        }
        return false
    }

    private fun checkMsg(callback: () -> Unit) {
        msgReceived = wNotification.bundle?.getString("android.text") ?: "Empty"
        val messages = getMessages()
        replyText = "I am a bot"

        for (i in messages) {
            if (msgReceived.contains(Regex(i.receipt, RegexOption.IGNORE_CASE))) {
                replyText = i.replyMsg
                val notifName = wNotification.name
//                if (replyIfAppIsSelected(true, "com.slack", notifName, callback)) break
                if (replyIfAppIsSelected(true, "com.slack", notifName, callback)
//                ||
//                hasAppName(notifName, "com.facebook") ||
//                hasAppName(notifName, "com.messenger")
                ) {
                    callback()
                }
            }
        }
    }

    private fun replyIfAppIsSelected(
        isSelected: Boolean,
        userSelectedApp: String,
        notifName: String,
        callback: () -> Unit
    ): Boolean {
        if (isSelected && hasAppName(notifName, userSelectedApp)) {
            callback()
            return true
        }

        return false
    }

    private fun createIntentBundle() {
        intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val bundle = Bundle()
        bundle.putCharSequence(wNotification.remoteInputs[0].resultKey, replyText)

        RemoteInput.addResultsToIntent(wNotification.remoteInputs.toTypedArray(), intent, bundle)
    }

    private fun wNotificationPendingIntent(
        sbn: StatusBarNotification?
    ) {
        try {
            Log.d(Constants.DEBUG, wNotification.pendingIntent.toString())
            wNotification.pendingIntent?.let {
                CoroutineScope(Dispatchers.Default).launch {
                    isRunning = false
                    cancelNotification(sbn?.key)
                    Log.d(Constants.DEBUG, "Before send")
                    it.send(this@NotificationService, 0, intent)
                    delay(500)
                    isRunning = true
                }
            }
        } catch (e: Exception) {
            isRunning = true
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

    private fun hasAppName(notifName: String, appName: String): Boolean {
        return notifName.contains(Regex(appName, RegexOption.IGNORE_CASE))
    }

    companion object {
        private var isRunning: Boolean = false
        fun start() {
            isRunning = true
        }

        fun stop() {
            isRunning = false
        }
    }
}