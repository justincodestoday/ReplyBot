package com.example.firebasechatapp.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.authDirectory.viewModel.loginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.mockito.Mockito


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

//    private lateinit var useCase: LoginUseCase

    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: loginViewModel
    private val authRepo = Mockito.mock(AuthService::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
//        loginViewModel = LoginViewModel(useCase)
    }

    @Test
    fun `user should be able to login with correct credentials`() = runTest {
        Mockito.`when`(authRepo.login("abc@abc.com", "qwerty")).thenReturn(true)
        loginViewModel.email.value = "abc@abc.com"
        loginViewModel.password.value = "qweqweqwe"
//        loginViewModel.login()
        assertEquals(loginViewModel.loginFinish.first(), Unit)
    }

    @Test
    fun `user should not be able to login with wrong credentials`() = runTest {
        Mockito.`when`(authRepo.login("abc@abc.com", "qwerty")).thenReturn(false)
        loginViewModel.email.value = "abc@abc.com"
        loginViewModel.password.value = "qweqweqwe"
//        loginViewModel.login()

        assertEquals(loginViewModel.error.first(),Any())
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}