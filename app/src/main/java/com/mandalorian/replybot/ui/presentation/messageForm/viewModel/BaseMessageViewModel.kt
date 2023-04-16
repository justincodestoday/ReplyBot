package com.mandalorian.replybot.ui.presentation.messageForm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.utils.Utils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseMessageViewModel(val repo: MessageRepository) : BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

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