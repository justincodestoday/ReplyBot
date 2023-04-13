package com.mandalorian.replybot.ui.presentation.authDirectory.viewModel

import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class loginViewModel @Inject constructor(private val auth: AuthService):BaseViewModel() {
    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            val res = safeApiCall { auth.login(email, pass) }
            if (res != null) {
                loginFinish.emit(Unit)
            } else {
                error.emit("Login failed")
            }
        }
    }
}