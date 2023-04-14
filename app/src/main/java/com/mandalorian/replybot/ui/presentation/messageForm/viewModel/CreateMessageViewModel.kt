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
        message: Message,
    )
    {
        val validationStatus = message.title?.let {
            message.receipt?.let { it1 ->
                message.replyMsg?.let { it2 ->
                    validate(
                        it,
                        it1,
                        it2
                    )
                }
            }
        }
        viewModelScope.launch {
            if (validationStatus == true) {
                if (message.isActivated) {
                    safeApiCall { repo.addMessage(message, true) }
                    finish.emit(Unit)
                    Log.d("debugging", "Please la pt.2")
                } else {
                    error.emit("Im done")
                }
            }
        }
    }
}