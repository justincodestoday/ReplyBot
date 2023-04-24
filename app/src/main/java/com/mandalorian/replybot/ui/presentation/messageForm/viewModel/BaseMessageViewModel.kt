package com.mandalorian.replybot.ui.presentation.messageForm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.utils.Utils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseMessageViewModel(val repo: MessageRepository) : BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val id: MutableStateFlow<String> = MutableStateFlow("")
    val title: MutableStateFlow<String> = MutableStateFlow("")
    val receipt: MutableStateFlow<String> = MutableStateFlow("")
    val replyMsg: MutableStateFlow<String> = MutableStateFlow("")
    val isActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun validate(
        title: String,
        sendMsg: String,
        replyMsg: String,

    ): Boolean {
        if (Utils.validate(
                title, sendMsg, replyMsg
            )
        ) {
            return true
        }
        viewModelScope.launch {
            error.emit("")
        }
        return false
    }
}