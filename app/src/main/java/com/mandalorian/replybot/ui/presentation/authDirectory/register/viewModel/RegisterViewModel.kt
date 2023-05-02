package com.mandalorian.replybot.ui.presentation.authDirectory.register.viewModel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.User
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.utils.Constants
import com.mandalorian.replybot.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val auth: AuthService) : BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val formErrors = ObservableArrayList<String>()
    val username: MutableStateFlow<String> = MutableStateFlow("")
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")
    val confirmPassword: MutableStateFlow<String> = MutableStateFlow("")

//    fun register() {
//        if (ValidationUtils.validateUsername(username.value) &&
//            ValidationUtils.validateEmail(email.value) &&
//            ValidationUtils.validatePassword(password.value) &&
//            password == confirmPassword
//        ) {
//            viewModelScope.launch {
//                safeApiCall { auth.createUser(User(username.value, email.value, password.value)) }
//                success.emit("Register successful")
//                finish.emit(Unit)
//            }
//        } else if (!ValidationUtils.validateUsername(username.value)) {
//            viewModelScope.launch {
//                error.emit("Invalid username")
//            }
//        } else if (!ValidationUtils.validateEmail(email.value)) {
//            viewModelScope.launch {
//                error.emit("Invalid email")
//            }
//        } else if (!ValidationUtils.validatePassword(password.value)) {
//            viewModelScope.launch {
//                error.emit("Invalid password")
//            }
//        } else if (confirmPassword.value != password.value) {
//            viewModelScope.launch {
//                error.emit("Passwords do not match")
//            }
//        } else {
//            viewModelScope.launch {
//                error.emit("Please provide all the information")
//            }
//        }
//    }

    fun register() {
        viewModelScope.launch {
            if (isFormValid()) {
                safeApiCall {
                    auth.register(
                        User(
                            username = username.value,
                            email = email.value,
                            password = password.value
                        )
                    )
                }
                Log.d(Constants.DEBUG, "registering")
                success.emit("Register successful")
                finish.emit(Unit)
            } else {
                error.emit("Please provide the proper information")
            }
        }
    }

    fun isFormValid(): Boolean {
        formErrors.clear()
        if (!ValidationUtils.validateUsername(username.value)) {
            formErrors.add("Invalid username")
        } else if (!ValidationUtils.validateEmail(email.value)) {
            formErrors.add("Invalid email")
        } else if (!ValidationUtils.validatePassword(password.value)) {
            formErrors.add("Invalid password")
        } else if (confirmPassword.value != password.value) {
            formErrors.add("Passwords do not match")
        }
        return formErrors.isEmpty()
    }
}