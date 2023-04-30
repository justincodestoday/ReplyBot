package com.mandalorian.replybot.ui.presentation.authDirectory.register.viewModel

import androidx.lifecycle.viewModelScope
import com.mandalorian.replybot.model.User
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val auth:AuthService):BaseViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
//    val username: MutableStateFlow<String> = MutableStateFlow("")
//    val email: MutableStateFlow<String> = MutableStateFlow("")
//    val password: MutableStateFlow<String> = MutableStateFlow("")
//    val confirmPassword: MutableStateFlow<String> = MutableStateFlow("")

    fun register (
        username: String,
        email: String,
        pass:String,
        conPass: String,
    ){
        if (Utils.validate(username, email, pass ,conPass) && pass == conPass) {
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
                success.emit("Register successful")
                finish.emit(Unit)
            }
        } else {
            viewModelScope.launch {
               error.emit("Please provide all the information")
            }
        }
    }
}