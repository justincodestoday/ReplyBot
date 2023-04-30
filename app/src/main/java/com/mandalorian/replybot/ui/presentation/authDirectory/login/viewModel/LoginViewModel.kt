package com.mandalorian.replybot.ui.presentation.authDirectory.login.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: AuthService) : BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<String>()
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun login() {
        viewModelScope.launch {
            if (isFormValid()) {
                val res = safeApiCall { auth.login(email.value, password.value) }
                if (res != null) {
                    success.emit("Login successful")
                    finish.emit(Unit)
                } else {
                    error.emit("Wrong credentials")
                }
            }
        }
    }

    private fun isFormValid(): Boolean {
        formErrors.clear()
        if (!ValidationUtils.validateEmail(email.value)) {
            formErrors.add("Invalid email")
        } else if (password.value.trim { it <= ' ' }.isEmpty()) {
            formErrors.add("Missing password")
        }
        return formErrors.isEmpty()
    }
}