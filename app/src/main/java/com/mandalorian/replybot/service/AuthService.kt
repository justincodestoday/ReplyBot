package com.mandalorian.replybot.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.mandalorian.replybot.model.User
import kotlinx.coroutines.tasks.await

class AuthService(private val auth: FirebaseAuth, private val ref: CollectionReference) {

    suspend fun register(user: User) {
        val res = auth.createUserWithEmailAndPassword(user.email, user.password).await()
//        ref.document(res.user!!.uid).set(user).await()

        res.user?.uid?.let {
            ref.document(it).set(user.copy(id = it)).await()
        }
    }

    suspend fun login(email: String, pass: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, pass).await()
        return res.user?.uid != null
    }

    fun isAuthenticate(): Boolean {
        val user = auth.currentUser
        if (user === null) {
            return false
        }
        return true
    }

    fun deAuthenticate() {
        auth.signOut()
    }

    suspend fun getCurrentUser(): User? {
        return auth.currentUser?.email?.let {
            ref.document(it).get().await().toObject(User::class.java)
        }
    }

    fun getUid(): String? {
        return auth.uid
    }
}