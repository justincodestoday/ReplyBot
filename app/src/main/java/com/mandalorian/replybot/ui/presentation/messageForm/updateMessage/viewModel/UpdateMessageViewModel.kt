package com.mandalorian.replybot.ui.presentation.messageForm.updateMessage.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.baseMessage.viewModel.BaseMessageViewModel
import com.mandalorian.replybot.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMessageViewModel @Inject constructor(repo: MessageRepository) :
    BaseMessageViewModel(repo) {
    val message: MutableLiveData<Message> = MutableLiveData()

    fun getMessageById(id: String) {
        viewModelScope.launch {
            val res = repo.getMessageById(id)
            res?.let {
                message.value = it
            }
        }
    }

    fun updateMessage(
        id: String,
        message: Message,
        isActivated: Boolean
    ) {
        val validationStatus = message.let {
            Utils.validate(it.title, it.incomingMsg, it.replyMsg)
        }
        viewModelScope.launch {
            if (validationStatus) {
                safeApiCall { repo.updateMessage(id, message, isActivated) }
                finish.emit(Unit)
            } else {
                error.emit("Please provide the necessary information")
            }
        }
    }

    fun deleteMessage(
        id: String
    ) {
        viewModelScope.launch {
            try {
                safeApiCall { repo.deleteMessage(id) }
                finish.emit(Unit)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}