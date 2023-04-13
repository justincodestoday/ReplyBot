package com.mandalorian.replybot.ui.presentation.messageForm.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMessageViewModel @Inject constructor(repo: MessageRepository) : BaseMessageViewModel(repo) {
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
    ) {
        val validationStatus = Utils.validate(
            message.title,
            message.sendMsg,
            message.replyMsg
        )
        viewModelScope.launch {
            if(validationStatus) {
                safeApiCall {  repo.updateMessage(id, message) }
                finish.emit(Unit)
            } else {
                error.emit("Validation failed")
            }
        }
    }
}