package com.mandalorian.replybot.ui.presentation.authDirectory.login.viewModel

import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: AuthService) : BaseViewModel() {
    val loginFinish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun login() {
        viewModelScope.launch {
            val res = safeApiCall { auth.login(email.value, password.value) }
            if (res != null) {
                loginFinish.emit(Unit)
            } else {
                error.emit("Login failed")
            }
        }
    }
}