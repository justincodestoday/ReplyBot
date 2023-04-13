package com.mandalorian.replybot.repository

import com.mandalorian.replybot.model.Message

interface MessageRepository {
    suspend fun getAllMessages(): List<Message>

    suspend fun getMessageById(id: String): Message?

    suspend fun addMessage(message: Message, isActivated: Boolean)

    suspend fun updateMessage(id: String, message: Message): Message

    suspend fun deleteMessage(id: String)
}