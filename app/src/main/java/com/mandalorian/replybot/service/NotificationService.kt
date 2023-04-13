package com.mandalorian.replybot.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationService: NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)
    }
}