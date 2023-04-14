package com.mandalorian.replybot.ui.presentation.messageForm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
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
    ) {
        val validationStatus = message.title?.let {
            message.receipt?.let { it1 ->
                message.replyMsg?.let { it2 ->
                    Utils.validate(
                        it,
                        it1,
                        it2
                    )
                }
            }
        }
        viewModelScope.launch {
            if (validationStatus == true) {
                safeApiCall { repo.updateMessage(id, message) }
                finish.emit(Unit)
            } else {
                error.emit("Validation failed")
            }
        }
    }
}