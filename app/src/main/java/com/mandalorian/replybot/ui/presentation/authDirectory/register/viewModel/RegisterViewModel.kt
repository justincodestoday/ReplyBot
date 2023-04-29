package com.mandalorian.replybot.ui.presentation.authDirectory.register.viewModel

import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.User
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.utils.Utils
import com.mandalorian.replybot.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val auth: AuthService) : BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun register(
        username: String,
        email: String,
        pass: String,
        conPass: String,
    ) {

        if (ValidationUtils.validateUsername(username) && ValidationUtils.validateEmail(email) && ValidationUtils.validatePassword(
                pass
            ) && pass == conPass
        ) {
            viewModelScope.launch {
                safeApiCall {
                    auth.createUser(
                        User(
                            username,
                            email,
                            pass,
                        )
                    )
                }
                finish.emit(Unit)
            }
        } else if (!ValidationUtils.validateUsername(username)) {
            viewModelScope.launch {
                error.emit("Invalid username")
            }
        } else if (!ValidationUtils.validateEmail(email)) {
            viewModelScope.launch {
                error.emit("Invalid email")
            }
        } else if (!ValidationUtils.validatePassword(pass)) {
            viewModelScope.launch {
                error.emit("Invalid password")
            }
        } else {
            viewModelScope.launch {
                error.emit("Please provide all the information")
            }
        }
    }
}