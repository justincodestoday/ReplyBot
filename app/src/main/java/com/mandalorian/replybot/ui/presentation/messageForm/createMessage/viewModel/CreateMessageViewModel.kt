package com.mandalorian.replybot.ui.presentation.messageForm.createMessage.viewModel

import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.baseMessage.viewModel.BaseMessageViewModel
import com.mandalorian.replybot.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMessageViewModel @Inject constructor(repo: MessageRepository) :
    BaseMessageViewModel(repo) {

    fun addMessage(
        message: Message,
    ) {
        val validationStatus = message.let {
            Utils.validate(it.title, it.incomingMsg, it.replyMsg)
        }
        viewModelScope.launch {
            if (validationStatus) {
                safeApiCall { repo.addMessage(message) }
                finish.emit(Unit)
            } else {
                error.emit("Please provide the necessary information")
            }
        }
    }
}