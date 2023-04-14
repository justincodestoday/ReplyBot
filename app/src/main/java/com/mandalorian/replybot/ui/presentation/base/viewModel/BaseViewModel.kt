package com.mandalorian.replybot.ui.presentation.base.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandalorian.replybot.model.Message
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel: ViewModel() {
    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    open suspend fun onViewCreated() {}

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            error.emit(e.message.toString())
            e.printStackTrace()
            null
        }
    }
}