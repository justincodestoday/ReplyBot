package com.mandalorian.replybot.ui.presentation.messageForm.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMessageViewModel @Inject constructor(repo: MessageRepository) :
    BaseMessageViewModel(repo) {
    fun addMessage(
        message: Message
    ) {
        val validationStatus = validate(
            message.title,
            message.sendMsg,
            message.replyMsg
        )
        viewModelScope.launch {
            if (validationStatus) {
                safeApiCall { repo.addMessage(message) }
                finish.emit(Unit)
                Log.d("debugging", "Please la pt.2")
            } else {
                error.emit("Im done")
            }
        }
    }
}