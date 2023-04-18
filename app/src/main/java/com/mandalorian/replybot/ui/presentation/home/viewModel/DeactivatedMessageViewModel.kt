package com.mandalorian.replybot.ui.presentation.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeactivatedMessageViewModel @Inject constructor(private val repo: MessageRepository): BaseViewModel() {
    val messages: MutableLiveData<List<Message>> = MutableLiveData()

    override suspend fun onViewCreated() {
        super.onViewCreated()
        getMessages()
    }

    fun getMessages() {
        viewModelScope.launch {
            val res = safeApiCall { repo.getAllMessages() }
            res?.let {
                messages.value = it.filter { !it.isActivated }
            }
        }
    }
}