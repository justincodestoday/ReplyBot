package com.mandalorian.replybot.ui.presentation.messageForm.baseMessage.viewModel

import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseMessageViewModel(val repo: MessageRepository) : BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val id: MutableStateFlow<String> = MutableStateFlow("")
    val title: MutableStateFlow<String> = MutableStateFlow("")
    val receipt: MutableStateFlow<String> = MutableStateFlow("")
    val replyMsg: MutableStateFlow<String> = MutableStateFlow("")
    val isActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)
}