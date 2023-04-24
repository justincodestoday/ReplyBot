package com.mandalorian.replybot.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mandalorian.replybot.repository.FireStoreMessageRepository
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyAppDependency {

    @Provides
    @Singleton
    fun getFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun getFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun getAuthRepository(auth: FirebaseAuth, db: FirebaseFirestore): AuthService {
        return AuthService(auth, db.collection("users"))
    }

    @Provides
    @Singleton
    fun getFireStoreMessageRepository(db: FirebaseFirestore): MessageRepository {
        return FireStoreMessageRepository(db.collection("messages"))
    }
}