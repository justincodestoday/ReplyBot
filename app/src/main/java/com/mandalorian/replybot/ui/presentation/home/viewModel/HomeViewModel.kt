package com.mandalorian.replybot.ui.presentation.home.viewModel

import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    val toCreateFragment: MutableSharedFlow<Unit> = MutableSharedFlow()
    val refresh: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun navigateToCreate() {
        viewModelScope.launch {
            toCreateFragment.emit(Unit)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            refresh.emit(Unit)
        }
    }
}