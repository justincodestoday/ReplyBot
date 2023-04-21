package com.mandalorian.replybot.ui.presentation.home.viewModel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.ui.presentation.home.HomeFragmentDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val navController: NavController) : BaseViewModel() {
    val refresh: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun navigateToCreateMessage() {
        val action = HomeFragmentDirections.actionHomeFragmentToCreateMessageFragment()
        navController.navigate(action)
    }

    fun onRefresh() {
        viewModelScope.launch {
            refresh.emit(Unit)
        }
    }
}