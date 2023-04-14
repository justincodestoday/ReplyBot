package com.mandalorian.replybot.repository

import com.google.firebase.firestore.CollectionReference
import com.mandalorian.replybot.model.Message
import kotlinx.coroutines.tasks.await

class FireStoreMessageRepository(private val ref: CollectionReference): MessageRepository {
    override suspend fun getAllMessages(): List<Message> {
        return ref.get().await().toObjects(Message::class.java)
    }

    override suspend fun getMessageById(id: String): Message? {
        val res = ref.document(id).get().await()
        return res.toObject(Message::class.java)?.copy(id = id)
    }

    override suspend fun addMessage(message: Message, isActivated: Boolean) {
        val doc = ref.document() // Create a new document with a random ID
        val id = doc.id // Get the ID of the new document
        val updatedMessage = message.copy(id = id) // Set the ID and createdAt fields of the product
        doc.set(updatedMessage).await()
    }

    override suspend fun updateMessage(id: String, message: Message): Message {
        val doc = ref.document(id)
        val updatedMessage = message.copy(id = id)
        doc.set(updatedMessage).await()
        return updatedMessage
    }

    override suspend fun deleteMessage(id: String) {
        ref.document(id).delete().await()
    }
}